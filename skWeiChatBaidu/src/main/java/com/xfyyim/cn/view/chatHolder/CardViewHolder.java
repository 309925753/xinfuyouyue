package com.xfyyim.cn.view.chatHolder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.event.EventFlashChat;
import com.xfyyim.cn.bean.message.ChatMessage;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.xfyyim.cn.MyApplication.getContext;
import static com.xfyyim.cn.helper.AvatarHelper.getAvatarUrl;

class CardViewHolder extends AChatHolderInterface {

    ImageView ivCardImage;
    ImageView ivUnRead;
    RelativeLayout rl_tekan;
    TextView tv_card_name;
    TextView tv_interest;

    @Override
    public int itemLayoutId(boolean isMysend) {
        return isMysend ? R.layout.chat_from_item_card : R.layout.chat_to_item_card;
    }

    @Override
    public void initView(View view) {
        ivCardImage = view.findViewById(R.id.iv_card_head);
        ivUnRead = view.findViewById(R.id.unread_img_view);
        mRootView = view.findViewById(R.id.chat_warp_view);
        rl_tekan = view.findViewById(R.id.rl_tekan);
        tv_card_name = view.findViewById(R.id.tv_card_name);
        tv_interest = view.findViewById(R.id.tv_interest);

    }

    @Override
    public void fillData(ChatMessage message) {
//        AvatarHelper.getInstance().displayAvatar(message.getContent(), message.getFromUserId(), ivCardImage, true);
        Glide.with(getContext()).load(getAvatarUrl(message.getFromUserId(), true)).bitmapTransform(new BlurTransformation(getContext(), 40)).into(ivCardImage);



        if(!TextUtils.isEmpty(message.getContent())){
            User user= JSON.parseObject(message.getContent(),User.class);
            tv_card_name.setText(user.getNickName()+"  "+user.getCityName()+"  "+user.getAge());

        }

        ivUnRead.setVisibility(View.GONE);
//        if (!isMysend) {
////            ivUnRead.setVisibility(message.isSendRead() ? View.GONE : View.VISIBLE);
//            ivUnRead.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onRootClick(View v) {
        sendReadMessage(mdata);
        ivUnRead.setVisibility(View.GONE);
    // BasicInfoActivity.start(mContext, mdata.getObjectId(), BasicInfoActivity.FROM_ADD_TYPE_CARD);
        EventBus.getDefault().post(new EventFlashChat(mdata.getToUserId()));
      //  PersonBlumActivity.start(mContext, mdata.getObjectId());
    }



    /**
     * 重写该方法，return true 表示显示红点
     *
     * @return
     */
    @Override
    public boolean enableUnRead() {
        return true;
    }
}
