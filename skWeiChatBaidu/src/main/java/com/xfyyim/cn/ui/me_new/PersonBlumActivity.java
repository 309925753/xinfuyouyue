package com.xfyyim.cn.ui.me_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.PublicCareRecyclerAdapter;
import com.xfyyim.cn.adapter.PublicNearAdapter;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.circle.PublicMessage;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.circle.range.SendTextPicActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class PersonBlumActivity extends BaseActivity implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    SwipeRecyclerView rv_blum;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;

    private View mHeadView;

    ImageView avatar_img;
    ImageView img_vip;

    LinearLayout ll_fan, ll_att, ll_blum;
    TextView tv_fans;
    TextView tv_att;
    TextView tv_zan;
    TextView tv_commit;
    TextView tv_age;
    TextView tv_status;
    TextView tv_my_blum;


    private static int PAGER_SIZE = 10;
    private int pager_index = 0;
    String friendId;
    private boolean more;
    private String messageId;
    private List<PublicMessage> mMessages = new ArrayList<>();
    private PublicCareRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_blum);
        unbinder = ButterKnife.bind(this);
        friendId = getIntent().getStringExtra("FriendId");
        initActionBar();
        initView();
        getUserInfo();
        requestData(true);

    }

    public void initView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonBlumActivity.this);
        rv_blum.setLayoutManager(linearLayoutManager
        );

        mAdapter = new PublicCareRecyclerAdapter(PersonBlumActivity.this, coreManager, mMessages);
        rv_blum.setAdapter(mAdapter);


        mHeadView = LayoutInflater.from(PersonBlumActivity.this).inflate(R.layout.header_my_blum, rv_blum, false);
        avatar_img = mHeadView.findViewById(R.id.avatar_img);
        img_vip = mHeadView.findViewById(R.id.img_vip);
        ll_fan = mHeadView.findViewById(R.id.ll_fan);
        ll_att = mHeadView.findViewById(R.id.ll_att);
        ll_blum = mHeadView.findViewById(R.id.ll_blum);
        tv_fans = mHeadView.findViewById(R.id.tv_fans);
        tv_att = mHeadView.findViewById(R.id.tv_att);
        tv_zan = mHeadView.findViewById(R.id.tv_zan);
        tv_age = mHeadView.findViewById(R.id.tv_age);
        tv_status = mHeadView.findViewById(R.id.tv_status);
        tv_my_blum = mHeadView.findViewById(R.id.tv_my_blum);
        tv_commit = mHeadView.findViewById(R.id.tv_commit);
        rv_blum.addHeaderView(mHeadView);

        tv_commit.setOnClickListener(this);


        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            requestData(true);
            pager_index = 0;
            mRefreshLayout.finishRefresh(true);

        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pager_index++;
            requestData(false);
            mRefreshLayout.finishLoadMore(true);
        });

    }


    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                Intent intent = new Intent(PersonBlumActivity.this, SendTextPicActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_title_left:
                finish();
                break;

        }
    }


    public void setUiData(User user) {
        tv_title_center.setText(user.getNickName());
        AvatarHelper.getInstance().displayAvatar(friendId, avatar_img, false);
        if (user.getFaceIdentity() == 1) {
            img_vip.setVisibility(View.VISIBLE);
        } else {
            img_vip.setVisibility(View.GONE);
        }

        tv_fans.setText(String.valueOf(user.getFansCount()));
        tv_att.setText(String.valueOf(user.getAttCount()));
        tv_zan.setText(String.valueOf(user.getFriendsCount()));

        if (user.getUserId().equals(friendId)) {
            tv_status.setVisibility(View.GONE);
        } else {
            if (user.getDistance() == 0) {
                tv_status.setText(user.getOnlineStatus());
            } else {
                tv_status.setText(user.getDistance() + "  " + user.getOnlineStatus());
            }

        }
        tv_age.setText(user.getAge() + "  " + user.getStarSign());

        tv_my_blum.setText("我的相册 " + user.getMsgCount());
        tv_zan.setText(user.getPraiseCount());

    }

    private void getUserInfo() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(PersonBlumActivity.this).getAccessToken());
        params.put("userId", friendId);

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User user = result.getData();
                            setUiData(user);

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }

    private void requestData(boolean isPullDownToRefresh) {
        if (isPullDownToRefresh) {// 上拉刷新
            messageId = null;
            more = true;
        }

        if (!more) {
            // ToastUtil.showToast(getContext(), getString(R.string.tip_last_item));
            mRefreshLayout.setNoMoreData(true);
            refreshComplete();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("pageSize", String.valueOf(PAGER_SIZE));
        params.put("pageIndex", String.valueOf(pager_index));
//        if (distance>0){
//            params.put("distance", String.valueOf(distance));
//        }
//        if (latitude>0){
//            params.put("distance", String.valueOf(latitude));
//        }
//
//        if (longitude>0){
//            params.put("distance", String.valueOf(longitude));
//        }

        HttpUtils.get().url(coreManager.getConfig().MSG_MY_LIST)
                .params(params)
                .build()
                .execute(new ListCallback<PublicMessage>(PublicMessage.class) {
                    @Override
                    public void onResponse(ArrayResult<PublicMessage> result) {
                        if (PersonBlumActivity.this != null && Result.checkSuccess(PersonBlumActivity.this, result)) {
                            List<PublicMessage> data = result.getData();

                            if (data != null && data.size() > 0) {
                                mMessages.addAll(data);
                                if (data.size() == PAGER_SIZE) {
                                    more = true;
                                    mRefreshLayout.resetNoMoreData();
                                } else {
                                    // 服务器返回未满10条，下拉不在去请求
                                    more = false;
                                }
                            } else {
                                // 服务器未返回数据，下拉不再去请求
                                more = false;
                            }
                            mAdapter.notifyDataSetChanged();
                            refreshComplete();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (PersonBlumActivity.this != null) {
                            ToastUtil.showNetError(PersonBlumActivity.this);
                            refreshComplete();
                        }
                    }
                });
    }


    /**
     * 停止刷新动画
     */
    private void refreshComplete() {
        rv_blum.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        }, 200);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}