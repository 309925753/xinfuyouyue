package com.xfyyim.cn.ui.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventNotifyUpdate;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.lock.DeviceLockHelper;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me_new.CurrentLocationActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.Md5Util;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.MyVipPaymentPopupWindow;
import com.xfyyim.cn.view.SelectionFrame;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SwitchButton;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xfyyim.cn.view.window.WindowShowService;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;


public class NewSettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    SkinImageView ivTitleLeft;
    @BindView(R.id.tv_title_left)
    SkinTextView tvTitleLeft;
    @BindView(R.id.pb_title_center)
    ProgressBar pbTitleCenter;
    @BindView(R.id.tv_title_center)
    SkinTextView tvTitleCenter;
    @BindView(R.id.iv_title_center)
    ImageView ivTitleCenter;
    @BindView(R.id.iv_title_right)
    SkinImageView ivTitleRight;
    @BindView(R.id.iv_title_right_right)
    SkinImageView ivTitleRightRight;
    @BindView(R.id.tv_title_right)
    SkinTextView tvTitleRight;
    @BindView(R.id.mergerStatus)
    MergerStatus mergerStatus;
    @BindView(R.id.tvCurrentLocation)
    TextView tvCurrentLocation;
    @BindView(R.id.tvSetCity)
    TextView tvSetCity;
    @BindView(R.id.rlCurrentLocation)
    RelativeLayout rlCurrentLocation;
    @BindView(R.id.tvCurrentDistance)
    TextView tvCurrentDistance;
    @BindView(R.id.rlCurrentDistance)
    RelativeLayout rlCurrentDistance;
    @BindView(R.id.ivBicycle)
    ImageView ivBicycle;
    @BindView(R.id.ivPlane)
    ImageView ivPlane;
    @BindView(R.id.sbDistance)
    RangeSeekBar sbDistance;
    @BindView(R.id.tvRange)
    TextView tvRange;
    @BindView(R.id.sbBExpandScope)
    SwitchButton sbBExpandScope;
    @BindView(R.id.tvCurrentSex)
    TextView tvCurrentSex;

    @BindView(R.id.tvCurrentMinAge)
    TextView tvCurrentMinAge;
    @BindView(R.id.tvCurrentMaxMinAge)
    TextView tvCurrentMaxMinAge;
    @BindView(R.id.ivSetMin)
    ImageView ivSetMin;
    @BindView(R.id.seekbar)
    RangeSeekBar seekbar;
    @BindView(R.id.ivSetMax)
    ImageView ivSetMax;
    RelativeLayout rlPersonal;
    @BindView(R.id.ivPrivacy)
    ImageView ivPrivacy;
    @BindView(R.id.tvSetPrivacy)
    TextView tvSetPrivacy;
    @BindView(R.id.rlPrivacy)
    RelativeLayout rlPrivacy;
    @BindView(R.id.ivNews)
    ImageView ivNews;
    @BindView(R.id.tvSetNews)
    TextView tvSetNews;
    @BindView(R.id.rlNews)
    RelativeLayout rlNews;
    @BindView(R.id.ivCache)
    ImageView ivCache;
    @BindView(R.id.tvSetCache)
    TextView tvSetCache;
    @BindView(R.id.rlCache)
    RelativeLayout rlCache;
    @BindView(R.id.ivSafe)
    ImageView ivSafe;
    @BindView(R.id.tvSetSafe)
    TextView tvSetSafe;
    @BindView(R.id.rlSafe)
    RelativeLayout rlSafe;
    @BindView(R.id.ivSetHelp)
    ImageView ivSetHelp;
    @BindView(R.id.tvSetHelp)
    TextView tvSetHelp;
    @BindView(R.id.rlHelp)
    RelativeLayout rlHelp;
    @BindView(R.id.ivSetAbout)
    ImageView ivSetAbout;
    @BindView(R.id.tvSetAbout)
    TextView tvSetAbout;
    @BindView(R.id.rlAbout)
    RelativeLayout rlAbout;
    @BindView(R.id.tvSwitchAccount)
    TextView tvSwitchAccount;
    @BindView(R.id.tvLoginOut)
    TextView tvLoginOut;
    @BindView(R.id.tvVersionNumber)
    TextView tvVersionNumber;
    private Unbinder unbinder;
    private boolean isAutoExpandRange;
    private boolean isRefreshHome = false;


    public static final String MAXAGE = "set_maxAge";
    public static final String MINAGE = "set_minAge";
    public static final String DISTANCE = "set_distance";
    public static final String ISAUTO = "set_auto";
    public static final String SEX = "set_sex";

    int maxAge;
    int minAge;
    int distance;
    int auto;
    int sex;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        inintView();
        initActionBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSelfData();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("设置中心");

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
                            user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                                setData();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }


    private void inintView() {
        findViewById(R.id.tvLoginOut).setOnClickListener(this::onClick);
        findViewById(R.id.tvSwitchAccount).setOnClickListener(this::onClick);
        findViewById(R.id.rlAbout).setOnClickListener(this::onClick);
        findViewById(R.id.rlHelp).setOnClickListener(this::onClick);
        findViewById(R.id.rlSafe).setOnClickListener(this::onClick);
        findViewById(R.id.rlCache).setOnClickListener(this::onClick);
        findViewById(R.id.rlPrivacy).setOnClickListener(this::onClick);
        findViewById(R.id.rlNews).setOnClickListener(this::onClick);
        findViewById(R.id.tvCurrentSex).setOnClickListener(this::onClick);
        rlCurrentLocation.setOnClickListener(this);
        EventBusHelper.register(this);

        maxAge = PreferenceUtils.getInt(this, MAXAGE, 0);
        minAge = PreferenceUtils.getInt(this, MINAGE, 0);
        distance = PreferenceUtils.getInt(this, DISTANCE, 0);
        auto = PreferenceUtils.getInt(this, ISAUTO, 0);
        sex = PreferenceUtils.getInt(this, SEX, 0);

        seekbar.setRange(18f, 50f);
        sbDistance.setRange(0f, 1000f);


        if (sex == 1) {
            tvCurrentSex.setText(R.string.sex_man);
        } else if (sex == 0) {
            tvCurrentSex.setText(R.string.sex_woman);
        } else {
            tvCurrentSex.setText(R.string.sex_unlimited);
        }

        if (auto == 1) {
            sbBExpandScope.setChecked(true);
        } else {
            sbBExpandScope.setChecked(false);
        }

        if (maxAge > 0 && minAge > 0) {
            tvCurrentMinAge.setText(String.valueOf(minAge));
            if (maxAge==50){
                tvCurrentMaxMinAge.setText(String.valueOf(maxAge)+"+");
            }else{
                tvCurrentMaxMinAge.setText(String.valueOf(maxAge));

            }

            seekbar.setProgress(minAge, maxAge);
        }
        if (distance > 0) {
            sbDistance.setProgress(distance);
            tvCurrentDistance.setText(distance + "km+");

        }

    }

    private void setData() {
        if (PreferenceUtils.getBoolean(NewSettingsActivity.this, coreManager.getSelf().getUserId() + SpContext.ISSELECT)) {
            tvSetCity.setText(PreferenceUtils.getString(NewSettingsActivity.this, coreManager.getSelf().getUserId() + SpContext.CITY));
        } else {
            tvSetCity.setText(MyApplication.getInstance().getBdLocationHelper().getCityName());
        }

        if (sex != user.getSettings().getDisplaySex()) {


            if (user.getSettings().getDisplaySex() == 1) {
                sex = 1;
                tvCurrentSex.setText(R.string.sex_man);
            } else if (user.getSettings().getDisplaySex() == 0) {
                sex = 0;
                tvCurrentSex.setText(R.string.sex_woman);
            } else {
                sex = -1;
                tvCurrentSex.setText(R.string.sex_unlimited);
            }

        }


        if (auto != user.getSettings().getIsAutoExpandRange()) {
            if (user.getSettings().getIsAutoExpandRange() == 1) {
                sbBExpandScope.setChecked(true);
            } else {
                sbBExpandScope.setChecked(false);
            }
        }


        sbBExpandScope.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isAutoExpandRange = isChecked;
                isRefreshHome = true;
            }
        });


        if (distance != user.getSettings().getDistance()) {
            sbDistance.setProgress(user.getSettings().getDistance());
            String dis=String.valueOf(user.getSettings().getDistance());
            if (dis.equals("1000")){
                dis=dis+"km+";
            }else{
                dis=dis+"km";
            }
            tvCurrentDistance.setText(dis);
        }


