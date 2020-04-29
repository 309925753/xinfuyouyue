package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.db.dao.UserDao;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SwitchButton;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


public class MyPrivacyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_left)
    SkinImageView ivTitleLeft;
    @BindView(R.id.tv_title_left)
    SkinTextView tvTitleLeft;
    @BindView(R.id.pb_title_center)
    ProgressBar pbTitleCenter;
    @BindView(R.id.tv_title_center)
    SkinTextView tvTitleCenter;
    @BindView(R.id.iv_title_center)
    ImageView ivTitleCenter;
    @BindView(R.id.iv_title_right)
    SkinImageView ivTitleRight;
    @BindView(R.id.iv_title_right_right)
    SkinImageView ivTitleRightRight;
    @BindView(R.id.tv_title_right)
    SkinTextView tvTitleRight;
    @BindView(R.id.mergerStatus)
    MergerStatus mergerStatus;
    private  boolean  isAutoExpandRange;
    private   SwitchButton sbNotAllowComAlbum;
public  int currentSelect;
    private Unbinder  unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_privacy);
        unbinder =ButterKnife.bind(this);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("隐私与权限");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/



    }

    private void initView() {

        RelativeLayout rlUserBlocked =  findViewById(R.id.rlUserBlocked);
         sbNotAllowComAlbum =  findViewById(R.id.sbNotAllowComAlbum);
        SwitchButton sbComContacts =  findViewById(R.id.sbComContacts);
        SwitchButton sbPrivacyContacts = (SwitchButton) findViewById(R.id.sbPrivacyContacts);

        currentSelect=coreManager.getSelf().getSettings().getNotSeeFilterMyPhotos();
        if(coreManager.getSelf().getSettings().getNotSeeFilterMyPhotos()==1){
            sbNotAllowComAlbum.setChecked(true);
        }else {
            sbNotAllowComAlbum.setChecked(false);
        }

        //不让未配对的人看我的相册       在查看相册里面做判断做一个条件判断权限操作
        sbNotAllowComAlbum.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    currentSelect=1;
                }else{
                    currentSelect=0;
                }
                isAutoExpandRange=isChecked;
            }
        });
        //已屏蔽用户
        rlUserBlocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserBlocked();
            }
        });

        updateSelfData();
    }

    private void openUserBlocked() {
        startActivity(new Intent(this, MyUserBlockedActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                if (coreManager.getSelf().getSettings().getNotSeeFilterMyPhotos()!=currentSelect){
                    update();
                }
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
    private void update(){

        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("userId", coreManager.getSelf().getUserId());
        if(isAutoExpandRange){
            params.put("notSeeFilterMyPhotos", "1");
        }else {
            params.put("notSeeFilterMyPhotos", "0");
        }
        HttpUtils.get().url(coreManager.getConfig().USER_UPDATE_SETTINGS_CORE)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {
                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(MyPrivacyActivity.this, result)) {
                            updateData();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }
    private void updateData(){

        if(coreManager.getSelf().getSettings().getNotSeeFilterMyPhotos()==1){
            sbNotAllowComAlbum.setChecked(true);
        }else {
            sbNotAllowComAlbum.setChecked(false);
        }
    }
    private void updateSelfData() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            User user = result.getData();
                            boolean updateSuccess = UserDao.getInstance().updateByUser(user);
                            // 设置登陆用户信息
                            if (updateSuccess) {
                                // 如果成功，保存User变量，
                                coreManager.setSelf(user);
                                updateData();
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Exception e) {

                    }
                });
    }

}
