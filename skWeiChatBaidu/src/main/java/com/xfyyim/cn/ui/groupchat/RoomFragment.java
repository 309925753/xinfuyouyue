package com.xfyyim.cn.ui.groupchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.R;
import com.xfyyim.cn.Reporter;
import com.xfyyim.cn.adapter.FriendSortAdapter;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.RoomMember;
import com.xfyyim.cn.bean.message.MucRoom;
import com.xfyyim.cn.broadcast.MsgBroadcast;
import com.xfyyim.cn.broadcast.MucgroupUpdateUtil;
import com.xfyyim.cn.db.dao.FriendDao;
import com.xfyyim.cn.db.dao.OnCompleteListener2;
import com.xfyyim.cn.db.dao.RoomMemberDao;
import com.xfyyim.cn.sortlist.BaseComparator;
import com.xfyyim.cn.sortlist.BaseSortModel;
import com.xfyyim.cn.sortlist.SideBar;
import com.xfyyim.cn.sortlist.SortHelper;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.message.MucChatActivity;
import com.xfyyim.cn.util.AsyncUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.ClearEditText;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 我的群组
 */
public class RoomFragment extends EasyFragment {
    private PullToRefreshListView mPullToRefreshListView;
    private FriendSortAdapter mAdapter;
    private List<BaseSortModel<Friend>> mSortFriends;
    private BaseComparator<Friend> mBaseComparator;
    private SideBar mSideBar;
    private TextView mTextDialog;
    private ClearEditText mEditText;

    private String mLoginUserId;
    private Handler mHandler = new Handler();
    private boolean mNeedUpdate = true;
    private String searchKey;
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MucgroupUpdateUtil.ACTION_UPDATE)) {
                update();
            }
        }
    };

    public RoomFragment() {
        mSortFriends = new ArrayList<BaseSortModel<Friend>>();
        mBaseComparator = new BaseComparator<Friend>();
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        mLoginUserId = coreManager.getSelf().getUserId();
        if (createView) {
            initView();
        }
    }

    public void update() {
        if (isResumed()) {
            loadData();
        } else {
            mNeedUpdate = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNeedUpdate) {
            loadData();
            mNeedUpdate = false;
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mUpdateReceiver);
        super.onDestroy();
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View mHeadView = inflater.inflate(R.layout.head_for_room_fragment, null);
        mEditText = mHeadView.findViewById(R.id.search_edit);
        mEditText.setFocusClear(false);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchKey = s.toString();
                loadData();
            }
        });
        mPullToRefreshListView.getRefreshableView().addHeaderView(mHeadView, null, false);
        mAdapter = new FriendSortAdapter(getActivity(), mSortFriends);
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
        mPullToRefreshListView.setMode(Mode.PULL_FROM_START);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                updateRoom();
            }
        });

        mPullToRefreshListView.setOnItemClickListener((parent, view, position, id) -> {
            Friend friend = mSortFriends.get((int) id).getBean();
            Intent intent = new Intent(getActivity(), MucChatActivity.class);
            intent.putExtra(AppConstant.EXTRA_USER_ID, friend.getUserId());
            intent.putExtra(AppConstant.EXTRA_NICK_NAME, friend.getNickName());
            intent.putExtra(AppConstant.EXTRA_IS_GROUP_CHAT, true);
            startActivity(intent);
            if (friend.getUnReadNum() > 0) {// 如该群组未读消息数量大于1, 刷新MessageFragment
                MsgBroadcast.broadcastMsgNumReset(getActivity());
                MsgBroadcast.broadcastMsgUiUpdate(getActivity());
            }
        });

        mSideBar = (SideBar) findViewById(R.id.sidebar);
        mTextDialog = (TextView) findViewById(R.id.text_dialog);
        mSideBar.setTextView(mTextDialog);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mPullToRefreshListView.getRefreshableView().setSelection(position);
                }
            }
        });
       /* mPullToRefreshListView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BaseSortModel<Friend> sortFriend = mSortFriends.get((int) id);
                if (sortFriend == null || sortFriend.getBean() == null) {
                    return false;
                }
                showLongClickOperationDialog(sortFriend);
                return true;
            }
        });*/

        getActivity().registerReceiver(mUpdateReceiver, MucgroupUpdateUtil.getUpdateActionFilter());
    }

    private void loadData() {
        AsyncUtils.doAsync(this, e -> {
            Reporter.post("加载数据失败，", e);
            AsyncUtils.runOnUiThread(requireContext(), ctx -> {
                ToastUtil.showToast(ctx, R.string.data_exception);
            });
        }, c -> {
            long startTime = System.currentTimeMillis();
            final List<Friend> friends;
            friends = FriendDao.getInstance().getAllRooms(mLoginUserId);
            if (!TextUtils.isEmpty(searchKey)) {
                Iterator<Friend> iterator = friends.iterator();
                while (iterator.hasNext()) {
                    Friend friend = iterator.next();
                    if (friend.getNickName().contains(searchKey)) {
                        continue;
                    }
                    RoomMember roomMember = RoomMemberDao.getInstance().searchMemberContains(friend, searchKey);
                    if (roomMember != null) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            Map<String, Integer> existMap = new HashMap<>();
            List<BaseSortModel<Friend>> sortedList = SortHelper.toSortedModelList(friends, existMap, Friend::getShowName);

            long delayTime = 200 - (startTime - System.currentTimeMillis());// 保证至少200ms的刷新过程
            if (delayTime < 0) {
                delayTime = 0;
            }
            c.postDelayed(r -> {
                mSideBar.setExistMap(existMap);
                mSortFriends = sortedList;
                mAdapter.setData(sortedList);
                mPullToRefreshListView.onRefreshComplete();
            }, delayTime);
        });
    }

    /**
     * 下载我的群组
     */
    private void updateRoom() {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("type", "0");
        params.put("pageIndex", "0");
        params.put("pageSize", "1000");// 给一个尽量大的值

        HttpUtils.get().url(coreManager.getConfig().ROOM_LIST_HIS)
                .params(params)
                .build()
                .execute(new ListCallback<MucRoom>(MucRoom.class) {
                    @Override
                    public void onResponse(ArrayResult<MucRoom> result) {
                        if (result.getResultCode() == 1) {
                            FriendDao.getInstance().addRooms(mHandler, mLoginUserId, result.getData(),
                                    new OnCompleteListener2() {
                                        @Override
                                        public void onLoading(int progressRate, int sum) {

                                        }

                                        @Override
                                        public void onCompleted() {// 下载完成
                                            if (coreManager.isLogin()) {
                                                // 1.调用smack内join方法加入群组
                                                List<Friend> mFriends = FriendDao.getInstance().getAllRooms(mLoginUserId);
                                                for (int i = 0; i < mFriends.size(); i++) {// 已加入的群组不会重复加入，方法内已去重
                                                    coreManager.joinMucChat(mFriends.get(i).getUserId(), 0);
                                                }
                                                // 2.更新我的群组列表
                                                loadData();
                                            }
                                        }
                                    });
                        } else {
                            mPullToRefreshListView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showNetError(getActivity());
                        mPullToRefreshListView.onRefreshComplete();
                    }
                });
    }
}
