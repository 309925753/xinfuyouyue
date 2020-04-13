package com.xfyyim.cn.fragmentnew;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.BillCashBean;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class BillCashWithdrawalFragment extends EasyFragment {

    private RecyclerView rclBillWithdrawal;
    private SmartRefreshLayout mRefreshLayout;
    private BillCashWithdrawalAdapter billCashWithdrawalAdapter;


    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_bill_cash_withdrawal;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {

        initView();


    }

    private void initView() {
        rclBillWithdrawal = (RecyclerView) findViewById(R.id.rclBillWithdrawal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclBillWithdrawal.setLayoutManager(linearLayoutManager);
        billCashWithdrawalAdapter = new BillCashWithdrawalAdapter(getActivity());
        List<BillCashBean> deviceBeanList = new ArrayList<>();
        deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "121", "1", "1", "12"));
        deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
        deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "64"));
        deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "121", "5", "4", "233"));
        billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rclBillWithdrawal.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();


        mRefreshLayout = findViewById(R.id.refreshLayout);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
                deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "121", "5", "4", "233"));
                billCashWithdrawalAdapter.notifyDataSetChanged();

                mRefreshLayout.finishLoadMore(3000);
                //      mRefreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
                deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "121", "5", "4", "233"));
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });

    }


    public class BillCashWithdrawalAdapter extends RecyclerView.Adapter<BillCashWithdrawalAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        public List<BillCashBean> deviceBeanList = new ArrayList<>();

        public BillCashWithdrawalAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
            ;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvPayType;
            TextView tvTime;
            TextView tvMoney;

            public ViewHolder(View view) {
                super(view);
                tvPayType = (TextView) view.findViewById(R.id.tvPayType);
                tvTime = (TextView) view.findViewById(R.id.tvTime);
                tvMoney = (TextView) view.findViewById(R.id.tvMoney);
            }
        }


        @Override
        public BillCashWithdrawalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_bill_cash_consumption_adapter, null);
            BillCashWithdrawalAdapter.ViewHolder viewHolder = new BillCashWithdrawalAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final BillCashWithdrawalAdapter.ViewHolder holder, final int position) {
            if (deviceBeanList != null && deviceBeanList.size() > 0) {
                final BillCashBean billCashBean = deviceBeanList.get(position);

                holder.tvPayType.setText(billCashBean.getBillName() + "RMB");
                holder.tvTime.setText(billCashBean.getBillTime());
                holder.tvMoney.setText("手续费：" + billCashBean.getBillMoney() + "RMB");


            }
        }

        @Override
        public int getItemCount() {
            return deviceBeanList.size();
        }
    }


}

