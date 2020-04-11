package com.xfyyim.cn.viewhold;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;

public class AllAdapterViewHolder extends BaseViewHolder {
    public TextView tv_time;
    public TextView tel;
    public TextView tv_count;
    public TextView tv_message;
    public ImageView avatar_img;

    public AllAdapterViewHolder(View view) {
        super(view);

        tv_time = view.findViewById(R.id.tv_time_ago);
        tel = view.findViewById(R.id.te1);
        tv_message = view.findViewById(R.id.tv_message);
        tv_count = view.findViewById(R.id.tv_count);
        avatar_img = view.findViewById(R.id.avatar_img);
    }
}
