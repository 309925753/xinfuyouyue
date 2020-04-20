package com.xfyyim.cn.ui.circle.range;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.TopicAdapter;
import com.xfyyim.cn.bean.circle.TopicEntity;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class TopicActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;

    @BindView(R.id.recyclerView)
    SwipeRecyclerView rv_topic;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private View mHeadView;


    Unbinder unbinder;
    TopicAdapter adapter;

    private TextView tv_topic_name;
    private TextView tv_topic_desc;
    private TextView tv_topic_time;
    private ImageView img_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        unbinder = ButterKnife.bind(this);
        initActionBar();
        initview();
    }


    public void initview() {


        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getTopicList();
            mRefreshLayout.finishRefresh(true);

        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            getTopicList();
            mRefreshLayout.finishLoadMore(true);
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        getTopicList();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);

        tv_title_center.setText("话题列表");
        tv_title_center.setTextColor(getResources().getColor(R.color.white));


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    public void csetTopicAdapter(List<TopicEntity.DataBean> list) {
        if (adapter == null) {
            rv_topic.setLayoutManager(new LinearLayoutManager(TopicActivity.this));
            adapter = new TopicAdapter(list, TopicActivity.this);
            rv_topic.setAdapter(adapter);


            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(TopicActivity.this,TopicDetailActivity.class);
                    intent.putExtra("Topic",(Serializable) list.get(position));
                    startActivity(intent);
                }
            });

        } else {
            adapter.notifyDataSetChanged();
        }


    }

    public void getTopicList() {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(this).getAccessToken());

        DialogHelper.showDefaulteMessageProgressDialog(this);

        HttpUtils.post().url(coreManager.getConfig().FILTER_TOPIC_LIST)
                .params(params)
                .build()
                .execute(new ListCallback<TopicEntity.DataBean>(TopicEntity.DataBean.class) {
                    @Override
                    public void onResponse(ArrayResult<TopicEntity.DataBean> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(TopicActivity.this, result)) {
                            List<TopicEntity.DataBean> list=result.getData();
                            csetTopicAdapter(list);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        Toast.makeText(TopicActivity.this, R.string.check_network, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 停止刷新动画
     */
    private void refreshComplete() {
        rv_topic.postDelayed(new Runnable() {
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
