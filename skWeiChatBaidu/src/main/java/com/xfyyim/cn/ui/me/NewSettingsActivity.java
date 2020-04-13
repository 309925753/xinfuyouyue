package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        unbinder = ButterKnife.bind(this);
        inintView();
        initActionBar();
    }


    private void initActionBar() {

        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("设置中心");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

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
                finish();
                break;
            case R.id.tvLoginOut:
                //退出登录
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
}