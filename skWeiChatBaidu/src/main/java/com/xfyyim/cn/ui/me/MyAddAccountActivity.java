package com.xfyyim.cn.ui.me;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 选择不同的登录
 */
public class MyAddAccountActivity extends BaseActivity implements View.OnClickListener {
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
    private ViewPager vpAccount;
    private List<View> viewList = new ArrayList<>();

    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_account);
        unbinder=ButterKnife.bind(this);
        initView();
        initActionBar();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("切换账号");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    private void initView() {

        vpAccount = (ViewPager) findViewById(R.id.vpAccount);
        RelativeLayout rlWxLogin = (RelativeLayout) findViewById(R.id.rlWxLogin);
        TextView tvLogin = (TextView) findViewById(R.id.tvLogin);
        TextView tvAgreement = (TextView) findViewById(R.id.tvAgreement);

        //和隐私政策
        String agreement1 = "点击登陆即代表同意" + "<font color='#FF0000' size='9'>" + "用户协议" + "</font>" + "和" + "<font color='#FF0000' size='9'>" + "隐私政策" + "</font>";

        tvAgreement.setText(Html.fromHtml(agreement1));
        rlWxLogin.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvAgreement.setOnClickListener(this::onClick);
        View item_add_account1 = this.getLayoutInflater().inflate(
                R.layout.item_add_account1, null);
        View item_add_account2 = this.getLayoutInflater().inflate(
                R.layout.item_add_account2, null);
        View item_add_account3 = this.getLayoutInflater().inflate(
                R.layout.item_add_account3, null);
        TextView tvTtitle = (TextView) item_add_account3.findViewById(R.id.tvTtitle);
        TextView tvTtitle1 = (TextView) item_add_account3.findViewById(R.id.tvTtitle1);

        String title1 = "<font color='#FC697B' size='18'>" + "右滑" + "</font>" + "喜欢";
        String title2 = "<font color='#FC697B' size='18'>" + "左滑" + "</font>" + "无感";
        tvTtitle.setText(Html.fromHtml(title1));
        tvTtitle1.setText(Html.fromHtml(title2));

        viewList.add(item_add_account1);
        viewList.add(item_add_account2);
        viewList.add(item_add_account3);

        vpAccount.setCurrentItem(1);
        vpAccount.setOffscreenPageLimit(viewList.size() - 1);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPageAdapter.setData(viewList);
        vpAccount.setAdapter(viewPageAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.rlWxLogin:

                break;
            case R.id.tvLogin:

                break;
            case R.id.tvAgreement:

                break;

        }
    }

    public class ViewPageAdapter extends PagerAdapter {
        List<View> viewsList;
        private LayoutInflater inflater;
        Context context;

        public ViewPageAdapter(Context context) {
            this.context = context;
            viewsList = new ArrayList<>();
            inflater = LayoutInflater.from(context);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return viewsList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View view = viewsList.get(position);
            viewGroup.addView(view);
            return view;
        }

        public void setData(List<View> list) {
            this.viewsList = list;
            if (list == null || list.size() == 0) {
                return;
            }
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
