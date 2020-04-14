package com.xfyyim.cn.ui.me;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.fragmentnew.BillCashWithdrawalFragment;
import com.xfyyim.cn.fragmentnew.BillConsumptionFragment;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.XRadioGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyWalletBillDetailsActivity extends BaseActivity implements ViewPager.OnPageChangeListener, XRadioGroup.OnCheckedChangeListener , View.OnClickListener {

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
    private ArrayList<Object> items = new ArrayList<Object>();
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private XRadioGroup xRadioGroup;
    private RadioButton radioBack;
    private RadioButton radioClose;

    private Unbinder  unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_bill_details);
        unbinder =  ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("帐单明细");
        mergerStatus.setBackground(getDrawable(R.drawable.bg_new_my_wallet_red));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvTitleRight.setTextColor(getColor(R.color.white));
        }
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }
    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radioClose = (RadioButton) findViewById(R.id.radio_close);
        radioBack = (RadioButton) findViewById(R.id.radio_back);
        xRadioGroup = (XRadioGroup) findViewById(R.id.radiogroup_message);

        items.add(new BillConsumptionFragment());
        items.add(new BillCashWithdrawalFragment());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), items);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, false);
        viewPager.setOffscreenPageLimit(items.size());
        viewPager.setOnPageChangeListener(this);
        xRadioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioBack.setChecked(true);
                break;
            case 1:
                radioClose.setChecked(true);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(XRadioGroup var1, int var2) {
        switch (var2) {
            case R.id.radio_back:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radio_close:
                viewPager.setCurrentItem(1);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Object> items;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Object> items) {
            super(fm);
            this.items = items;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return (Fragment) items.get(position);
                case 1:
                    return (Fragment) items.get(position);
                case 2:
                    return (Fragment) items.get(position);
            }
            return (Fragment) items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

}
