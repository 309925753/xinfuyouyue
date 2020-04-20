package com.xfyyim.cn.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;

/*
在线闪聊待接收
 */
public class WaitingChatPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mMenuView;
    private WaitingChatPopupWindow.BtnOnClick btnOnClick;
    private Context context;

    public WaitingChatPopupWindow(FragmentActivity context, User  user) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.item_waiting_chat, null);
        TextView ivChatStatus=(TextView)mMenuView.findViewById(R.id.ivChatStatus);
        TextView tvUserName=(TextView)mMenuView.findViewById(R.id.tvUserName);
        TextView tvInterest1=(TextView)mMenuView.findViewById(R.id.tvInterest1);
        TextView tvInterest2=(TextView)mMenuView.findViewById(R.id.tvInterest2);
        TextView tvInterest3=(TextView)mMenuView.findViewById(R.id.tvInterest3);

        TextView tvImmediatelyChat=(TextView)mMenuView.findViewById(R.id.tvImmediatelyChat);
        tvImmediatelyChat.setOnClickListener(this::onClick);
        tvUserName.setText(user.getNickName()+","+user.getAge());
        if((!TextUtils.isEmpty(user.getMyIndustry()))&&user.getMyIndustry().length()>0){
            String[]  myIndustry=user.getMyIndustry().split(",");
           /* tvInterest1.setText();
            tvInterest2.setText();
            tvInterest3.setText();*/

        }

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
        int height = (int) context.getResources().getDisplayMetrics().heightPixels / 2+((int) context.getResources().getDisplayMetrics().heightPixels / 11); // 高度
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
        void btnOnClick();
    }

    public void setBtnOnClice(WaitingChatPopupWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.tvImmediatelyChat:
                btnOnClick.btnOnClick();
                break;
        }
    }
}