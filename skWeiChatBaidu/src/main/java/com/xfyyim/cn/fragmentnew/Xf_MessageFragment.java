package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventNotifyOnlineChat;
import com.xfyyim.cn.bean.event.EventOnlieChat;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.fragment.MessageFragment;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.nocsroll.MessagePagerAdapter;
import com.xfyyim.cn.nocsroll.MyNavigationLayoutContainer;
import com.xfyyim.cn.nocsroll.NoScrollViewPager;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.message.ChatActivity;
import com.xfyyim.cn.ui.search.SearchAllActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.ImmediateiyChatPopupWindow;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

public class Xf_MessageFragment extends EasyFragment {


    List<Fragment> listFragments = new ArrayList<>();
    List<Integer> listIds = new ArrayList<>();

    int radioButtonWith;
    int indicatorHeight;
    MessageFragment messageFragment;
    AllnewFragment allFragmet;
    RadioGroup rg_choice;
    RadioButton rb_readmessage;
    RadioButton rb_unread;
    MyNavigationLayoutContainer myNavigationLayoutContainer;
    View myNavigationView;
    NoScrollViewPager vp_message_tab;
    private TextView mTvTitle;
    private TextView tvFriendCount;
    private TextView tvOnlineChatCount;
    private ImageView mIvTitleRight;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_xf_notifition;
    }


    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initActionBar();
        initView();
        setIndicatorWidth();
        setOnListener();
        addFragment();

    }

    public void initView() {
        rg_choice = findViewById(R.id.rg_choice);
        rb_readmessage = findViewById(R.id.rb_readmessage);
        rb_unread = findViewById(R.id.rb_unread);
        myNavigationLayoutContainer = findViewById(R.id.myNavigationLayoutContainer);
        vp_message_tab = findViewById(R.id.vp_message_tab);
        myNavigationView = findViewById(R.id.myNavigationView);
        ImageView avatar_img=findViewById(R.id.img_1);
        AvatarHelper.getInstance().displayAvatar(coreManager.getSelf().getUserId(), avatar_img, true);
        EventBusHelper.register(this);
    }

    private void addFragment() {
        listFragments.add(allFragmet = new AllnewFragment());
        listFragments.add(messageFragment = new MessageFragment());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        vp_message_tab.setAdapter(new MessagePagerAdapter(fm, listFragments, listIds));
        vp_message_tab.setOffscreenPageLimit(listFragments.size() - 1);
    }

    private void initActionBar() {
        findViewById(R.id.iv_title_left).setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_title_center);
        mTvTitle.setText("消息");
        mTvTitle.setTextColor(getResources().getColor(R.color.white));
        mIvTitleRight =  findViewById(R.id.iv_title_right);
        mIvTitleRight.setImageResource(R.mipmap.search_white);
        mIvTitleRight.setVisibility(View.VISIBLE);
        LinearLayout llOnlineChat=(LinearLayout)findViewById(R.id.llOnlineChat);
        llOnlineChat.setOnClickListener(this);
        tvOnlineChatCount = (TextView) findViewById(R.id.tvOnlineChatCount);

    }

    public void setOnListener() {

        rg_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vp_message_tab.setAnimation(false);
                switch (checkedId) {
                    case R.id.rb_unread:
                        rb_unread.setTextSize(16);
                        rb_readmessage.setTextSize(14);
                        vp_message_tab.setCurrentItem(0);
                        break;

                    case R.id.rb_readmessage:
                        rb_unread.setTextSize(14);
                        rb_readmessage.setTextSize(16);
                        vp_message_tab.setCurrentItem(1);
                        break;
                }
            }
        });


        vp_message_tab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float fraction, int positionOffsetPixels) {
                myNavigationLayoutContainer.scrollTo(-(Math.round(ScreenUtils.getScreenWidth(getContext()) / 2 - radioButtonWith - radioButtonWith / 2)), 0);
                if (position == 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
                    params.width = radioButtonWith / 2;
                    params.height = indicatorHeight;
                    params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 2 - radioButtonWith, 0, 0, 0);
                    myNavigationView.setLayoutParams(params);
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
                    params.width = (int) (radioButtonWith / 2);
                    params.height = indicatorHeight;
                    params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 3, 0, 0, 0);

                    myNavigationView.setLayoutParams(params);

                    myNavigationLayoutContainer.scrollTo(-(Math.round(ScreenUtils.getScreenWidth(getActivity()) / 3)), 0);
                }
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        rb_unread.setChecked(true);
                        break;

                    case 1:
                        rb_readmessage.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAllActivity.start(requireActivity(), "chatHistory");
            }
        });
    }

    private void setIndicatorWidth() {

        radioButtonWith = getResources().getDimensionPixelSize(R.dimen.fragment_message_radio_button_width);
        indicatorHeight = getResources().getDimensionPixelSize(R.dimen.fragment_message_radio_button_height);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
        params.width = radioButtonWith / 2;
        params.height = indicatorHeight;
        params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 2 - radioButtonWith, 0, 0, 0);
        params.addRule(Gravity.CENTER_VERTICAL);
        myNavigationView.setLayoutParams(params);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llOnlineChat:
                //判断用户有没有闪聊特权次数没有去支付
                if (coreManager.getSelf().getUserVIPPrivilege() != null) {
                if (coreManager.getSelf().getUserVIPPrivilege().getIsChatByMonthQuantity() == 1 || (coreManager.getSelf().getUserVIPPrivilege().getChatQuantity() + coreManager.getSelf().getUserVIPPrivilege().getChatByMonthQuantity() >= 1)) {
                    OnlineChat();
                } else {
                    BuyMember(coreManager.getSelf().getUserVIPPrivilegeConfig());
                }
                  }
                break;
            case R.id.img_1:
                break;

        }
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventNotifyOnlineChat message) {
        LogUtil.e("在线闪聊发起方");
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

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        //支付成功
        updateSelfData();
    }
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventOnlieChat message) {
        LogUtil.e("message online = " +message.MessageData);
        if(!TextUtils.isEmpty(message.MessageData)){
            tvOnlineChatCount.setText("当前在线"+message.MessageData+"人");
        }

    }
    /**
     * 支付完成后更新用户信息
     */
    private void updateSelfData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }

    private void OnlineChat(){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
     /*   params.put("longitude",  MyApplication.getInstance().getBdLocationHelper().getLongitude()+"");
        params.put("latitude",   MyApplication.getInstance().getBdLocationHelper().getLatitude()+"");
      *//*  params.put("longitude",  1.0+"");
        params.put("latitude",  1.0+"");*/
        if (PreferenceUtils.getBoolean(getActivity(),coreManager.getSelf().getUserId()+ SpContext.ISSELECT)){
            params.put("longitude",  PreferenceUtils.getString(getActivity(),coreManager.getSelf().getUserId()+ SpContext.LON));
            params.put("latitude",   PreferenceUtils.getString(getActivity(),coreManager.getSelf().getUserId()+ SpContext.LAT));
        }else {
            params.put("longitude",  String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLongitude()));
            params.put("latitude",   String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLatitude()));
        }
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
                        }, 5000);
                        if (Result.checkSuccess(getActivity(), result)) {
                            DialogHelper.dismissProgressDialog();
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
    private void BuyMember(UserVIPPrivilegePrice  userVIPPrivilegePrice) {
        MyPrivilegePopupWindow my = new MyPrivilegePopupWindow(getActivity(), 2, "每月90个闪聊配对", "每日3个蒙脸的在线配对，实时互动，畅聊无阻，\n                 通过聊天解锁对方头像", userVIPPrivilegePrice);

        LogUtil.e("BuyMember  BuyMember");
        my.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {
                payChat(type,vip,userVIPPrivilegePrice);
            }
        });
    }
    /**
     * 支付类型
     * @param type
     * @param selectMonth
     */
    private void payChat(String type, int selectMonth, UserVIPPrivilegePrice  userVIPPrivilegePrice){
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
