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


public class MyPrerogativeLikeFragment extends EasyFragment {

    private PrivilegeBean privilegeBean=new PrivilegeBean();
    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_my_prerogative_like;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();
    }

    private void initView() {

        TextView tvSelect=(TextView)findViewById(R.id.tvSelect);
        // 普通特权-购买后可以查看谁喜欢我，有时限 0 - 无， 1 - 有
        if(privilegeBean.getVipLevel()==-1){
            //显示
            tvSelect.setText("￥"+10+"获取查看谁喜欢我");
        }else {
            //进入查看谁喜欢我的页面
          //  startActivity(new Intent(getActivity(), CheckLikesMeActivity.class));
        }

        findViewById(R.id.tvSelect).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(privilegeBean.getVipLevel()!=-1){
                    //开通喜欢我的无限次数
                    BuyMember();
                }
            }
        });
    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember() {
        //谁喜欢我，在线聊天  购买
        MyPrivilegePopupWindow myBuy = new MyPrivilegePopupWindow(getActivity(), "查看谁喜欢我", "哇，99+个小姐姐喜欢我!她们是谁？");
        LogUtil.e("BuyMember  BuyMember");
        myBuy.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type,int vip) {
                LogUtil.e("**********************************************************");
                LogUtil.e("type = " +type +"---vip = " +vip);
                LogUtil.e("**********************************************************");
            }
        });
    }

}
