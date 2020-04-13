package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyAboutActivity extends BaseActivity implements View.OnClickListener {

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
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_about);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("关于");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }
    private void initView() {
        RelativeLayout rlUserAbout = (RelativeLayout) findViewById(R.id.rlUserAbout);
        RelativeLayout rlPrivacyPolicy = (RelativeLayout) findViewById(R.id.rlPrivacyPolicy);
        RelativeLayout rlCommunitySpecification = (RelativeLayout) findViewById(R.id.rlCommunitySpecification);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlUserAbout:
                //用户协议
                break;
            case R.id.rlPrivacyPolicy:
                //隐私政策
                break;
            case R.id.rlCommunitySpecification:
                //社区规范
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

}
