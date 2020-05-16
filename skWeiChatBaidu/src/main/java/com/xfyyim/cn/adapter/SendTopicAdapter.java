package com.xfyyim.cn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.circle.TopicEntity;

import java.util.List;


public class SendTopicAdapter  extends BaseQuickAdapter<TopicEntity.DataBean.ListBean, SendTopicAdapter.TopicViewHolder>{


    Context context;
    public SendTopicAdapter(@Nullable List<TopicEntity.DataBean.ListBean> data, Context context) {
        super(R.layout.item_topic_hor,data);
        this.context = context;
    }

    @Override
    protected void convert(TopicViewHolder helper, TopicEntity.DataBean.ListBean item) {
        if (item.getTitle()!=null&&! TextUtils.isEmpty(item.getTitle())){
            if (item.getTitle().contains("#")&&item.getTitle().startsWith("#")){
                helper.tv_topic_name.setText(item.getTitle());
            }else {
                helper.tv_topic_name.setText("#" + item.getTitle());
            }
        }
    }




    class TopicViewHolder extends BaseViewHolder{
        TextView tv_topic_name;
        public TopicViewHolder(View view) {
            super(view);
            tv_topic_name=view.findViewById(R.id.tv_topic_name);
        }


    }
}
