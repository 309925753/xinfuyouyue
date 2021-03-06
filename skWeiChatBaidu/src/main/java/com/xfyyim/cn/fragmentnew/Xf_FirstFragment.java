package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.SpContext;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventNotifyUpdate;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.customer.AngleTransformer;
import com.xfyyim.cn.customer.MyAlphaTransformer;
import com.xfyyim.cn.customer.StackLayout;
import com.xfyyim.cn.customer.StackPageTransformer;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.PreferenceUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.MyVipPaymentPopupWindow;
import com.xfyyim.cn.view.SuperCriticalLightWindow;
import com.xfyyim.cn.view.SuperSolarizePopupWindow;
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
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import fm.jiecao.jcvideoplayer_lib.MessageEvent;
import okhttp3.Call;

public class Xf_FirstFragment extends EasyFragment {
    private TextView mTvTitle;
    private List<User> mUsers;
    List<User> listTest = new ArrayList<>();
    @BindView(R.id.ll_superLight)
    LinearLayout ll_superLigth;
    RelativeLayout rlShow;
    LinearLayout llFuction;
    TextView tvlikeTimes;

    StackLayout mStackLayout;
    Adapter mAdapter;
    private int pageIndex = 0;
    private int pageSize = 50;
    private boolean isSliding = false;
    private int payFunction = 0;//判断支付回调功能类型
    private boolean isRegret = false;

    User lastUnLikeUser;
    User user;

