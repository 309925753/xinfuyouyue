package com.xfyyim.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.BillCashBean;

import java.util.ArrayList;
import java.util.List;

public class BillCashWithdrawalAdapter extends RecyclerView.Adapter<BillCashWithdrawalAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    public List<BillCashBean> deviceBeanList = new ArrayList<>();
    public BillCashWithdrawalAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPayType;
        ImageView ivlikeType;
        TextView tvBuyName;
        TextView tvTime;
        TextView tvMoney;
        public ViewHolder(View view) {
            super(view);
            ivPayType = (ImageView) view.findViewById(R.id.ivPayType);
            ivlikeType = (ImageView) view.findViewById(R.id.ivlikeType);
            tvBuyName = (TextView) view.findViewById(R.id.tvBuyName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvMoney = (TextView) view.findViewById(R.id.tvMoney);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bill_cash_adapter, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(deviceBeanList != null && deviceBeanList.size() > 0){
            final BillCashBean billCashBean = deviceBeanList.get(position);

            holder.tvBuyName.setText(billCashBean.getBillName());
            holder.tvTime.setText(billCashBean.getBillTime());
            holder.tvMoney.setText("-"+billCashBean.getBillMoney()+"RMB");
            if(billCashBean.getConsumptionType().equals("1")){
                holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_peek));
            }else if(billCashBean.getConsumptionType().equals("2")){
                holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_superexposure));
            }else  if(billCashBean.getConsumptionType().equals("3")){
                holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_superlike));
            }else  if(billCashBean.getConsumptionType().equals("4")){
                holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_chat_frequency));
            }

        }
    }

    @Override
    public int getItemCount() {
        return deviceBeanList.size();
    }
}
