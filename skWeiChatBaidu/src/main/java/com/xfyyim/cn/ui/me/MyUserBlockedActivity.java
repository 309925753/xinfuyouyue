package com.xfyyim.cn.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.BillCashBean;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyUserBlockedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_title_left)
    SkinImageView ivTitleLeft;
    @BindView(R.id.tv_title_left)
    SkinTextView tvTitleLeft;
    @BindView(R.id.pb_title_center)
    ProgressBar pbTitleCenter;
    @BindView(R.id.tv_title_center)
    SkinTextView tvTitleCenter;
    @BindView(R.id.iv_title_center)
    ImageView ivTitleCenter;
    @BindView(R.id.iv_title_right)
    SkinImageView ivTitleRight;
    @BindView(R.id.iv_title_right_right)
    SkinImageView ivTitleRightRight;
    @BindView(R.id.tv_title_right)
    SkinTextView tvTitleRight;
    @BindView(R.id.mergerStatus)
    MergerStatus mergerStatus;
    private SmartRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_blocked);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("谁喜欢我");

        RecyclerView rcvUserBlocked = (RecyclerView) findViewById(R.id.rcvUserBlocked);
        mRefreshLayout = findViewById(R.id.refreshLayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvUserBlocked.setLayoutManager(linearLayoutManager);
        UserBlockedAdapter billCashWithdrawalAdapter = new UserBlockedAdapter(this);
        List<BillCashBean> deviceBeanList = new ArrayList<>();
        deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "121", "1", "1", "12"));
        deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
        deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "64"));
        deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "121", "5", "4", "233"));
        billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rcvUserBlocked.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新2******************");
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "64"));
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "11", "3", "2", "56"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "121", "4", "3", "64"));
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    public class UserBlockedAdapter extends RecyclerView.Adapter<UserBlockedAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        public List<BillCashBean> deviceBeanList = new ArrayList<>();

        public UserBlockedAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
            ;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RoundedImageView ivUserHead;
            TextView tvShowType;
            TextView tvUserName;

            public ViewHolder(View view) {
                super(view);
                ivUserHead = (RoundedImageView) view.findViewById(R.id.ivUserHead);
                tvShowType = (TextView) view.findViewById(R.id.tvShowType);
                tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_user_block_adapter, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (deviceBeanList != null && deviceBeanList.size() > 0) {
                final BillCashBean billCashBean = deviceBeanList.get(position);
                holder.ivUserHead.setBackground(context.getDrawable(R.mipmap.ic_launcher_round));
                holder.tvShowType.setText("显示");
                holder.tvUserName.setText("宇宙的点");

            }
        }

        @Override
        public int getItemCount() {
            return deviceBeanList.size();
        }
    }


}
