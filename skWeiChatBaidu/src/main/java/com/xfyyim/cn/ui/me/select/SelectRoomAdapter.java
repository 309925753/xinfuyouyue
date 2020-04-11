package com.xfyyim.cn.ui.me.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.SelectFriendItem;
import com.xfyyim.cn.db.dao.FriendDao;
import com.xfyyim.cn.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectRoomAdapter extends SelectAdapter {
    private List<SelectFriendItem> mItemList;

    public void startSelect(Activity ctx) {
        SelectRoomActivity.start(ctx, mRequestCode, mItemList);
    }

    @Override
    public int getLabel() {
        return R.string.hint_selec_room;
    }

    @NonNull
    @Override
    public List<Friend> query(BaseActivity ctx) {
        List<Friend> ret = new ArrayList<>();
        if (mItemList == null) {
            return ret;
        }
        for (int i = 0; i < mItemList.size(); i++) {
            String friendId = mItemList.get(i).getUserId();
            Friend friend = FriendDao.getInstance().getFriend(ctx.coreManager.getSelf().getUserId(), friendId);
            ret.add(friend);
        }
        return ret;
    }

    @Override
    public boolean consume(Context ctx, int requestCode, Intent data) {
        if (requestCode != mRequestCode) {
            return false;
        }
        String mSelectedRoomIds = data.getStringExtra("SELECTED_ITEMS");
        if (TextUtils.isEmpty(mSelectedRoomIds)) {
            return false;
        }
        mItemList = JSON.parseArray(mSelectedRoomIds, SelectFriendItem.class);

        if (mItemList.size() > 0) {
            final StringBuilder sb = new StringBuilder();
            sb.append(mItemList.get(0).getName());
            for (int i = 1; i < mItemList.size(); i++) {
                sb.append(",");
                sb.append(mItemList.get(i).getName());
            }
            tvValue.setText(sb);
            tvValue.setVisibility(View.VISIBLE);
        } else {
            tvValue.setText("");
            tvValue.setVisibility(View.GONE);
        }
        return true;
    }
}
