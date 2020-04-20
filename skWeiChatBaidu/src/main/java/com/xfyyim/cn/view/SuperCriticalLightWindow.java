package com.xfyyim.cn.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.util.ArithUtils;

import static com.xfyyim.cn.MyApplication.getContext;

public class SuperCriticalLightWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private View mMenuView;
    private boolean walletSelect = true;
    private SuperCriticalLightWindow.BtnOnClick btnOnClick;
    private UserVIPPrivilegePrice userVIPPrivilegePrice = new UserVIPPrivilegePrice();

    public SuperCriticalLightWindow(Activity context,UserVIPPrivilegePrice _userVIPPrivilegePrice) {
        super(context);
        //
        this.context = context;
        this.userVIPPrivilegePrice=_userVIPPrivilegePrice;
        //加载弹出框的布局
        mMenuView = context.getLayoutInflater().inflate(
                R.layout.dialog_super_critical_light_layout, null);

        TextView tvSetCheck = (TextView) mMenuView.findViewById(R.id.tvSetCheck);
        TextView tvSuperPrice = (TextView) mMenuView.findViewById(R.id.tvSuperPrice);
        tvSuperPrice.setText(ArithUtils.round1(userVIPPrivilegePrice.getOutPrice())+"");
        String title1 = "认充值即默认同意" + "<font color=\"#FC617E\">" + "《幸福有约购买协议》" + " " + "</font>" + "";
        tvSetCheck.setText(Html.fromHtml(title1));


        Drawable[] drawableLeft = {context.getResources().getDrawable(
                R.mipmap.my_prerogative_check)};
        tvSetCheck.setCompoundDrawablesWithIntrinsicBounds(drawableLeft[0], null, null, null);
        tvSetCheck.setCompoundDrawablePadding(20);
        tvSetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (walletSelect) {
                    walletSelect = false;
                    drawableLeft[0] = context.getResources().getDrawable(
                            R.mipmap.my_prerogative_checked);
                } else {
                    walletSelect = true;
                    drawableLeft[0] = context.getResources().getDrawable(
                            R.mipmap.my_prerogative_check);
                }
                tvSetCheck.setCompoundDrawablesWithIntrinsicBounds(drawableLeft[0], null, null, null);
                tvSetCheck.setCompoundDrawablePadding(20);

            }
        });

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
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(context.getDrawable(R.drawable.dialog_style_bg));
        //点击外部消失
        this.setOutsideTouchable(true);
        //从底部显示
        this.setTouchable(true);
       /* int width = (int) getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
        int height = (int) getContext().getResources().getDisplayMetrics().heightPixels / 2 + ((int) getContext().getResources().getDisplayMetrics().heightPixels / 10); // 高度
        this.setWidth(width);
        this.setHeight(height);*/
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);
        this.showAtLocation(mMenuView, Gravity.BOTTOM, 0, 0);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });

        mMenuView.findViewById(R.id.tvWalletAlipay).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.tvWalletWx).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.tvWalletBalance).setOnClickListener(this::onClick);
        tvSetCheck.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvWalletAlipay:
                btnOnClick.btnOnClick("0");
                dismiss();
                break;
            case R.id.tvWalletWx:
                btnOnClick.btnOnClick("1");
                dismiss();
                break;
            case R.id.tvWalletBalance:
                btnOnClick.btnOnClick("2");
                dismiss();
                break;
            case R.id.tvSetCheck:
                dismiss();
                break;

        }
    }

    public interface BtnOnClick {
        void btnOnClick(String type);
    }

    public void setBtnOnClice(SuperCriticalLightWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }


}
