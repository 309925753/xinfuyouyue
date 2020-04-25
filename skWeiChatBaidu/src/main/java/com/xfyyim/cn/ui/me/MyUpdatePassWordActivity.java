package com.xfyyim.cn.ui.me;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.ChangePasswordActivity;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.secure.LoginPassword;
import com.xfyyim.cn.util.secure.chat.SecureChatUtil;
import com.xfyyim.cn.view.ClearEditText;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


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
    @BindView(R.id.mergerStatus)
    MergerStatus mergerStatus;
    @BindView(R.id.Cetpassword)
    ClearEditText Cetpassword;
    @BindView(R.id.cetNewPasswrod)
    ClearEditText cetNewPasswrod;
    @BindView(R.id.cetPaword)
    ClearEditText cetPaword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.ll_bottpom)
    LinearLayout llBottpom;
    @BindView(R.id.main_content)
    RelativeLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update_pass_word);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
    }


    private void initView() {
        loginBtn.setOnClickListener(this::onClick);
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("修改密码");
    }

    /**
     * 确认两次输入的密码是否一致
     */
    private boolean configPassword() {
        String passwordOld = Cetpassword.getText().toString().trim();
        String password = cetNewPasswrod.getText().toString().trim();
        String confirmPassword = cetPaword.getText().toString().trim();

        if (TextUtils.isEmpty(passwordOld) || passwordOld.length() < 6) {
            Cetpassword.requestFocus();
            Cetpassword.setError(StringUtils.editTextHtmlErrorTip(this, R.string.password_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            cetNewPasswrod.requestFocus();
            cetNewPasswrod.setError(StringUtils.editTextHtmlErrorTip(this, R.string.password_empty_error));
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword) || confirmPassword.length() < 6) {
            cetPaword.requestFocus();
            cetPaword.setError(StringUtils.editTextHtmlErrorTip(this, R.string.confirm_password_empty_error));
            return false;
        }
        if (confirmPassword.equals(password)) {
            return true;
        } else {
            cetPaword.requestFocus();
            cetPaword.setError(StringUtils.editTextHtmlErrorTip(this, R.string.password_confirm_password_not_match));
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (configPassword()) {

                }
                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }
    private void updatePassword(){
       /* Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                                finish();
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });*/

        final String oldPassword = Cetpassword.getText().toString().trim();
        final String password = cetPaword.getText().toString().trim();


    }
}

