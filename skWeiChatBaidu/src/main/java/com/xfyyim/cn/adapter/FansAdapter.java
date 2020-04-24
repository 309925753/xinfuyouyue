package com.xfyyim.cn.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.AttentionEntity;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;

import java.util.List;

public class FansAdapter extends BaseQuickAdapter<AttentionEntity, BaseViewHolder> {


    Context context;

    public FansAdapter(@Nullable List<AttentionEntity> data, Context context) {
        super(R.layout.item_list_custom,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AttentionEntity item) {
        String time= TimeUtils.getFriendlyTimeDesc(mContext, item.getCreateTime());
             helper.setText(R.id.tv_time,time);
             helper.setText(R.id.nick_name_tv,item.getToNickname());
        String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getToUserId()), false);
      //  helper.getView(R.id.img_vip).setVisibility(View.VISIBLE);
        helper.getView(R.id.tv_att).setVisibility(View.VISIBLE);
        helper.addOnClickListener(R.id.tv_att);

        GlideImageUtils.setImageView(mContext,url,helper.getView(R.id.avatar_img));


    }
}
