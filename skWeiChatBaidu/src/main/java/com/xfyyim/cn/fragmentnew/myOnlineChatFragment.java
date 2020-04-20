package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.makeramen.roundedimageview.RoundedImageView;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.UserVIPPrivilege;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.message.ChatActivity;
import com.xfyyim.cn.util.ArithUtils;
import com.xfyyim.cn.view.ImmediateiyChatPopupWindow;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
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

    private void initView() {


        AvatarHelper.getInstance().displayAvatar(coreManager.getSelf().getUserId(), ivUserHead, true);


        findViewById(R.id.rlGetChat).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //
                if (userVIPPrivilege.getIsChatByMonthQuantity() == 1) {
                    //进行闪聊操作
                    openOnlineChat();
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
                openOnlineChat();
            }
        });
        getUserPrivilegeInfo();
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

                                tvFlashChat.setText("￥" + ArithUtils.round1(userVIPPrivilege.getChatByMonthPrice()) + "在线闪聊");

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
                OnlineChat();
            }
        });

    }

    private void OnlineChat(){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
       /* params.put("longitude",  MyApplication.getInstance().getBdLocationHelper().getLongitude()+"");
        params.put("latitude",   MyApplication.getInstance().getBdLocationHelper().getLatitude()+"");*/
        params.put("longitude",  1.0+"");
        params.put("latitude",  1.0+"");
        HttpUtils.post().url(coreManager.getConfig().USER_CHAT_ONLINE)
                .params(params)
                .build()
                .execute(new BaseCallback<Friend>(Friend.class) {
                    @Override
                    public void onResponse(ObjectResult<Friend> result) {

                        if (Result.checkSuccess(getActivity(), result)) {
                            Friend friend = result.getData();
                            if(friend!=null && (!TextUtils.isEmpty(friend.getUserId()+""))){
                                LogUtil.e("friend information  = " + friend.toString());
                                Intent intent = new Intent(getActivity(), ChatActivity.class);
                                intent.setClass(getActivity(), ChatActivity.class);
                                intent.putExtra(ChatActivity.FRIEND, friend);
                                startActivity(intent);
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
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
                LogUtil.e("**********************************************************");
                LogUtil.e("type = " + type + "---vip = " + vip);
                LogUtil.e("**********************************************************");
            }
        });
    }


}
