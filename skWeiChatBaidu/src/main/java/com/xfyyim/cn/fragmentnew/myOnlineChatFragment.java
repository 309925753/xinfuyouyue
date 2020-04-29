package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.UserVIPPrivilege;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventNotifyByTag;
import com.xfyyim.cn.bean.event.EventNotifyOnlineChat;
import com.xfyyim.cn.bean.event.EventNotifyWaitOnlineChat;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.bean.message.XmppMessage;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.MainActivity;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me.payCompleteActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.message.ChatActivity;
import com.xfyyim.cn.util.ArithUtils;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.ImmediateiyChatPopupWindow;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.WaitingChatPopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;


public class myOnlineChatFragment extends EasyFragment {

    @BindView(R.id.ivUserHead)
    RoundedImageView ivUserHead;
    @BindView(R.id.tvDataTime)
    TextView tvDataTime;
    @BindView(R.id.tvFlashChat)
    TextView tvFlashChat;
    @BindView(R.id.tvImmediatelyChat)
    TextView tvImmediatelyChat;
    private UserVIPPrivilege userVIPPrivilege = new UserVIPPrivilege();
    private UserVIPPrivilegePrice userVIPPrivilegePrice = new UserVIPPrivilegePrice();


    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_my_online_chat;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {

        initView();

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventNotifyOnlineChat message) {
        LogUtil.e("在线闪聊发起方");
        DialogHelper.dismissProgressDialog();
         Friend friend = JSON.parseObject(message.MessageData, Friend.class);
        ImmediateiyChatPopupWindow immediateiyChatPopupWindow=new ImmediateiyChatPopupWindow(getActivity(),coreManager.getSelf().getUserId());
        immediateiyChatPopupWindow.setBtnOnClice(new ImmediateiyChatPopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick() {
                if(friend!=null && (!TextUtils.isEmpty(friend.getUserId()+""))){
                    LogUtil.e("friend information  = " + friend.toString());
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.setClass(getActivity(), ChatActivity.class);
                    intent.putExtra(ChatActivity.FRIEND, friend);
                    startActivity(intent);
                }
            }
        });
    }



    private void initView() {
        findViewById(R.id.rlGetChat).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (userVIPPrivilege.getIsChatByMonthQuantity() == 1) {
                    //进行闪聊操作
                    OnlineChat();
                } else {
                    //开通喜欢我的无限次数
                    getUserPrivilegeConfigInfo();
                }
            }
        });
        tvImmediatelyChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //立即闪聊
                OnlineChat();

            }
        });
        getUserPrivilegeInfo();
        EventBusHelper.register(this);
    }
   /* @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功
        startActivity(new Intent(getActivity(), payCompleteActivity.class));
    }*/

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
                            if (userVIPPrivilegePrice != null) {
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
                            LogUtil.e("UserVIPPrivilege = " + userVIPPrivilege.toString());
                            //按月购买次数+闪聊买的次数（举报的次数）
                            if (userVIPPrivilege.getIsChatByMonthQuantity() == 1) {
                                tvDataTime.setText("剩余" + (userVIPPrivilege.getChatQuantity() + userVIPPrivilege.getChatByMonthQuantity()) + "次");
                                tvFlashChat.setText("在线闪聊");
                            } else {
                                if( userVIPPrivilege.getChatQuantity()>=1){
                                    tvImmediatelyChat.setVisibility(View.VISIBLE);
                                    tvDataTime.setText("暂未开通,剩余" + userVIPPrivilege.getChatQuantity() + "次");
                                }else {
                                    tvDataTime.setText("暂未开通");
                                }
                                tvFlashChat.setText("￥" + ArithUtils.round1(userVIPPrivilege.getChatByMonthPrice()) + "获取闪聊特权");
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
     * 闪聊操作
     */
    private void openOnlineChat() {
        //WaitingChatPopupWindow
        ImmediateiyChatPopupWindow immediateiyChatPopupWindow=new ImmediateiyChatPopupWindow(getActivity(),coreManager.getSelf().getUserId());
        immediateiyChatPopupWindow.setBtnOnClice(new ImmediateiyChatPopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick() {

            }
        });

    }

    private void OnlineChat(){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        if (PreferenceUtils.getBoolean(getActivity(),coreManager.getSelf().getUserId()+ SpContext.ISSELECT)){
            params.put("longitude",  String.valueOf(PreferenceUtils.getBoolean(getActivity(),coreManager.getSelf().getUserId()+ SpContext.LON)));
            params.put("latitude",   String.valueOf(PreferenceUtils.getBoolean(getActivity(),coreManager.getSelf().getUserId()+ SpContext.LAT)));
        }else {
            params.put("longitude",  String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLongitude()));
            params.put("latitude",   String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLatitude()));
        }
    /*   params.put("longitude",  MyApplication.getInstance().getBdLocationHelper().getLongitude()+"");
        params.put("latitude",   MyApplication.getInstance().getBdLocationHelper().getLatitude()+"");
      *//*  params.put("longitude",  1.0+"");
        params.put("latitude",  1.0+"");*/
        params.put("type",  "0");//type 类型（0-爸爸发起 1-儿子接收）
        params.put("fromUserId","0");
        DialogHelper.showDefaulteMessageProgressDialog(getActivity());
        HttpUtils.post().url(coreManager.getConfig().USER_CHAT_ONLINE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                      //  DialogHelper.dismissProgressDialog();
                        Timer timer = new Timer();// 实例化Timer类
                        timer.schedule(new TimerTask() {
                            public void run() {
                                DialogHelper.dismissProgressDialog();
                                this.cancel();
                            }
                        }, 3000);
                        if (Result.checkSuccess(getActivity(), result)) {

                          /*  Friend friend = result.getData();
                            if(friend!=null && (!TextUtils.isEmpty(friend.getUserId()+""))){
                                openOnlineChat( friend);
                                LogUtil.e("friend information  = " + friend.toString());
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.setClass(getActivity(), ChatActivity.class);
                                intent.putExtra(ChatActivity.FRIEND, friend);
                                startActivity(intent);
                            }*/
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showLongToast(getActivity(),"暂时没有配对人，请再试一下");
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember() {
        MyPrivilegePopupWindow my = new MyPrivilegePopupWindow(getActivity(), 2, "每月90个闪聊配对", "每日3个蒙脸的在线配对，实时互动，畅聊无阻，\n                 通过聊天解锁对方头像", userVIPPrivilegePrice);

        LogUtil.e("BuyMember  BuyMember");
        my.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {

                payType(type,vip);

                LogUtil.e("**********************************************************");
                LogUtil.e("type = " + type + "---vip = " + vip);
                LogUtil.e("**********************************************************");
            }
        });
    }

    /**
     * 支付类型
     * @param type
     * @param selectMonth
     */
    private void payType(String type, int selectMonth){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("payType", type);
        //判断月份
        if(selectMonth==1){
            params.put("price",String.valueOf(userVIPPrivilegePrice.getChatByMonthPrice1()));
            params.put("mon", "1");
        }else if(selectMonth==2){
            selectMonth=2;
            params.put("price",String.valueOf(userVIPPrivilegePrice.getChatByMonthPrice2()));
            params.put("mon", "3");
        }else if(selectMonth==3){
            selectMonth=3;
            params.put("price",String.valueOf(userVIPPrivilegePrice.getChatByMonthPrice3()));
            params.put("mon", "12");
        }
        params.put("funType", String.valueOf(7));
        params.put("num", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        AlipayHelper.rechargePay(getActivity(), coreManager,params);
    }
}
