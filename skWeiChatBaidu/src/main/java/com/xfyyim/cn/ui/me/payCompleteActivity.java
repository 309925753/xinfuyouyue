package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class payCompleteActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_complete);
        ButterKnife.bind(this);
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        tvTitleCenter.setText("支付成功");
        ivTitleLeft.setOnClickListener(this::onClick);
        tvSubmit.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.iv_title_left:
               startActivity(new Intent(payCompleteActivity.this, MyPrerogativeActivity.class));
               finish();
            break;
            case  R.id.tvSubmit:
                startActivity(new Intent(payCompleteActivity.this, MyPrerogativeActivity.class));
                finish();
                break;
        }

    //
    }
}
