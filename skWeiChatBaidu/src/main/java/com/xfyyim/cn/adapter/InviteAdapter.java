package com.xfyyim.cn.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.InviteEntity;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;

import java.util.List;

public class InviteAdapter extends BaseQuickAdapter<InviteEntity, BaseViewHolder> {


    Context context;

    public InviteAdapter(@Nullable List<InviteEntity> data, Context context) {
        super(R.layout.item_invite_list,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InviteEntity item) {
        String time= TimeUtils.long_to_yMdHm_str(item.getTime());
             helper.setText(R.id.tv_time,time);
             helper.setText(R.id.nick_name_tv,item.getToNickName());
        String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getToUserId()), false);

        GlideImageUtils.setImageView(mContext,url,helper.getView(R.id.avatar_img));


    }
}
