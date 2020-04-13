package com.xfyyim.cn.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.xfyyim.cn.R;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class MyVipPaymentPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private MyVipPaymentPopupWindow.BtnOnClick btnOnClick;
    private Context context;
    private List<View> viewList = new ArrayList<>();
    private boolean selectChecked = true;
    private  LinearLayout  rLVipYellow,rLVipYellow2,rLVipYellow3,rLVipYellow4;
    private TextView   tvMember,tvMember2,tvMember3,tvMember4;
    private TextView   Tvmonth,Tvmonth2,Tvmonth3,Tvmonth4;
    private TextView   TvVipMoney,TvVipMoney2,TvVipMoney3,TvVipMoney4;
    private TextView   tvVipLow,tvVipLow2,tvVipLow3,tvVipLow4;
    private TextView   tvVipProvince,tvVipProvince2,tvVipProvince3,tvVipProvince4;
    private int vip1=1,vip2,vip3,vip4;



    @RequiresApi(api = Build.VERSION_CODES.O)
    public MyVipPaymentPopupWindow(FragmentActivity context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.my_vip_payment_layout, null);

        ViewPager mViewPager = (ViewPager) mMenuView.findViewById(R.id.vpVip);
        ImageView ivPrerogativeSelect = (ImageView) mMenuView.findViewById(R.id.ivPrerogativeSelect);

        mMenuView.findViewById(R.id.llPrerogativeSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectChecked) {
                    selectChecked = false;
                    ivPrerogativeSelect.setImageResource(R.mipmap.my_prerogative_checked);
                    LogUtil.e("**************selectChecked=" + selectChecked);
                } else {
                    selectChecked = true;
                    ivPrerogativeSelect.setImageResource(R.mipmap.my_prerogative_check);
                    LogUtil.e("**************selectChecked=" + selectChecked);
                }
            }
        });

        //VIP会员权益
        View children_prerogative = context.getLayoutInflater().inflate(
                R.layout.item_vip_prerogative, null);

        //每天5个超级喜欢
        View children_prerogative_like = context.getLayoutInflater().inflate(
                R.layout.item_vip_prerogative_like, null);

        //滑错无限反悔
        View children_online_chat = context.getLayoutInflater().inflate(
                R.layout.item_vip_prerogative_location, null);

        //任意定位
        View children_prerogative_location = context.getLayoutInflater().inflate(
                R.layout.item_vip_prerogative_location, null);
    ImageView    ivVIpCenter  =children_prerogative_location.findViewById(R.id.ivVIpCenter);
        ivVIpCenter.setImageResource(R.mipmap.my_vip_change_positioning);
        TextView tvPayType = (TextView) children_prerogative_location.findViewById(R.id.tvPayType);
        tvPayType.setText("任意更改定位");
        TextView tvPayTypeContent = (TextView) children_prerogative_location.findViewById(R.id.tvPayTypeContent);
        tvPayType.setText(" 位置漫游，与全球朋友say嗨！");
       ImageView ivVipPoint=children_prerogative_location.findViewById(R.id.ivVipPoint);
        ivVipPoint.setImageResource(R.mipmap.my_prerogative_point_four);

        //喜欢次数
        View children_prerogative_times = context.getLayoutInflater().inflate(
                R.layout.item_vip_prerogative_location, null);
     ImageView   ivVIpCenterLikeTimes=   children_prerogative_times.findViewById(R.id.ivVIpCenter);
        ivVIpCenterLikeTimes.setImageResource(R.mipmap.my_vip_liking_times);
        TextView tvPayLikeType = (TextView) children_prerogative_times.findViewById(R.id.tvPayType);
        tvPayLikeType.setText("无限喜欢次数");
        TextView tvPayContent = (TextView) children_prerogative_times.findViewById(R.id.tvPayTypeContent);
        tvPayContent.setText("突破限制，放肆喜欢不再顾虑！");
      ImageView  ivVipPointFive=children_prerogative_times.findViewById(R.id.ivVipPoint);
        ivVipPointFive.setImageResource(R.mipmap.my_prerogative_point_five);

        viewList.add(children_prerogative);
        viewList.add(children_prerogative_like);
        viewList.add(children_online_chat);
        viewList.add(children_prerogative_location);
        viewList.add(children_prerogative_times);

        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(viewList.size() - 1);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(context);
        viewPageAdapter.setData(viewList);
        mViewPager.setAdapter(viewPageAdapter);



        rLVipYellow=(LinearLayout)mMenuView.findViewById(R.id.rLVipYellow);
        tvVipProvince=(TextView)mMenuView.findViewById(R.id.tvVipProvince);
           tvMember=(TextView)mMenuView.findViewById(R.id.tvMember);
           Tvmonth=(TextView)mMenuView.findViewById(R.id.Tvmonth);
           TvVipMoney=(TextView)mMenuView.findViewById(R.id.TvVipMoney);
           tvVipLow=(TextView)mMenuView.findViewById(R.id.tvVipLow);



           rLVipYellow2=(LinearLayout)mMenuView.findViewById(R.id.rLVipYellow2);
          tvVipProvince2=(TextView)mMenuView.findViewById(R.id.tvVipProvince2);
           tvMember2=(TextView)mMenuView.findViewById(R.id.tvMember2);
           Tvmonth2=(TextView)mMenuView.findViewById(R.id.Tvmonth2);
           TvVipMoney2=(TextView)mMenuView.findViewById(R.id.TvVipMoney2);
           tvVipLow2=(TextView)mMenuView.findViewById(R.id.tvVipLow2);


           rLVipYellow3=(LinearLayout)mMenuView.findViewById(R.id.rLVipYellow3);
        tvVipProvince3=(TextView)mMenuView.findViewById(R.id.tvVipProvince3);
           tvMember3=(TextView)mMenuView.findViewById(R.id.tvMember3);
           Tvmonth3=(TextView)mMenuView.findViewById(R.id.Tvmonth3);
           TvVipMoney3=(TextView)mMenuView.findViewById(R.id.TvVipMoney3);
           tvVipLow3=(TextView)mMenuView.findViewById(R.id.tvVipLow3);

           rLVipYellow4=(LinearLayout)mMenuView.findViewById(R.id.rLVipYellow4);
          tvVipProvince4=(TextView)mMenuView.findViewById(R.id.tvVipProvince4);
           tvMember4=(TextView)mMenuView.findViewById(R.id.tvMember4);
           Tvmonth4=(TextView)mMenuView.findViewById(R.id.Tvmonth4);
           TvVipMoney4=(TextView)mMenuView.findViewById(R.id.TvVipMoney4);
           tvVipLow4=(TextView)mMenuView.findViewById(R.id.tvVipLow4);


        rLVipYellow.setOnClickListener(this::onClick);
        rLVipYellow2.setOnClickListener(this::onClick);
        rLVipYellow3.setOnClickListener(this::onClick);
        rLVipYellow4.setOnClickListener(this::onClick);

        //支付选择
        mMenuView.findViewById(R.id.rlAlipay).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.rlWx).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.rlBalance).setOnClickListener(this::onClick);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //	        this.setWidth(ViewPiexlUtil.dp2px(context,200));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Buttom_Popwindow);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.alp_background));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(context.getDrawable(R.drawable.dialog_style_bg));
        //点击外部消失
        this.setOutsideTouchable(true);
        //从底部显示
        this.setTouchable(true);
        this.setFocusable(true);
        int width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        int height = (int) context.getResources().getDisplayMetrics().heightPixels / 2 + ((int) context.getResources().getDisplayMetrics().heightPixels / 4); // 高度
        this.setWidth(width - 100);
        this.setHeight(height);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);
        this.showAtLocation(mMenuView, Gravity.CENTER, 0, 0);


        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });
    }


    public interface BtnOnClick {
        void btnOnClick(String type, int vip);
    }

    public void setBtnOnClice(MyVipPaymentPopupWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAlipay://支付宝
                 btnOnClick.btnOnClick("0", swichVip());
                dismiss();
                break;
            case R.id.rlWx://微信
                swichVip();
                 btnOnClick.btnOnClick("1",swichVip());
                dismiss();
                break;
            case R.id.rlBalance://余额
                swichVip();
                   btnOnClick.btnOnClick("2",swichVip());
                dismiss();
                break;
            case R.id.rLVipYellow://
                setSelectrLVipYellow();
                setrLVipYellow2();
                setrLVipYellow3();
                setrLVipYellow4();
                vip1=1;
                vip2=0;
                vip3=0;
                vip4=0;
                break;
            case R.id.rLVipYellow2://
                setrLVipYellow();
                setSelectrLVipYellow2();
                setrLVipYellow3();
                setrLVipYellow4();
                vip1=0;
                vip2=1;
                vip3=0;
                vip4=0;
                break;
            case R.id.rLVipYellow3://
                setrLVipYellow();
                setrLVipYellow2();
                setSelectrLVipYellow3();
                setrLVipYellow4();
                vip1=0;
                vip2=0;
                vip3=1;
                vip4=0;
                break;
            case R.id.rLVipYellow4://
                setrLVipYellow();
                setrLVipYellow3();
                setrLVipYellow3();
                setSelectrLVipYellow4();
                vip1=0;
                vip2=0;
                vip3=0;
                vip4=1;
                break;

        }

    }
    private int swichVip(){
        int vip=0;
        if(vip1==1){
            vip= 1;
        }else if(vip2==1){
            vip=2;
        }else if(vip3==1){
            vip=3;
        }else {
            vip=4;
        }
        return vip;
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
    private void setSelectrLVipYellow(){
        rLVipYellow.setBackground(context.getDrawable(R.drawable.bg_new_my_wallet_pay_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince.setVisibility(View.VISIBLE);
            tvMember.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            Tvmonth.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            TvVipMoney.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            tvVipLow.setVisibility(View.VISIBLE);;
        }
    }
    private void setrLVipYellow(){
        rLVipYellow.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince.setVisibility(View.INVISIBLE);
            tvMember.setTextColor((context.getColor(R.color.text_black_666)));
            Tvmonth.setTextColor((context.getColor(R.color.text_black_333)));
            TvVipMoney.setTextColor((context.getColor(R.color.text_black_999)));
            tvVipLow.setVisibility(View.INVISIBLE);
        }
    }

    private void setSelectrLVipYellow2(){
        rLVipYellow2.setBackground(context.getDrawable(R.drawable.bg_new_my_wallet_pay_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince2.setVisibility(View.VISIBLE);
            tvMember2.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            Tvmonth2.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            TvVipMoney2.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            tvVipLow2.setVisibility(View.VISIBLE);;
        }
    }
    private void setrLVipYellow2(){
        rLVipYellow2.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince2.setVisibility(View.INVISIBLE);
            tvMember2.setTextColor((context.getColor(R.color.text_black_666)));
            Tvmonth2.setTextColor((context.getColor(R.color.text_black_333)));
            TvVipMoney2.setTextColor((context.getColor(R.color.text_black_999)));
            tvVipLow2.setVisibility(View.INVISIBLE);
        }
    }

    private void setSelectrLVipYellow3(){
        rLVipYellow3.setBackground(context.getDrawable(R.drawable.bg_new_my_wallet_pay_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince3.setVisibility(View.VISIBLE);
            tvMember3.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            Tvmonth3.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            TvVipMoney3.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            tvVipLow3.setVisibility(View.VISIBLE);;
        }
    }
    private void setrLVipYellow3(){
        rLVipYellow3.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince3.setVisibility(View.INVISIBLE);
            tvMember3.setTextColor((context.getColor(R.color.text_black_666)));
            Tvmonth3.setTextColor((context.getColor(R.color.text_black_333)));
            TvVipMoney3.setTextColor((context.getColor(R.color.text_black_999)));
            tvVipLow3.setVisibility(View.INVISIBLE);
        }
    }
    private void setSelectrLVipYellow4(){
        rLVipYellow4.setBackground(context.getDrawable(R.drawable.bg_new_my_wallet_pay_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince4.setVisibility(View.VISIBLE);
            tvMember4.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            Tvmonth4.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            TvVipMoney4.setTextColor((context.getColor(R.color.text_fffb7a7a)));
            tvVipLow4.setVisibility(View.VISIBLE);;
        }
    }
    private void setrLVipYellow4(){
        rLVipYellow4.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvVipProvince4.setVisibility(View.INVISIBLE);
            tvMember4.setTextColor((context.getColor(R.color.text_black_666)));
            Tvmonth4.setTextColor((context.getColor(R.color.text_black_333)));
            TvVipMoney4.setTextColor((context.getColor(R.color.text_black_999)));
            tvVipLow4.setVisibility(View.INVISIBLE);
        }
    }
}