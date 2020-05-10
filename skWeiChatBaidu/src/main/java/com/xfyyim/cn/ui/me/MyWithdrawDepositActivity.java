package com.xfyyim.cn.ui.me;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

import static com.xfyyim.cn.MyApplication.getContext;

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
    private boolean isWxSeletect = true;
    private boolean isZfbSeletect = false;
    private  String  _userMobile="";
    private String  _userName="";


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
                    isWxSeletect = false;
                    ivZfbSelect.setImageResource(R.mipmap.pay_checked);
                    ivWxSelect.setImageResource(R.mipmap.pay_check);
                } else {
                    isZfbSeletect = false;
                    isWxSeletect = true;
                  ivZfbSelect.setImageResource(R.mipmap.pay_check);
                  ivWxSelect.setImageResource(R.mipmap.pay_checked);
                }
                break;
            case R.id.iv_wx_select:
                if (!isWxSeletect) {
                    isWxSeletect = true;
                    isZfbSeletect = false;
                  ivWxSelect.setImageResource(R.mipmap.pay_checked);
                  ivZfbSelect.setImageResource(R.mipmap.pay_check);
                } else {
                    isWxSeletect = false;
                    isZfbSeletect = true;
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
                    if(isWxSeletect){
                    }else if(isZfbSeletect){
                        /**
                         * 微信 第三方登录或取openid
                         */
                        showData(v);
                    }
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
            }/* else if (Double.valueOf(moneyStr) > coreManager.getSelf().getBalance()) {
                DialogHelper.tip(MyWithdrawDepositActivity.this, getString(R.string.tip_balance_not_enough));
            }*/ else {// 获取用户code
                return true;
            }
        }
        return false;
    }

    /**
     * 显示数据
     * @param v
     */
    private void showData(View  v){
        View contentView =this.getLayoutInflater().inflate(
                R.layout.dialog_zfb_layout, null);
        PopupWindow    popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        ColorDrawable dw = new ColorDrawable(getContext().getResources().getColor(R.color.alp_background));
        popupWindow.setBackgroundDrawable(dw);

        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_style_bg));
        //点击外部消失
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);

        popupWindow.setOutsideTouchable(true);
        int width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        //   int height = (int) context.getResources().getDisplayMetrics().heightPixels / 2+((int) context.getResources().getDisplayMetrics().heightPixels / 11); // 高度
        popupWindow.setWidth(width - 100);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
      EditText   et_user_mobile= (EditText)contentView.findViewById(R.id.et_user_mobile);
        EditText   et_user_name=(EditText) contentView.findViewById(R.id.et_user_name);
        TextView   tvPayVip=(TextView) contentView.findViewById(R.id.tvPayVip);
        tvPayVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String    userMobile=et_user_mobile.getText().toString();
                String    userName=et_user_name.getText().toString();
             //   checkUser(userMobile,userName);
                if (TextUtils.isEmpty(userMobile)) {
                    DialogHelper.tip(MyWithdrawDepositActivity.this, "手机号不能为空");
                    return ;
                }
                if (userMobile.length()<10) {
                    DialogHelper.tip(MyWithdrawDepositActivity.this, "手机号长度不能小于10位");
                    return ;
                }
                if (TextUtils.isEmpty(userName)) {
                    DialogHelper.tip(MyWithdrawDepositActivity.this, "支付宝姓名不能为空");
                    return ;
                }
                popupWindow.dismiss();
                _userMobile=userMobile;
                _userName=userMobile;
                String moneyStr = tixianmoney.getText().toString();

                /**
                 * to  do  请求支付还是微信
                 */

                if(isZfbSeletect){


                }
                if(isWxSeletect){
                    /**
                     * 微信 第三方登录或取openid
                     */
                }


            }
        });

    }


    private  void  checkUser(String userMobile,String userName){


    }
}
