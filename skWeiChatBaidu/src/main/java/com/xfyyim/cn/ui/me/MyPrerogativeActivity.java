package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.ViewPageFragmentAdapter;
import com.xfyyim.cn.fragmentnew.MyPrerogativeFragment;
import com.xfyyim.cn.fragmentnew.MyPrerogativeLikeFragment;
import com.xfyyim.cn.fragmentnew.myOnlineChatFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPrerogativeActivity extends FragmentActivity {

    //页卡视图集合
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prerogative);

        ininView();
    }


    private void ininView() {

        mViewPager = (ViewPager) findViewById(R.id.my_vp);
        findViewById(R.id.iv_title_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFragmentList.add(new MyPrerogativeFragment());
        mFragmentList.add(new MyPrerogativeLikeFragment());
        mFragmentList.add(new myOnlineChatFragment());

        mFragmentList.add(new MyPrerogativeFragment());
        mFragmentList.add(new MyPrerogativeLikeFragment());
        mFragmentList.add(new myOnlineChatFragment());

        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(2);
        String[] titles = new String[5];
        titles[0] = "1";
        titles[1] = "1";
        titles[2] = "1";
        titles[3] = "1";
        titles[4] = "1";

        mViewPager.setAdapter(new ViewPageFragmentAdapter(getSupportFragmentManager(), mFragmentList, titles));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (state != ViewPager.SCROLL_STATE_IDLE) return;

                // 当视图在第一个时，将页面号设置为图片的最后一张。
                if (currentPosition == 0) {
                    mViewPager.setCurrentItem(mFragmentList.size() - 2, false);

                } else if (currentPosition == mFragmentList.size() - 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    mViewPager.setCurrentItem(1, false);
                }
            }
        });

    }
}
