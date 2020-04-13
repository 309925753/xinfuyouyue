package com.xfyyim.cn.ui.me;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.fragmentnew.BillCashWithdrawalFragment;
import com.xfyyim.cn.fragmentnew.BillConsumptionFragment;
import com.xfyyim.cn.view.XRadioGroup;

import java.util.ArrayList;

public class MyWalletBillDetailsActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, XRadioGroup.OnCheckedChangeListener {

    private ArrayList<Object> items = new ArrayList<Object>();
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private XRadioGroup xRadioGroup;
    private RadioButton radioBack;
    private RadioButton radioClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_bill_details);
        initView();
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
