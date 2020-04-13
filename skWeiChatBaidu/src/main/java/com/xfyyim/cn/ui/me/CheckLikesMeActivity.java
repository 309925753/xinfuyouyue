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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import butterknife.Unbinder;

public class CheckLikesMeActivity extends BaseActivity implements View.OnClickListener {

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
    private CheckLikesMeAdapter checkLikesMeAdapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_likes_me);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("谁喜欢我");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }
    private void initView() {

        mRefreshLayout = findViewById(R.id.refreshLayout);

        RecyclerView rclBill = (RecyclerView) findViewById(R.id.rclCheck);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rclBill.setLayoutManager(linearLayoutManager);
        checkLikesMeAdapter = new CheckLikesMeAdapter(this);
        List<BillCashBean> deviceBeanList = new ArrayList<>();
        deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "于晓，24", "1", "1", "购买超级曝光"));
        deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "孙子，12", "3", "2", "购买闪聊偷看"));
        deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "太孙子，2", "4", "3", "购买超级喜欢"));
        deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "欧阳，12", "5", "4", "购买闪聊次数"));
        checkLikesMeAdapter.deviceBeanList = deviceBeanList;
        rclBill.setAdapter(checkLikesMeAdapter);
        checkLikesMeAdapter.notifyDataSetChanged();

        findViewById(R.id.ivSolarize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //爆光
            }
        });

      /*  mRefreshLayout.setOnRefreshListener(rl -> {
           //刷新
            LogUtil.e("*******************刷新1******************");

        });*/


        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新2******************");
                deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "于晓，24", "1", "1", "购买超级曝光"));
                deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "欧阳，12", "5", "4", "购买闪聊次数"));
                deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "于晓，24", "1", "1", "购买超级曝光"));
                checkLikesMeAdapter.notifyDataSetChanged();
                mRefreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "于晓，24", "1", "1", "购买超级曝光"));
                deviceBeanList.add(new BillCashBean("2020-01-17  09:12", "孙子，12", "3", "2", "购买闪聊偷看"));
                deviceBeanList.add(new BillCashBean("2020-02-14  09:12", "太孙子，2", "4", "3", "购买超级喜欢"));
                deviceBeanList.add(new BillCashBean("2020-03-15  09:12", "欧阳，12", "5", "4", "购买闪聊次数"));
                deviceBeanList.add(new BillCashBean("2020-01-14  09:12", "于晓，24", "1", "1", "购买超级曝光"));
                checkLikesMeAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });

    }

    /**
     * 打开超级爆光页面
     */
    private void OpenSolarize() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public class CheckLikesMeAdapter extends RecyclerView.Adapter<CheckLikesMeAdapter.ViewHolder> {
        private LayoutInflater mInflater;
        private Context context;
        public List<BillCashBean> deviceBeanList = new ArrayList<>();

        public CheckLikesMeAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
            ;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivlike;
            TextView tvLikeName;

            public ViewHolder(View view) {
                super(view);
                ivlike = (ImageView) view.findViewById(R.id.ivlike);
                tvLikeName = (TextView) view.findViewById(R.id.tvLikeName);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_check_likes_adapter, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (deviceBeanList != null && deviceBeanList.size() > 0) {
                final BillCashBean billCashBean = deviceBeanList.get(position);
                holder.tvLikeName.setText("" + billCashBean.getBillMoney() + "");
                holder.ivlike.setImageResource(R.mipmap.my_vip_regrets);
            }
        }

        @Override
        public int getItemCount() {
            return deviceBeanList.size();
        }
    }

}
