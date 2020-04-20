package com.xfyyim.cn.fragmentnew;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.Friend;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.broadcast.OtherBroadcast;
import com.xfyyim.cn.customer.AngleTransformer;
import com.xfyyim.cn.customer.MyAlphaTransformer;
import com.xfyyim.cn.customer.StackLayout;
import com.xfyyim.cn.customer.StackPageTransformer;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.EasyFragment;
import com.xfyyim.cn.ui.me_new.PersonInfoActivity;
import com.xfyyim.cn.ui.message.ChatActivity;
import com.xfyyim.cn.util.SkinUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.MyVipPaymentPopupWindow;
import com.xfyyim.cn.view.MyWalletPopupWindow;
import com.xfyyim.cn.view.SuperCriticalLightWindow;
import com.xfyyim.cn.view.SuperSolarizePopupWindow;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.LogUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class Xf_FirstFragment extends EasyFragment {
    private TextView mTvTitle;
    private TextView tvFriendCount;
    private ImageView mIvTitleRight;
    private List<User> mUsers;
    @BindView(R.id.ll_superLight)
    LinearLayout ll_superLigth;
    private int selectItem=0;


    StackLayout mStackLayout;
    Adapter mAdapter;
    private  int pageIndex=0;
    private  int pageSize=25;
    private boolean  isSliding=false;

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

        mStackLayout = (StackLayout) findViewById(R.id.stack_layout);


        ll_superLigth.setOnClickListener(this::onClick);
        findViewById(R.id.llRegretsUnLike).setOnClickListener(this::onClick);
        findViewById(R.id.llUnLike).setOnClickListener(this::onClick);
        findViewById(R.id.ll_superLight).setOnClickListener(this::onClick);
        findViewById(R.id.llUserLike).setOnClickListener(this::onClick);
        findViewById(R.id.llSuperLike).setOnClickListener(this::onClick);

    }
    private void  openSuperCritcal(){
     /*   SuperCriticalLightWindow myWalletPopupWindow=new SuperCriticalLightWindow(getActivity());
        myWalletPopupWindow.setBtnOnClice(new SuperCriticalLightWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {
                LogUtil.e("type =  " +type +"-------------vip = " +vip);
            }
        });
*/

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

        mStackLayout.setOnSwipeListener(
                new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
              /*  if(itemLeft>=1){
                   selectItem= itemLeft-1;
                }*/
                isSliding=true;
                LogUtil.e("cjh left "+itemLeft+"  isSwipeLeft  "+isSwipeLeft+"--swipedItemPos = " +swipedItemPos);
                if (isSwipeLeft){
                  //  ToastUtil.showToast(getActivity(),"不喜欢接口");
                    unLike(list.get(selectItem));
                }else{
                    userLike(list.get(selectItem));
                 //   ToastUtil.showToast(getActivity(),"喜欢接口");
                }
                if (itemLeft==0){
                    requestData();
                }
            }
        });

    }

    /**
     * 喜欢
     * @param user
     */
    private  void userLike(User  user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        params.put("nickname",user.getNickName());
        params.put("age",user.getAge()+"");
        HttpUtils.post().url(coreManager.getConfig().USER_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            exchangeAdapter(user);
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 不喜欢
     * @param user
     */
    private  void unLike(User  user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        params.put("nickname",user.getNickName());
        params.put("age",user.getAge()+"");
        HttpUtils.post().url(coreManager.getConfig().USER_UN_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            exchangeAdapter(user);
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 超级爆光
     * @param user
     */
    private  void superLight(User  user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        params.put("nickname",user.getNickName());
        params.put("age",user.getAge()+"");
        HttpUtils.post().url(coreManager.getConfig().USER_OPEN_SUPEREXPOSURE)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {
                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        if (Result.checkSuccess(getActivity(), result)) {
                            LogUtil.e("result = " +result.getData());
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    /**
     * 反悔不喜欢
     * @param user
     */
    private  void RegretsUnLike(User  user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        HttpUtils.post().url(coreManager.getConfig().USER_REGRETS_UN_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                            }
                            if(user.getUserVIPPrivilege().getVipLevel().equals("-1")){
                                BuyMember(user);
                            }else {
                                if(mUsers!=null && (!isSliding)){
                                    swithSuperSolarize(0);
                                }else {
                                    mUsers.remove(0);
                                    mUsers.add(0,userSelect);
                                    setmAdapter(mUsers);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    private User  userSelect=new User();
    private void exchangeAdapter(User  user){
        userSelect=user;
      /*  if(selectItem>=1){
            mUsers.add(0,user);
        }else if (selectItem==0 && mUsers.size()==1){
         return;
        }else if (selectItem==0 && mUsers.size()>=2){
            mUsers.add(0,user);
        }else if (selectItem>=1 && mUsers.size()>=2){
            mUsers.add(0,user);
        }*/
        if(mUsers!=null &&mUsers.size()==3){
            requestData();
        }
         if(mUsers.size()==3){
            mUsers.remove(0);
            mUsers.add(user);
        }else if(mUsers.size()>=1){
            mUsers.remove(0);
        }
        setmAdapter(mUsers);
        mAdapter.notifyDataSetChanged();

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
        requestData();
    }

    private void requestData() {
        mUsers = new ArrayList<>();
        double latitude = 1.0;
        double longitude = 1.0;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(getActivity()).getAccessToken());
//      params.put("sex", String.valueOf(0));
//      params.put("minAge", String.valueOf(1));
//      params.put("maxAge", String.valueOf(10));
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("pageIndex", pageIndex+"");
        params.put("pageSize", pageSize+"");
//        params.put("distance", distance);
        pageIndex++;
        HttpUtils.post().url(coreManager.getConfig().USER_NEAR_BY)
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

    @Override
    public void onClick(View v) {
        LogUtil.e("selectItem  = " +selectItem+"***mUsers.size()  = " +mUsers.size());
        if(mUsers!=null && mUsers.size()>0) {
         /*   if(mUsers.size()==selectItem ) {
                selectItem = selectItem - 1;
            }*/
            switch (v.getId()) {
                case R.id.llRegretsUnLike:
                    RegretsUnLike(mUsers.get(selectItem));
                    break;
                case R.id.llUnLike:
                    unLike(mUsers.get(selectItem));
                    break;
                case R.id.ll_superLight:
                    // superLight(mUsers.get(selectItem));
                    swithSuperSolarize(1);
                    break;
                case R.id.llUserLike:
                    userLike(mUsers.get(selectItem));
                    break;
                case R.id.llSuperLike:
                    superLike(mUsers.get(selectItem));
                    break;
            }
        }
    }

    /**
     * 超级喜欢
     * @param user
     */
    private  void superLike(User  user){
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("likeUserId",user.getUserId());
        params.put("nickname",user.getNickName());
        params.put("age",user.getAge()+"");
        HttpUtils.post().url(coreManager.getConfig().USER_SUPER_LIKE)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User userSuper = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(userSuper);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(userSuper);
                            }
                            if(userSuper.getUserVIPPrivilege().getVipLevel().equals("-1")){
                                BuyMember(userSuper);
                            } else {
                                //判断超级喜欢每天5次
                                if(userSuper.getUserVIPPrivilege().getQuantity()==0){
                                    ToastUtil.showLongToast(getContext(),"当天次数已用完");
                                }
                        }

                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {
                    }
                });
    }

    //购买会员
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyMember(User  user) {
        //显示VIP购买会员
        MyVipPaymentPopupWindow myVipPaymentPopupWindow = new MyVipPaymentPopupWindow(getActivity(), user.getUserVIPPrivilegeConfig(), coreManager.getSelf().getUserId(), coreManager.getSelf().getNickName(),user.getUserVIPPrivilege().getVipLevel());
        myVipPaymentPopupWindow.setBtnOnClice(new MyVipPaymentPopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(String type, int vip) {

                LogUtil.e("********************************* pay type = " + type + "-------------vip = " + vip);
            }
        });
    }
    private void swithSuperSolarize(int switchType){
        SuperSolarizePopupWindow  RegretsUnLikePopupWindow=new SuperSolarizePopupWindow(getActivity(),switchType);
        RegretsUnLikePopupWindow.setBtnOnClice(new SuperSolarizePopupWindow.BtnOnClick() {
            @Override
            public void btnOnClick(int sumbitType) {
                //超级爆光再来一次
                if(switchType==1&&sumbitType==1){
                    superLight(userSelect);
                //超级爆光滑动卡片
                }else if(switchType==1&&sumbitType==0) {
                 //无法反悔
                }else if(switchType==0&&sumbitType==1) {

                }

            }
        });
    }
}
