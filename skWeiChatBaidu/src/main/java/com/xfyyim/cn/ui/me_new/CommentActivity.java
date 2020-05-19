package com.xfyyim.cn.ui.me_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.CommentAdapter;
import com.xfyyim.cn.bean.CommentEntity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.circle.range.CircleDetailFromZanActivity;
import com.xfyyim.cn.util.ToastUtil;
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


public class CommentActivity extends BaseActivity {


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
    TextView tv_title_center;   @BindView(R.id.tv_empty)
    TextView tv_empty;

    CommentAdapter mAdapter;
    private static int PAGER_SIZE = 10;
    private boolean more;
    private int pageIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        unbinder = ButterKnife.bind(this);
        initView();
        initActionBar();
    }


    public void initView() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex=0;
            requestData();

        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
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
        tv_title_center.setText("评论列表");
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
        params.put("pageSize", "20");
        params.put("pageIndex", String.valueOf(pageIndex));

        HttpUtils.get().url(coreManager.getConfig().FRIENDS_COMMENT_LIST)
                .params(params)
                .build()
                 .execute(new ListCallback<CommentEntity>(CommentEntity.class) {
                     @Override
                     public void onResponse(ArrayResult<CommentEntity> result) {
                         refreshComplete();
                         List<CommentEntity> data = result.getData();
                         if (data != null && data.size() > 0){
                             tv_empty.setVisibility(View.GONE);
                             rv_list.setVisibility(View.VISIBLE);
                             setAdapter(data);
                         }else{
                             if (pageIndex>0){
                                 ToastUtil.showToast(CommentActivity.this,"没有更多评论了");
                             }else{
                                 tv_empty.setVisibility(View.VISIBLE);
                                 rv_list.setVisibility(View.GONE);
                             }

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


    public void setAdapter( List<CommentEntity> list){
        if (mAdapter == null) {

            LinearLayoutManager linearLayout = new LinearLayoutManager(CommentActivity.this);
            rv_list.setLayoutManager(linearLayout);

            mAdapter = new CommentAdapter(list, CommentActivity.this);
            rv_list.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(CommentActivity.this, CircleDetailFromZanActivity.class);
                    intent.putExtra("MessageData",list.get(position).getMsg());
                    startActivity(intent);
                }
            });

        } else {
            mAdapter.notifyDataSetChanged();
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
}
