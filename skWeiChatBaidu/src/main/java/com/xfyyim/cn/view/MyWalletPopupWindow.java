package com.xfyyim.cn.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.util.ArithUtils;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfyyim.cn.MyApplication.getContext;

public class MyWalletPopupWindow extends PopupWindow implements View.OnClickListener {

    private boolean isBuyTimes = true, isBuyTimes2 = true, isBuyTimes3 = true, isBuyTimes4 = true, walletSelect = true;
    private Context context;
    private View mMenuView;
    private MyWalletPopupWindow.BtnOnClick btnOnClick;
    private   String  inputMoney=null;
    private UserVIPPrivilegePrice userVIPPrivilegePrice = new UserVIPPrivilegePrice();
    private  int  functionType;
    private  int  Frequency10More;//10次以上
    private  int  Frequency10MorePrice;//10次以上总价
    private  int oncePrice=userVIPPrivilegePrice.getV2Price();   //选中的单价
    private int selectCounts=2; //选中的数量

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MyWalletPopupWindow(Activity context,UserVIPPrivilegePrice _userVIPPrivilegePrice,int functionType) {
        super(context);
        this.context = context;
        this.userVIPPrivilegePrice=_userVIPPrivilegePrice;
        this.functionType=functionType;
            //加载弹出框的布局
            mMenuView = context.getLayoutInflater().inflate(
                    R.layout.dialog_mywallet_layout, null);

            TextView tvSetCheck = (TextView) mMenuView.findViewById(R.id.tvSetCheck);

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
                        drawableLeft[0] =context.getResources().getDrawable(
                                R.mipmap.my_prerogative_check);
                    }

