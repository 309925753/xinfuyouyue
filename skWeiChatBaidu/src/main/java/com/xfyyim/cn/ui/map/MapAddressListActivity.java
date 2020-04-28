package com.xfyyim.cn.ui.map;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.BuildConfig;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.NearPositionAdapter;
import com.xfyyim.cn.map.MapHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.tool.ButtonColorChange;
import com.xfyyim.cn.util.PermissionUtil;
import com.xfyyim.cn.util.ScreenUtil;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/20.
 */
public class MapAddressListActivity extends BaseActivity {
    private LinearLayout ll_map;
    private FrameLayout container;
    private TextView tvTitleRight;
    private ImageView ivReturn;
    private MapHelper mapHelper;
    private MapHelper.Picker picker;
    private MapHelper.LatLng beginLatLng;
    private MapHelper.LatLng currentLatLng;
    private ClearEditText ce_map_position;
    private RecyclerView rv_map_position;
    private NearPositionAdapter nearPositionAdapter;
    private List<MapHelper.Place> placesSeach = new ArrayList<>();
    private List<MapHelper.Place> seachPlace = new ArrayList<>();
    private Map<String, MapHelper.Place> placeMap = new HashMap<>();
    private boolean showTitle = true;

    MapHelper.LatLng lan = null;
    GeoCoder gc;


    private NearPositionAdapter.OnRecyclerItemClickListener itemClickListener = new NearPositionAdapter.OnRecyclerItemClickListener() {
        @Override
        public void onItemClick(int Position, MapHelper.Place dataBean) {
            placeMap.clear();
            placeMap.put("place", dataBean);
            picker.moveMap(dataBean.getLatLng());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.requestLocationPermissions(this, 0x01);
        setContentView(R.layout.activity_maplist_picker);
        initActionBar();
        initView();
        initMap();
        initEvent();
        if (BuildConfig.DEBUG) {
            com.xfyyim.cn.util.LogUtils.log("after create");
        }
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        findViewById(R.id.iv_title_left).setOnClickListener(v -> finish());
        TextView tvTitle = findViewById(R.id.tv_title_center);
        tvTitle.setText(getString(R.string.location));
        tvTitleRight = findViewById(R.id.tv_title_right);
        tvTitleRight.setText( getResources().getString(R.string.sure));
        tvTitleRight.setBackground(mContext.getResources().getDrawable(R.drawable.bg_btn_grey_circle));
        ButtonColorChange.colorChange(mContext, tvTitleRight);
        tvTitleRight.setTextColor(getResources().getColor(R.color.white));

        tvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapHelper.Place place = placeMap.get("place");
                if (place == null) {
                    if (placesSeach.size() > 0) {// 默认选中第一个
                        place = placesSeach.get(0);
                    }
                }
                String address;
                if (place != null) {
                    address = place.getName();
                } else {
                    address = "";
                }

                Intent intent = new Intent();
                intent.putExtra(AppConstant.EXTRA_LATITUDE, currentLatLng.getLatitude());
                intent.putExtra(AppConstant.EXTRA_LONGITUDE, currentLatLng.getLongitude());
                intent.putExtra(AppConstant.EXTRA_ADDRESS, address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
        public void initView () {
            ll_map = findViewById(R.id.ll_map);
            ivReturn = findViewById(R.id.iv_location);
            ce_map_position = findViewById(R.id.ce_map_position);
            ce_map_position.clearFocus();
            rv_map_position = findViewById(R.id.rv_map_position);
            nearPositionAdapter = new NearPositionAdapter(this);
            nearPositionAdapter.setRecyclerItemClickListener(itemClickListener);
        }

    private void initMap() {
        mapHelper = MapHelper.getInstance();
        picker = mapHelper.getPicker(this);
        getLifecycle().addObserver(picker);
        container = findViewById(R.id.map_view_container);
        picker.attack(container, () -> {
            // 初始化底部周边相关动画，
            // 中心打上图标，
            picker.addCenterMarker(R.drawable.ic_position, "pos");
            mapHelper.requestLatLng(latLng -> {
                // 记录开始时定位的位置，用来点击按钮跳回来，
                beginLatLng = latLng;
                picker.moveMap(latLng);
                // 加载周边位置信息，
                // 记录当前位置也在这个方法里，
                loadMapDatas(latLng);
            }, t -> {
                ToastUtil.showToast(MapAddressListActivity.this, getString(R.string.tip_auto_location_failed) + t.getMessage());
                // 总有个默认的经纬度，拿出来，
                beginLatLng = picker.currentLatLng();
                picker.moveMap(beginLatLng);
                loadMapDatas(beginLatLng);
            });
        });
    }

    private void initEvent() {


        ce_map_position.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                seachPlace.clear();
                if (TextUtils.isEmpty(s.toString())) {

                    loadMapDatas(currentLatLng);
                } else {
                    getGeoPointBystr(s.toString());
                }

            }
        });

        findViewById(R.id.rl_map_position).setOnClickListener(v -> {
            // 点击空白区域隐藏软键盘
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(findViewById(R.id.rl_map_position).getWindowToken(), 0); //强制隐藏键盘
            }
        });


    }


    public void getGeoPointBystr(String str) {


        gc = GeoCoder.newInstance();
        gc.setOnGetGeoCodeResultListener(listener);
        gc.geocode(new GeoCodeOption()
                .city("上海")
                .address(str));
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
                    lan = new MapHelper.LatLng(latitude, longitude);
                    loadMapDatas(lan);
                }
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

        }
    };

    private void loadMapDatas(MapHelper.LatLng latLng) {
        currentLatLng = latLng;
        tvTitleRight.setVisibility(View.VISIBLE);
        ivReturn.setVisibility(View.VISIBLE);

        mapHelper.requestPlaceList(latLng, (List<MapHelper.Place> places) -> {
            nearPositionAdapter.setData(places);
            placesSeach.clear();
            placesSeach.addAll(places);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MapAddressListActivity.this);
            rv_map_position.setLayoutManager(layoutManager);
            rv_map_position.setAdapter(nearPositionAdapter);
        }, t -> ToastUtil.showToast(MapAddressListActivity.this, getString(R.string.tip_places_around_failed) + t.getMessage()));
    }


    public void cancelKeyBoard(View view) {
        // 点击空白区域隐藏软键盘
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(findViewById(R.id.tv_keyboard).getWindowToken(), 0); //强制隐藏键盘
        }
    }
}
