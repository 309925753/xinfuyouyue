package com.xfyyim.cn.util.glideUtil;

import android.content.Context;
import android.widget.ImageView;

import com.angel.job.utils.glideUtil.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xfyyim.cn.R;

public class GlideImageUtils {

    public static void setImageView(Context mContext, String imgUrl, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(imgUrl)
//                    .placeholder(R.mipmap.icon)
                    .error(R.mipmap.icon)
                    .centerCrop()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }

    public static void setImageView_Banner(Context mContext, String imgUrl, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(imgUrl)
//                    .placeholder(R.mipmap.icon)
                    .error(R.mipmap.icon)
                    .centerCrop()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }

    public static void setGlideRoundImageView(Context mContext, String imgUrl, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .placeholder(R.mipmap.icon)
                    .error(R.mipmap.icon)
                    .fitCenter()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .transform(new CircleRoundTransform(mContext))
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }


    /**
     * 圆角矩形
     *
     * @param mContext
     * @param drawId
     * @param view
     */
    public static void setImageDrawable(Context mContext, int drawId, ImageView view) {
        if (mContext != null) {


            Glide.with(mContext)
                    .load(drawId)
                    .fitCenter()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }


    /**
     * 圆角矩形
     *
     * @param mContext
     * @param view
     */
    public static void setImageDrawableCirCle(Context mContext, String url, ImageView view, int error) {
        if (mContext != null) {


            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.icon)
//            //设置加载失败后的图片显示
                    .error(error)
                    .fitCenter()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .transform(new CircleRoundTransform(mContext))
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }


    /**
     * 圆角矩形
     *
     * @param mContext
     * @param view
     */
    public static void setImageDrawableCirCle(Context mContext, String url, ImageView view) {
        if (mContext != null) {


            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.icon)
//            //设置加载失败后的图片显示
                    .error(R.mipmap.icon)
                    .fitCenter()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .transform(new CircleRoundTransform(mContext))
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }

    public static void setImageView_Error(Context mContext, int errorImage, String imgUrl, ImageView view) {
        if (mContext != null) {
            Glide.with(mContext)
                    .load(imgUrl)
                    .placeholder(errorImage)
                    .error(errorImage)
                    .fitCenter()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .into(view);
        }
    }


    public static void setImageGifView(Context mContext, String imgUrl, ImageView view) {
        Glide.with(mContext)
                .load(imgUrl)
                //设置等待时的图片
                .asGif()
                .placeholder(R.mipmap.icon)
//            //设置加载失败后的图片显示
                .error(R.mipmap.icon)
                .fitCenter()
                //默认淡入淡出动画
                .crossFade()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //设置图片加载的优先级
                .priority(Priority.HIGH)
                .into(view);
    }

    public static void setImageGifView(Context mContext, String imgUrl, ImageView view, int errorImage) {
        Glide.with(mContext)
                .load(imgUrl)
                //设置等待时的图片
                .asGif()
                .placeholder(R.mipmap.icon)
//            //设置加载失败后的图片显示
                .error(errorImage)
                .fitCenter()
                //默认淡入淡出动画
                .crossFade()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.RESULT)

                //设置图片加载的优先级
                .priority(Priority.HIGH)
                .into(view);
    }

    public static void setCircleImageView(Context mContext, String imgUrl, ImageView view) {
        Glide.with(mContext)
                .load(imgUrl)
                //设置等待时的图片
                .placeholder(R.mipmap.icon)
//            //设置加载失败后的图片显示
                .error(R.mipmap.icon)
                .centerCrop()
                //默认淡入淡出动画
                .crossFade()
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
                //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //设置图片加载的优先级
                .priority(Priority.HIGH)
                .transform(new CircleTransform(mContext))
                .into(view);
    }


}
