package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {

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

    private Unbinder  unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("账号与安全");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }
    private void initView() {
        RelativeLayout RlMobile = (RelativeLayout) findViewById(R.id.RlMobile);
        RelativeLayout rlPassword = (RelativeLayout) findViewById(R.id.rlPassword);
        RelativeLayout rlManageRenewal = (RelativeLayout) findViewById(R.id.rlManageRenewal);

        TextView tvUserMobile = (TextView) findViewById(R.id.tvUserMobile);
        TextView tvUserPassword = (TextView) findViewById(R.id.tvUserPassword);
        RlMobile.setOnClickListener(this);
        rlPassword.setOnClickListener(this);
        rlManageRenewal.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.RlMobile:
                //手机号码
                startActivity(new Intent(this, MyUpdateMobileActivity.class));
                break;
            case R.id.rlPassword:
                startActivity(new Intent(this, MyUpdatePassWordActivity.class));
                //设置/修改密码
                break;
            case R.id.rlManageRenewal:
                //管理自动续费
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
