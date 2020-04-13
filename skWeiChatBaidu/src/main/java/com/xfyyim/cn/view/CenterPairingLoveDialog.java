package com.xfyyim.cn.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.xfyyim.cn.R;


public class CenterPairingLoveDialog extends Dialog   implements View.OnClickListener{

    private Context context;
    private View view;
    private CenterPairingLoveDialog.BtnOnClick btnOnClick;

    public CenterPairingLoveDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_myprivilege_pairinglove_layout, null);
        TextView   tvBuyVip = (TextView) view.findViewById(R.id.tvBuyVip);
        TextView    tvGiveUp = (TextView) view.findViewById(R.id.tvGiveUp);
        tvBuyVip.setOnClickListener(this::onClick);
        tvGiveUp.setOnClickListener(this::onClick);

        setView();
    }

    private void setView() {
      view.setAlpha(1.0f);
        this.setContentView(view);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(R.drawable.dialog_style_bg);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
       /* lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标*/
        lp.width =((int) context.getResources().getDisplayMetrics().widthPixels)-100; // 宽度
        lp.height= (int) context.getResources().getDisplayMetrics().heightPixels/2+((int) context.getResources().getDisplayMetrics().heightPixels/6); // 高度

        dialogWindow.setAttributes(lp);
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface BtnOnClick {
        void btnOnClick(String type);
    }

    public void setBtnOnClice(CenterPairingLoveDialog.BtnOnClick btnOnClick) {
        this.btnOnClick = btnOnClick;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBuyVip://
                btnOnClick.btnOnClick("1");
                dismiss();
                break;
            case R.id.tvGiveUp://
                btnOnClick.btnOnClick("0");
                dismiss();
                break;

        }

    }
}