                    tvSetCheck.setCompoundDrawablesWithIntrinsicBounds(drawableLeft[0], null, null, null);
                    tvSetCheck.setCompoundDrawablePadding(20);

                }
            });


            TextView tvBuyTimes = (TextView) mMenuView.findViewById(R.id.tvBuyTimes);
            TextView tvBuyMoney = (TextView) mMenuView.findViewById(R.id.tvBuyMoney);

            TextView tvBuyTimes2 = (TextView) mMenuView.findViewById(R.id.tvBuyTimes2);
            TextView tvBuyMoney2 = (TextView) mMenuView.findViewById(R.id.tvBuyMoney2);

            TextView tvBuyTimes3 = (TextView) mMenuView.findViewById(R.id.tvBuyTimes3);
            TextView tvBuyMoney3 = (TextView) mMenuView.findViewById(R.id.tvBuyMoney3);

            TextView tvBuyTimes4 = (TextView) mMenuView.findViewById(R.id.tvBuyTimes4);
            TextView tvBuyMoney4 = (TextView) mMenuView.findViewById(R.id.tvBuyMoney4);

            LinearLayout rlBuyTimes4 = (LinearLayout) mMenuView.findViewById(R.id.rlBuyTimes4);
            LinearLayout rlBuyTimes3 = (LinearLayout) mMenuView.findViewById(R.id.rlBuyTimes3);
            LinearLayout rlBuyTimes = (LinearLayout) mMenuView.findViewById(R.id.rlBuyTimes);
            LinearLayout rlBuyTimes2 = (LinearLayout) mMenuView.findViewById(R.id.rlBuyTimes2);

        /**
         * 功能类型
         */
        switch (functionType){
                case 2:
                    tvBuyMoney.setText(ArithUtils.round1(userVIPPrivilegePrice.getSuperLikeByFrequency1())+"RMB");
                    tvBuyMoney2.setText(ArithUtils.round1(userVIPPrivilegePrice.getSuperLikeByFrequency2())+"RMB");
                    tvBuyMoney3.setText(ArithUtils.round1(userVIPPrivilegePrice.getSuperLikeByFrequency10())+"RMB");
                    tvBuyMoney4.setText(ArithUtils.round1(userVIPPrivilegePrice.getSuperLikeByFrequency10More())+"RMB");
                    Frequency10MorePrice=userVIPPrivilegePrice.getSuperLikeByFrequency10More();
                    oncePrice=userVIPPrivilegePrice.getSuperLikeByFrequency2();
                    break;
                case 3:
                    tvBuyMoney.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatByFrequency1())+"RMB");
                    tvBuyMoney2.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatByFrequency2())+"RMB");
                    tvBuyMoney3.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatByFrequency10())+"RMB");
                    tvBuyMoney4.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatByFrequency10More())+"RMB");
                    Frequency10MorePrice=userVIPPrivilegePrice.getChatByFrequency10More();
                    oncePrice=userVIPPrivilegePrice.getChatByFrequency2();
                    break;
                case 4:
                    tvBuyMoney.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatPeekByFrequency1())+"RMB");
                    tvBuyMoney2.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatPeekByFrequency2())+"RMB");
                    tvBuyMoney3.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatPeekByFrequency10())+"RMB");
                    tvBuyMoney4.setText(ArithUtils.round1(userVIPPrivilegePrice.getChatPeekByFrequency10More())+"RMB");
                    Frequency10MorePrice=userVIPPrivilegePrice.getChatPeekByFrequency10More();
                    oncePrice=userVIPPrivilegePrice.getChatPeekByFrequency2();
                    break;

            }

            rlBuyTimes.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Frequency10More=0;
                    switch (functionType){
                        case 2:
                            oncePrice=userVIPPrivilegePrice.getSuperLikeByFrequency1();
                            break;
                        case 3:
                            oncePrice=userVIPPrivilegePrice.getChatByFrequency1();
                            break;
                        case 4:
                            oncePrice=userVIPPrivilegePrice.getChatPeekByFrequency1();
                            break;
                    }
                    selectCounts=1;
                    isBuyTimes = false;
                    isBuyTimes2 = true;
                    isBuyTimes3 = true;
                    isBuyTimes4 = true;

                    rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                    tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                    tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                    rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
                }
            });
            rlBuyTimes2.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Frequency10More=0;
                    isBuyTimes = true;
                    isBuyTimes2 = false;
                    isBuyTimes3 = true;
                    isBuyTimes4 = true;

                    switch (functionType){
                        case 2:
                            oncePrice=userVIPPrivilegePrice.getSuperLikeByFrequency2();
                            break;
                        case 3:
                            oncePrice=userVIPPrivilegePrice.getChatByFrequency2();
                            break;
                        case 4:
                            oncePrice=userVIPPrivilegePrice.getChatPeekByFrequency2();
                            break;
                    }
                    selectCounts=2;

                    rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                    tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                    tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                    rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
                }
            });
            rlBuyTimes3.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Frequency10More=0;
                    isBuyTimes = true;
                    isBuyTimes2 = true;
                    isBuyTimes3 = false;
                    isBuyTimes4 = true;
                    switch (functionType){
                        case 2:
                            oncePrice=userVIPPrivilegePrice.getSuperLikeByFrequency10();
                            break;
                        case 3:
                            oncePrice=userVIPPrivilegePrice.getChatByFrequency10();
                            break;
                        case 4:
                            oncePrice=userVIPPrivilegePrice.getChatPeekByFrequency10();
                            break;
                    }
                    selectCounts=10;

                    rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                    tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                    tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                    rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_666)));
                }
            });
            rlBuyTimes4.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    isBuyTimes = true;
                    isBuyTimes2 = true;
                    isBuyTimes3 = true;
                    isBuyTimes4 = false;

                    rlBuyTimes4.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_pay_red));
                    tvBuyTimes4.setTextColor((getContext().getColor(R.color.text_black_ff9b9b)));
                    tvBuyMoney4.setTextColor((getContext().getColor(R.color.text_black_fb7a7a)));

                    rlBuyTimes.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes2.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes2.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney2.setTextColor((getContext().getColor(R.color.text_black_666)));

                    rlBuyTimes3.setBackground(getContext().getDrawable(R.drawable.bg_new_my_wallet_white));
                    tvBuyTimes3.setTextColor((getContext().getColor(R.color.text_black_999)));
                    tvBuyMoney3.setTextColor((getContext().getColor(R.color.text_black_666)));


                    DialogHelper.showLimitSingleInputDialog((Activity) context, "充值次数",
                            "请输入充值次数" + "最多100次", 1, 1, 10, v1 -> {
                                String text = ((EditText) v1).getText().toString().trim();
                                if (TextUtils.isEmpty(text)) {
                                    return;
                                }
                                inputMoney=text;
                                if(!TextUtils.isEmpty(text)){
                                    Frequency10More=Integer.parseInt(text);
                                    tvBuyTimes4.setText("购买"+Frequency10More+"次");
                                    tvBuyMoney4.setText(ArithUtils.round1(Frequency10MorePrice*Frequency10More)+"RMB");


                                }
                                tvBuyTimes4.setText("购买" + text + "次");
                            });
                }
            });
        mMenuView.findViewById(R.id.tvWalletAlipay).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.tvWalletWx).setOnClickListener(this::onClick);
        mMenuView.findViewById(R.id.tvWalletBalance).setOnClickListener(this::onClick);




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

            this.setFocusable(true);// 取得焦点
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            //设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_style_bg));
            //点击外部消失
        this.setOutsideTouchable(true);

            //从底部显示
        this.setTouchable(true);
        /*    int width = (int) getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
            int height = (int) getContext().getResources().getDisplayMetrics().heightPixels / 2 ; // 高度
        this.setWidth(width);
        this.setHeight(height);*/

        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        this.showAtLocation(mMenuView, Gravity.BOTTOM, 0, 0);

        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                    lp.alpha = 1f;
                    context.getWindow().setAttributes(lp);
                    dismiss();
                }
            });

        }

    public interface BtnOnClick {
        void btnOnClick(String payType, int selectCounts,int oncePrice,int  Frequency10More,int  Frequency10MorePrice);
    }


    public void setBtnOnClice(MyWalletPopupWindow.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tvWalletAlipay:
                btnOnClick.btnOnClick("2",selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                dismiss();
                break;
            case  R.id.tvWalletWx:
                btnOnClick.btnOnClick("0",selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                dismiss();
                break;
            case  R.id.tvWalletBalance:
                btnOnClick.btnOnClick("1",selectCounts,oncePrice,Frequency10More,Frequency10MorePrice);
                dismiss();
                break;
        }
    }

}
