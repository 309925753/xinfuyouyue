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
import com.xfyyim.cn.bean.AddAttentionResult;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.circle.PublicMessage;
import com.xfyyim.cn.bean.event.EventDeleteDynamic;
import com.xfyyim.cn.bean.event.SendTextSucc;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.circle.range.SendTextPicActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.TimeUtils;
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
import butterknife.Unbinder;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
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

    @BindView(R.id.rl_root)
    LinearLayout rl_root;

    private View mHeadView;

    ImageView avatar_img;
    ImageView img_vip;

    LinearLayout ll_fan;
    LinearLayout ll_att;
    LinearLayout ll_zan;
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
    private String mLoginUserId;
    private List<PublicMessage> mMessages = new ArrayList<>();
    private PublicCareRecyclerAdapter mAdapter;

    private boolean isAtt = false;
       User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_blum);
        unbinder = ButterKnife.bind(this);
        friendId = getIntent().getStringExtra("FriendId");
        mLoginUserId = coreManager.getSelf().getUserId();
        initActionBar();
        initView();
        getUserInfo();
        requestData(true);

    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventDeleteDynamic message) {
       getUserInfo();
           if(user.getMsgCount()==1){
               tv_my_blum.setText("我的相册0");
           }else {
               tv_my_blum.setText("我的相册 " + (user.getMsgCount()-1));
           }
    }

 @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshData( SendTextSucc sendTextSucc){
                requestData(true);
    }


    public void initView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonBlumActivity.this);
        rv_blum.setLayoutManager(linearLayoutManager
        );

        mAdapter = new PublicCareRecyclerAdapter(PersonBlumActivity.this, rl_root, coreManager, mMessages);
        rv_blum.setAdapter(mAdapter);


        mHeadView = LayoutInflater.from(PersonBlumActivity.this).inflate(R.layout.header_my_blum, rv_blum, false);
        avatar_img = mHeadView.findViewById(R.id.avatar_img);
        img_vip = mHeadView.findViewById(R.id.img_vip);
        ll_fan = mHeadView.findViewById(R.id.ll_fan);
        ll_att = mHeadView.findViewById(R.id.ll_att);
        ll_zan = mHeadView.findViewById(R.id.ll_zan);
        tv_fans = mHeadView.findViewById(R.id.tv_fans);
        tv_att = mHeadView.findViewById(R.id.tv_att);
        tv_zan = mHeadView.findViewById(R.id.tv_zan);
        tv_age = mHeadView.findViewById(R.id.tv_age);
        tv_status = mHeadView.findViewById(R.id.tv_status);
        tv_my_blum = mHeadView.findViewById(R.id.tv_my_blum);
        tv_commit = mHeadView.findViewById(R.id.tv_commit);


        rv_blum.addHeaderView(mHeadView);

        tv_commit.setOnClickListener(this);
       ll_fan.setOnClickListener(this);
        ll_att.setOnClickListener(this);
        ll_zan.setOnClickListener(this);



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
        EventBusHelper.register(this);
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
                if (mLoginUserId.equals(friendId)) {
                    Intent intent = new Intent(PersonBlumActivity.this, SendTextPicActivity.class);
                    intent.putExtra("topicType", "0");
                    startActivity(intent);
                } else {
                    if (isAtt) {
                        deleteFriend(friendId);
                    } else {
                        doAddAttention(friendId);
                    }
                }

                break;
            case R.id.iv_title_left:
                finish();
                break;

            case R.id.avatar_img:
                Intent intent = new Intent(PersonBlumActivity.this, PersonInfoActivity.class);
                intent.putExtra("FriendId", friendId);
                startActivity(intent);
                break;
            case R.id.ll_fan:
                if (user.getFansCount() == 0) {
                    ToastUtil.showLongToast(PersonBlumActivity.this, "暂无粉丝");
                } else {
                    Intent intent2 = new Intent(PersonBlumActivity.this, FansActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.ll_att:
                if (user.getAttCount() == 0) {
                    ToastUtil.showLongToast(PersonBlumActivity.this, "暂无关注的人");
                } else {
                    Intent intent2 = new Intent(PersonBlumActivity.this, AttentionActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.ll_blum:
                if (user.getFriendsCount() == 0) {
                    ToastUtil.showLongToast(PersonBlumActivity.this, "暂无获赞的人");
                } else {
                    Intent intentperson = new Intent(PersonBlumActivity.this, ZanActivity.class);
                    intentperson.putExtra("FriendId", user.getUserId());
                    startActivity(intentperson);
                }
                break;
        }
    }


    public void setUiData(User user) {

        isAtt = user.getIsBeAtt() == 0 ? false : true;
        if (mLoginUserId.equals(friendId)) {
            tv_commit.setText("发布动态");


        } else {
            if (!isAtt) {
                tv_commit.setText("关注");
                tv_commit.setBackground(mContext.getDrawable(R.drawable.shape_fc607e_10));
            } else {
                tv_commit.setText("已关注");
                tv_commit.setBackground(mContext.getDrawable(R.drawable.shape_e5e5e5_10));
            }

        }

        tv_title_center.setText(user.getNickName());
        AvatarHelper.getInstance().displayAvatar(friendId, avatar_img, false);
        if (user.getFaceIdentity() == 1) {
            img_vip.setVisibility(View.VISIBLE);
        } else {
            img_vip.setVisibility(View.GONE);
        }

        tv_fans.setText(String.valueOf(user.getFansCount()));//粉丝
        tv_att.setText(String.valueOf(user.getAttCount()));//关注
        tv_zan.setText(String.valueOf(user.getGetPraisesCount()));//获赞数

        if (user.getUserId().equals(friendId)) {
            tv_status.setVisibility(View.GONE);
        } else {
            String time="";
            if (user.getShowLastLoginTime()>0){
                 time = TimeUtils.getFriendlyTimeDesc(this,user.getShowLastLoginTime());
            }

            if (user.getDistance() == 0) {
                tv_status.setText(time);
            } else {
                tv_status.setText(user.getDistance() + "  " +time);
            }

        }
        tv_age.setText(user.getAge() + "  " + user.getStarSign());

        tv_my_blum.setText("我的相册 " + user.getMsgCount());
    //   tv_zan.setText(String.valueOf(user.getPraiseCount()));

    }

    public void getUserInfo() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(PersonBlumActivity.this).getAccessToken());
        params.put("userId", friendId);
        params.put("forUserId", friendId);

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                             user = result.getData();
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
            mRefreshLayout.setNoMoreData(true);
            refreshComplete();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);

        params.put("userId", friendId);

      //  params.put("userId", mLoginUserId);
    //   params.put("forUserId", friendId);
        params.put("pageSize", String.valueOf(PAGER_SIZE));
        params.put("pageIndex", String.valueOf(pager_index));

        HttpUtils.get().url(coreManager.getConfig().MSG_MY_LIST)
                .params(params)
                .build()
                .execute(new ListCallback<PublicMessage>(PublicMessage.class) {
                    @Override
                    public void onResponse(ArrayResult<PublicMessage> result) {
                        if (PersonBlumActivity.this != null && Result.checkSuccess(PersonBlumActivity.this, result)) {
                            List<PublicMessage> data = result.getData();

                            if (data != null && data.size() > 0) {
                                mMessages.clear();
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


    /**
     * 加关注
     */
    private void doAddAttention(String messageUserId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("toUserId", messageUserId);
        params.put("fromAddType", messageUserId);


        HttpUtils.get().url(coreManager.getConfig().FRIENDS_ATTENTION_ADD)
                .params(params)
                .build()
                .execute(new BaseCallback<AddAttentionResult>(AddAttentionResult.class) {
                    @Override
                    public void onResponse(ObjectResult<AddAttentionResult> result) {
                        if (Result.checkSuccess(mContext, result)) {
                            ToastUtil.showToast(mContext, "关注成功");
                            isAtt = true;
                            tv_commit.setText("已关注");
                            tv_commit.setBackground(mContext.getDrawable(R.drawable.shape_e5e5e5_10));
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 取消关注
     */
    private void deleteFriend(String userID) {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("toUserId", userID);

        HttpUtils.get().url(coreManager.getConfig().FRIENDS_ATTENTION_DELETE)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        ToastUtil.showToast(mContext, "取消关注成功");
                        isAtt = false;
                        tv_commit.setText("关注");
                        tv_commit.setBackground(mContext.getDrawable(R.drawable.shape_fc607e_10));
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }



}
