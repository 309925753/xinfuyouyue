package com.xfyyim.cn.ui.circle.range;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.TopicAdapter;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.circle.TopicEntity;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.bouncycastle.LICENSE;

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

    Unbinder unbinder;
    TopicAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        unbinder= ButterKnife.bind(this);
        initActionBar();

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
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
        }
    }
   public void  setTopicAdapter(List<TopicEntity.DataBean.TopicsBean> list){
        if (adapter==null){
            rv_topic.setLayoutManager(new LinearLayoutManager(TopicActivity.this));
            adapter=new TopicAdapter(list,TopicActivity.this);
            rv_topic.setAdapter(adapter);
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });
        }else{
            adapter.notifyDataSetChanged();
        }



   }

    public void getTopicList(){


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(this).getAccessToken());
        params.put("pageIndex", "0");
        params.put("pageSize", "20");

        DialogHelper.showDefaulteMessageProgressDialog(this);

        HttpUtils.post().url(coreManager.getConfig().FILTER_TOPIC_LIST)
                .params(params)
                .build()
                .execute(new ListCallback<TopicEntity.DataBean>(TopicEntity.DataBean.class) {
                    @Override
                    public void onResponse(ArrayResult<TopicEntity.DataBean> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(TopicActivity.this, result)) {
                            List<TopicEntity.DataBean> datas = result.getData();
                            if (datas != null && datas.size() > 0) {
                               List<TopicEntity.DataBean.TopicsBean> list=datas.get(0).getTopics();
                                setTopicAdapter(list);


                            }

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
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
