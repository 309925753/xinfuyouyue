package com.xfyyim.cn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.ZanEntivity;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;

import java.util.List;

public class ZanAdapter extends BaseQuickAdapter<ZanEntivity, BaseViewHolder> {


    Context context;

    public ZanAdapter(@Nullable List<ZanEntivity> data, Context context) {
        super(R.layout.item_list_comment,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ZanEntivity item) {
        String time= TimeUtils.getFriendlyTimeDesc(mContext, item.getTime());
             helper.setText(R.id.tv_time,time);
             helper.setText(R.id.nick_name_tv,item.getNickname());
                 helper.setText(R.id.tv_conment,"赞了你");
      if (item.getFaceIdentity()==0){
          helper.getView(R.id.img_vip).setVisibility(View.GONE);
      }else{
          helper.getView(R.id.img_vip).setVisibility(View.VISIBLE);
      }



        String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getUserId()), false);

        GlideImageUtils.setImageView(mContext,url,helper.getView(R.id.avatar_img));
        
        helper.getView(R.id.avatar_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PersonInfoActivity.class);
                intent.putExtra("FriendId",String.valueOf(item.getUserId()));
                context.startActivity(intent);
            }
        });

    }
}
