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
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

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
    @BindView(R.id.tv_show)
    TextView tvShow;
    RecyclerView rcvUserBlocked;
    private SmartRefreshLayout mRefreshLayout;
    private List<User> deviceBeanList = new ArrayList<>();
    private UserBlockedAdapter billCashWithdrawalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_blocked);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_FILTER_CIRCLE)
                .params(params)
                .build()
                .execute(new ListCallback<User>(User.class) {
                    @Override
                    public void onResponse(ArrayResult<User> result) {
                        if (Result.checkSuccess(getApplicationContext(), result)) {
                            if(result.getData()!=null &&  result.getData().size()>0){
                                rcvUserBlocked.setVisibility(View.VISIBLE);
                                billCashWithdrawalAdapter.deviceBeanList=result.getData();
                                billCashWithdrawalAdapter.notifyDataSetChanged();
                            }else {
                                tvShow.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    private void initView() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("已屏蔽用户");
         rcvUserBlocked = (RecyclerView) findViewById(R.id.rcvUserBlocked);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvUserBlocked.setLayoutManager(linearLayoutManager);
        billCashWithdrawalAdapter = new UserBlockedAdapter(this);

        billCashWithdrawalAdapter.deviceBeanList = deviceBeanList;
        rcvUserBlocked.setAdapter(billCashWithdrawalAdapter);
        billCashWithdrawalAdapter.notifyDataSetChanged();

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新2******************");
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.e("*******************刷新3******************");
                billCashWithdrawalAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        });

        billCashWithdrawalAdapter.setBtnOnClice(new UserBlockedAdapter.BtnOnClick() {
            @Override
            public void btnOnClick(User user) {
                filterUserCircle(user.getUserId());
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void filterUserCircle(String toUserId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("shieldType", "-1");
        params.put("type", "-1");
        params.put("toUserId", toUserId);
        HttpUtils.post().url(coreManager.getConfig().USER_SHOW_FILTER_CIRCLE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(MyUserBlockedActivity.this, result)) {
                            deviceBeanList.clear();
                            initData();

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }


    public static class UserBlockedAdapter extends RecyclerView.Adapter<UserBlockedAdapter.ViewHolder> implements View.OnClickListener {
        private LayoutInflater mInflater;
        private Context context;
        public List<User> deviceBeanList = new ArrayList<>();
        private BtnOnClick btnOnClick;

        @Override
        public void onClick(View v) {

        }

        public interface BtnOnClick {
            void btnOnClick(User user);
        }

        public void setBtnOnClice(BtnOnClick btnOnClick) {
            this.btnOnClick = btnOnClick;

        }

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
                final User user = deviceBeanList.get(position);
                AvatarHelper.getInstance().displayAvatar(user.getUserId(), holder.ivUserHead, true);
                holder.tvShowType.setText("显示");
                holder.tvUserName.setText(user.getNickName());
                holder.tvShowType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnOnClick.btnOnClick(user);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return deviceBeanList.size();
        }
    }


}
