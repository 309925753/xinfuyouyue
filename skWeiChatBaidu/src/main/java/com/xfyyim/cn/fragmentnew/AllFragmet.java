package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.AllPersonAdapeter;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.FriendEntity;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.message.ChatActivity;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.JsonCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AllFragmet extends EasyFragment {
    @BindView(R.id.rv_all)
    RecyclerView rv_all;
    AllPersonAdapeter allPersonAdapeter;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_all;
    }


    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
    }

    public void getUser() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.post().url(coreManager.getConfig().USER_FRIEDS_MATHLIST)
                .params(params)
                .build()
                .execute(new ListCallback<FriendEntity.DataBean>(FriendEntity.DataBean.class) {
                    @Override
                    public void onResponse(ArrayResult<FriendEntity.DataBean> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(getActivity(), result)) {
                            List<FriendEntity.DataBean> datas = result.getData();
                            setAdapter(datas);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setAdapter(List<FriendEntity.DataBean> list) {
        if (allPersonAdapeter == null) {
            LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
            rv_all.setLayoutManager(linearLayout);
            allPersonAdapeter = new AllPersonAdapeter(getActivity(), list);
            rv_all.setAdapter(allPersonAdapeter);
            allPersonAdapeter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Friend friend=new Friend();
                    friend.setUserId(list.get(position).getToUserId()+"");
                    friend.setNickName(list.get(position).getToNickname());
                    friend.setRemarkName(list.get(position).getToNickname());
                    friend.setUnReadNum(list.get(position).getMsgNum());
                    friend.setTimeSend(list.get(position).getLastTalkTime());
                    friend.setContent(list.get(position).getToUserType()+"ceshi");
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.setClass(getActivity(), ChatActivity.class);
                    intent.putExtra(ChatActivity.FRIEND, friend);
                    startActivity(intent);
                }
            });

        } else {
            allPersonAdapeter.notifyDataSetChanged();
        }
    }
}