    public static int index;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initActionBar();
        initView();


    }

    private void initActionBar() {
        findViewById(R.id.iv_title_left).setVisibility(View.GONE);
        mTvTitle = findViewById(R.id.tv_title_center);
        mTvTitle.setText("幸福有约");
        mTvTitle.setTextColor(getResources().getColor(R.color.white));
    }

    private void initView() {

        mStackLayout = (StackLayout) findViewById(R.id.stack_layout);
        rlShow = (RelativeLayout) findViewById(R.id.rl_Show);
        llFuction = (LinearLayout) findViewById(R.id.ll_fuction);
        tvlikeTimes = (TextView) findViewById(R.id.tv_like_times);


        ll_superLigth.setOnClickListener(this::onClick);
        findViewById(R.id.llRegretsUnLike).setOnClickListener(this::onClick);
        findViewById(R.id.llUnLike).setOnClickListener(this::onClick);
        findViewById(R.id.ll_superLight).setOnClickListener(this::onClick);
        findViewById(R.id.llUserLike).setOnClickListener(this::onClick);
        findViewById(R.id.llSuperLike).setOnClickListener(this::onClick);
        EventBusHelper.register(this);
    }


    public void setmAdapter(List<User> list) {
        mStackLayout.setAdapter(mAdapter = new Adapter(list));
        mStackLayout.addPageTransformer(
                new StackPageTransformer(),     // 堆叠
                new MyAlphaTransformer(),       // 渐变
                new AngleTransformer()          // 角度
        );


        mStackLayout.setOnSwipeListener(
                new StackLayout.OnSwipeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                        isSliding = true;
                        index = swipedItemPos;
                        LogUtil.e("cjh 滑动111 index  " + index);
                        LogUtil.e("cjh 滑动111222 " + itemLeft + "  isSwipeLeft  " + isSwipeLeft + "--swipedItemPos = " + swipedItemPos + "  数据： " + mUsers.size());
                        if (isSwipeLeft) {
                            unLike(list.get(swipedItemPos));
                            isRegret = true;
                        } else {
                            userLike(list.get(swipedItemPos));
                        }

                        if (itemLeft == 0) {
                            LogUtil.e("cjh 滑动ddddd  " + index);
                            requestData();
                        }
                    }
                });

    }

    /**
     * 喜欢
     *
     * @param user
     */
    private void userLike(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", user.getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                            isRegret = false;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 不喜欢
     *
     * @param user
     */
    private void unLike(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", user.getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_UN_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            lastUnLikeUser = user;
                            LogUtil.e("cjh 不喜欢请求   " + listTest.size());

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 超级爆光
     */
    private void superLight(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", userId);
        HttpUtils.post().url(coreManager.getConfig().USER_OPEN_SUPEREXPOSURE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
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
     * 反悔不喜欢
     *
     * @param user
     */
    private void RegretsUnLike(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", user.getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_REGRETS_UN_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
//                            User user = result.getData();
//                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
//                            // 设置登陆用户信息
//                            if (updateSuccess) {
//                                // 如果成功，保存User变量，
//                                coreManager.setSelf(user);
//                            }
//                            if (mUsers != null && (!isSliding)) {
//                                swithSuperSolarize(0);
//                            } else {
//                                if (lastUnLikeUser!=null) {
//                                    mUsers.add(0, lastUnLikeUser);
//                                    mAdapter.notifyDataSetChanged();
//                                }
//                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }


    class Adapter extends StackLayout.Adapter<Adapter.ViewHolder> {
        List<User> mData;

        public void setData(List<User> data) {

            mData = data;
        }

        public List<User> getData() {
            return mData;
        }

        public Adapter(List<User> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String url = AvatarHelper.getAvatarUrl(mData.get(position).getUserId(), false);
            GlideImageUtils.setImageView(getActivity(), url, holder.img_pic);
            holder.tvName.setText(mData.get(position).getNickName());


            if (mData.get(position).getSettings().getNotSeeFilterMyPhotos() == 1) {
                holder.rl_home.setVisibility(View.GONE);
                holder.ivShow.setVisibility(View.GONE);

            } else {
                holder.rl_home.setVisibility(View.VISIBLE);
                holder.ivShow.setVisibility(View.VISIBLE);

            }

            if (mData.get(position).getSex() == 1) {
                holder.img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_man));
                holder.ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_qing));
            } else {
                holder.img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_nv));
                holder.ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_pink));
            }
            if (!TextUtils.isEmpty(mData.get(position).getDescription())) {
                holder.tv_description.setText(String.valueOf(mData.get(position).getDescription()));
            }

            holder.tvAge.setText(mData.get(position).getAge() + "");


            if (!TextUtils.isEmpty(mData.get(position).getStarSign())) {
                holder.tvCnstellation.setText(mData.get(position).getStarSign());
                holder.tvCnstellation.setVisibility(View.VISIBLE);
            }

            if (mData.get(position).getMyPhotos() != null && mData.get(position).getMyPhotos().size() >= 1) {
                holder.tv_Likecount.setText(String.valueOf(mData.get(position).getMyPhotos().size()));
            }
            if (mData.get(position).getFaceIdentity() == 1) {
                holder.ivHead.setVisibility(View.VISIBLE);
            }
           /* if (mData.get(position).getMsgImgs() != null && !TextUtils.isEmpty(mData.get(position).getMsgImgs())) {
                holder.tvLike.setText(mData.get(position).getMsgImgs().split(",").length+"");
            }*/
            if (mData.get(position).getSettings().getNotSeeFilterMyPhotos() == 0) {
                holder.ivShow.setVisibility(View.VISIBLE);
                holder.rl_home.setVisibility(View.VISIBLE);
            } else {
                holder.ivShow.setVisibility(View.INVISIBLE);
                holder.rl_home.setVisibility(View.INVISIBLE);
            }

            if (mData.get(position).getUserVIPPrivilege() != null) {
                if (mData.get(position).getUserVIPPrivilege().getVipLevel().equals("-1")) {
                    holder.tvVip.setVisibility(View.INVISIBLE);
                    holder.tvName.setTextColor(getResources().getColor(R.color.text_black_333));
                } else {
                    holder.tvVip.setVisibility(View.VISIBLE);
                }
            }
            String cityDistance = "";
            if (!TextUtils.isEmpty(mData.get(position).getCityName())) {
                cityDistance = mData.get(position).getCityName();
            }

            double distance = Math.rint(mData.get(position).getDistance() / 100) / 10;

            if (distance > 0) {
                cityDistance = cityDistance + "(" + distance + "km)";
            } else {
                cityDistance = cityDistance;
            }
            holder.tv_city.setText(cityDistance);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                    intent.putExtra("FriendId", mData.get(position).getUserId());

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends StackLayout.ViewHolder {
            ImageView img_pic;
            ImageView img_sex;
            LinearLayout ll_sex_bg;
            ImageView ivHead;
            ImageView ivShow;
            TextView tvName;
            TextView tvCnstellation;
            TextView tvAge;
            TextView tvVip;
            TextView tv_Likecount;
            TextView tv_city;
            TextView tv_description;
            RelativeLayout rl_home;


            public ViewHolder(View itemView) {
                super(itemView);
                img_pic = itemView.findViewById(R.id.img_pic);
                ivShow = itemView.findViewById(R.id.ivtSee);
                ivHead = itemView.findViewById(R.id.ivHead);
                img_sex = itemView.findViewById(R.id.img_sex);
                tvName = itemView.findViewById(R.id.t1);
                tvAge = itemView.findViewById(R.id.t2);
                tvCnstellation = itemView.findViewById(R.id.t3);
                tvVip = itemView.findViewById(R.id.t4);
                tv_Likecount = itemView.findViewById(R.id.tv_Likecount);
                ll_sex_bg = itemView.findViewById(R.id.ll_sex_bg);
                tv_city = itemView.findViewById(R.id.tv_city);
                tv_description = itemView.findViewById(R.id.tv_description);
                rl_home = itemView.findViewById(R.id.rlhome);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
        getUserInfo();
    }

    private void requestData() {
        pageIndex++;
        mUsers = new ArrayList<>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());

        if (PreferenceUtils.getBoolean(getActivity(), coreManager.getSelf().getUserId() + SpContext.ISSELECT)) {
            params.put("longitude", PreferenceUtils.getString(getActivity(), coreManager.getSelf().getUserId() + SpContext.LON));
            params.put("latitude", PreferenceUtils.getString(getActivity(), coreManager.getSelf().getUserId() + SpContext.LAT));
        } else {
            params.put("longitude", String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLongitude()));
            params.put("latitude", String.valueOf(MyApplication.getInstance().getBdLocationHelper().getLatitude()));
        }

        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSize", String.valueOf(pageSize));

        HttpUtils.post().url(coreManager.getConfig().USER_NEAR_BY)
                .params(params)
                .build()
                .execute(new ListCallback<User>(User.class) {
                    @Override
                    public void onResponse(ArrayResult<User> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(getActivity(), result)) {
                            List<User> datas = result.getData();
                            listTest.clear();
                            index=0;
                            listTest = result.getData();
                            if (datas != null && datas.size() > 0) {
                                rlShow.setVisibility(View.GONE);
                                mStackLayout.setVisibility(View.VISIBLE);
                                llFuction.setVisibility(View.VISIBLE);
                                mUsers.addAll(datas);
                                setmAdapter(mUsers);
                            } else {
                                //    ToastUtil.showLongToast(getContext(),"请去设置中心修改位置范围");
                                rlShow.setVisibility(View.VISIBLE);
                                mStackLayout.setVisibility(View.GONE);
                                llFuction.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llRegretsUnLike:
                    if (coreManager.getSelf().getUserVIPPrivilege() != null) {
                        if (coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")) {
                            BuyMember(listTest.get(index));
                        } else {
                            RegretsUnLike(listTest.get(index));
                        }
                    }
                    break;
                case R.id.llUnLike:
                    LogUtil.e("cjh 点击  index  " + index + "   size  " + listTest.size());
                    if (listTest.size()==0) {
                        requestData();
                    } else {
                        mStackLayout.OnLeft();
                        unLike(listTest.get(index));

                    }


                    break;
                case R.id.ll_superLight:
                    payFunction = 1;
                    openSuperSolarize();
                    break;
                case R.id.llUserLike:
                    if (listTest.size()==0) {
                        requestData();
                    } else {

                        if (coreManager.getSelf().getUserVIPPrivilege() != null && !coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")) {
                            mStackLayout.OnLeft();
                            userLike(listTest.get(index));
                        } else {
                            if (coreManager.getSelf().getLikeTimesPerDay() > 0) {
                                mStackLayout.OnLeft();
                                userLike(listTest.get(index));
                            } else {
                                BuyMember(listTest.get(index));
                            }
                        }
                    }
                    break;
                case R.id.llSuperLike:
                    if (listTest.size()==0) {
                        requestData();
                    } else {

                        if (coreManager.getSelf().getUserVIPPrivilege() != null && !coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")) {
                            int num = coreManager.getSelf().getUserVIPPrivilege().getQuantity() + coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity();
                            if (coreManager.getSelf().getUserVIPPrivilege().getQuantity() == 0) {
                                BuyMember(listTest.get(index));
                            } else {
                                mStackLayout.OnLeft();
                                superLike(listTest.get(index));
                            }
                        } else {
                            if (coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity() == 0) {
                                BuyMember(listTest.get(index));
                            } else {
                                mStackLayout.OnLeft();
                                superLike(listTest.get(index));
                            }
                        }
                    }


//                    if (coreManager.getSelf().getUserVIPPrivilege() != null&&!coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1")) {
//                        if (coreManager.getSelf().getUserVIPPrivilege().getVipLevel().equals("-1") || coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity() >= 1) {
//                            if (coreManager.getSelf().getUserVIPPrivilege().getQuantity() >= 1 || coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity() >= 1) {
//                                //  tvlikeTimes.setText(String.valueOf(coreManager.getSelf().getUserVIPPrivilege().getQuantity()));
//                                superLike(mUsers.get(index));
//                            } else {
//                                if ((coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity() == 0)) {
//                                    BuyMember(mUsers.get(index));
//                                }
//                            }
//
//                        } else {
//                            if (coreManager.getSelf().getUserVIPPrivilege().getQuantity() >= 1 || coreManager.getSelf().getUserVIPPrivilege().getSuperLikeQuantity() >= 1) {
//                                //    tvlikeTimes.setText(String.valueOf(coreManager.getSelf().getUserVIPPrivilege().getQuantity()));
//                                superLike(mUsers.get(index));
//                            } else {
//                                ToastUtil.showLongToast(getContext(), "当天次数已用完");
//                            }
//
//                        }
//                    }

                    break;
            }

    }

    /**
     * 超级喜欢
     *
     * @param user
     */
    private void superLike(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId", user.getUserId());
        params.put("nickname", user.getNickName());
        params.put("age", user.getAge() + "");
        HttpUtils.post().url(coreManager.getConfig().USER_SUPER_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User userSuper = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(userSuper);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(userSuper);
                            }

                            if (userSuper.getUserVIPPrivilege() != null) {
                                if (userSuper.getUserVIPPrivilege().getQuantity() >= 1 || userSuper.getUserVIPPrivilege().getSuperLikeQuantity() >= 1) {
                                    tvlikeTimes.setText(String.valueOf(userSuper.getUserVIPPrivilege().getQuantity() + userSuper.getUserVIPPrivilege().getSuperLikeQuantity()));
                                    tvlikeTimes.setVisibility(View.VISIBLE);
                                } else {
                                    tvlikeTimes.setVisibility(View.GONE);
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember(User user) {
        //显示VIP购买会员
        if (user.getUserVIPPrivilege() != null) {


            MyVipPaymentPopupWindow myVipPaymentPopupWindow = new MyVipPaymentPopupWindow(getActivity(), user.getUserVIPPrivilegeConfig(), coreManager.getSelf().getUserId(), coreManager.getSelf().getNickName(), user.getUserVIPPrivilege().getVipLevel());
            myVipPaymentPopupWindow.setBtnOnClice(new MyVipPaymentPopupWindow.BtnOnClick() {
                @Override
                public void btnOnClick(String type, int vip) {
                    LogUtil.e("********************************* pay type = " + type + "-------------vip = " + vip);
                    submitPay(type, vip, user.getUserVIPPrivilegeConfig());
                }
            });
        }
    }

    /**
     * 转支付类型返回支付签名数据
     *
     * @param type
     * @param vip
     */
    private void submitPay(String type, int vip, UserVIPPrivilegePrice vipPrivilegePriceList) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("payType", type);
        if (vip == 1) {
            params.put("price", vipPrivilegePriceList.getV0Price() + "");
            params.put("level", vipPrivilegePriceList.getV0());
        } else if (vip == 2) {
            params.put("price", vipPrivilegePriceList.getV1Price() + "");
            params.put("level", vipPrivilegePriceList.getV1());
        } else if (vip == 3) {
            params.put("price", vipPrivilegePriceList.getV2Price() + "");
            params.put("level", vipPrivilegePriceList.getV2());
        } else if (vip == 4) {
            params.put("price", vipPrivilegePriceList.getV3Price() + "");
            params.put("level", vipPrivilegePriceList.getV3());
        }
        params.put("funType", String.valueOf(5));
        params.put("num", String.valueOf(-1));
        params.put("mon", String.valueOf(-1));
        AlipayHelper.rechargePay(getActivity(), coreManager, params);

    }

    private void swithSuperSolarize(int switchType) {
        SuperSolarizePopupWindow RegretsUnLikePopupWindow = new SuperSolarizePopupWindow(getActivity(), switchType, true);
        RegretsUnLikePopupWindow.setBtnOnClice(new SuperSolarizePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(int sumbitType) {
                //超级爆光再来一次
                if (switchType == 1 && sumbitType == 1) {
                    openSuperSolarize();
                    //超级爆光滑动卡片
                } else if (switchType == 1 && sumbitType == 0) {
                    //无法反悔
                } else if (switchType == 0 && sumbitType == 1) {

                }

            }
        });
    }

    /**
     * 开启超级级爆光
     * 支付成功回调同时再弹出超级爆光页面
     */
    private void openSuperSolarize() {
        if (coreManager.getSelf().getUserVIPPrivilegeConfig() != null) {
            SuperCriticalLightWindow superCriticalLightWindow = new SuperCriticalLightWindow(getActivity(), coreManager.getSelf().getUserVIPPrivilegeConfig());
            superCriticalLightWindow.setBtnOnClice(new SuperCriticalLightWindow.BtnOnClick() {
                @Override
                public void btnOnClick(String type) {
                    LogUtil.e("type =  " + type);
                        /*Intent intent = new Intent(MyNewWalletActivity.this, WxPayAdd.class);
                        startActivity(intent);*/
                    //   AlipayHelper.recharge(getActivity(), coreManager, userVIPPrivilegePrice.getOutPrice()+"");
                    //
                    superLightPay(type, coreManager.getSelf().getUserVIPPrivilegeConfig().getOutPrice());
                }
            });
        }
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
        AlipayHelper.rechargePay(getActivity(), coreManager, params);
    }


    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        switch (payFunction) {
            //超级爆光回调
            case 1:
                superLight(coreManager.getSelf().getUserId());
                break;
            default:
                updateSelfData();
                break;
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventNotifyUpdate message) {
        LogUtil.e("cjhhelle 111= " + message.MessageData);

        pageIndex = 0;
        requestData();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(MessageEvent message) {

        LogUtil.e("cjhhelle"        );
        if (message.message.equals("HomeLike")) {
            rlShow.setVisibility(View.VISIBLE);
            mStackLayout.setVisibility(View.GONE);
            llFuction.setVisibility(View.GONE);
            pageIndex = 0;
            requestData();
        }

    }


    /**
     * 支付完成后更新用户信息
     */
    private void updateSelfData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }

    private void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {

                        if (result.getResultCode() == 1 && result.getData() != null) {
                            user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                            if (!(user.getUserVIPPrivilege().getQuantity() + user.getUserVIPPrivilege().getSuperLikeQuantity() == 0)) {
                                tvlikeTimes.setText(String.valueOf(user.getUserVIPPrivilege().getQuantity() + user.getUserVIPPrivilege().getSuperLikeQuantity()));
                                tvlikeTimes.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(getActivity());
                    }
                });
    }

}
