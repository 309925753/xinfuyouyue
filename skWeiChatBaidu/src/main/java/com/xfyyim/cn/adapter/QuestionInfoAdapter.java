package com.xfyyim.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.QuestEntity;

import java.util.List;

public class QuestionInfoAdapter extends RecyclerView.Adapter<QuestionInfoAdapter.ItemViewHolderCommon> {
    List<QuestEntity> list;
    private LayoutInflater mLayoutInflater;
    Context context;

    public QuestionInfoAdapter(Context context, List<QuestEntity> listq) {
        this.list = listq;
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderCommon holder, int position) {
        holder.tv_a.setText(list.get(position).getAnswer());
        holder.tv_q.setText(list.get(position).getQuestion());


    }

    public static class ItemViewHolderCommon extends RecyclerView.ViewHolder {
        private LinearLayout ll_modify_qiestion;
        TextView tv_q;
        TextView tv_a;

        public ItemViewHolderCommon(View itemView) {
            super(itemView);
            ll_modify_qiestion = itemView.findViewById(R.id.ll_modify_qiestion);
            tv_a = itemView.findViewById(R.id.tv_a);
            tv_q = itemView.findViewById(R.id.tv_q);
        }
    }



    @NonNull
    @Override
    public QuestionInfoAdapter.ItemViewHolderCommon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_question, parent, false);

        return new ItemViewHolderCommon(view);
    }
}