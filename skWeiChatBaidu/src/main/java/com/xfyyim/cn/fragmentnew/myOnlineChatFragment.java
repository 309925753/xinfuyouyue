package com.xfyyim.cn.fragmentnew;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.PrivilegeBean;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;


public class myOnlineChatFragment extends EasyFragment {

    private PrivilegeBean privilegeBean=new PrivilegeBean();


    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_my_online_chat;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {

        initView();

    }

    private void initView() {
            //暂未开通 剩余9次
        TextView  tvDataTime=(TextView)findViewById(R.id.tvDataTime);
        TextView  tvFlashChat=(TextView)findViewById(R.id.tvFlashChat);


        if(privilegeBean.getMatchTimesPerDay()>=1){
            tvDataTime.setText("暂未开通 剩余"+privilegeBean.getMatchTimesPerDay()+"次");
        }
        //matchTimesBuy
        if(privilegeBean.getMatchTimesBuy()>=1){
            tvDataTime.setText("剩余"+privilegeBean.getMatchTimesBuy()+"次");
            tvFlashChat.setText("续费闪聊");
        }


        findViewById(R.id.rlGetChat).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //开通喜欢我的无限次数
                OpenMember();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void OpenMember() {
        MyPrivilegePopupWindow my = new MyPrivilegePopupWindow(getActivity(), "每月90个闪聊配对", "每日3个蒙脸的在线配对，实时互动，畅聊无阻，\n                 通过聊天解锁对方头像");
        LogUtil.e("BuyMember  BuyMember");
        my.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type,int vip) {
                LogUtil.e("**********************************************************");
                LogUtil.e("type = " +type +"---vip = " +vip);
                LogUtil.e("**********************************************************");
            }
        });
    }


}
