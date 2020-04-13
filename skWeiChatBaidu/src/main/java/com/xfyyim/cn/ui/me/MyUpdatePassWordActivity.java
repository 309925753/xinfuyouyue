package com.xfyyim.cn.ui.me;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyUpdatePassWordActivity extends BaseActivity implements View.OnClickListener {

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
    private TextView tvGetMobile, tvGetVerificationCode, etPassword, etNewPassword;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update_pass_word);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("修改密码");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    private void initView() {
        tvGetMobile = (TextView) findViewById(R.id.tvGetMobile);
        tvGetVerificationCode = (TextView) findViewById(R.id.tvGetVerificationCode);

        etPassword = (TextView) findViewById(R.id.etPassword);
        etNewPassword = (TextView) findViewById(R.id.etNewPassword);

        findViewById(R.id.rlFinish).setOnClickListener(this);
        tvGetVerificationCode.setOnClickListener(this);

    }

    /**
     * 得到验证码
     */
    private void getVerificationCode() {
        String GetVerificationCode = tvGetVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(GetVerificationCode)) {
            ToastUtil.showLongToast(this, "验证码不能为空");
            return;
        }
        if (GetVerificationCode.length() >= 4) {
            ToastUtil.showLongToast(this, "验证码不能小于4位");
            return;
        }
    }

    /**
     * 得到密码
     */
    private void getPassword() {
        String NewPassword = etNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(NewPassword)) {
            ToastUtil.showLongToast(this, "密码不能为空");
            return;
        }
        if (NewPassword.length() >= 4) {
            ToastUtil.showLongToast(this, "密码不能小于4位");
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.rlFinish:
                getVerificationCode();
                break;
            case R.id.tvGetVerificationCode:
                getPassword();
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

