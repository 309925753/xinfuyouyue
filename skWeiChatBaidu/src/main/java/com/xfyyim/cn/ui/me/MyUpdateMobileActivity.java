package com.xfyyim.cn.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.baidu.idl.face.platform.utils.MD5Utils;
import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.RegUtil;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.secure.LoginPassword;
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
import butterknife.Unbinder;
import okhttp3.Call;


public class MyUpdateMobileActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPhone)
    EditText etPhone;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update_mobile);
        unbinder = ButterKnife.bind(this);
        inintView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("修改电话号码");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    private void inintView() {
       findViewById(R.id.rlNext).setOnClickListener(this::onClick);
    }

    private void getPassWord() {
        String _password = etPassword.getText().toString().trim();
        String _phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(_password)) {
            ToastUtil.showLongToast(this, "密码不能为空，请重新输入");
            setEtPassword();
            return;
        }
        if (!RegUtil.checkPhone(_phone)) {
            ToastUtil.showLongToast(this, "手机号有误，请重新输入");
            setEtMobile();
            return ;
        }
        postUpdateMobile(_password,_phone);
    }

    private  void   postUpdateMobile(String  _password,String  _phone){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("password", LoginPassword.encodeMd5(_password));
        params.put("newTelephone", _phone);
        params.put("userId", coreManager.getSelf().getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_UPDATE_TELEPHONE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(MyUpdateMobileActivity.this, result)) {
                            ToastUtil.showLongToast(MyUpdateMobileActivity.this, "修改成功");
                            finish();
                        } else {
                            ToastUtil.showLongToast(MyUpdateMobileActivity.this, "修改失败，请重新修改");
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(MyUpdateMobileActivity.this);
                    }
                });
    }
    /**
     * 聚焦
     */
    public void setEtMobile() {
        etPhone.setFocusable(true);
        etPhone.setFocusableInTouchMode(true);
        etPhone.requestFocus();
    }
    /**
     * 聚焦
     */
    public void setEtPassword() {
        etPhone.setFocusable(true);
        etPhone.setFocusableInTouchMode(true);
        etPhone.requestFocus();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.rlNext:
                getPassWord();
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
