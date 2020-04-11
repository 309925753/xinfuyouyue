package com.xfyyim.cn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.FriendEntity;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.photopicker.Image;
import com.xfyyim.cn.viewhold.AllAdapterViewHolder;

import java.util.List;

public class AllPersonAdapeter extends BaseQuickAdapter<FriendEntity.DataBean, AllAdapterViewHolder> {
    Context mContext;

    public AllPersonAdapeter(Context mContext, @Nullable List<FriendEntity.DataBean> data) {
        super(R.layout.item_fragment_all, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(@NonNull AllAdapterViewHolder helper, FriendEntity.DataBean item) {

        if (item.getMsgNum() == 0) {
            helper.tv_count.setVisibility(View.GONE);
        } else {
            helper.tv_count.setVisibility(View.VISIBLE);
            helper.tv_count.setText(item.getMsgNum() + "");
        }
        if (item.getLastTalkTime() == 0) {
            helper.tv_time.setVisibility(View.GONE);
        } else {
            helper.tv_time.setVisibility(View.VISIBLE);
            helper.tv_time.setText(item.getLastTalkTime() + "");
        }

        if (item.getLastChatContent() == null || TextUtils.isEmpty(item.getLastChatContent())) {
            helper.tv_message.setVisibility(View.GONE);
        } else {
            helper.tv_message.setVisibility(View.VISIBLE);
            helper.tv_message.setText(item.getLastChatContent());
        }

        helper.tel.setText(item.getToNickname());

        String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getToUserId()), false);
        GlideImageUtils.setImageDrawableCirCle(mContext, url, helper.avatar_img);

    }
}
