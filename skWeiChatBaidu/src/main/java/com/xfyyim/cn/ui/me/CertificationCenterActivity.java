package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_center);
        unbinder=ButterKnife.bind(this);

        initView();
        initActionBar();
    }


    private void initActionBar() {

        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("认证中心");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }
    private void initView() {
        findViewById(R.id.rlCertificationImmediately).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //立即认证
            }
        });
        TextView tvPrivacyPolicy = (TextView) findViewById(R.id.tvPrivacyPolicy);
        String title1 = "认证用户可以提升信用度、推荐值。认证中将采集您的面部信息，通过旷世科技和陌陌科技的人脸识别技术进行对比，详见" + "<font color=\"#FF0000\">" + "隐私政策" + " " + "</font>" + "";
        tvPrivacyPolicy.setText(Html.fromHtml(title1));

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
        }
    }
}
