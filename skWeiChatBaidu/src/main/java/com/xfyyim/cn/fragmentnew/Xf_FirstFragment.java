package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.customer.AngleTransformer;
import com.xfyyim.cn.customer.MyAlphaTransformer;
import com.xfyyim.cn.customer.StackLayout;
import com.xfyyim.cn.customer.StackPageTransformer;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.util.SkinUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class Xf_FirstFragment extends EasyFragment {
    private TextView mTvTitle;
    private TextView tvFriendCount;
    private ImageView mIvTitleRight;
    private List<User> mUsers;
    @BindView(R.id.ll_superLight)
    LinearLayout ll_superLigth;


    StackLayout mStackLayout;
    Adapter mAdapter;

    @Override
    protected int inflateLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
        initTitleBackground();
        initView();


    }

    private void initView() {
        ll_superLigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        mStackLayout = (StackLayout) findViewById(R.id.stack_layout);

    }

    private void initTitleBackground() {
        SkinUtils.Skin skin = SkinUtils.getSkin(requireContext());
        findViewById(R.id.tool_bar).setBackgroundColor(skin.getAccentColor());
    }


    public void setmAdapter(List<User> list) {
        mStackLayout.setAdapter(mAdapter = new Adapter(list));
        mStackLayout.addPageTransformer(
                new StackPageTransformer(),     // 堆叠
                new MyAlphaTransformer(),       // 渐变
                new AngleTransformer()          // 角度
        );

        mStackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {

                LogUtil.e("cjh left "+itemLeft+"  isSwipeLeft  "+isSwipeLeft);
                if (isSwipeLeft){
                    ToastUtil.showToast(getActivity(),"不喜欢接口");
                }else{
                    ToastUtil.showToast(getActivity(),"喜欢接口");
                }
                if (itemLeft==0){
                    requestData("10000");
                }
            }
        });


    }


    class Adapter extends StackLayout.Adapter<Adapter.ViewHolder> {
        List<User> mData;

        public void setData(List<User> data) {

            mData = data;
        }

        public List<User> getData() {
            return mData;
        }

        public Adapter(List<User> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String url = AvatarHelper.getAvatarUrl(mData.get(position).getUserId(), false);

            GlideImageUtils.setImageView(getActivity(), url, holder.img_pic);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                    intent.putExtra("FriendId",mData.get(position).getUserId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends StackLayout.ViewHolder {
            ImageView img_pic;

            public ViewHolder(View itemView) {
                super(itemView);
                img_pic = itemView.findViewById(R.id.img_pic);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        requestData("10000");
    }

    private void requestData(String distance) {
        mUsers = new ArrayList<>();
        double latitude = 30.093683;
        double longitude = 120.520789;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
//            params.put("sex", String.valueOf(0));
//        params.put("minAge", String.valueOf(1));
//        params.put("maxAge", String.valueOf(10));
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));

        params.put("distance", distance);

        HttpUtils.post().url(coreManager.getConfig().USER_NEAR)
                .params(params)
                .build()
                .execute(new ListCallback<User>(User.class) {
                    @Override
                    public void onResponse(ArrayResult<User> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(getActivity(), result)) {
                            List<User> datas = result.getData();
                            if (datas != null && datas.size() > 0) {
                                mUsers.addAll(datas);
                                setmAdapter(mUsers);
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
