package com.xfyyim.cn.ui.me_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.AttentionEntity;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


public class AttentionActivity extends BaseActivity {


    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    SwipeRecyclerView rv_list;

    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.iv_title_right)
    ImageView iv_title_right;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;

    AttentionAdapter attentionAdapter;
    private static int PAGER_SIZE = 10;
    private boolean more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        unbinder = ButterKnife.bind(this);
        initView();
        initActionBar();
    }


    public void initView() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            requestData();
        });

    }
    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_center.setText("关注列表");
        iv_title_right.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().FRIENDS_ATTENTION_LIST)
                .params(params)
                .build()
                 .execute(new ListCallback<AttentionEntity>(AttentionEntity.class) {
                     @Override
                     public void onResponse(ArrayResult<AttentionEntity> result) {
                         refreshComplete();
                         List<AttentionEntity> data = result.getData();
                         if (data != null && data.size() > 0){
                             setAdapter(data);
                         }
                     }

                     @Override
                     public void onError(Call call, Exception e) {
                         refreshComplete();
                     }

                     @Override
                     public void onFailure(Call call, IOException e) {
                         super.onFailure(call, e);
                         refreshComplete();
                     }
                 });

    }


    public void setAdapter( List<AttentionEntity> list){
        if (attentionAdapter == null) {

            LinearLayoutManager linearLayout = new LinearLayoutManager(AttentionActivity.this);
            rv_list.setLayoutManager(linearLayout);

            attentionAdapter = new AttentionAdapter(list, AttentionActivity.this);
            rv_list.setAdapter(attentionAdapter);

            attentionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(AttentionActivity.this,PersonInfoActivity.class);
                    intent.putExtra("FriendId",String.valueOf(list.get(position).getUserId()));
                    startActivity(intent);
                }
            });

        } else {
            attentionAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 停止刷新动画
     */
    private void refreshComplete() {
        rv_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        }, 200);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
    public class AttentionAdapter extends BaseQuickAdapter<AttentionEntity, BaseViewHolder> {


        Context context;

        public AttentionAdapter(@Nullable List<AttentionEntity> data, Context context) {
            super(R.layout.item_list_custom,data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, AttentionEntity item) {
            String time= TimeUtils.getFriendlyTimeDesc(mContext, item.getCreateTime());
            helper.setText(R.id.tv_time,time);
            helper.setText(R.id.nick_name_tv,item.getToNickname());
            String url = AvatarHelper.getAvatarUrl(String.valueOf(item.getToUserId()), false);

            GlideImageUtils.setImageView(mContext,url,helper.getView(R.id.avatar_img));


        }
    }
}
