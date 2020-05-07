package com.xfyyim.cn.fragmentnew;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.adapter.BillCashWithdrawalAdapter;
import com.xfyyim.cn.bean.BillCashBean;
import com.xfyyim.cn.bean.PayOrderBean;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;


public class BillConsumptionFragment extends EasyFragment {
    private RecyclerView rclBill;
    private SmartRefreshLayout mRefreshLayout;
    private BillCashWithdrawalAdapter billCashWithdrawalAdapter;
    private  int  pageSize=25;
    private int pageIndex=0;
    private boolean more=true;
    private     List<PayOrderBean> deviceBeanList = new ArrayList<>();

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_bill_consumption;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();

    }

    //USER_QUER_PAYORDER_BYUSERID
    private void initView() {
        //item_bill_cash_consumption_adapter
        rclBill = (RecyclerView) findViewById(R.id.rclBill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclBill.setLayoutManager(linearLayoutManager);
        billCashWithdrawalAdapter = new BillCashWithdrawalAdapter(getActivity());

         billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rclBill.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();

        mRefreshLayout = findViewById(R.id.refreshLayout);


        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新2******************");

                postPayOrder();
                mRefreshLayout.finishLoadMore(3000);
                //      mRefreshLayout.finishLoadMore();
                more=true;

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                postPayOrder();

                more=false;
            }
        });
        postPayOrder();

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
