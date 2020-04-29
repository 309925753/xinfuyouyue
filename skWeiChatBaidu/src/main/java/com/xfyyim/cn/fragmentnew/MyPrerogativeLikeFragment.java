package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.makeramen.roundedimageview.RoundedImageView;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.UserVIPPrivilege;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me.CheckLikesMeActivity;
import com.xfyyim.cn.ui.me.payCompleteActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.util.ArithUtils;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;


public class MyPrerogativeLikeFragment extends EasyFragment {

    @BindView(R.id.ivUserHead)
    RoundedImageView ivUserHead;
    @BindView(R.id.tvDataTime)
    TextView tvDataTime;
    @BindView(R.id.tvSelect)
    TextView tvSelect;
    private UserVIPPrivilege userVIPPrivilege = new UserVIPPrivilege();
    private UserVIPPrivilegePrice userVIPPrivilegePrice = new UserVIPPrivilegePrice();

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_my_prerogative_like;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();
    }

    private void initView() {

        AvatarHelper.getInstance().displayAvatar(coreManager.getSelf().getUserId(), ivUserHead, true);

        findViewById(R.id.tvSelect).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (userVIPPrivilege.getLikePrivilegeFlag() == 0) {
                    //开通喜欢我的无限次数
                    getUserPrivilegeConfigInfo();
                } else {
                startActivity(new Intent(getActivity(), CheckLikesMeActivity.class));

                }
            }
        });
        getUserPrivilegeInfo();

    }
  /*  @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功
        startActivity(new Intent(getActivity(), payCompleteActivity.class));

    }*/

    /**
     *获取用户特权信息
     */
    private void getUserPrivilegeInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_PRIVILEGE_INFO)
                .params(params)
                .build()
                .execute(new BaseCallback<UserVIPPrivilege>(UserVIPPrivilege.class) {
                    @Override
                    public void onResponse(ObjectResult<UserVIPPrivilege> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            userVIPPrivilege = result.getData();
                            LogUtil.e("UserVIPPrivilege = " +userVIPPrivilege.toString());
                            // 普通特权-购买后可以查看谁喜欢我，有时限 0 - 无， 1 - 有
                            if (userVIPPrivilege.getLikePrivilegeFlag() == 1) {
                                //显示
                                tvSelect.setText("查看谁喜欢我");
                                tvDataTime.setText("查看");
                            } else {
                                //显示
                                tvSelect.setText("￥" + ArithUtils.round1(userVIPPrivilege.getLikePrice()) + "获取查看谁喜欢我");
                                tvDataTime.setText("暂未激活特权");
                                //进入查看谁喜欢我的页面
                                //  startActivity(new Intent(getActivity(), CheckLikesMeActivity.class));
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });

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
                        if (Result.checkSuccess(getActivity(), result)) {
                            userVIPPrivilegePrice = result.getData();
                            LogUtil.e("vipPrivilegePriceList = " + userVIPPrivilegePrice.toString());
                            if ( userVIPPrivilegePrice !=null){
                                BuyMember();
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }


    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember() {
        //谁喜欢我，在线聊天  购买
        MyPrivilegePopupWindow myBuy = new MyPrivilegePopupWindow(getActivity(), 1,"查看谁喜欢我", "哇，99+个小姐姐喜欢我!她们是谁？",userVIPPrivilegePrice);
        LogUtil.e("BuyMember  BuyMember");
        myBuy.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {
                LogUtil.e("**********************************************************");
                LogUtil.e("type = " + type + "---vip = " + vip);
                LogUtil.e("**********************************************************");
                payType(type,vip,userVIPPrivilegePrice);
            }
        });
    }

    /**
     * 支付类型
     * @param type
     * @param vip
     */
    private void payType(String type, int vip,UserVIPPrivilegePrice _userVIPPrivilegePrice ){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("payType", type);
        if(vip==1){
            params.put("price",_userVIPPrivilegePrice.getLikePrivilegePrice1()+"");
            params.put("mon", "1");
        }else if(vip==2){
            params.put("price",_userVIPPrivilegePrice.getLikePrivilegePrice2()+"");
            params.put("mon", "3");
        }else if(vip==3){
            params.put("price",_userVIPPrivilegePrice.getLikePrivilegePrice3()+"");
            params.put("mon", "12");
        }
        params.put("funType", String.valueOf(6));
        params.put("num", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        AlipayHelper.rechargePay(getActivity(), coreManager,params);
    }
}
