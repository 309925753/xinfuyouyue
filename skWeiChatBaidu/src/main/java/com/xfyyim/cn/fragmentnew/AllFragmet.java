package com.xfyyim.cn.fragmentnew;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

import com.xfyyim.cn.R;
import com.xfyyim.cn.fragment.MessageFragment;
import com.xfyyim.cn.ui.base.EasyFragment;

import butterknife.BindView;
import butterknife.Unbinder;

public class AllFragmet extends EasyFragment {

    FragmentTransaction ft;
    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_all;
    }


    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.main_content,new MessageFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
