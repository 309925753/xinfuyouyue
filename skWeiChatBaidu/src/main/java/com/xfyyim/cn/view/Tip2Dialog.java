package com.xfyyim.cn.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.util.ScreenUtil;


/**
 * 提示框
 */
public class Tip2Dialog extends Dialog {
    private TextView
            mTipTv,
            mCancel,
            mConfirm;
    private String mTipString;
    private ConfirmOnClickListener mConfirmOnClickListener;

    public Tip2Dialog(Context context) {
        super(context, R.style.BottomDialog);
    }

    public void setTip(String tip) {
        this.mTipString = tip;
        if (mTipTv != null) {
            mTipTv.setText(mTipString);
        }
    }

    public void setmConfirmOnClickListener(String tip, ConfirmOnClickListener mConfirmOnClickListener) {
        setTip(tip);
        this.mTipString = tip;
        this.mConfirmOnClickListener = mConfirmOnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip2_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mTipTv = (TextView) findViewById(R.id.tip_tv);
        if (!TextUtils.isEmpty(mTipString)) {
            mTipTv.setText(mTipString);
        }
        mConfirm = findViewById(R.id.confirm);
        mCancel = findViewById(R.id.cancel);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.9);
        initEvent();
    }

    private void initEvent() {

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mConfirmOnClickListener != null) {
                    mConfirmOnClickListener.confirm();
                }
            }
        });
    }

    public interface ConfirmOnClickListener {
        void confirm();
    }
}
