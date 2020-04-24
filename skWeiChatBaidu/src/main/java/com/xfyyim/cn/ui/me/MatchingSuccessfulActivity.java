package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.ui.MainActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.message.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchingSuccessfulActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ivCoungratulations)
    ImageView ivCoungratulations;
    @BindView(R.id.tvImmediatelyChat)
    TextView tvImmediatelyChat;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.matching)
    RelativeLayout matching;
    private Unbinder unbinder;
    private  Friend friend=new Friend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_successful);
        unbinder = ButterKnife.bind(this);
        initView();
        getSupportActionBar().hide();
    }

    private void initView() {
        tvImmediatelyChat.setOnClickListener(this);
        tvBack.setOnClickListener(this::onClick);
        friend=(Friend)getIntent().getSerializableExtra("friend");
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
        switch (v.getId()){
            case R.id.tvImmediatelyChat:
                if(friend!=null){
                    Intent intent = new Intent(MatchingSuccessfulActivity.this, ChatActivity.class);
                    intent.setClass(MatchingSuccessfulActivity.this, ChatActivity.class);
                    intent.putExtra(ChatActivity.FRIEND, friend);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.tvBack:
                finish();
                break;
        }
    }
}
