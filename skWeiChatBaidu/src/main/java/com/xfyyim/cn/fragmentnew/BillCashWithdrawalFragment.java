package com.xfyyim.cn.fragmentnew;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.BillCashBean;
import com.xfyyim.cn.bean.PayOrderBean;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.util.DateUtils;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


public class BillCashWithdrawalFragment extends EasyFragment {

    private RecyclerView rclBillWithdrawal;
    private SmartRefreshLayout mRefreshLayout;
    private BillCashWithdrawalAdapter billCashWithdrawalAdapter;
    private  int  pageSize=25;
    private int pageIndex=0;
    private boolean more=true;
    private     List<PayOrderBean> deviceBeanList = new ArrayList<>();


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

         billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rclBillWithdrawal.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();


        mRefreshLayout = findViewById(R.id.refreshLayout);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                more=true;
                postPayOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                postPayOrder();
                more=false;
            }
        });
        postPayOrder();
    }


    public class BillCashWithdrawalAdapter extends RecyclerView.Adapter<BillCashWithdrawalAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        public List<PayOrderBean> deviceBeanList = new ArrayList<>();

        public BillCashWithdrawalAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
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
                final PayOrderBean billCashBean = deviceBeanList.get(position);

                holder.tvPayType.setText("+"+billCashBean.getMoney() + "RMB");
                holder.tvTime.setText( DateUtils.newTimedate(String.valueOf(billCashBean.getCreateTime())));
                if(!TextUtils.isEmpty(billCashBean.getServiceCharge())){
                    holder.tvMoney.setText("手续费：" + billCashBean.getServiceCharge() + "RMB");
                }
            }
        }

        @Override
        public int getItemCount() {
            return deviceBeanList.size();
        }
    }

    private void  postPayOrder(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("funType",String.valueOf(1));
        pageIndex++;
        HttpUtils.post().url(coreManager.getConfig().USER_QUER_PAYORDER_BYUSERID)
                .params(params)
                .build()
                .execute(new ListCallback<PayOrderBean>(PayOrderBean.class) {
                    @Override
                    public void onResponse(ArrayResult<PayOrderBean> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(getActivity(), result)) {
                            List<PayOrderBean> datas = result.getData();
                            if (datas != null && datas.size() > 0) {
                                deviceBeanList.addAll(datas);
                                billCashWithdrawalAdapter.deviceBeanList=deviceBeanList;
                                billCashWithdrawalAdapter.notifyDataSetChanged();
                                if(more){
                                    mRefreshLayout.finishLoadMore();
                                }else {
                                    mRefreshLayout.finishRefresh();
                                }

                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

