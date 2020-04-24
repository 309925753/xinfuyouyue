package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.fragment.FriendCircleActivity;
import com.xfyyim.cn.nocsroll.MessagePagerAdapter;
import com.xfyyim.cn.nocsroll.MyNavigationLayoutContainer;
import com.xfyyim.cn.nocsroll.NoScrollViewPager;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.circle.SelectPicPopupWindow;
import com.xfyyim.cn.ui.circle.range.SendShuoshuoActivity;
import com.xfyyim.cn.ui.circle.range.SendTextPicActivity;
import com.xfyyim.cn.ui.circle.range.TopicActivity;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class FindFriendFragment extends EasyFragment  implements View.OnClickListener {



    List<Fragment> listFragments = new ArrayList<>();
    List<Integer> listIds = new ArrayList<>();
    NearFragment nearFragment;
    CareFragment careFragment;
    MyNavigationLayoutContainer myNavigationLayoutContainer;
    View myNavigationView;
    NoScrollViewPager vp_find_tab;
    int radioButtonWith;
    int indicatorHeight;
    RadioGroup rg_tab;
    RadioButton rb_care;
    RadioButton rb_near;

    ImageView img_near;
    ImageView img_camera;
    private SelectPicPopupWindow menuWindow;


    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {

        initView();
        setIndicatorWidth();
        setOnListener();
        addFragment();

    }

    public void initView() {
        rg_tab = findViewById(R.id.rg_tab);
        rb_care = findViewById(R.id.rb_care);
        rb_near = findViewById(R.id.rb_near);
        img_camera = findViewById(R.id.img_camera);
        img_near = findViewById(R.id.img_near);
        myNavigationLayoutContainer = findViewById(R.id.myNavigationLayoutContainer);
        vp_find_tab = findViewById(R.id.vp_findtab);
        myNavigationView = findViewById(R.id.myNavigationView);


        img_camera.setOnClickListener(this);
        img_near.setOnClickListener(this);
    }


    private void addFragment() {

        listFragments.add(careFragment = new CareFragment());
        listFragments.add(nearFragment = new NearFragment());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        vp_find_tab.setAdapter(new MessagePagerAdapter(fm, listFragments, listIds));
        vp_find_tab.setOffscreenPageLimit(listFragments.size() - 1);
    }

    public void setOnListener() {



        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vp_find_tab.setAnimation(false);
                switch (checkedId) {
                    case R.id.rb_care:
                        rb_care.setTextSize(16);
                        rb_near.setTextSize(14);
                        vp_find_tab.setCurrentItem(0);
                        break;

                    case R.id.rb_near:
                        rb_care.setTextSize(14);
                        rb_near.setTextSize(16);
                        vp_find_tab.setCurrentItem(1);
                        break;
                }
            }
        });


        vp_find_tab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                        rb_care.setChecked(true);
                        break;

                    case 1:
                        rb_near.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.img_near:
                Intent intent=new Intent(getActivity(), TopicActivity.class);
                startActivity(intent);
                break;
            case R.id.img_camera:
                Intent intent1=new Intent(getActivity(), SendTextPicActivity.class);
                intent1.putExtra("topicType","1");
                startActivity(intent1);
                break;
        }
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
