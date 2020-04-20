package com.xfyyim.cn.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.MyWalletPopupWindow;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SuperCriticalLightWindow;
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
    @BindView(R.id.tvUserBalance)
    TextView tvUserBalance;
    private PopupWindow popupWindow;
    private View contentView;
    private Context context;
    private UserVIPPrivilegePrice userVIPPrivilegePrice = new UserVIPPrivilegePrice();

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_wallet);
        unbinder = ButterKnife.bind(this);
        initView();
        initClickListener();
        context = this;
        initActionBar();
        initView();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("钱包");
        tvTitleRight.setText("帐单明细");



        tvUserBalance.setText(coreManager.getSelf().getBalance()+"");
        mergerStatus.setBackground(getDrawable(R.drawable.bg_new_my_wallet_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvTitleRight.setTextColor(getColor(R.color.white));
        }
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    /**
     * 获取特权配置信息
     */
    private void getUserPrivilegeConfigInfo() {
        LogUtil.e("into  getUserPrivilegeConfigInfo");
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_PRIVILEGE_CONFIG_INFO)
                .params(params)
                .build()
                .execute(new BaseCallback<UserVIPPrivilegePrice>(UserVIPPrivilegePrice.class) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(ObjectResult<UserVIPPrivilegePrice> result) {
                        //
                        if (Result.checkSuccess(getContext(), result)) {
                            LogUtil.e("vipPrivilegePriceList = " + userVIPPrivilegePrice.toString());
                            if (userVIPPrivilegePrice != null) {
                                userVIPPrivilegePrice = result.getData();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
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
        //获取钱包配置信息
        getUserPrivilegeConfigInfo();
    }


    private void Dismiss() {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 1f;
        this.getWindow().setAttributes(lp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                SuperCriticalLightWindow superCriticalLightWindow = new SuperCriticalLightWindow(this, userVIPPrivilegePrice);
                superCriticalLightWindow.setBtnOnClice(new SuperCriticalLightWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String type) {
                        LogUtil.e("type =  " + type);
                        confirmPay(type, 1);
                    }
                });
                break;
            case R.id.rlSuperexLike://超级喜欢
                MyWalletPopupWindow myWalletSuperexLike = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 2);
                myWalletSuperexLike.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectType, int functionType, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectType = " + selectType + "--functionType-" + functionType + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);

                    }
                });
                break;
            case R.id.rlChat://在线闪聊
                //    showPopupWindow();
                MyWalletPopupWindow myWalletPopupWindow = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 3);
                myWalletPopupWindow.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectType, int functionType, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectType = " + selectType + "--functionType-" + functionType + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);

                    }
                });
                break;
            case R.id.rlToPeek://闪聊偷看
                MyWalletPopupWindow myWalletPeek = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 4);
                myWalletPeek.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectType, int functionType, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectType = " + selectType + "--functionType-" + functionType + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);
                    }
                });
                break;
        }
    }

    private void confirmPay(String payType, int functionType) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        //   params.put("paytype", type);
        //    params.put("vipLevel", privilegeBean.getVipLevel());
        if (functionType == 1) {
            //    params.put("functionType",vipPrivilegePriceList.getV0Price()+"");
        } else if (functionType == 2) {
            //     params.put("functionType",vipPrivilegePriceList.getV1Price()+"");
        } else if (functionType == 3) {
            //     params.put("functionType", vipPrivilegePriceList.getV2Price()+"");
        } else if (functionType == 4) {
            //     params.put("functionType", vipPrivilegePriceList.getV3Price()+"");
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
