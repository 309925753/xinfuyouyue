package com.xfyyim.cn.ui.me_new;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.AttentionAdapter;
import com.xfyyim.cn.bean.AddAttentionResult;
import com.xfyyim.cn.bean.AttentionEntity;
import com.xfyyim.cn.bean.circle.PublicMessage;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


public class FansActivity extends BaseActivity {


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
        tv_title_center.setText("粉丝列表");
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

        HttpUtils.get().url(coreManager.getConfig().FRIENDS_FANS_LIST)
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

            LinearLayoutManager linearLayout = new LinearLayoutManager(FansActivity.this);
            rv_list.setLayoutManager(linearLayout);

            attentionAdapter = new AttentionAdapter(list,FansActivity.this);
            rv_list.setAdapter(attentionAdapter);

            attentionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });


            attentionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (R.id.tv_att==view.getId()){
                        ToastUtil.showLongToast(FansActivity.this,"关注");
                        doAddAttention(String.valueOf(list.get(position).getToUserId()));
                    }
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


    /**
     * 加关注
     */
    private void doAddAttention(String mRoomUserId) {
        DialogHelper.showDefaulteMessageProgressDialog(FansActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("toUserId", mRoomUserId);
        params.put("fromAddType", mRoomUserId);


        HttpUtils.get().url(coreManager.getConfig().FRIENDS_ATTENTION_ADD)
                .params(params)
                .build()
                .execute(new BaseCallback<AddAttentionResult>(AddAttentionResult.class) {
                    @Override
                    public void onResponse(ObjectResult<AddAttentionResult> result) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showLongToast(FansActivity.this,"关注成功");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                    }
                });
    }

}
