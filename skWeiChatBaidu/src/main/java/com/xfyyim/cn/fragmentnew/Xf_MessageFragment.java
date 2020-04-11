package com.xfyyim.cn.fragmentnew;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.fragment.MessageFragment;
import com.xfyyim.cn.nocsroll.MessagePagerAdapter;
import com.xfyyim.cn.nocsroll.MyNavigationLayoutContainer;
import com.xfyyim.cn.nocsroll.NoScrollViewPager;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class Xf_MessageFragment extends EasyFragment {


    List<Fragment> listFragments = new ArrayList<>();
    List<Integer> listIds = new ArrayList<>();

    int radioButtonWith;
    int indicatorHeight;
    MessageFragment messageFragment;
    AllFragmet allFragmet;
    RadioGroup rg_choice;
    RadioButton rb_readmessage;
    RadioButton rb_unread;
    MyNavigationLayoutContainer myNavigationLayoutContainer;
    View myNavigationView;
    NoScrollViewPager vp_message_tab;
    private TextView mTvTitle;
    private TextView tvFriendCount;
    private ImageView mIvTitleRight;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_xf_notifition;
    }


    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initActionBar();
        initView();
        setIndicatorWidth();
        setOnListener();
        addFragment();

    }

    public void initView() {
        rg_choice = findViewById(R.id.rg_choice);
        rb_readmessage = findViewById(R.id.rb_readmessage);
        rb_unread = findViewById(R.id.rb_unread);
        myNavigationLayoutContainer = findViewById(R.id.myNavigationLayoutContainer);
        vp_message_tab = findViewById(R.id.vp_message_tab);
        myNavigationView = findViewById(R.id.myNavigationView);

    }

    private void addFragment() {
        listFragments.add(allFragmet = new AllFragmet());
        listFragments.add(messageFragment = new MessageFragment());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        vp_message_tab.setAdapter(new MessagePagerAdapter(fm, listFragments, listIds));
        vp_message_tab.setOffscreenPageLimit(listFragments.size() - 1);
    }

    private void initActionBar() {
        findViewById(R.id.iv_title_left).setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_title_center);
        mTvTitle.setText("消息");
        mTvTitle.setTextColor(getResources().getColor(R.color.white));
        mIvTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        mIvTitleRight.setImageResource(R.mipmap.folding_icon);
        mIvTitleRight.setVisibility(View.GONE);
        ImageView iv_title_right_right = findViewById(R.id.iv_title_right_right);
        iv_title_right_right.setVisibility(View.GONE);
    }

    public void setOnListener() {

        rg_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vp_message_tab.setAnimation(false);
                switch (checkedId) {
                    case R.id.rb_unread:
                        rb_unread.setTextSize(16);
                        rb_readmessage.setTextSize(14);
                        vp_message_tab.setCurrentItem(0);
                        break;

                    case R.id.rb_readmessage:
                        rb_unread.setTextSize(14);
                        rb_readmessage.setTextSize(16);
                        vp_message_tab.setCurrentItem(1);
                        break;
                }
            }
        });


        vp_message_tab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float fraction, int positionOffsetPixels) {
                myNavigationLayoutContainer.scrollTo(-(Math.round(ScreenUtils.getScreenWidth(getContext()) / 2 - radioButtonWith - radioButtonWith / 2)), 0);
                if (position == 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
                    params.width = radioButtonWith / 2;
                    params.height = indicatorHeight;
                    params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 2 - radioButtonWith, 0, 0, 0);
                    myNavigationView.setLayoutParams(params);
                } else {


                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
                    params.width = (int) (radioButtonWith / 2);
                    params.height = indicatorHeight;
                    params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 3, 0, 0, 0);

                    myNavigationView.setLayoutParams(params);

                    myNavigationLayoutContainer.scrollTo(-(Math.round(ScreenUtils.getScreenWidth(getActivity()) / 3)), 0);
                }
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        rb_unread.setChecked(true);
                        break;

                    case 1:
                        rb_readmessage.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicatorWidth() {

        radioButtonWith = getResources().getDimensionPixelSize(R.dimen.fragment_message_radio_button_width);
        indicatorHeight = getResources().getDimensionPixelSize(R.dimen.fragment_message_radio_button_height);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myNavigationView.getLayoutParams();
        params.width = radioButtonWith / 2;
        params.height = indicatorHeight;
        params.setMargins(ScreenUtils.getScreenWidth(getActivity()) / 2 - radioButtonWith, 0, 0, 0);
        params.addRule(Gravity.CENTER_VERTICAL);
        myNavigationView.setLayoutParams(params);
    }

}
