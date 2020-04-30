package com.xfyyim.cn.ui.me_new;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

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
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventNotifyUpdate;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.map.MapPickerAddressActivity;
import com.xfyyim.cn.ui.me.NewSettingsActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MyVipPaymentPopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

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
        EventBusHelper.register(this);
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(!coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")){
                    //todo -1没有会员
                    Intent intent = new Intent(CurrentLocationActivity.this, MapPickerAddressActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_LOCATE);
                }else {
                    BuyMember(coreManager.getSelf().getUserVIPPrivilegeConfig());
                }


            }
        });


    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember(UserVIPPrivilegePrice userVIPPrivilegePrice) {
        //显示VIP购买会员
        MyVipPaymentPopupWindow myVipPaymentPopupWindow = new MyVipPaymentPopupWindow(CurrentLocationActivity.this, userVIPPrivilegePrice, coreManager.getSelf().getUserId(), coreManager.getSelf().getNickName(), coreManager.getSelf().getUserVIPPrivilege().getVipLevel());
        //谁喜欢我，在线聊天  购买
        //   MyPrivilegePopupWindow  my=new MyPrivilegePopupWindow(getActivity());
        LogUtil.e("BuyMember  BuyMember");
        myVipPaymentPopupWindow.setBtnOnClice(new MyVipPaymentPopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {
                submitPay(type, vip);
                LogUtil.e("*********************************type = " + type + "-------------vip = " + vip);
            }
        });
    }

    /**
     * 转支付类型返回支付签名数据
     *
     * @param type
     * @param vip
     */
    private void submitPay(String type, int vip) {
        UserVIPPrivilegePrice vipPrivilegePriceList = coreManager.getSelf().getUserVIPPrivilegeConfig();
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("payType", type);
        if(vip==1){
            params.put("price",vipPrivilegePriceList.getV0Price()+"");
            params.put("level", vipPrivilegePriceList.getV0());
        }else if(vip==2){
            params.put("price",vipPrivilegePriceList.getV1Price()+"");
            params.put("level", vipPrivilegePriceList.getV1());
        }else if(vip==3){
            params.put("price", vipPrivilegePriceList.getV2Price()+"");
            params.put("level", vipPrivilegePriceList.getV2());
        }else if(vip==4){
            params.put("price", vipPrivilegePriceList.getV3Price()+"");
            params.put("level", vipPrivilegePriceList.getV3());
        }
        params.put("funType", String.valueOf(5));
        params.put("num", String.valueOf(-1));
        params.put("mon", String.valueOf(-1));
        AlipayHelper.rechargePay(CurrentLocationActivity.this, coreManager,params);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功刷新用户页面
        updateSelfData();
    }
    private void updateSelfData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User     user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

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
