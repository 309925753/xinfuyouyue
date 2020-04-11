package com.xfyyim.cn.fragment;

import android.os.Bundle;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;

public class FriendCircleActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendcircle);
        getSupportActionBar().hide();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,new DiscoverFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
}
