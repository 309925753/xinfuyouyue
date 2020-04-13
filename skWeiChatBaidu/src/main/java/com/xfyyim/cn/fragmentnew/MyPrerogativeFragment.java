package com.xfyyim.cn.fragmentnew;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.PrivilegeBean;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.view.CenterPairingLoveDialog;
import com.xfyyim.cn.view.MyVipPaymentPopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class MyPrerogativeFragment extends EasyFragment {

    private PopupWindow popupWindow;
    private View contentView;
    private List<View> viewList = new ArrayList<>();
    private ViewPager mViewPager;
    private   PrivilegeBean  privilegeBean=new PrivilegeBean();

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_my_prerogative;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();
        showPopwindow();
    }

    private void initView() {
         TextView  tvDataTime=(TextView)findViewById(R.id.tvDataTime);
        TextView  tvBuyVipPerogative=(TextView)findViewById(R.id.tvBuyVipPerogative);

        if(privilegeBean.getVipLevel()==-1){
            tvDataTime.setText("暂未激活会员");
            tvBuyVipPerogative.setText("￥9.9获取VIP会员");
        }else {
         //   tvDataTime.setText(privilegeBean.getVipExpiredTime()+"");
            tvDataTime.setText("VIP会员（2020-03-24到期）");
            tvBuyVipPerogative.setText("续费VIP会员");
        }


        findViewById(R.id.tvBuyVipPerogative).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(privilegeBean.getVipLevel()==-1){
                    //买会员
                    BuyMember();
                }
            }
        });
        findViewById(R.id.llMyMeber).setOnClickListener(this::onClick);
        findViewById(R.id.rlFiveLike).setOnClickListener(this::onClick);
        findViewById(R.id.llRegret).setOnClickListener(this::onClick);
        findViewById(R.id.llChangePosition).setOnClickListener(this::onClick);
        findViewById(R.id.llLikeTimes).setOnClickListener(this::onClick);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyMeber:
                openPopWindow(v, 0);
                break;
            case R.id.rlFiveLike:
                openPopWindow(v, 1);
                break;
            case R.id.llRegret:
                openPopWindow(v, 2);
                break;
            case R.id.llChangePosition:
                openPopWindow(v, 3);
                break;
            case R.id.llLikeTimes:
                openPopWindow(v, 4);
                break;
        }
    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember() {
        //显示VIP购买会员
        MyVipPaymentPopupWindow myVipPaymentPopupWindow = new MyVipPaymentPopupWindow(getActivity());
        //谁喜欢我，在线聊天  购买
        //   MyPrivilegePopupWindow  my=new MyPrivilegePopupWindow(getActivity());
        LogUtil.e("BuyMember  BuyMember");
        myVipPaymentPopupWindow.setBtnOnClice(new MyVipPaymentPopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type,int vip) {
                LogUtil.e("*********************************type = " +type+"-------------vip = " +vip);
            }
        });
    }


    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

    }

    public void setOnClick() {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    public void openPopWindow(View v, int position) {
        //加载弹出框的布局
        contentView = getActivity().getLayoutInflater().inflate(
                R.layout.dialog_myprivilege_layout, null);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        mViewPager = (ViewPager) contentView.findViewById(R.id.vp_Privilege);
        //VIP会员权益
        View children_prerogative = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_children_prerogative, null);
        children_prerogative.findViewById(R.id.tvPayVip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchType(1);
            }
        });
        //每天5个超级喜欢
        View children_prerogative_like = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_children_prerogative_like, null);
        children_prerogative_like.findViewById(R.id.tvGetVip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(" into  bottomAnimDialog");

                switchType(2);
            }
        });
        //滑错无限反悔
        View children_online_chat = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_children_slip_error, null);
        children_online_chat.findViewById(R.id.tvPaySlip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchType(3);
            }
        });

        //任意定位
        View children_prerogative_location = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_children_prerogative_location, null);
        children_prerogative_location.findViewById(R.id.tvPayLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchType(4);
            }
        });

        //喜欢次数
        View children_prerogative_times = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_children_prerogative_times, null);

        children_prerogative_times.findViewById(R.id.tvPayLikes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchType(5);
            }
        });


        viewList.add(children_prerogative);
        viewList.add(children_prerogative_like);
        viewList.add(children_online_chat);
        viewList.add(children_prerogative_location);
        viewList.add(children_prerogative_times);


        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getContext());
        viewPageAdapter.setData(viewList);
        mViewPager.setAdapter(viewPageAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(viewList.size() - 1);

        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_style_bg));
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        setOnClick();

        //从底部显示
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        int width = (int) getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
        int height = (int) getContext().getResources().getDisplayMetrics().heightPixels / 2 + ((int) getContext().getResources().getDisplayMetrics().heightPixels / 4); // 高度
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 判断选择的类型
     * @param type
     */
    private void switchType(int  type){
        popupWindow.dismiss();
        if(type==2){
            //弹框显示是配对喜欢
            showBottomDialog();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                BuyMember();
            }
        }
    }

    private void showBottomDialog() {
        CenterPairingLoveDialog bottomAnimDialog = new CenterPairingLoveDialog(getActivity(), R.style.CustomDialog);
        bottomAnimDialog.show();
        bottomAnimDialog.setBtnOnClice(new CenterPairingLoveDialog.BtnOnClick() {
            @Override
            public void btnOnClick(String type) {
                LogUtil.e("------------type =" +type);
            }
        });
    }

    public class ViewPageAdapter extends PagerAdapter {
        List<View> viewsList;
        private LayoutInflater inflater;
        Context context;

        public ViewPageAdapter(Context context) {
            this.context = context;
            viewsList = new ArrayList<>();
            inflater = LayoutInflater.from(context);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return viewsList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View view = viewsList.get(position);
            viewGroup.addView(view);
            return view;
        }

        public void setData(List<View> list) {
            this.viewsList = list;
            if (list == null || list.size() == 0) {
                return;
            }
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
