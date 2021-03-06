package com.xfyyim.cn.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.LikeMeBean;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.RecyclerSpace;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SuperCriticalLightWindow;
import com.xfyyim.cn.view.SuperSolarizePopupWindow;
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
import butterknife.Unbinder;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

public class

CheckLikesMeActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tv_rmot)
    TextView tvRmot;

    @BindView(R.id.ivSolarize)
    ImageView ivSolarize;
    private SmartRefreshLayout mRefreshLayout;
    private CheckLikesMeAdapter checkLikesMeAdapter;
    private Unbinder unbinder;
    private List<LikeMeBean> likeMeBeanList = new ArrayList<LikeMeBean>();
    private boolean more = true;
    private int payFunction;//支付功能类型回调
    private    RecyclerView rclBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_likes_me);
        unbinder = ButterKnife.bind(this);
        initView();
        initActionBar();

    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("喜欢我的人");

    }

    private void initView() {

        EventBusHelper.register(this);
        mRefreshLayout = findViewById(R.id.refreshLayout);
         rclBill = (RecyclerView) findViewById(R.id.rclCheck);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        rclBill.addItemDecoration(new RecyclerSpace(20));
//        rclBill.addItemDecoration(new RecycleItemSpance(15,2));

        rclBill.setLayoutManager(linearLayoutManager);

        checkLikesMeAdapter = new CheckLikesMeAdapter(this);
        checkLikesMeAdapter.likeMeBeanList = likeMeBeanList;
        rclBill.setAdapter(checkLikesMeAdapter);
        checkLikesMeAdapter.notifyDataSetChanged();

        checkLikesMeAdapter.setBtnOnClice(new CheckLikesMeAdapter.BtnOnClick() {
            @Override
            public void btnOnClick(LikeMeBean likeMeBean) {
                //跳转个人资料页面
                Intent intent1 = new Intent(CheckLikesMeActivity.this, PersonInfoActivity.class);
                intent1.putExtra("FriendId", String.valueOf(likeMeBean.getUserId()));
                intent1.putExtra("isShowLike",1);
                likeMeBeanList.clear();
                startActivity(intent1);
            }
        });

        ivSolarize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //爆光
                //   superLight(coreManager.getSelf());
                openSuperSolarize();

            }
        });

     mRefreshLayout.setOnRefreshListener(rl -> {
         pageIndex=0;
         getWhoLikeMe();

        });

     mRefreshLayout.setOnLoadMoreListener(rl->{
         pageIndex++;
         getWhoLikeMe();
     });


    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        switch (payFunction) {
            //超级爆光回调
            case 1:
                superLight(coreManager.getSelf());
                break;
        }
    }

    private int pageIndex = 0;

    private void getWhoLikeMe() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", "20");

        HttpUtils.post().url(coreManager.getConfig().USER_WHO_LIKE_ME)
                .params(params)
                .build()
                .execute(new ListCallback<LikeMeBean>(LikeMeBean.class) {
                    @Override
                    public void onResponse(ArrayResult<LikeMeBean> result) {
                        refreshComplete();
                        if (Result.checkSuccess(getApplicationContext(), result)) {
                            if(result.getData()!=null && result.getData().size()>0){
                                likeMeBeanList.clear();

                                rclBill.setVisibility(View.VISIBLE);
                                tvRmot.setVisibility(View.GONE);
                                ivSolarize.setVisibility(View.VISIBLE);
                                checkLikesMeAdapter.likeMeBeanList.addAll(result.getData());
                                tvTitleCenter.setText("喜欢我的人(" + likeMeBeanList.size() + ")");
                                checkLikesMeAdapter.notifyDataSetChanged();
                            }else {
                                if (likeMeBeanList==null||likeMeBeanList.size()==0){
                                    tvRmot.setVisibility(View.VISIBLE);
                                    rclBill.setVisibility(View.GONE);
                                    ivSolarize.setVisibility(View.GONE);

                                }

                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        refreshComplete();
                    }
                });
    }

    /**
     * 停止刷新动画
     */
    private void refreshComplete() {
        rclBill.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        }, 200);
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

    @Override
    protected void onResume() {
        super.onResume();
        getWhoLikeMe();
    }

    public static class CheckLikesMeAdapter extends RecyclerView.Adapter<CheckLikesMeAdapter.ViewHolder> implements View.OnClickListener {
        private LayoutInflater mInflater;
        private Context context;
        private List<LikeMeBean> likeMeBeanList = new ArrayList<LikeMeBean>();
        private BtnOnClick btnOnClick;

        public CheckLikesMeAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }

        public interface BtnOnClick {
            void btnOnClick(LikeMeBean likeMeBean);
        }

        public void setBtnOnClice(BtnOnClick btnOnClick) {
            this.btnOnClick = btnOnClick;

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
            if (likeMeBeanList != null && likeMeBeanList.size() > 0) {
                final LikeMeBean likeMeBean = likeMeBeanList.get(position);

                if (likeMeBean.getLikeUserAge()==0){

                    holder.tvLikeName.setText(likeMeBean.getMyNickname());
                }else{
                    holder.tvLikeName.setText( likeMeBean.getMyNickname()+", "+likeMeBean.getLikeUserAge());
                }

                String url = AvatarHelper.getAvatarUrl(likeMeBean.getUserId() + "", false);

                //   GlideImageUtils.setImageView(context, url, holder.ivlike);
                GlideImageUtils.setImageDrawableCirCle(context, url, holder.ivlike);
                holder.ivlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnOnClick.btnOnClick(likeMeBean);
                    }
                });
            }

            }

        @Override
        public int getItemCount() {
            return likeMeBeanList.size();
        }
    }



    private void swithSuperSolarize(int switchType) {
        SuperSolarizePopupWindow RegretsUnLikePopupWindow = new SuperSolarizePopupWindow(this, switchType, false);
        RegretsUnLikePopupWindow.setBtnOnClice(new SuperSolarizePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(int sumbitType) {
                superLight(coreManager.getSelf());
            }
        });
    }

    /**
     * 超级爆光
     *
     * @param user
     */
    private void superLight(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", user.getUserId());
        params.put("nickname", user.getNickName());
        params.put("age", user.getAge() + "");
        HttpUtils.post().url(coreManager.getConfig().USER_OPEN_SUPEREXPOSURE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (Result.checkSuccess(CheckLikesMeActivity.this, result)) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                            if (coreManager.getSelf().getUserVIPPrivilege().getOutFlag() == 1) {
                                swithSuperSolarize(1);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 打开超级爆光支付页面
     */
    private void openSuperSolarize() {
        SuperCriticalLightWindow superCriticalLightWindow = new SuperCriticalLightWindow(this, coreManager.getSelf().getUserVIPPrivilegeConfig());
        superCriticalLightWindow.setBtnOnClice(new SuperCriticalLightWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type) {
                payFunction = 1;
                superLightPay(type, coreManager.getSelf().getUserVIPPrivilegeConfig().getOutPrice());
            }
        });
    }

    /**
     * 超级爆光支付
     */
    private void superLightPay(String type, int outPrice) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("funType", String.valueOf(1));
        params.put("price", String.valueOf(outPrice));
        params.put("num", String.valueOf(1));
        params.put("mon", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        params.put("payType", type);
        AlipayHelper.rechargePay(CheckLikesMeActivity.this, coreManager, params);
    }
}
