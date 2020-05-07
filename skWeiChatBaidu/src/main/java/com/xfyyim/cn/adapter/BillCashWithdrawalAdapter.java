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
import com.xfyyim.cn.bean.PayOrderBean;
import com.xfyyim.cn.util.ArithUtils;
import com.xfyyim.cn.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class BillCashWithdrawalAdapter extends RecyclerView.Adapter<BillCashWithdrawalAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    public List<PayOrderBean> deviceBeanList = new ArrayList<>();
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
            final PayOrderBean billCashBean = deviceBeanList.get(position);
            holder.tvTime.setText( DateUtils.newTimedate(String.valueOf(billCashBean.getCreateTime())));
            holder.tvMoney.setText("-"+ billCashBean.getMoney()+"RMB");
            switch (billCashBean.getAppType()) {
                case 1 :
                    holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_peek));
                    holder.tvBuyName.setText("购买超级曝光");
                     // 超级曝光
                    break;
                case 2 :
                    // 超级喜欢
                    holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_superlike));
                    holder.tvBuyName.setText("购买超级喜欢");
                    break;
                case 3 :
                   // 在线闪聊（次数）
                    holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_chat_frequency));
                    holder.tvBuyName.setText("购买在线闪聊");
                    break;
                case 4 :
                     // 闪聊偷看
                    holder.ivlikeType.setBackground(context.getDrawable(R.mipmap.my_wallet_superexposure));
                    holder.tvBuyName.setText("购买闪聊偷看");
                    break;
                case 5 :
                    // VIP
                    holder.tvBuyName.setText("购买VIP");
                    break;
                case 6 :
                    // 查看谁喜欢我
                    holder.tvBuyName.setText("购买查看谁喜欢我");
                    break;
                case 7 :
                   // 在线闪聊（月份）
                    holder.tvBuyName.setText("购买在线闪聊");
                    break;
            }
            switch (billCashBean.getAppType()) {
                case 0 :
                    holder.ivPayType.setImageResource(R.mipmap.my_prerogative_wx);
                    break;
                case 1 :
                    holder.ivPayType.setImageResource(R.mipmap.my_prerogative_banlce);
                    break;
                case 2:
                    holder.ivPayType.setImageResource(R.mipmap.my_prerogative_zf);
                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return deviceBeanList.size();
    }
}
