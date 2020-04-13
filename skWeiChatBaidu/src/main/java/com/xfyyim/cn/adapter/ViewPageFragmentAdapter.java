package com.xfyyim.cn.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 通用的ViewpageFragment 的Adapter
 */
public class ViewPageFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public ViewPageFragmentAdapter(FragmentManager fm, List<Fragment> mFragments, String[] title) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}

