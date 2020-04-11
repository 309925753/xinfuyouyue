package com.xfyyim.cn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.xfyyim.cn.bean.message.ChatMessage;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.ImageLoadHelper;
import com.xfyyim.cn.util.FileUtil;
import com.xfyyim.cn.view.ZoomImageView;

import java.io.File;
import java.util.List;

public class ChatOverviewAdapter extends PagerAdapter {
    private Context mContext;
    private List<ChatMessage> mChatMessages;
    private SparseArray<View> mViews = new SparseArray<>();

    public ChatOverviewAdapter(Context context, List<ChatMessage> chatMessages) {
        mContext = context;
        mChatMessages = chatMessages;
    }

    public void refreshItem(String url, int index) {
        AvatarHelper.getInstance().displayUrl(url, (ZoomImageView) mViews.get(index));
    }

    @Override
    public int getCount() {
        return mChatMessages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = new ZoomImageView(mContext);
            mViews.put(position, view);
        }

        ChatMessage chatMessage = mChatMessages.get(position);
        if (!TextUtils.isEmpty(chatMessage.getFilePath()) && FileUtil.isExist(chatMessage.getFilePath())) {
            ImageLoadHelper.showFile(
                    mContext, new File(chatMessage.getFilePath()), (ZoomImageView) view
            );
        } else {
            ImageLoadHelper.showImage(
                    mContext, chatMessage.getContent(), (ZoomImageView) view
            );
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = mViews.get(position);
        if (view == null) {
            super.destroyItem(container, position, object);
        } else {
            container.removeView(view);
        }

    }
}
