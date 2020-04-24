package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.broadcast.OtherBroadcast;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.base.CoreManager;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


public class CertificationCenterActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tvHead)
    TextView tvHead;
    @BindView(R.id.tvPairing)
    TextView tvPairing;
    @BindView(R.id.rlCertificationImmediately)
    RelativeLayout rlCertificationImmediately;
    @BindView(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.tvCertification)
    TextView tvCertification;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_center);
        unbinder = ButterKnife.bind(this);

        initView();
        initActionBar();
    }


    private void initActionBar() {

        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("认证中心");

    }

    private void initView() {
        findViewById(R.id.rlCertificationImmediately).setOnClickListener(this::onClick);
        TextView tvPrivacyPolicy = (TextView) findViewById(R.id.tvPrivacyPolicy);
        String title1 = "认证用户可以提升信用度、推荐值。认证中将采集您的面部信息，通过百度科技人脸识别技术进行对比，详见" + "<font color=\"#FF0000\">" + "隐私政策" + " " + "</font>" + "";
        tvPrivacyPolicy.setText(Html.fromHtml(title1));
        LogUtil.e("-----------------------getFaceIdentity---------------------" +CoreManager.getSelf(this).getFaceIdentity());
        if(CoreManager.getSelf(this).getFaceIdentity()==1){
            tvCertification.setText("认证成功");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.rlCertificationImmediately:
                if(CoreManager.getSelf(this).getFaceIdentity()==1){
                  return;
                }
             Intent intent = new Intent(this, FaceLivenessExpActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    final String image = data.getStringExtra("image");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                          postCerifcalion(image);
                        }
                    }).start();
                }
                break;
        }
    }

    /**
     * 百度返回图片数据并请求后台
     * @param base64
     */
    private void postCerifcalion(String base64) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("image", base64);
        params.put("userId", coreManager.getSelf().getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_FACE_VERIFY)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        // 人脸在线活体检测
                        if (Result.checkSuccess(CertificationCenterActivity.this, result)) {
                            tvCertification.setText("认证成功");
                            updateSelfData();
                            ToastUtil.showLongToast(CertificationCenterActivity.this, "认证成功");
                        } else {
                            ToastUtil.showLongToast(CertificationCenterActivity.this, "认证失败请重新认证");
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(CertificationCenterActivity.this);
                    }
                });
    }

    private void updateSelfData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);

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
                                // 通知MeFragment更新
                                sendBroadcast(new Intent(OtherBroadcast.SYNC_SELF_DATE_NOTIFY));
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }


}
