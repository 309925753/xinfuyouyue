package com.xfyyim.cn.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;

import java.util.List;

public class SendTopicAdapter extends BaseQuickAdapter<String , BaseViewHolder> {


    public SendTopicAdapter(@Nullable List<String> data) {
        super(R.layout.item_topic_hor,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
