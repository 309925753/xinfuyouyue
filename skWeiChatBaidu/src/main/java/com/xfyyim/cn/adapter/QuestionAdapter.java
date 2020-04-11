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

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_ADD = 0;//添加问题
    private static int TYPE_COMMON = 1;
    private int mMaxAlbum=9;//最大选择问题数量
    List<QuestEntity> list;
    private LayoutInflater mLayoutInflater;
Context context;
    public QuestionAdapter(Context context,List<QuestEntity> listq) {
       this.list=listq;
       this.context=context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return position==list.size()?TYPE_ADD : TYPE_COMMON;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ADD) {
            return new ItemViewHolderAdd(mLayoutInflater.inflate(R.layout.item_question_add, parent, false));
        } else {
            return new ItemViewHolderCommon(mLayoutInflater.inflate(R.layout.item_question, parent, false));
        }


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View itemView = null;
        if (holder instanceof ItemViewHolderAdd) {
            ItemViewHolderAdd itemViewHolderAdd = (ItemViewHolderAdd) holder;
            if (position >= mMaxAlbum) {
                itemViewHolderAdd.itemView.setVisibility(View.GONE);
            } else {
                itemViewHolderAdd.itemView.setVisibility(View.VISIBLE);
                itemView = ((ItemViewHolderAdd) holder).itemView;
            }
        } else if (holder instanceof ItemViewHolderCommon) {
                  ((ItemViewHolderCommon) holder).tv_a.setText(list.get(position).getAnswer());
                  ((ItemViewHolderCommon) holder).tv_q.setText(list.get(position).getQuestion());

            itemView = ((ItemViewHolderCommon) holder).itemView;
        }
        if (onItemClickListener != null && null != itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    public static class ItemViewHolderAdd extends RecyclerView.ViewHolder {
        private LinearLayout ll_add_question;
        public ItemViewHolderAdd(View itemView) {
            super(itemView);
            ll_add_question = itemView.findViewById(R.id.ll_add_question);
        }
    }
    public static class ItemViewHolderCommon extends RecyclerView.ViewHolder {
        private LinearLayout ll_modify_qiestion;
        TextView tv_q;
        TextView tv_a;

        public ItemViewHolderCommon(View itemView) {
            super(itemView);
            ll_modify_qiestion = itemView.findViewById(R.id.ll_modify_qiestion);
            tv_a=itemView.findViewById(R.id.tv_a);
            tv_q=itemView.findViewById(R.id.tv_q);
        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