//        范围
        sbDistance.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (leftValue==1000){
                    tvCurrentDistance.setText((int) leftValue + "km+");
                }else{
                    tvCurrentDistance.setText((int) leftValue + "km");

                }

                isRefreshHome = true;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        if (maxAge != user.getSettings().getMaxAge() || minAge != user.getSettings().getMinAge()) {
            seekbar.setProgress(user.getSettings().getMinAge(), user.getSettings().getMaxAge());
            tvCurrentMinAge.setText(String.valueOf(user.getSettings().getMinAge()));
            String maxA=String.valueOf(user.getSettings().getMaxAge());
            if (maxA.equals("50")){
                maxA=maxA+"+";
            }
            tvCurrentMaxMinAge.setText(maxA);
        }

        seekbar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                String minAge = String.valueOf(((int) leftValue));
                String maxAge = String.valueOf(((int) rightValue));

                tvCurrentMinAge.setText(minAge);
                if (maxAge.equals("50")){
                   maxAge=maxAge+"+";
                }
                tvCurrentMaxMinAge.setText(maxAge);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                update();

                break;
            case R.id.tvLoginOut:
                //退出登录
                showExitDialog();
                break;
            case R.id.tvCurrentSex:
                //选择男或者女
                showSelectSexDialog();
                break;
            case R.id.tvSwitchAccount:
                //切换账号
                startActivity(new Intent(this, MySwitchAccountActivity.class));
                break;
            case R.id.rlAbout:
                //关于幸福有约
                startActivity(new Intent(this, MyAboutActivity.class));
                break;
            case R.id.rlHelp:
                //帮助与反馈
                break;
            case R.id.rlSafe:
                //账号与安全
                startActivity(new Intent(this, AccountSafeActivity.class));
                break;
            case R.id.rlCache:
                //数据和缓存
                startActivity(new Intent(this, MyDatCacheActivity.class));
                break;
            case R.id.rlPrivacy:
                //隐私与权限
                startActivity(new Intent(this, MyPrivacyActivity.class));
                break;
            case R.id.rlNews:
                //消息提醒与聊天
                startActivity(new Intent(this, MyRemindersChatsActivity.class));
                break;
            case R.id.rlCurrentLocation:
                //位置
                startActivity(new Intent(this, CurrentLocationActivity.class));
             /*   if(!coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")){
                    //todo -1没有会员

                }else {
                    BuyMember(coreManager.getSelf().getUserVIPPrivilegeConfig());
                }*/

                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void update() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("distance", String.valueOf((int) sbDistance.getLeftSeekBar().getProgress()));
        params.put("maxAge", String.valueOf((int) seekbar.getRightSeekBar().getProgress()));
        params.put("minAge", String.valueOf((int) seekbar.getLeftSeekBar().getProgress()));
        params.put("displaySex", sex + "");


        PreferenceUtils.putInt(this, MAXAGE, (int) seekbar.getRightSeekBar().getProgress());
        PreferenceUtils.putInt(this, MINAGE, (int) seekbar.getLeftSeekBar().getProgress());
        PreferenceUtils.putInt(this, DISTANCE, (int) sbDistance.getLeftSeekBar().getProgress());
        PreferenceUtils.putInt(this, SEX,sex);


        if (isAutoExpandRange) {
            params.put("isAutoExpandRange", "1");
            PreferenceUtils.putInt(this, ISAUTO, 1);

        } else {
            params.put("isAutoExpandRange", "0");
            PreferenceUtils.putInt(this, ISAUTO, 0);
        }

        HttpUtils.get().url(coreManager.getConfig().USER_UPDATE_SETTINGS_CORE)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {
                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(NewSettingsActivity.this, result)) {
                            finish();
                            if (isRefreshHome) {
                                EventBus.getDefault().post(new EventNotifyUpdate("isRefreshHome"));
                            }
                        } else {
                            ToastUtil.showToast(mContext, result.getResultMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }

    // 退出当前账号
    private void showExitDialog() {
        SelectionFrame mSF = new SelectionFrame(this);
        mSF.setSomething(null, getString(R.string.sure_exit_account), new SelectionFrame.OnSelectionFrameClickListener() {
            @Override
            public void cancelClick() {

            }

            @Override
            public void confirmClick() {
                stopService(new Intent(mContext, WindowShowService.class));
                logout();
                // 退出时清除设备锁密码，
                DeviceLockHelper.clearPassword();
                UserSp.getInstance(mContext).clearUserInfo();
                MyApplication.getInstance().mUserStatus = LoginHelper.STATUS_NO_USER;
                coreManager.logout();
                LoginHelper.broadcastLogout(mContext);
                LoginActivity.start(NewSettingsActivity.this);
                finish();
            }
        });
        mSF.show();
    }

    private void logout() {
        HashMap<String, String> params = new HashMap<String, String>();
        // 得到电话
        String phoneNumber = coreManager.getSelf().getTelephone();
        String digestTelephone = Md5Util.toMD5(phoneNumber);
        params.put("telephone", digestTelephone);
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        // 默认为86
        params.put("areaCode", String.valueOf(86));
        params.put("deviceKey", "android");

        HttpUtils.get().url(coreManager.getConfig().USER_LOGOUT)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {

                    @Override
                    public void onResponse(ObjectResult<String> result) {
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    private void showSelectSexDialog() {
        String[] sexs = new String[]{ getString(R.string.sex_woman),getString(R.string.sex_man), getString(R.string.sex_unlimited)};
        int index;
        if (sex==0){
            index=0;
        }else if(sex==1){
            index=1;
        }else{
            index=2;
        }

        new AlertDialog.Builder(this).setTitle(R.string.select_sex)
                .setSingleChoiceItems(sexs, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            sex = 0;
                            tvCurrentSex.setText(R.string.sex_woman);
                            isRefreshHome = true;
                        } else if (which == 1) {
                            sex = 1;
                            tvCurrentSex.setText(R.string.sex_man);
                            isRefreshHome = true;
                        } else {
                            sex = -1;
                            tvCurrentSex.setText(R.string.sex_unlimited);
                            isRefreshHome = true;
                        }


                        dialog.dismiss();

                    }
                }).setCancelable(true).create().show();
    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember(UserVIPPrivilegePrice userVIPPrivilegePrice) {
        //显示VIP购买会员
        MyVipPaymentPopupWindow myVipPaymentPopupWindow = new MyVipPaymentPopupWindow(NewSettingsActivity.this, userVIPPrivilegePrice, coreManager.getSelf().getUserId(), coreManager.getSelf().getNickName(), coreManager.getSelf().getUserVIPPrivilege().getVipLevel());
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
        if (vip == 1) {
            params.put("price", vipPrivilegePriceList.getV0Price() + "");
            params.put("level", vipPrivilegePriceList.getV0());
        } else if (vip == 2) {
            params.put("price", vipPrivilegePriceList.getV1Price() + "");
            params.put("level", vipPrivilegePriceList.getV1());
        } else if (vip == 3) {
            params.put("price", vipPrivilegePriceList.getV2Price() + "");
            params.put("level", vipPrivilegePriceList.getV2());
        } else if (vip == 4) {
            params.put("price", vipPrivilegePriceList.getV3Price() + "");
            params.put("level", vipPrivilegePriceList.getV3());
        }
        params.put("funType", String.valueOf(5));
        params.put("num", String.valueOf(-1));
        params.put("mon", String.valueOf(-1));
        AlipayHelper.rechargePay(NewSettingsActivity.this, coreManager, params);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功刷新用户页面
        updateSelfData();
    }

}