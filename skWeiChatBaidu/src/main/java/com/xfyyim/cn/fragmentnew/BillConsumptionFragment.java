package com.xfyyim.cn.fragmentnew;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.BillCashWithdrawalAdapter;
import com.xfyyim.cn.bean.BillCashBean;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class BillConsumptionFragment extends EasyFragment {
    private RecyclerView rclBill;
    private SmartRefreshLayout mRefreshLayout;
    private BillCashWithdrawalAdapter billCashWithdrawalAdapter;


    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_bill_consumption;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();

    }


    private void initView() {
        //item_bill_cash_consumption_adapter
        rclBill = (RecyclerView) findViewById(R.id.rclBill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclBill.setLayoutManager(linearLayoutManager);
        billCashWithdrawalAdapter = new BillCashWithdrawalAdapter(getActivity());
        List<BillCashBean> deviceBeanList = new ArrayList<>();
        deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "121", "1", "1", "购买超级曝光"));
        deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "购买闪聊偷看"));
        deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "购买超级喜欢"));
        deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "121", "5", "4", "购买闪聊次数"));
        billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rclBill.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();

        mRefreshLayout = findViewById(R.id.refreshLayout);


        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新2******************");
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "购买闪聊偷看"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "购买超级喜欢"));
                billCashWithdrawalAdapter.notifyDataSetChanged();

                mRefreshLayout.finishLoadMore(3000);
                //      mRefreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "购买闪聊偷看"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "购买超级喜欢"));
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "购买闪聊偷看"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "购买超级喜欢"));
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });


    }


}
