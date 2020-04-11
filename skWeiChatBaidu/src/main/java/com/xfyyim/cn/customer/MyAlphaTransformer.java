package com.xfyyim.cn.customer;

import android.view.View;

import com.xfyyim.cn.R;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2017-07-19
 * Time: 00:33
 * <br/>
 *
 * 自定义透明度渐变, 包含收藏和删除的图片变化
 */
public final class MyAlphaTransformer extends StackLayout.PageTransformer {
    private float mMinAlpha = 0f;
    private float mMaxAlpha = 1f;

    public MyAlphaTransformer(float minAlpha, float maxAlpha) {
        mMinAlpha = minAlpha;
        mMaxAlpha = maxAlpha;
    }

    public MyAlphaTransformer() {
        this(0f, 1f);
    }

    @Override
    public void transformPage(View view, float position, boolean isSwipeLeft) {

        View ivLike = view.findViewById(R.id.iv_like),
                ivDelete = view.findViewById(R.id.iv_delete),
                contentView = view.findViewById(R.id.img_pic);

        ivLike.setAlpha(mMinAlpha);
        ivDelete.setAlpha(mMinAlpha);
        contentView.setAlpha(mMaxAlpha);

        if (position > -1 && position <= 0) { // [-1,0]
            contentView.setVisibility(View.VISIBLE);

            // 渐变
            float diffAlpha = (mMaxAlpha-mMinAlpha) * Math.abs(position);
            contentView.setAlpha(mMaxAlpha - diffAlpha);

            // 向左滑: 显示"爱心"; 向右滑: 显示"叉叉"
            if(isSwipeLeft)
                ivLike.setAlpha(diffAlpha);
            else
                ivDelete.setAlpha(diffAlpha);
        } else{
            contentView.setAlpha(mMaxAlpha);
        }
    }
}
