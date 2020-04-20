package com.xfyyim.cn.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.xfyyim.cn.R;


public class SuperSolarizePopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private SuperSolarizePopupWindow.BtnOnClick btnOnClick;
    private Context context;
    private int functionType;

    public SuperSolarizePopupWindow(FragmentActivity context,int switchType) {
        super(context);
        this.context = context;
        this.functionType=switchType;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.home_pull_back_layout, null);


        LinearLayout  llSuer=(LinearLayout)mMenuView.findViewById(R.id.llSuer);
        ImageView  ivpullBack=(ImageView)mMenuView.findViewById(R.id.ivpullBack);

        TextView  tvForget=(TextView)mMenuView.findViewById(R.id.tvForget);
        TextView  tvHint=(TextView)mMenuView.findViewById(R.id.tvHint);
        TextView  tvConfirm=(TextView)mMenuView.findViewById(R.id.tvConfirm);
        TextView  tvSlideCard=(TextView)mMenuView.findViewById(R.id.tvSlideCard);
        //1表示超级曝光否则是无法反悔
        if(switchType==1){
            llSuer.setVisibility(View.VISIBLE);
            tvForget.setText("超级曝光已完成");
            tvHint.setText("刚刚你身边9.2倍数的用户看到了\n  你快去滑动与他们配对吧");
            tvConfirm.setText("再来一次");
            tvSlideCard.setText("滑动卡片");
            tvSlideCard.setVisibility(View.VISIBLE);
        }else {
            llSuer.setVisibility(View.GONE);
            ivpullBack.setVisibility(View.VISIBLE);
            tvForget.setText("无法反悔");
            tvHint.setText("无法撤回，请先滑动一张卡片");
            tvConfirm.setText("好");
            tvSlideCard.setVisibility(View.GONE);

        }


        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //	        this.setWidth(ViewPiexlUtil.dp2px(context,200));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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
       /* int height = (int) context.getResources().getDisplayMetrics().heightPixels / 2+((int) context.getResources().getDisplayMetrics().heightPixels / 11); // 高度
       */ this.setWidth(width - 100);
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
        tvConfirm.setOnClickListener(this::onClick);
        tvSlideCard.setOnClickListener(this::onClick);


    }


    public interface BtnOnClick {
        void btnOnClick(int functionType);
    }

    public void setBtnOnClice(SuperSolarizePopupWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirm:
                if(functionType==1){
                    btnOnClick.btnOnClick(1);
                }else {
                    btnOnClick.btnOnClick(0);
                }
                dismiss();
                break;
            case R.id.tvSlideCard:
                if(functionType==1){
                    btnOnClick.btnOnClick(1);
                }else {
                    btnOnClick.btnOnClick(0);
                }
                dismiss();
                break;


        }

    }

}