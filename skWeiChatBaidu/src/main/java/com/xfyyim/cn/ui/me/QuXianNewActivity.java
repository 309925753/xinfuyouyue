package com.xfyyim.cn.ui.me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.PaySecureHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.me.redpacket.ChangePayPasswordActivity;
import com.xfyyim.cn.ui.me.redpacket.QuXianActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me.redpacket.scan.ScanWithdrawActivity;
import com.xfyyim.cn.util.Constants;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.secure.Money;

import java.text.DecimalFormat;

public class QuXianNewActivity extends BaseActivity {
    public static String amount;// 提现金额 单位:元
    private IWXAPI api;
    private EditText mMentionMoneyEdit;
    private TextView mBalanceTv;
    private TextView mAllMentionTv;
    private TextView mSureMentionTv;
    private TextView tvAlipay;
    private TextView mScanWithdrawTv;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qu_xian);

        api = WXAPIFactory.createWXAPI(QuXianNewActivity.this, Constants.VX_APP_ID, false);
        api.registerApp(Constants.VX_APP_ID);

        initActionbar();
        initView();
        intEvent();

      //  checkHasPayPassword();
    }

    private void checkHasPayPassword() {
        boolean hasPayPassword = PreferenceUtils.getBoolean(this, Constants.IS_PAY_PASSWORD_SET + coreManager.getSelf().getUserId(), true);
        if (!hasPayPassword) {
            ToastUtil.showToast(this, R.string.tip_no_pay_password);
            Intent intent = new Intent(this, ChangePayPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initActionbar() {
        getSupportActionBar().hide();
        findViewById(R.id.iv_title_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView mTvTitle = (TextView) findViewById(R.id.tv_title_center);
        mTvTitle.setText(getString(R.string.withdraw));
    }

    private void initView() {
        mMentionMoneyEdit = (EditText) findViewById(R.id.tixianmoney);
        mBalanceTv = (TextView) findViewById(R.id.blance_weixin);
        mBalanceTv.setText(decimalFormat.format(coreManager.getSelf().getBalance()));
        mAllMentionTv = (TextView) findViewById(R.id.tixianall);
        mSureMentionTv = (TextView) findViewById(R.id.tixian);
        tvAlipay = (TextView) findViewById(R.id.withdraw_alipay);
        mScanWithdrawTv = (TextView) findViewById(R.id.withdraw_back);

        mSureMentionTv.setVisibility(coreManager.getConfig().enableWxPay ? View.VISIBLE : View.GONE);
        tvAlipay.setVisibility(coreManager.getConfig().enableAliPay ? View.VISIBLE : View.GONE);
        if (coreManager.getConfig().isOpenManualPay) {
            mScanWithdrawTv.setVisibility(View.VISIBLE);
        }
    }

    private void intEvent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mMentionMoneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 删除开头的0，
                int end = 0;
                for (int i = 0; i < editable.length(); i++) {
                    char ch = editable.charAt(i);
                    if (ch == '0') {
                        end = i + 1;
                    } else {
                        break;
                    }
                }
                if (end > 0) {
                    editable.delete(0, end);
                    mMentionMoneyEdit.setText(editable);
                }
            }
        });

        mAllMentionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double money = coreManager.getSelf().getBalance();
                if (money < 1) {
                    DialogHelper.tip(QuXianNewActivity.this, getString(R.string.tip_withdraw_too_little));
                } else {
                    mMentionMoneyEdit.setText(String.valueOf(money));
                }
            }
        });

        findViewById(R.id.tixian_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moneyStr = mMentionMoneyEdit.getText().toString();
                if (checkMoney(moneyStr)) {
                    amount = Money.fromYuan(moneyStr);

                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    api.sendReq(req);

                    finish();
                }
            }
        });

        findViewById(R.id.withdraw_alipay_ll).setOnClickListener(v -> {
            String moneyStr = mMentionMoneyEdit.getText().toString();
            if (checkMoney(moneyStr)) {
                amount = Money.fromYuan(moneyStr);
                    AlipayHelper.auth(this, coreManager, "123456", userId -> {
                        AlipayHelper.withdraw(this, coreManager, amount, "123456");
                    });
            }
        });

        /**
         * 后台审核提现
         */
        mScanWithdrawTv.setOnClickListener(view -> {
            ScanWithdrawActivity.start(mContext, mMentionMoneyEdit.getText().toString());
        });
    }

    private boolean checkMoney(String moneyStr) {
        if (TextUtils.isEmpty(moneyStr)) {
            DialogHelper.tip(QuXianNewActivity.this, getString(R.string.tip_withdraw_empty));
        } else {
            if (Double.valueOf(moneyStr) < 1) {
                DialogHelper.tip(QuXianNewActivity.this, getString(R.string.tip_withdraw_too_little));
            } else if (Double.valueOf(moneyStr) > coreManager.getSelf().getBalance()) {
                DialogHelper.tip(QuXianNewActivity.this, getString(R.string.tip_balance_not_enough));
            } else {// 获取用户code
                return true;
            }
        }
        return false;
    }
}