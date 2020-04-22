package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.PrivacySetting;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.broadcast.OtherBroadcast;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.helper.PrivacySettingHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.lock.DeviceLockHelper;
import com.xfyyim.cn.util.Md5Util;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MergerStatus;
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
    SeekBar sbDistance;
    @BindView(R.id.tvRange)
    TextView tvRange;
    @BindView(R.id.sbBExpandScope)
    SwitchButton sbBExpandScope;
    @BindView(R.id.tvCurrentSex)
    TextView tvCurrentSex;
    @BindView(R.id.tvCurrentAge)
    TextView tvCurrentAge;
    @BindView(R.id.ivSetMin)
    ImageView ivSetMin;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.ivSetMax)
    ImageView ivSetMax;
    @BindView(R.id.ivPersonal)
    ImageView ivPersonal;
    @BindView(R.id.tvSetPersonal)
    TextView tvSetPersonal;
    @BindView(R.id.rlPersonal)
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
    private  boolean  isAutoExpandRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        inintView();
        initActionBar();
        updateSelfData();
    }

    private void initData() {
        if(coreManager.getSelf().getSettings().getIsAutoExpandRange()==1){
            sbBExpandScope.setChecked(true);
        }else {
            sbBExpandScope.setChecked(false);
        }

        sbBExpandScope.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                isAutoExpandRange=isChecked;
            }
        });

        sbDistance.setProgress(coreManager.getSelf().getSettings().getDistance());
        tvCurrentDistance.setText(coreManager.getSelf().getSettings().getDistance()+"km+");
        //范围
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCurrentDistance.setText(progress+"km+");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar.setProgress(coreManager.getSelf().getSettings().getAgeDistance());
        //年龄
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCurrentAge.setText("18-"+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void initActionBar() {

        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("设置中心");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

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
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                                initData();
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
        findViewById(R.id.rlPersonal).setOnClickListener(this::onClick);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                update();
                finish();
                break;
            case R.id.tvLoginOut:
                //退出登录
                showExitDialog();
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
            case R.id.rlPersonal:
                //个人信息
                startActivity(new Intent(this, MyPersonalInformationActivity.class));
                break;
            case R.id.rlCurrentLocation:
                //位置
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

    private void update(){

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("distance", sbDistance.getProgress()+"");
        params.put("ageDistance", seekbar.getProgress()+"");
        params.put("displaySex", 0+"");
        if(isAutoExpandRange){
            params.put("isAutoExpandRange", "1");
        }else {
            params.put("isAutoExpandRange", "0");
        }

        HttpUtils.get().url(coreManager.getConfig().USER_UPDATE_SETTINGS_CORE)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {
                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(NewSettingsActivity.this, result)) {

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

}