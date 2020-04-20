package com.xfyyim.cn.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xfyyim.cn.AppConfig;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.Code;
import com.xfyyim.cn.bean.event.MessageLogin;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.PasswordHelper;
import com.xfyyim.cn.helper.UsernameHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.Constants;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.secure.LoginPassword;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

/**
 * 注册-1.输入手机号
 */
public class RegisterActivity extends BaseActivity {
    public static final String EXTRA_AUTH_CODE = "auth_code";
    public static final String EXTRA_PHONE_NUMBER = "phone_number";
    public static final String EXTRA_PASSWORD = "password";
    public static final String EXTRA_SMS_CODE = "sms_code";
    public static final String EXTRA_INVITE_CODE = "invite_code";
    public static int isSmsRegister = 0;
    private EditText mPhoneNumEdit;
    private EditText mPassEdit;
    private ImageView mImageCodeIv;
    private ImageView mRefreshIv;
    private EditText mAuthCodeEdit;
    private Button mSendAgainBtn;
    private Button mNextStepBtn;
    private Button mNoAuthCodeBtn;
    private int mobilePrefix = 86;
    private String mRandCode;
    private int reckonTime = 60;
    private String thirdToken;
    private String thirdTokenType;
    // 短信码是否已经发送，启用时必须发送了才能下一步，
    private boolean mSmsSent;
    private boolean privacyAgree = true;
    private Handler mReckonHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1) {
                mSendAgainBtn.setText(reckonTime + " " + "S");
                if (reckonTime == 30) {
                    // 剩下30秒时显示收不到验证码按钮，
                    if (AppConfig.isShiku()) {
                        // 30秒后可以跳过验证码功能不在定制版生效，
                        mNoAuthCodeBtn.setVisibility(View.VISIBLE);
                    }
                }
                reckonTime--;
                if (reckonTime < 0) {
                    mReckonHandler.sendEmptyMessage(0x2);
                } else {
                    mReckonHandler.sendEmptyMessageDelayed(0x1, 1000);
                }
            } else if (msg.what == 0x2) {
                // 60秒结束
                mSendAgainBtn.setText(getString(R.string.send));
                mSendAgainBtn.setEnabled(true);
                reckonTime = 60;
            }
        }
    };

    public RegisterActivity() {
        noLoginRequired();
    }

    public static void registerFromThird(Context ctx, int mobilePrefix, String phone, String password, String thirdToken, String thirdTokenType) {
        Intent intent = new Intent(ctx, RegisterActivity.class);
        intent.putExtra("mobilePrefix", mobilePrefix);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        intent.putExtra("thirdToken", thirdToken);
        intent.putExtra("thirdTokenType", thirdTokenType);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        mobilePrefix = getIntent().getIntExtra("mobilePrefix", 86);
        thirdToken = getIntent().getStringExtra("thirdToken");
        thirdTokenType = getIntent().getStringExtra("thirdTokenType");
        initActionBar();
        initView();
        initEvent();
        EventBusHelper.register(this);
    }

    private void initActionBar() {
        getSupportActionBar().hide();
    }

    private void initView() {
        mPhoneNumEdit = (EditText) findViewById(R.id.phone_numer_edit);
        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            mPhoneNumEdit.setText(phone);
        }
        mPassEdit = (EditText) findViewById(R.id.password_edit);
        PasswordHelper.bindPasswordEye(mPassEdit, findViewById(R.id.tbEye));
        String password = getIntent().getStringExtra("password");
        if (!TextUtils.isEmpty(password)) {
            mPassEdit.setText(password);
        }
        mAuthCodeEdit =  findViewById(R.id.auth_code_edit);
        mSendAgainBtn =  findViewById(R.id.send_again_btn);
        mNextStepBtn =  findViewById(R.id.login_btn);
        UsernameHelper.initEditText(mPhoneNumEdit, coreManager.getConfig().registerUsername);

