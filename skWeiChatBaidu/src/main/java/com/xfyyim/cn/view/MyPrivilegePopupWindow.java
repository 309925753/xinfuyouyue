package com.xfyyim.cn.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.xfyyim.cn.R;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;


public class MyPrivilegePopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private MyPrivilegePopupWindow.BtnOnClick btnOnClick;
    private Context context;
    private boolean selectChecked = true;
    private int vip1,vip2=1,vip3;

    public MyPrivilegePopupWindow(FragmentActivity context, String privilageTitle, String privilageNumber) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.my_privilege_payment_layout, null);
        RelativeLayout rLlightYellow = (RelativeLayout) mMenuView.findViewById(R.id.rLlightYellow);


        ImageView ivPrerogativeCheck = (ImageView) mMenuView.findViewById(R.id.ivPrerogativeCheck);

        mMenuView.findViewById(R.id.llIvprerogativeCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectChecked) {
                    selectChecked = false;
                    ivPrerogativeCheck.setImageResource(R.mipmap.my_prerogative_checked);
                    LogUtil.e("**************selectChecked=" + selectChecked);
                } else {
                    selectChecked = true;
                    ivPrerogativeCheck.setImageResource(R.mipmap.my_prerogative_check);
                    LogUtil.e("**************selectChecked=" + selectChecked);
                }

            }
        });

        TextView tvDiscount = (TextView) mMenuView.findViewById(R.id.tvDiscount);
        TextView tvDay = (TextView) mMenuView.findViewById(R.id.tvDay);
        TextView TvMoney = (TextView) mMenuView.findViewById(R.id.TvMoney);
        TextView TvOriginalMoney = (TextView) mMenuView.findViewById(R.id.TvOriginalMoney);

        TextView tvPrivilageTitle = (TextView) mMenuView.findViewById(R.id.tvPrivilageTitle);
        TextView tvPrivilageNumber = (TextView) mMenuView.findViewById(R.id.tvPrivilageNumber);
        tvPrivilageTitle.setText(privilageTitle);
        tvPrivilageNumber.setText(privilageNumber);


        RelativeLayout rLlightYellow2 = (RelativeLayout) mMenuView.findViewById(R.id.rLlightYellow2);
        TextView tvDiscount2 = (TextView) mMenuView.findViewById(R.id.tvDiscount2);
        TextView tvDay2 = (TextView) mMenuView.findViewById(R.id.tvDay2);
        TextView TvMoney2 = (TextView) mMenuView.findViewById(R.id.TvMoney2);
        TextView TvOriginalMoney2 = (TextView) mMenuView.findViewById(R.id.TvOriginalMoney2);


        RelativeLayout rLlightYellow3 = (RelativeLayout) mMenuView.findViewById(R.id.rLlightYellow3);
        TextView tvDiscount3 = (TextView) mMenuView.findViewById(R.id.tvDiscount3);
        TextView tvDay3 = (TextView) mMenuView.findViewById(R.id.tvDay3);
        TextView TvMoney3 = (TextView) mMenuView.findViewById(R.id.TvMoney3);
        TextView TvOriginalMoney3 = (TextView) mMenuView.findViewById(R.id.TvOriginalMoney3);


        //类型选择
        rLlightYellow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                vip1=1;
                vip2=0;
                vip3=0;
                rLlightYellow.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_red));

                //判断有没有折扣
                // tvDiscount.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                tvDiscount.setVisibility(View.VISIBLE);
                tvDay.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                //tv .setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                TvMoney.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                TvOriginalMoney.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                TvOriginalMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


                rLlightYellow2.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay2.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney2.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney2.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount2.setVisibility(View.INVISIBLE);


                rLlightYellow3.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay3.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney3.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney3.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount3.setVisibility(View.INVISIBLE);


            }
        });
        rLlightYellow2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                vip1=0;
                vip2=1;
                vip3=0;

                rLlightYellow2.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_red));

                //判断有没有折扣
                //   // tvDiscount.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                tvDiscount2.setVisibility(View.VISIBLE);

                tvDay2.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                TvMoney2.setTextColor((context.getColor(R.color.text_black_fb7a7a)));

                TvOriginalMoney2.setTextColor((context.getColor(R.color.text_black_fb7a7a)));


                rLlightYellow3.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay3.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney3.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney3.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount3.setVisibility(View.INVISIBLE);


                rLlightYellow.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount.setVisibility(View.INVISIBLE);


            }
        });

        rLlightYellow3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                vip1=0;
                vip2=0;
                vip3=1;

                rLlightYellow3.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_red));

                //判断有没有折扣
                tvDiscount3.setVisibility(View.VISIBLE);

                tvDay3.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                TvMoney3.setTextColor((context.getColor(R.color.text_black_fb7a7a)));
                TvOriginalMoney3.setTextColor((context.getColor(R.color.text_black_fb7a7a)));


                rLlightYellow.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount.setVisibility(View.INVISIBLE);


                rLlightYellow2.setBackground(context.getDrawable(R.drawable.bg_new_my_pure_lightyellow));
                tvDay2.setTextColor((context.getColor(R.color.text_black_666)));
                TvMoney2.setTextColor((context.getColor(R.color.text_black_333)));
                TvOriginalMoney2.setTextColor((context.getColor(R.color.text_black_999)));
                tvDiscount2.setVisibility(View.INVISIBLE);


            }
        });


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

    public void setBtnOnClice(MyPrivilegePopupWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAlipay://支付宝
                btnOnClick.btnOnClick("0",swichVip());
                dismiss();
                break;
            case R.id.rlWx://微信
                btnOnClick.btnOnClick("1",swichVip());
                dismiss();
                break;
            case R.id.rlBalance://余额
                btnOnClick.btnOnClick("2",swichVip());
                dismiss();
                break;
          /*  case R.id.rLlightYellow://

                break;
            case R.id.rLlightYellow2://

                break;
            case R.id.rLlightYellow3://

                break;*/

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
        }
        return vip;
    }
}