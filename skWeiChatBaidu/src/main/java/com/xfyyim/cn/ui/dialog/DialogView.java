package com.xfyyim.cn.ui.dialog;

import android.app.Activity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.dialog.base.BaseDialog;

/**
 * Created by Administrator on 2016/4/21.
 */
public class DialogView extends BaseDialog {

    private TextView mTitleTv;
    private AutoCompleteTextView mContentET;
    private TextView mCommitBtn;
    private TextView mCancle;
    private View.OnClickListener mOnClickListener;

    {
        RID = R.layout.dialog_view;
    }

    public DialogView(Activity activity) {
        mActivity = activity;
        initView();
    }

    public DialogView(Activity activity, View.OnClickListener onClickListener) {
        mActivity = activity;
        initView();
        mOnClickListener = onClickListener;
    }

    // User In RoomInfoActivity Modify Group Name.Desc
    public DialogView(Activity activity, String title, String hint, View.OnClickListener onClickListener) {
        mActivity = activity;
        initView();
        setView(title, hint);
        this.mOnClickListener = onClickListener;
    }

    public DialogView(Activity activity, String title, String hint, int maxLines, int lines, InputFilter[] i, View.OnClickListener onClickListener) {
        mActivity = activity;
        initView();
        setView(title, hint, maxLines, lines, i);
        this.mOnClickListener = onClickListener;
    }

    protected void initView() {
        super.initView();
        mTitleTv = (TextView) mView.findViewById(R.id.title);


        mContentET = (AutoCompleteTextView) mView.findViewById(R.id.content);

        mCommitBtn =  mView.findViewById(R.id.sure_btn);
        mCancle=mView.findViewById(R.id.cancel);
    }

    private void setView(String title, String hint) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        }
        if (!TextUtils.isEmpty(hint)) {
            mContentET.setHint(hint);
        }

        mContentET.setFilters(new InputFilter[]{DialogHelper.mExpressionFilter, DialogHelper.mChineseEnglishNumberFilter});

        mCommitBtn.setOnClickListener(v -> {
//            mDialog.dismiss();
            if (mOnClickListener != null)
                mOnClickListener.onClick(mContentET);
        });

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


    }

    private void setView(String title, String hint, int maxLines, int lines, InputFilter[] i) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        }
        if (!TextUtils.isEmpty(hint)) {
            mContentET.setHint(hint);
        }
        if (i != null) {
            mContentET.setFilters(i);
        }

        mCommitBtn.setOnClickListener(v -> {
            mDialog.dismiss();
            if (mOnClickListener != null)
                mOnClickListener.onClick(mContentET);
        });
    }

    public View getmView() {
        return mView;
    }

    public void setSureClick(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void setHint(String hint) {
        mContentET.setHint(hint);
    }

    public void setLines(int lines) {
        mContentET.setLines(lines);
    }

    public void setMaxLines(int maxLines) {
        mContentET.setMaxLines(maxLines);
    }

    public void setFilters(InputFilter[] i) {
        mContentET.setFilters(i);
    }

    public String getContent() {
        return mContentET.getText().toString();
    }
}
