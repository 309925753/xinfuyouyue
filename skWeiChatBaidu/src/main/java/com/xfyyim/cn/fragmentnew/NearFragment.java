package com.xfyyim.cn.fragmentnew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.PublicMessageRecyclerAdapter;
import com.xfyyim.cn.adapter.PublicNearAdapter;
import com.xfyyim.cn.adapter.SendTopicAdapter;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.circle.Comment;
import com.xfyyim.cn.bean.circle.PublicMessage;
import com.xfyyim.cn.bean.circle.TopicEntity;
import com.xfyyim.cn.bean.event.EventNotifyAttionNear;
import com.xfyyim.cn.bean.event.EventNotifyMatching;
import com.xfyyim.cn.bean.event.EventNotifyNear;
import com.xfyyim.cn.db.dao.CircleMessageDao;
import com.xfyyim.cn.downloader.Downloader;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.MainActivity;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.circle.MessageEventComment;
import com.xfyyim.cn.ui.circle.MessageEventNotifyDynamic;
import com.xfyyim.cn.ui.circle.MessageEventReply;
import com.xfyyim.cn.ui.circle.SelectPicPopupWindow;
import com.xfyyim.cn.ui.circle.range.CircleDetailActivity;
import com.xfyyim.cn.ui.circle.range.TopicDetailActivity;
import com.xfyyim.cn.ui.me.MatchingSuccessfulActivity;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.TimeUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.TrillCommentInputDialog;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.MessageEvent;
import okhttp3.Call;

/**
 * 朋友圈的Fragment
 * Created by Administrator
 */

public class NearFragment extends EasyFragment {
    private static final int REQUEST_CODE_SEND_MSG = 1;
    private static final int REQUEST_CODE_PICK_PHOTO = 2;
    private static int PAGER_SIZE = 10;
    private String mUserId;
    private String mUserName;
    private SelectPicPopupWindow menuWindow;
    // 页面
    private SmartRefreshLayout mRefreshLayout;
    private SwipeRecyclerView mListView;
    private PublicNearAdapter mAdapter;
    private List<PublicMessage> mMessages = new ArrayList<>();
    private boolean more;
    private String messageId;
    private boolean showTitle = true;
    RelativeLayout rl_root;

    RecyclerView rv_topic_hor;
    TextView tv_like;
    RelativeLayout rl_like;
    ImageView img_likefirst;
    private View mHeadView;

