package com.xfyyim.cn.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {


    Context context;

    public CommentAdapter(@Nullable List<CommentEntity> data, Context context) {
        super(R.layout.item_list_comment,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        String time= TimeUtils.getFriendlyTimeDesc(mContext, item.getTime());
             helper.setText(R.id.tv_time,time);
             helper.setText(R.id.nick_name_tv,item.getNickname());
             if (item.getBody()!=null){
                 helper.setText(R.id.tv_conment,item.getBody().toString());
             }
        String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getUserId()), false);

        GlideImageUtils.setImageView(mContext,url,helper.getView(R.id.avatar_img));


    }
}
