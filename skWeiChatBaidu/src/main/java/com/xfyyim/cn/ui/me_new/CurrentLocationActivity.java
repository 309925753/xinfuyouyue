package com.xfyyim.cn.ui.me_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.bean.event.EventNotifyUpdate;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.map.MapPickerAddressActivity;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class CurrentLocationActivity extends BaseActivity {
    private static final int REQUEST_CODE_SELECT_LOCATE = 10;  // 位置

    Unbinder unbinder;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
    @BindView(R.id.tv_city_name)
    TextView tv_city_name;
    @BindView(R.id.tv_address_detail)
    TextView tv_address_detail;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.check_box1)
    CheckBox check_box1;
    @BindView(R.id.tv_city_newname)
    TextView tv_city_newname;
    @BindView(R.id.tv_address_newdetail)
    TextView tv_address_newdetail;


    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.iv_title_right)
    ImageView iv_title_right;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;


    @BindView(R.id.rl_newaddress)
    RelativeLayout rl_newaddress;
    @BindView(R.id.rl_current_location)
    RelativeLayout rl_current_location;
    long lat;
    long lon;
    String city;
    String address;


    boolean isSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentlocation);
        unbinder = ButterKnife.bind(this);
        initActionBar();
        initDate();
    }


    public void initDate() {
        isSelect = PreferenceUtils.getBoolean(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.ISSELECT, false);

        tv_city_name.setText(MyApplication.getInstance().getBdLocationHelper().getCityName());
        tv_address_detail.setText(MyApplication.getInstance().getBdLocationHelper().getAddress());

        city = PreferenceUtils.getString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.CITY);
        address = PreferenceUtils.getString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.Address);

        if (address == null || TextUtils.isEmpty(address)) {
            rl_newaddress.setVisibility(View.GONE);
        } else {
            rl_newaddress.setVisibility(View.VISIBLE);
            tv_city_newname.setText(city);
            tv_address_newdetail.setText(address);
        }

        checkStates(isSelect);
        rl_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = false;

                checkStates(isSelect);
            }
        });

        rl_newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = true;
                checkStates(isSelect);
                EventBus.getDefault().post(new EventNotifyUpdate("Update"));

            }
        });


        tv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentLocationActivity.this, MapPickerAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_LOCATE);
            }
        });


    }

    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_center.setText("位置");
        iv_title_right.setVisibility(View.GONE);

    }

    private void checkStates(boolean isSelect) {
        PreferenceUtils.putBoolean(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.ISSELECT, isSelect);

        if (!isSelect) {
            checkBox.setChecked(true);
            check_box1.setChecked(false);
        } else {
            checkBox.setChecked(false);
            check_box1.setChecked(true);
        }
    }

    GeoCoder geo;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SELECT_LOCATE) {
            // 选择位置返回
            double latitude = data.getDoubleExtra(AppConstant.EXTRA_LATITUDE, 0);
            double longitude = data.getDoubleExtra(AppConstant.EXTRA_LONGITUDE, 0);
            LatLng latLng1 = new LatLng(latitude, longitude);
            String address = data.getStringExtra(AppConstant.EXTRA_ADDRESS);


            if (latitude != 0 && longitude != 0 && !TextUtils.isEmpty(address)) {
                Log.e("zq", "纬度:" + latitude + "   经度：" + longitude + "   位置：" + address);
                rl_newaddress.setVisibility(View.VISIBLE);
                PreferenceUtils.putString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.Address, address);
                PreferenceUtils.putString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.LAT, String.valueOf(latitude));
                PreferenceUtils.putString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.LON, String.valueOf(longitude));

                tv_address_newdetail.setText(address);
                geo = GeoCoder.newInstance();
                geo.setOnGetGeoCodeResultListener(listener);
                geo.reverseGeoCode(new ReverseGeoCodeOption().location(latLng1));

            } else {
                ToastUtil.showToast(mContext, getString(R.string.loc_startlocnotice));
            }
        }


    }


    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    return;
                } else {
                    double latitude = geoCodeResult.getLocation().latitude;
                    double longitude = geoCodeResult.getLocation().longitude;
                }
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult.getAddressDetail().city != null || TextUtils.isEmpty(reverseGeoCodeResult.getAddressDetail().city)) {
                PreferenceUtils.putString(CurrentLocationActivity.this, coreManager.getSelf().getUserId() + SpContext.CITY, reverseGeoCodeResult.getAddressDetail().city);
                tv_city_newname.setText(reverseGeoCodeResult.getAddressDetail().city);

            }


        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {

            unbinder.unbind();
        }
    }
}
