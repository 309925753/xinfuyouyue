package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xfyyim.cn.BuildConfig;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.ShareEntity;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.UserVIPPrivilegePrice;
import com.xfyyim.cn.bean.event.EventPaySuccess;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.circle.range.SendTextPicActivity;
import com.xfyyim.cn.ui.contacts.ContactsMsgInviteActivity;
import com.xfyyim.cn.ui.me.CertificationCenterActivity;
import com.xfyyim.cn.ui.me.CheckLikesMeActivity;
import com.xfyyim.cn.ui.me.EditInfoActivity;
import com.xfyyim.cn.ui.me.MyNewWalletActivity;
import com.xfyyim.cn.ui.me.MyPrerogativeActivity;
import com.xfyyim.cn.ui.me.NewSettingsActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me_new.AttentionActivity;
import com.xfyyim.cn.ui.me_new.FansActivity;
import com.xfyyim.cn.ui.me_new.InviteActivity;
import com.xfyyim.cn.ui.me_new.PersonBlumActivity;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.ui.me_new.ZanActivity;
import com.xfyyim.cn.util.EventBusHelper;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.util.share.WxShareUtils;
import com.xfyyim.cn.view.MyPrivilegePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;

public class MeNewFragment extends EasyFragment implements View.OnClickListener {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_edit_info)

    TextView tv_edit_info;

    @BindView(R.id.tv_vip)
    TextView tv_vip;
    @BindView(R.id.tv_history)
    TextView tv_history;

    @BindView(R.id.tv_blum)
    TextView tv_blum;
    @BindView(R.id.tv_fans)
    TextView tv_fans;
    @BindView(R.id.tv_guanzhu)
    TextView tv_guanzhu;

    @BindView(R.id.ll_showdt)
    LinearLayout ll_showdt;
    @BindView(R.id.ll_my_blum)
    LinearLayout ll_my_blum;

    @BindView(R.id.rl_pyq)
    RelativeLayout rl_pyq;

    @BindView(R.id.rl_mytequan)
    RelativeLayout rl_mytequan;

    @BindView(R.id.rl_likeme)
    RelativeLayout rl_likeme;

    @BindView(R.id.rl_wallet)
    RelativeLayout rl_wallet;

    @BindView(R.id.rl_renzheng)
    RelativeLayout rl_renzheng;

    @BindView(R.id.rl_set)
    RelativeLayout rl_set;

    @BindView(R.id.rl_guide)
    RelativeLayout rl_guide;
    @BindView(R.id.rl_invite)
    RelativeLayout rl_invite;

    @BindView(R.id.rl_biaobai)
    RelativeLayout rl_biaobai;

    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.rlInfoBackground)
    RelativeLayout rlInfoBackground;


    @BindView(R.id.avatar_img)
    ImageView avatar_img;

    @BindView(R.id.ll_att)
    LinearLayout ll_att;
    @BindView(R.id.ll_fan)
    LinearLayout ll_fan;
    @BindView(R.id.ll_blum)
    LinearLayout ll_blum;

    User user;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_last_me;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initView();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(coreManager.getSelf().getLikeMeCount() + "")) {
            tv2.setText(coreManager.getSelf().getLikeMeCount() + "人喜欢我");
        }
        if (!TextUtils.isEmpty(coreManager.getSelf().getBalance() + "")) {
            tvBalance.setText(coreManager.getSelf().getBalance() + "RMB");
        }
    }

    public void initView() {
        rl_pyq.setOnClickListener(this);
        rl_mytequan.setOnClickListener(this);
        rl_likeme.setOnClickListener(this);
        rl_wallet.setOnClickListener(this::onClick);
        rl_renzheng.setOnClickListener(this);
        rl_set.setOnClickListener(this);
        rl_guide.setOnClickListener(this);
        rl_invite.setOnClickListener(this);
        rl_biaobai.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        ll_showdt.setOnClickListener(this);
        ll_att.setOnClickListener(this);
        ll_fan.setOnClickListener(this);
        ll_blum.setOnClickListener(this);
        tv_history.setOnClickListener(this);
    //    rlInfoBackground.setOnClickListener(this);
        avatar_img.setOnClickListener(this);
        tv_edit_info.setOnClickListener(this);
        EventBusHelper.register(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventPaySuccess message) {
        getUserInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_edit_info:
                Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.avatar_img:
                Intent intent1 = new Intent(getActivity(), PersonInfoActivity.class);
                intent1.putExtra("FriendId", coreManager.getSelf().getUserId());
                startActivity(intent1);
                break;

            case R.id.rl_pyq:
                startToPersonBlum();
                break;
            case R.id.rl_mytequan:
                startActivity(new Intent(getActivity(), MyPrerogativeActivity.class));
                break;
            case R.id.rl_likeme:
                //查看喜欢我特权按月：0无权 1有权"
                if (coreManager.getSelf().getUserVIPPrivilege().getLikePrivilegeFlag()==1) {
                    startActivity(new Intent(getActivity(), CheckLikesMeActivity.class));
                } else {
                    BuyMember(coreManager.getSelf().getUserVIPPrivilegeConfig());
                }
                break;
            case R.id.rl_wallet:
                startActivity(new Intent(getActivity(), MyNewWalletActivity.class));
                break;
            case R.id.rl_renzheng:
                startActivity(new Intent(getActivity(), CertificationCenterActivity.class));
                break;
            case R.id.rl_set:
                startActivity(new Intent(getActivity(), NewSettingsActivity.class));
                break;
            case R.id.rl_guide:
                break;
            case R.id.rl_invite:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            case R.id.rl_biaobai:
                startActivity(new Intent(getActivity(), ContactsMsgInviteActivity.class));

                break;
            case R.id.rl_share:


                getShareInfo();
                break;
            case R.id.ll_showdt:
                Intent intentDt = new Intent(getActivity(), SendTextPicActivity.class);
                intentDt.putExtra("topicType", "1");
                startActivity(intentDt);
                break;
            case R.id.ll_att:
                if (user.getAttCount() == 0) {
                    ToastUtil.showLongToast(getActivity(), "暂无关注的人");
                } else {
                    Intent intent2 = new Intent(getActivity(), AttentionActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.ll_blum:
                startToPersonBlum();
                break;
            case R.id.ll_fan:
                if (user.getFansCount() == 0) {
                    ToastUtil.showLongToast(getActivity(), "暂无粉丝");
                } else {
                    Intent intent2 = new Intent(getActivity(), FansActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.tv_history:
                Intent inten = new Intent(getActivity(), ZanActivity.class);
                startActivity(inten);
                break;
        }
    }


    public void startToPersonBlum() {
        Intent intentperson = new Intent(getActivity(), PersonBlumActivity.class);
        intentperson.putExtra("FriendId", user.getUserId());
        startActivity(intentperson);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    public void startTo(Class<?> cc) {
        Intent intent = new Intent(getActivity(), cc);
        startActivity(intent);

    }


    private void getUserInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            user = result.getData();
                            setUserDate(user);
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
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(getActivity());
                    }
                });
    }


    private void getShareInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());

        HttpUtils.get().url(coreManager.getConfig().USER_SHARE_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<ShareEntity>(ShareEntity.class) {
                    @Override
                    public void onResponse(ObjectResult<ShareEntity> result) {
                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            ShareEntity shareEntity = result.getData();
                            String url = shareEntity.getUrl();
                            String title = shareEntity.getTitle();
                            String content = shareEntity.getContent();
                            String iconUrl = shareEntity.getIcon();
                            Glide.with(getActivity()).load(iconUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    if (resource == null) {
                                        WxShareUtils.shareWeb(getActivity(), BuildConfig.WECHAT_APP_ID, url, title, content, null, 1);
                                    } else {
                                        WxShareUtils.shareWeb(getActivity(), BuildConfig.WECHAT_APP_ID, url, title, content, resource, 1);

                                    }
                                }
                            });

                        } else {
                            ToastUtil.showToast(getActivity(), "服务没数据");
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(getActivity());
                    }
                });
    }

    public void setUserDate(User user) {


        if (user.getDescription() != null & !TextUtils.isEmpty(user.getDescription())) {
            tv_edit_info.setVisibility(View.VISIBLE);
            tv_edit_info.setText(user.getDescription());
        } else {
            tv_edit_info.setVisibility(View.GONE);
        }

        AvatarHelper.getInstance().displayAvatar(coreManager.getSelf().getUserId(), avatar_img, true);
        tv_fans.setText(String.valueOf(user.getFansCount()));
        tv_guanzhu.setText(String.valueOf(user.getAttCount()));

        tv_name.setText(user.getNickName());
        tv_blum.setText(String.valueOf(coreManager.getSelf().getMsgCount()));

        if (user.getUserVIPPrivilege().getVipLevel().equals("-1")) {
            tv_vip.setVisibility(View.GONE);
        } else {
            tv_vip.setVisibility(View.VISIBLE);
        }

        //相册
        if (user.getMsgImgs() != null && !TextUtils.isEmpty(user.getMsgImgs())) {

            List<String> blumList = new ArrayList<>();
            if (user.getMsgImgs().contains(",")) {
                String blums[] = user.getMsgImgs().split(",");
                for (String s : blums) {
                    blumList.add(s);
                }
            } else {
                blumList.add(user.getMsgImgs());
            }

            if (blumList != null && blumList.size() > 0) {


                int index = blumList.size() == 3 ? 3 : blumList.size();
                ll_my_blum.removeAllViews();
                for (int i = 0; i < index; i++) {
                    ImageView imageView = new ImageView(getActivity());
                    int wid = ScreenUtils.dip2px(33, getActivity());
                    int hight = ScreenUtils.dip2px(33, getActivity());

                    LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(wid, hight);
                    ls.setMargins(0, 0, 10, 0);
                    imageView.setLayoutParams(ls);
                    GlideImageUtils.setImageView(getActivity(), blumList.get(i), imageView);
                    ll_my_blum.addView(imageView);

                }
            } else {
                ll_my_blum.removeAllViews();
            }
        }

    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember(UserVIPPrivilegePrice userVIPPrivilegePrice) {
        //谁喜欢我，在线聊天  购买
        MyPrivilegePopupWindow myBuy = new MyPrivilegePopupWindow(getActivity(), 1, "查看谁喜欢我", "哇，99+个人喜欢我！TA们是谁？", userVIPPrivilegePrice);
        LogUtil.e("BuyMember  BuyMember");
        myBuy.setBtnOnClice(new MyPrivilegePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {
                LogUtil.e("**********************************************************");
                LogUtil.e("type = " + type + "---vip = " + vip);
                LogUtil.e("**********************************************************");
                submitPay(type, vip, userVIPPrivilegePrice);
            }
        });
    }

    /**
     * 转支付类型返回支付签名数据
     *
     * @param type
     * @param vip
     */
    private void submitPay(String type, int vip, UserVIPPrivilegePrice _userVIPPrivilegePrice) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("payType", type);

        if (vip == 1) {
            params.put("price", _userVIPPrivilegePrice.getLikePrivilegePrice1() + "");
            params.put("mon", "1");
        } else if (vip == 2) {
            params.put("price", _userVIPPrivilegePrice.getLikePrivilegePrice2() + "");
            params.put("mon", "3");
        } else if (vip == 3) {
            params.put("price", _userVIPPrivilegePrice.getLikePrivilegePrice3() + "");
            params.put("mon", "12");
        }
        params.put("funType", String.valueOf(6));
        params.put("num", String.valueOf(-1));
        params.put("level", String.valueOf(-1));
        AlipayHelper.rechargePay(getActivity(), coreManager, params);

    }


}