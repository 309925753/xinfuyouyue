package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.secure.Money;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyWithdrawDepositActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.tixianmoney)
    EditText tixianmoney;
    @BindView(R.id.tv_poundage)
    TextView tvPoundage;
    @BindView(R.id.iv_zfb)
    ImageView ivZfb;
    @BindView(R.id.tv_zfb)
    TextView tvZfb;
    @BindView(R.id.iv_zfb_select)
    ImageView ivZfbSelect;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.iv_wx_select)
    ImageView ivWxSelect;
    @BindView(R.id.tv_sumbit)
    TextView tvSumbit;
    private Unbinder unbinder;
    private boolean isWxSeletect = false;
    private boolean isZfbSeletect = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_withdraw_deposit);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
        initActionbar();
        intEvent();
    }

    private void initData() {
        tvPoundage.setText("手续费：按照0.03%收取");


    }
    private void intEvent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        tixianmoney.addTextChangedListener(new TextWatcher() {
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
                    tixianmoney.setText(editable);
                }
            }
        });
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
        ivZfbSelect.setOnClickListener(this);
        ivWxSelect.setOnClickListener(this);
        tvSumbit.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_zfb_select:
                if (!isZfbSeletect) {
                    isZfbSeletect = true;
                    ivZfbSelect.setImageResource(R.mipmap.pay_checked);
                    ivWxSelect.setImageResource(R.mipmap.pay_check);
                } else {
                    isZfbSeletect = false;
                  ivZfbSelect.setImageResource(R.mipmap.pay_check);
                  ivWxSelect.setImageResource(R.mipmap.pay_checked);
                }
                break;
            case R.id.iv_wx_select:
                if (!isWxSeletect) {
                    isWxSeletect = true;
                  ivWxSelect.setImageResource(R.mipmap.pay_checked);
                  ivZfbSelect.setImageResource(R.mipmap.pay_check);
                } else {
                    isWxSeletect = false;
                   ivWxSelect.setImageResource(R.mipmap.pay_check);
                    ivZfbSelect.setImageResource(R.mipmap.pay_checked);
                }
                break;
            case R.id.tv_sumbit:
                String moneyStr = tixianmoney.getText().toString();
                if (checkMoney(moneyStr)) {
                    //如果是支付宝 得去输入手机号和密码
                    /**
                     * to do
                     */

                }
                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }
    private boolean checkMoney(String moneyStr) {
        if (TextUtils.isEmpty(moneyStr)) {
            DialogHelper.tip(MyWithdrawDepositActivity.this, getString(R.string.tip_withdraw_empty));
        } else {
            if (Double.valueOf(moneyStr) < 1) {
                DialogHelper.tip(MyWithdrawDepositActivity.this, getString(R.string.tip_withdraw_too_little));
            } else if (Double.valueOf(moneyStr) > coreManager.getSelf().getBalance()) {
                DialogHelper.tip(MyWithdrawDepositActivity.this, getString(R.string.tip_balance_not_enough));
            } else {// 获取用户code
                return true;
            }
        }
        return false;
    }
}
