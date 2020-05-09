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
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.me.redpacket.WxPayAdd;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.MyWalletPopupWindow;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SuperCriticalLightWindow;
import com.xfyyim.cn.view.SuperSolarizePopupWindow;
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
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
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
    private  int payFunction;//支付功能类型回调

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
        EventBusHelper.register(this);
    }


    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功
        ToastUtil.showLongToast(getContext(),"支付成功");
        switch (payFunction){
            //超级爆光回调
            case 1:
                superLight(coreManager.getSelf());
                payFunction=0;
                break;
        }
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
                startActivity(new Intent(this, MyWithdrawDepositActivity.class));
                break;
            case R.id.rlSuperexPosure://超级曝光
                openSuperSolarize();
                break;
            case R.id.rlSuperexLike://超级喜欢
                MyWalletPopupWindow myWalletSuperexLike = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 2);
                myWalletSuperexLike.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectCounts, int oncePrice, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectCounts = " + selectCounts + "--oncePrice-" + oncePrice + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);
                        confirmPay(2,payType,selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                    }
                });
                break;
            case R.id.rlChat://在线闪聊
                //    showPopupWindow();
                MyWalletPopupWindow myWalletPopupWindow = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 3);
                myWalletPopupWindow.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectCounts, int oncePrice, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectCounts = " + selectCounts + "--oncePrice-" + oncePrice + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);
                        confirmPay(3,payType,selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                    }
                });
                break;
            case R.id.rlToPeek://闪聊偷看
                MyWalletPopupWindow myWalletPeek = new MyWalletPopupWindow(this, userVIPPrivilegePrice, 4);
                myWalletPeek.setBtnOnClice(new MyWalletPopupWindow.BtnOnClick() {
                    @Override
                    public void btnOnClick(String payType, int selectCounts, int oncePrice, int Frequency10More, int Frequency10MorePrice) {
                        LogUtil.e("payType =  " + payType + "-------------selectCounts = " + selectCounts + "--oncePrice-" + oncePrice + "--Frequency10More-" + Frequency10More + "--Frequency10MorePrice--" + Frequency10MorePrice);
                        confirmPay(4,payType,selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                    }
                });
                break;
        }
    }

    /**
     * 支付给的数据  支付功能类型 支付方式  选中次数 单次价格 自定价格  自定次数
     * @param functionType
     * @param payType
     * @param selectCounts
     * @param oncePrice
     * @param Frequency10More
     * @param Frequency10MorePrice
     */
    private void confirmPay(int functionType,String payType, int selectCounts, int oncePrice,int Frequency10More, int Frequency10MorePrice) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("funType", String.valueOf(functionType));
        if(Frequency10More==0){
            params.put("price", String.valueOf(oncePrice));
            params.put("num", String.valueOf(selectCounts));
        }else if(Frequency10More>=0) {
            params.put("price", String.valueOf(Frequency10MorePrice));
            params.put("num", String.valueOf(Frequency10More));
        }
        params.put("mon", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        params.put("payType", payType);
        AlipayHelper.rechargePay(MyNewWalletActivity.this, coreManager,params);
    }

    /**
     * 根据类型判断是不是超级爆光
     * @param switchType
     */
    private void swithSuperSolarize(int switchType){
        SuperSolarizePopupWindow RegretsUnLikePopupWindow=new SuperSolarizePopupWindow(MyNewWalletActivity.this,switchType,false);
        RegretsUnLikePopupWindow.setBtnOnClice(new SuperSolarizePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(int sumbitType) {
                //超级爆光再来一次
                if(switchType==1&&sumbitType==1){
                    openSuperSolarize();
                    //超级爆光滑动卡片
                }
            }
        });
    }

    /**
     * 打开超级爆光支付页面
     */
    private void  openSuperSolarize(){
        SuperCriticalLightWindow superCriticalLightWindow = new SuperCriticalLightWindow(this, userVIPPrivilegePrice);
        superCriticalLightWindow.setBtnOnClice(new SuperCriticalLightWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type) {
                LogUtil.e("type =  " + type);
                payFunction=1;
                //0 是微信  1钱包  2是支付宝
                superLightPay(type,userVIPPrivilegePrice.getOutPrice());
            }
        });
    }

    /**
     *超级爆光支付
     */
    private void superLightPay(String  type,int  outPrice){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("funType", String.valueOf(1));
        params.put("price", String.valueOf(outPrice));
        params.put("num", String.valueOf(1));
        params.put("mon", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        params.put("payType", type);
        AlipayHelper.rechargePay(MyNewWalletActivity.this, coreManager,params);
    }

    /**
     * 超级爆光
     * @param user
     */
    private  void superLight(User user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        params.put("nickname",user.getNickName());
        params.put("age",user.getAge()+"");
        HttpUtils.post().url(coreManager.getConfig().USER_OPEN_SUPEREXPOSURE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (Result.checkSuccess(MyNewWalletActivity.this, result)) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                            if(coreManager.getSelf().getUserVIPPrivilege().getOutFlag()==1){
                                swithSuperSolarize(1);

                            }
                            LogUtil.e("result = " +result.getData());
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