//        if (coreManager.getConfig().registerInviteCode > 0) {
//            // 启用邀请码，
//            findViewById(R.id.llInvitationCode).setVisibility(View.VISIBLE);
//        }


        findViewById(R.id.main_content).setOnClickListener(v -> {
            // 点击空白区域隐藏软键盘
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(findViewById(R.id.main_content).getWindowToken(), 0); //强制隐藏键盘
            }
        });

    }


    private void verifyPhoneNumber(String phoneNumber, final Runnable onSuccess) {
        if (!UsernameHelper.verify(this, phoneNumber, coreManager.getConfig().registerUsername)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("telephone", phoneNumber);
        params.put("areaCode", "" + mobilePrefix);

        HttpUtils.get().url(coreManager.getConfig().VERIFY_TELEPHONE)
                .params(params)
                .build(true, true)
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (result == null) {
                            ToastUtil.showToast(RegisterActivity.this,
                                    R.string.data_exception);
                            return;
                        }

                        if (result.getResultCode() == 1) {
                            onSuccess.run();
                        } else {
                            // 手机号已经被注册
                            if (!TextUtils.isEmpty(result.getResultMsg())) {
                                ToastUtil.showToast(RegisterActivity.this,
                                        result.getResultMsg());
                            } else {
                                ToastUtil.showToast(RegisterActivity.this,
                                        R.string.tip_server_error);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(RegisterActivity.this);
                    }
                });
    }

    /**
     * 跳过短信验证码到下一步，
     */
    private void nextStepWithOutAuthCode(final String phoneStr, final String passStr) {
        verifyPhoneNumber(phoneStr, new Runnable() {
            @Override
            public void run() {
                realNextStep(phoneStr, passStr);
            }
        });
    }

    private void realNextStep(String phoneStr, String passStr) {

        RegisterUserBasicInfoActivity.start(
                this,
                "" + mobilePrefix,
                phoneStr,
                LoginPassword.encodeMd5(passStr),
                mAuthCodeEdit.getText().toString().trim(),
             "",
                thirdToken,
                thirdTokenType,
                passStr
        );
        // 不需要结束，登录后通过EventBus消息结束这些，
        // finish();
    }

    /**
     * 验证手机是否注册
     */
    private void verifyPhoneIsRegistered(final String phoneStr, final String imageCodeStr) {
        verifyPhoneNumber(phoneStr, new Runnable() {
            @Override
            public void run() {
                requestAuthCode(phoneStr, imageCodeStr);
            }
        });
    }

    /**
     * 请求验证码
     */
    private void requestAuthCode(String phoneStr, String imageCodeStr) {
        Map<String, String> params = new HashMap<>();
        String language = Locale.getDefault().getLanguage();
        params.put("language", language);
        params.put("areaCode", String.valueOf(mobilePrefix));
        params.put("telephone", phoneStr);
        params.put("imgCode", imageCodeStr);
        params.put("isRegister", String.valueOf(1));
        params.put("version", "1");

        DialogHelper.showDefaulteMessageProgressDialog(this);

        HttpUtils.get().url(coreManager.getConfig().SEND_AUTH_CODE)
                .params(params)
                .build(true, true)
                .execute(new BaseCallback<Code>(Code.class) {

                    @Override
                    public void onResponse(ObjectResult<Code> result) {
                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1) {
                            mSmsSent = true;
                            if (result.getData() != null && result.getData().getCode() != null) {
                                Log.e(TAG, "onResponse: " + result.getData().getCode());
                                mRandCode = result.getData().getCode();// 记录验证码
                            }
                            mSendAgainBtn.setEnabled(false);
                            // 开始倒计时
                            mReckonHandler.sendEmptyMessage(0x1);
                        } else {
                            if (!TextUtils.isEmpty(result.getResultMsg())) {
                                ToastUtil.showToast(RegisterActivity.this,
                                        result.getResultMsg());
                            } else {
                                ToastUtil.showToast(RegisterActivity.this,
                                        getString(R.string.tip_server_error));
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showNetError(mContext);
                    }
                });
    }

    private void initEvent() {
        mPhoneNumEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 注册页面手机号输入完成后自动刷新验证码，
                    // 只在移开焦点，也就是点击其他EditText时调用，
                }
            }
        });
        mPhoneNumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 手机号码修改时让图形验证码和短信验证码失效，
                // 每输入一个字符调用一次，
                mRandCode = null;
                mAuthCodeEdit.setText("");
            }
        });


//        mNoAuthCodeBtn.setOnClickListener(new View.OnClickListener() {// 收不到验证码
//            @Override
//            public void onClick(View v) {
//                // 不检查验证码就前往下一步，
//                nextStepWithoutAuthCode();
//            }
//        });

        mSendAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhoneStr = mPhoneNumEdit.getText().toString().trim();
                String mPassStr = mPassEdit.getText().toString().trim();
                if (checkInput(mPhoneStr, mPassStr))
                    return;

                // 验证手机号是否注册
                verifyPhoneIsRegistered(mPhoneStr, "");

            }
        });

        // 注册
        mNextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.putInt(RegisterActivity.this, Constants.AREA_CODE_KEY, mobilePrefix);
                    nextStep();
            }
        });
    }

    /**
     * 验证验证码
     */
    private void nextStep() {
        String mPhoneStr = mPhoneNumEdit.getText().toString().trim();
        String mPassStr = mPassEdit.getText().toString().trim();
        if (checkInput(mPhoneStr, mPassStr))
            return;
        String mAuthCodeStr = mAuthCodeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mAuthCodeStr)) {
            ToastUtil.showToast(mContext, getString(R.string.please_input_auth_code));
            return;
        }

        isSmsRegister = 1;
        if (!TextUtils.isEmpty(mRandCode)) {
            if (mAuthCodeStr.equals(mRandCode)) {// 验证码正确,进入填写资料页面
                realNextStep(mPhoneStr, mPassStr);
            } else {
                // 验证码错误
                Toast.makeText(this, R.string.auth_code_error, Toast.LENGTH_SHORT).show();
            }
        } else {
                ToastUtil.showToast(mContext, getString(R.string.please_send_sms_code));
                return;
        }
    }


    /**
     * 检查是否需要停止注册，
     *
     * @return 测试不合法返回true, 停止继续注册，
     */
    private boolean checkInput(String mPhoneStr, String mPassStr) {
        if (!privacyAgree) {
            ToastUtil.showToast(mContext, R.string.tip_privacy_not_agree);
            return true;
        }
        if (!UsernameHelper.verify(this, mPhoneStr, coreManager.getConfig().registerUsername)) {
            return true;
        }
        if (TextUtils.isEmpty(mPassStr)) {
            ToastUtil.showToast(mContext, getString(R.string.tip_password_empty));
            return true;
        }
        if (mPassStr.length() < 6) {
            ToastUtil.showToast(mContext, getString(R.string.tip_password_too_short));
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(MessageLogin message) {
        finish();
    }
}
