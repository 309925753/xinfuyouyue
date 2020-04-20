package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me.CertificationCenterActivity;
import com.xfyyim.cn.ui.me.CheckLikesMeActivity;
import com.xfyyim.cn.ui.me.MyNewWalletActivity;
import com.xfyyim.cn.ui.me.MyPrerogativeActivity;
import com.xfyyim.cn.ui.me.NewSettingsActivity;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
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

    @BindView(R.id.rl_biaobai)
    RelativeLayout rl_biaobai;

    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.rlInfoBackground)
    RelativeLayout rlInfoBackground;


    @BindView(R.id.avatar_img)
    ImageView avatar_img;
    User user;
    @BindView(R.id.tv2)
    TextView tv2;

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
        if(!TextUtils.isEmpty(coreManager.getSelf().getLikeMeCount()+"")){
            tv2.setText(coreManager.getSelf().getLikeMeCount()+"人喜欢我");
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
        rl_biaobai.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        ll_showdt.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        rlInfoBackground.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rlInfoBackground:
                Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                intent.putExtra("FriendId", coreManager.getSelf().getUserId());
                startActivity(intent);
                break;
            case R.id.rl_pyq:
                break;
            case R.id.rl_mytequan:
                startActivity(new Intent(getActivity(), MyPrerogativeActivity.class));
                break;
            case R.id.rl_likeme:
                startActivity(new Intent(getActivity(), CheckLikesMeActivity.class));
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
            case R.id.rl_biaobai:
                break;
            case R.id.rl_share:
                break;
            case R.id.ll_showdt:
                break;
            case R.id.tv_history:
                ToastUtil.showToast(getActivity(), "查看历史");
                break;
        }
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

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
        params.put("userId", "10000022");

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            user = result.getData();
                            setUserDate(user);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
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
        tv_blum.setText("0");
        tv_name.setText(user.getNickName());

//        if (user.getVip())

    }
}


