package com.xfyyim.cn.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.annotation.RequiresApi;

import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xfyyim.cn.MyApplication.getContext;


public class MyNewWalletActivity extends BaseActivity implements View.OnClickListener {

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
    private PopupWindow popupWindow;
    private View contentView;
    private boolean isBuyTimes = true, isBuyTimes2 = true, isBuyTimes3 = true, isBuyTimes4 = true, walletSelect = true;
    private Context context;

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_wallet);
        unbinder=ButterKnife.bind(this);
        initView();
        initClickListener();
        context = this;
        setFaceConfig();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("钱包");
        tvTitleRight.setText("帐单明细");
        mergerStatus.setBackground(getDrawable(R.drawable.bg_new_my_wallet_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvTitleRight.setTextColor(getColor(R.color.white));
        }
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    private void setFaceConfig() {

     /*   FaceSDKManager.getInstance().initialize(this,"xfyy-face-android", "idl-license.face-android");

        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
        // 根据需求添加活体动作
        livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadUp);
        livenessList.add(LivenessTypeEnum.HeadDown);
        livenessList.add(LivenessTypeEnum.HeadLeft);
        livenessList.add(LivenessTypeEnum.HeadRight);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        config.setLivenessTypeList(livenessList);

        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);

        FaceSDKManager.getInstance().setFaceConfig(config);*/
    }

    private void initClickListener() {
        //帐单明细
        findViewById(R.id.tv_title_right).setOnClickListener(this::onClick);
        //提现
        findViewById(R.id.tvCashWithdrawal).setOnClickListener(this::onClick);
        //超级曝光
        findViewById(R.id.rlSuperexPosure).setOnClickListener(this);
        //超级喜欢
        findViewById(R.id.rlSuperexLike).setOnClickListener(this);
        //在线闪聊
        findViewById(R.id.rlChat).setOnClickListener(this);
        //闪聊偷看
        findViewById(R.id.rlToPeek).setOnClickListener(this);

    }

    private void initView() {


    }

    private void showPopupWindow() {
        //加载弹出框的布局
        contentView = this.getLayoutInflater().inflate(
                R.layout.dialog_mywallet_layout, null);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tvSetCheck = (TextView) contentView.findViewById(R.id.tvSetCheck);

        Drawable[] drawableLeft = {getResources().getDrawable(
                R.mipmap.my_prerogative_check)};
        tvSetCheck.setCompoundDrawablesWithIntrinsicBounds(drawableLeft[0], null, null, null);
        tvSetCheck.setCompoundDrawablePadding(20);
        tvSetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (walletSelect) {
                    walletSelect = false;
                    drawableLeft[0] = getResources().getDrawable(
                            R.mipmap.my_prerogative_checked);
                } else {
                    walletSelect = true;
                    drawableLeft[0] = getResources().getDrawable(
                            R.mipmap.my_prerogative_check);
                }

                tvSetCheck.setCompoundDrawablesWithIntrinsicBounds(drawableLeft[0], null, null, null);
                tvSetCheck.setCompoundDrawablePadding(20);

            }
        });


        TextView tvBuyTimes = (TextView) contentView.findViewById(R.id.tvBuyTimes);
        TextView tvBuyMoney = (TextView) contentView.findViewById(R.id.tvBuyMoney);

        TextView tvBuyTimes2 = (TextView) contentView.findViewById(R.id.tvBuyTimes2);
        TextView tvBuyMoney2 = (TextView) contentView.findViewById(R.id.tvBuyMoney2);

        TextView tvBuyTimes3 = (TextView) contentView.findViewById(R.id.tvBuyTimes3);
        TextView tvBuyMoney3 = (TextView) contentView.findViewById(R.id.tvBuyMoney3);

        TextView tvBuyTimes4 = (TextView) contentView.findViewById(R.id.tvBuyTimes4);
        TextView tvBuyMoney4 = (TextView) contentView.findViewById(R.id.tvBuyMoney4);

        LinearLayout rlBuyTimes4 = (LinearLayout) contentView.findViewById(R.id.rlBuyTimes4);
        LinearLayout rlBuyTimes3 = (LinearLayout) contentView.findViewById(R.id.rlBuyTimes3);
        LinearLayout rlBuyTimes = (LinearLayout) contentView.findViewById(R.id.rlBuyTimes);
        LinearLayout rlBuyTimes2 = (LinearLayout) contentView.findViewById(R.id.rlBuyTimes2);


        rlBuyTimes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isBuyTimes = false;
                isBuyTimes2 = true;
                isBuyTimes3 = true;
                isBuyTimes4 = true;

                rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
            }
        });
        rlBuyTimes2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isBuyTimes = true;
                isBuyTimes2 = false;
                isBuyTimes3 = true;
                isBuyTimes4 = true;
                rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
            }
        });
        rlBuyTimes3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isBuyTimes = true;
                isBuyTimes2 = true;
                isBuyTimes3 = false;
                isBuyTimes4 = true;
                rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
            }
        });
        rlBuyTimes4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isBuyTimes = true;
                isBuyTimes2 = true;
                isBuyTimes3 = true;
                isBuyTimes4 = false;

                rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));


                DialogHelper.showLimitSingleInputDialog((Activity) context, "充值次数",
                        "请输入充值次数" + "最多100次", 1, 1, 10, v1 -> {
                            String text = ((EditText) v1).getText().toString().trim();
                            if (TextUtils.isEmpty(text)) {
                                return;
                            }
                            LogUtil.e("充值次数 = " + text);
                            tvBuyTimes4.setText("购买" + text + "次");
                        });
            }
        });
        contentView.findViewById(R.id.tvWalletAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
                LogUtil.e("isBuyTimes= " + isBuyTimes + "isBuyTimes2 = " + isBuyTimes2 + "isBuyTimes3 = " + isBuyTimes3 + "isBuyTimes4 = " + isBuyTimes4);
            }
        });
        contentView.findViewById(R.id.tvWalletWx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
                LogUtil.e("isBuyTimes= " + isBuyTimes + "isBuyTimes2 = " + isBuyTimes2 + "isBuyTimes3 = " + isBuyTimes3 + "isBuyTimes4 = " + isBuyTimes4);
            }
        });
        contentView.findViewById(R.id.tvWalletBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
                LogUtil.e("isBuyTimes= " + isBuyTimes + "isBuyTimes2 = " + isBuyTimes2 + "isBuyTimes3 = " + isBuyTimes3 + "isBuyTimes4 = " + isBuyTimes4);

            }
        });

        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_style_bg));
        //点击外部消失
        popupWindow.setOutsideTouchable(true);

        //从底部显示
        popupWindow.setTouchable(true);
        int width = (int) getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
        int height = (int) getContext().getResources().getDisplayMetrics().heightPixels / 2 + ((int) getContext().getResources().getDisplayMetrics().heightPixels / 10); // 高度
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        this.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Dismiss();
            }
        });
    }


    private void Dismiss() {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 1f;
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.iv_title_left:
                    finish();
                    break;
            case R.id.tv_title_right://帐单明细
                startActivity(new Intent(this, MyWalletBillDetailsActivity.class));
                break;
            case R.id.tvCashWithdrawal://提现
                startActivity(new Intent(this, MyCashWithdrawalActivity.class));
                break;
            case R.id.rlSuperexPosure://超级曝光
                startActivity(new Intent(this, NewSettingsActivity.class));
                break;
            case R.id.rlSuperexLike://超级喜欢
                startActivity(new Intent(this, CheckLikesMeActivity.class));
                break;
            case R.id.rlChat://在线闪聊
                // showPopupWindow();认证中心
                startActivity(new Intent(this, CertificationCenterActivity.class));
                break;
            case R.id.rlToPeek://闪聊偷看
                //   showPopupWindow();
              /*  Intent intent = new Intent(this, FaceLivenessExpActivity.class);
                startActivityForResult(intent, 100);*/
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    String image = data.getStringExtra("image");
                    LogUtil.e("image size = " + image.length());
                }
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