    int pageIndex=0;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        Downloader.getInstance().init(MyApplication.getInstance().mAppDir + File.separator + coreManager.getSelf().getUserId()
                + File.separator + Environment.DIRECTORY_MOVIES);// 初始化视频下载目录
        initViews();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.stopVoice();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        // 退出页面时关闭视频和语音，
        JCVideoPlayer.releaseAllVideos();
        if (mAdapter != null) {
            mAdapter.stopVoice();
        }
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventNotifyNear message) {
        List<PublicMessage> mMes = new ArrayList<>();
        if(mMessages!=null && mMessages.size()>0){
            for(int i=0;i<mMessages.size();i++){
                if(message.MessageData.equals(mMessages.get(i).getUserId())){
                    PublicMessage   publicMessage= mMessages.get(i);
                    publicMessage.setIsAttion(1);
                    mMes.add(publicMessage);
                }else {
                    mMes.add(mMessages.get(i));
                }
            }
        }
        mMessages.clear();
        mMessages.addAll(mMes);
        mAdapter.notifyDataSetChanged();

    }


    /**
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(EventNotifyAttionNear message) {
        List<PublicMessage> mMes = new ArrayList<>();
        if(mMessages!=null && mMessages.size()>0){
            for(int i=0;i<mMessages.size();i++){
                if(message.MessageData.equals(mMessages.get(i).getUserId())){
                    PublicMessage   publicMessage= mMessages.get(i);
                    publicMessage.setIsAttion(-1);
                    mMes.add(publicMessage);
                }else {
                    mMes.add(mMessages.get(i));
                }
            }
        }
        mMessages.clear();
        mMessages.addAll(mMes);
        mAdapter.notifyDataSetChanged();

    }

    public void initViews() {
        more = true;
        rl_root=findViewById(R.id.rl_root);
        mUserId = coreManager.getSelf().getUserId();
        mUserName = coreManager.getSelf().getNickName();
        mListView = findViewById(R.id.recyclerView);
        mListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.header_near, mListView, false);
        rv_topic_hor=mHeadView.findViewById(R.id.rv_topic_hor);
        tv_like=mHeadView.findViewById(R.id.tv_like_count);
        rl_like=mHeadView.findViewById(R.id.rl_like);
        img_likefirst=mHeadView.findViewById(R.id.avatar_img);




        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex=0;
            requestData(true);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
            requestData(false);
        });

        EventBus.getDefault().register(this);

        mListView.addHeaderView(mHeadView);


        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int totalScroll;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalScroll += dy;
                if (totalScroll < 0) {
                    totalScroll = 0;
                }

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getTopicList();
        requestData(true);
    }

    public void getTopicList() {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());

        DialogHelper.showDefaulteMessageProgressDialog(getActivity());

        HttpUtils.post().url(coreManager.getConfig().FILTER_TOPIC_LIST)
                .params(params)
                .build()
                    .execute(new BaseCallback< TopicEntity.DataBean>(TopicEntity.DataBean.class) {
                        @Override
                        public void onResponse(ObjectResult<TopicEntity.DataBean> result) {
                            DialogHelper.dismissProgressDialog();

                            if (Result.checkSuccess(getActivity(), result)) {
                                TopicEntity.DataBean bean=result.getData();
                                if(bean.getQuantity()==0){
                                    rl_like.setVisibility(View.GONE);
                                }else{
                                    tv_like.setText(String.valueOf(bean.getQuantity()));
                                }

                                List<TopicEntity.DataBean.ListBean> list=bean.getList();
                                setTopicAdapter(list);
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            DialogHelper.dismissProgressDialog();
                            Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            super.onFailure(call, e);
                            DialogHelper.dismissProgressDialog();
                            Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                        }
                    });



    }

    public void initData() {
        mAdapter = new PublicNearAdapter(getActivity(),rl_root, coreManager, mMessages);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnItemToClickListener(new PublicNearAdapter.OnItemToClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
                intent.putExtra("PublicMessage",mMessages.get(position));
                intent.putExtra("CareType",mMessages.get(position).getIsAttion());
                getActivity().startActivity(intent);
            }
        });


        requestData(true);
    }

    private void requestData(boolean isPullDownToRefresh) {
        if (isPullDownToRefresh) {// 上拉刷新
            messageId = null;
            more = true;
        }

        if (!more) {
            mRefreshLayout.setNoMoreData(true);
            refreshComplete();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("pageSize", String.valueOf(PAGER_SIZE));
        params.put("pageIndex", String.valueOf(pageIndex));
//        if (distance>0){
//            params.put("distance", String.valueOf(distance));
//        }
//        if (latitude>0){
//            params.put("distance", String.valueOf(latitude));
//        }
//
//        if (longitude>0){
//            params.put("distance", String.valueOf(longitude));
//        }

        HttpUtils.get().url(coreManager.getConfig().MSG_NEAR_LIST)
                .params(params)
                .build()
                .execute(new ListCallback<PublicMessage>(PublicMessage.class) {
                    @Override
                    public void onResponse(ArrayResult<PublicMessage> result) {
                        if (getContext() != null && Result.checkSuccess(requireContext(), result)) {
                            List<PublicMessage> data = result.getData();
                            if (isPullDownToRefresh) {
                                mMessages.clear();
                            }
                            if (data != null && data.size() > 0) {
                                mMessages.addAll(data);
                                if (data.size() == PAGER_SIZE) {
                                    more = true;
                                    mRefreshLayout.resetNoMoreData();
                                } else {
                                    // 服务器返回未满10条，下拉不在去请求
                                    more = false;
                                }
                            } else {
                                // 服务器未返回数据，下拉不再去请求
                                more = false;
                            }
                            mAdapter.notifyDataSetChanged();
                            refreshComplete();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (getContext() != null) {
                            ToastUtil.showNetError(requireContext());
                            refreshComplete();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_SEND_MSG) {
            // 发布说说成功,刷新Fragment
            String messageId = data.getStringExtra(AppConstant.EXTRA_MSG_ID);
            CircleMessageDao.getInstance().addMessage(mUserId, messageId);
            requestData(true);
        }
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(final MessageEvent message) {
        if (message.message.equals("prepare")) {// 准备播放视频，关闭语音播放
            mAdapter.stopVoice();
        }else if (message.message.equals("NearForbitUser")){
            requestData(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(MessageEventNotifyDynamic message) {
        // 收到赞 || 评论 || 提醒我看  || 好友更新动态 协议 刷新页面
        requestData(true);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(final MessageEventComment message) {
        TrillCommentInputDialog trillCommentInputDialog = new TrillCommentInputDialog(getActivity(),
                getString(R.string.enter_pinlunt),
                str -> {
                    Comment mComment = new Comment();
                    Comment comment = mComment.clone();
                    if (comment == null)
                        comment = new Comment();
                    comment.setBody(str);
                    comment.setUserId(mUserId);
                    comment.setNickName(mUserName);
                    comment.setTime(TimeUtils.sk_time_current_time());
                    addComment(message, comment);
                });
        Window window = trillCommentInputDialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);// 软键盘弹起
            trillCommentInputDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(final MessageEventReply message) {
        if (message.event.equals("Reply")) {
            TrillCommentInputDialog trillCommentInputDialog = new TrillCommentInputDialog(getActivity(), getString(R.string.replay) + "：" + message.comment.getNickName(),
                    str -> {
                        Comment mComment = new Comment();
                        Comment comment = mComment.clone();
                        if (comment == null)
                            comment = new Comment();
                        comment.setToUserId(message.comment.getUserId());
                        comment.setToNickname(message.comment.getNickName());
                        comment.setToBody(message.comment.getToBody());
                        comment.setBody(str);
                        comment.setUserId(mUserId);
                        comment.setNickName(mUserId);
                        comment.setTime(TimeUtils.sk_time_current_time());
                        Reply(message, comment);
                    });
            Window window = trillCommentInputDialog.getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);// 软键盘弹起
                trillCommentInputDialog.show();
            }
        }
    }

    /**
     * 停止刷新动画
     */
    private void refreshComplete() {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        }, 200);
    }


    private void addComment(MessageEventComment message, final Comment comment) {
        String messageId = message.id;
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("messageId", messageId);
        if (comment.isReplaySomeBody()) {
            params.put("toUserId", comment.getToUserId() + "");
            params.put("toNickname", comment.getToNickname());
            params.put("toBody", comment.getToBody());
        }
        params.put("body", comment.getBody());

        HttpUtils.post().url(coreManager.getConfig().MSG_COMMENT_ADD)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        // 评论成功
                        if (getContext() != null && Result.checkSuccess(requireContext(), result)) {
                            comment.setCommentId(result.getData());
                            message.pbmessage.setCommnet(message.pbmessage.getCommnet() + 1);
                            PublicMessageRecyclerAdapter.CommentAdapter adapter = (PublicMessageRecyclerAdapter.CommentAdapter) message.view.getAdapter();
                            adapter.addComment(comment);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(getActivity());
                    }
                });
    }

    /**
     * 回复
     */
    private void Reply(MessageEventReply event, final Comment comment) {
        final int position = event.id;
        final PublicMessage message = mMessages.get(position);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("messageId", message.getMessageId());

        if (!TextUtils.isEmpty(comment.getToUserId())) {
            params.put("toUserId", comment.getToUserId());
        }
        if (!TextUtils.isEmpty(comment.getToNickname())) {
            params.put("toNickname", comment.getToNickname());
        }
        params.put("body", comment.getBody());

        HttpUtils.post().url(coreManager.getConfig().MSG_COMMENT_ADD)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        // 评论成功
                        if (getContext() != null && Result.checkSuccess(requireContext(), result)) {
                            comment.setCommentId(result.getData());
                            message.setCommnet(message.getCommnet() + 1);
                            PublicMessageRecyclerAdapter.CommentAdapter adapter = (PublicMessageRecyclerAdapter.CommentAdapter) event.view.getAdapter();
                            adapter.addComment(comment);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(getActivity());
                    }
                });
    }

    SendTopicAdapter adapter;
    public void setTopicAdapter(List<TopicEntity.DataBean.ListBean> list) {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rv_topic_hor.setLayoutManager(linearLayoutManager);
            adapter = new SendTopicAdapter(list,getActivity());
            rv_topic_hor.setAdapter(adapter);


            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(getActivity(), TopicDetailActivity.class);
                    intent.putExtra("Topic",(Serializable) list.get(position));
                    startActivity(intent);
                }
            });

        } else {
            adapter.notifyDataSetChanged();
        }


    }




    /**
     * 定位到评论位置
     */
    public void showToCurrent(String mCommentId) {
        int pos = -1;
        for (int i = 0; i < mMessages.size(); i++) {
            if (StringUtils.strEquals(mCommentId, mMessages.get(i).getMessageId())) {
                pos = i + 2;
                break;
            }
        }
        // 如果找到就定位到这条说说
        if (pos != -1) {
            mListView.scrollToPosition(pos);
        }
    }
}
