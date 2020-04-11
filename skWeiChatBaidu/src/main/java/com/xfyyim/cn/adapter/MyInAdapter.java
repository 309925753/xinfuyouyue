package com.xfyyim.cn.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.MyInEntity;
import com.xfyyim.cn.customer.FlowLayout;
import com.xfyyim.cn.customer.SkillTextView;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;

import org.bouncycastle.math.Primes;

import java.util.ArrayList;
import java.util.List;

public class MyInAdapter extends BaseQuickAdapter<MyInEntity, MyInAdapter.MyViewHolder> {
public Context context;

    public MyInAdapter(@Nullable List<MyInEntity> data, Context context) {
        super(R.layout.item_my,data);
        this.context = context;
    }

    @Override
    protected void convert(MyViewHolder helper, MyInEntity item) {
              List<String> list=new ArrayList<>();
              String items[]=item.getContextName().split(",");
              for (String s:items){
                  list.add(s);
              }
            initSkill(list,item.getBackground_type(),helper.flowLayout_tags);
    }

    private void initSkill(final List<String> mTagData,int bgtype,FlowLayout flowLayout_tags) {

        if (mTagData != null) {
            for (int i = 0; i < mTagData.size(); i++) {
                String signName = mTagData.get(i);
                SkillTextView skillTextView = getSkillView(bgtype);
                skillTextView.setText(signName);
                flowLayout_tags.addView(skillTextView);
            }

        }
    }

    /**
     * 获取标签tagview
     *
     * @return
     */
    private SkillTextView getSkillView(int type) {
        SkillTextView skillTextView = new SkillTextView(context);
        skillTextView.setGravity(Gravity.CENTER);
        skillTextView.setTextColor(context.getResources().getColor(R.color.white));
        skillTextView.setTextSize(10);
        skillTextView.setEnabled(false);
        skillTextView.setChecked(false);
        switch (type) {
            case 1:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
            case 2:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
            case 3:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
            case 4:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
            case 5:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
            case 6:
                skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
                break;
        }
        skillTextView.setBackground(context.getResources().getDrawable(R.drawable.share_sign_zise));
        int leftRightPadding = ScreenUtils.dip2px(8, mContext);
        int topBottomPadding = ScreenUtils.dip2px(4, mContext);
        skillTextView.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        return skillTextView;
    }


    public class MyViewHolder extends BaseViewHolder{

        ImageView img_icon;
        FlowLayout flowLayout_tags;
        public MyViewHolder(View view) {
            super(view);
            flowLayout_tags=view.findViewById(R.id.flowLayout_tags);
            img_icon=view.findViewById(R.id.img_icon);
        }
    }
}
