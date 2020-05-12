package com.xfyyim.cn.view.chatHolder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.event.EventFlashChat;
import com.xfyyim.cn.bean.event.EventNotifyWaitOnlineChat;
import com.xfyyim.cn.bean.message.ChatMessage;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.ui.me.MyNewWalletActivity;
import com.xfyyim.cn.ui.me.redpacket.alipay.AlipayHelper;
import com.xfyyim.cn.ui.me_new.PersonBlumActivity;
import com.xfyyim.cn.ui.other.BasicInfoActivity;
import com.xfyyim.cn.util.glideUtil.CircleRoundTransform;
import com.xfyyim.cn.view.MyWalletPopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.xfyyim.cn.MyApplication.getContext;
import static com.xfyyim.cn.helper.AvatarHelper.getAvatarUrl;

class CardViewHolder extends AChatHolderInterface {

    ImageView ivCardImage;
    TextView tvPersonName;
    TextView tvPersonSex;
    ImageView ivUnRead;
    ImageView iv_User_Head;
    RelativeLayout rl_tekan;
    TextView tv_name;
    TextView tv_interest;
    CardView  cvShow;

    @Override
    public int itemLayoutId(boolean isMysend) {
        return isMysend ? R.layout.chat_from_item_card : R.layout.chat_to_item_card;
    }

    @Override
    public void initView(View view) {
        ivCardImage = view.findViewById(R.id.iv_card_head);
        tvPersonName = view.findViewById(R.id.person_name);
        tvPersonSex = view.findViewById(R.id.person_sex);
        ivUnRead = view.findViewById(R.id.unread_img_view);
        mRootView = view.findViewById(R.id.chat_warp_view);
        rl_tekan = view.findViewById(R.id.rl_tekan);
        tv_name = view.findViewById(R.id.tv_name);
        tv_interest = view.findViewById(R.id.tv_interest);
        iv_User_Head=view.findViewById(R.id.iv_User_Head);
        cvShow=view.findViewById(R.id.cv_show);

    }

    @Override
    public void fillData(ChatMessage message) {
        AvatarHelper.getInstance().displayAvatar(message.getContent(), message.getObjectId(), ivCardImage, true);
        if(message.getToUserName().equals("8")){
            cvShow.setVisibility(View.VISIBLE);
            AvatarHelper.getInstance().displayAvatar(message.getContent(), message.getObjectId(), iv_User_Head, true);
            // Glide.with(getContext()).load(getAvatarUrl(message.getObjectId(), true)).transform(new CircleRoundTransform(mContext)).bitmapTransform(new BlurTransformation(getContext(), 40)).into(iv_User_Head);
            Glide.with(getContext()).load(getAvatarUrl(message.getObjectId(), true)).bitmapTransform(new BlurTransformation(getContext(), 40)).into(iv_User_Head);
            tv_name.setText(String.valueOf(message.getContent()));
        }else {
            cvShow.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(message.getContent())){
            tvPersonName.setText(String.valueOf(message.getContent()));
        }

        if (!isMysend) {
            ivUnRead.setVisibility(message.isSendRead() ? View.GONE : View.VISIBLE);
        }
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
