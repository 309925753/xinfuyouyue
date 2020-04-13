package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyRemindersChatsActivity extends BaseActivity implements View.OnClickListener {

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

    private Unbinder  unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminders_chats);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("消息提醒与聊天");

    }

    private void initView() {

        SwitchButton sbShowMessagePreview = (SwitchButton) findViewById(R.id.sbShowMessagePreview);
        SwitchButton sbSLetterNotice = (SwitchButton) findViewById(R.id.sbSLetterNotice);
        SwitchButton sbVibration = (SwitchButton) findViewById(R.id.sbVibration);
        SwitchButton sbVoice = (SwitchButton) findViewById(R.id.sbVoice);
        SwitchButton sbSendMessage = (SwitchButton) findViewById(R.id.sbSendMessage);
        SwitchButton sbOpenExpression = (SwitchButton) findViewById(R.id.sbOpenExpression);

      /*  PrivacySetting privacySetting = PrivacySettingHelper.getPrivacySettings(this);
        // 获得振动状态
        boolean vibration = privacySetting.getIsVibration() == 1;
        sbVibration.setChecked(vibration);
      */

        sbShowMessagePreview.setChecked(true);
        //显示消息预览
        sbShowMessagePreview.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });

        sbVibration.setChecked(true);
        //振动
        sbVibration.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        //声音
        sbVoice.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });

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
}
