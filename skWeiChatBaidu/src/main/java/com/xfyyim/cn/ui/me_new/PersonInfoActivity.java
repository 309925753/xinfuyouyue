package com.xfyyim.cn.ui.me_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stx.xhb.xbanner.XBanner;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.MyInAdapter;
import com.xfyyim.cn.adapter.QuestionInfoAdapter;
import com.xfyyim.cn.bean.MyInEntity;
import com.xfyyim.cn.bean.PhotoEntity;
import com.xfyyim.cn.bean.QuestEntity;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.customer.FlowLayout;
import com.xfyyim.cn.customer.SkillTextView;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.me.EditInfoActivity;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int MAX_COUNT_TAGS = 8;//标签最大数

    @BindView(R.id.flowLayout_tags)
    FlowLayout flowLayoutTags;

    String friendId;
    private Unbinder unbinder;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.iv_title_right)
    ImageView iv_title_right;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.nick_name)
    TextView nick_name;
    @BindView(R.id.rl_blum)
    RelativeLayout rl_blum;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_xingzuo)
    TextView tv_xingzuo;
    @BindView(R.id.tv_vip)
    TextView tv_vip;
    @BindView(R.id.private_wechat_like)
    TextView private_wechat_like;
    @BindView(R.id.person_sign)
    TextView person_sign;
    @BindView(R.id.my_info)
    TextView my_info;
    @BindView(R.id.ll_position)
    LinearLayout ll_position;
    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.ll_work)
    LinearLayout ll_work;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.ll_home_town)
    LinearLayout ll_home_town;
    @BindView(R.id.ll_mysign)
    LinearLayout ll_mysign;
    @BindView(R.id.tv_hometown)
    TextView tv_hometown;
    @BindView(R.id.ll_place)
    LinearLayout ll_place;
    @BindView(R.id.tv_place)
    TextView tv_place;
    @BindView(R.id.my_intresting)
    TextView my_intresting;
    @BindView(R.id.rv_myin)
    RecyclerView rv_myin;
    @BindView(R.id.ll_my_blum)
    LinearLayout ll_my_blum;

    @BindView(R.id.img_sex)
    ImageView img_sex;
    @BindView(R.id.ll_sex_bg)
    LinearLayout ll_sex_bg;

    @BindView(R.id.ll_renzheng)
    RelativeLayout ll_renzheng;

    @BindView(R.id.rl_seelike)
    RelativeLayout rl_seelike;

    @BindView(R.id.tv_my_sign)
    TextView tv_my_sign;

    @BindView(R.id.tv_myquestion)
    TextView tv_myquestion;
    @BindView(R.id.rv_question)
    RecyclerView rv_question;

    @BindView(R.id.line_info)
    View line_info;
    @BindView(R.id.line_question)
    View line_question;
    @BindView(R.id.line_sign)
    View line_sign;  @BindView(R.id.line)
    View line;
    User user;
    MyInAdapter adapter;
    String usrId;
    QuestionInfoAdapter questionAdapter;

    String mLoginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        unbinder = ButterKnife.bind(this);
        friendId = getIntent().getStringExtra("FriendId");
        initActionBar();
        mLoginId=coreManager.getSelf().getUserId();
        rl_blum.setOnClickListener(this);
    }


    private void initActionBar() {

        getSupportActionBar().hide();
        usrId = coreManager.getSelf().getUserId();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);
        tv_title_center.setText("个人资料");

        if (friendId.equals(usrId)) {
            iv_title_right.setVisibility(View.VISIBLE);
            iv_title_right.setImageDrawable(getResources().getDrawable(R.mipmap.pen_edit));
            iv_title_right.setOnClickListener(this);
        } else {
            iv_title_right.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.iv_title_right:
                Intent intent = new Intent(PersonInfoActivity.this, EditInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_blum:

//                if (!mLoginId.equals(friendId)) {
//                    if (user.getSettings().getNotSeeFilterMyPhotos() == 1 && user.getIsMatch() == 1) {
//                        ToastUtil.showToast(PersonInfoActivity.this, "您还不是对方好友，无法查看");
//                    } else {
//                        Intent intent1=new Intent(PersonInfoActivity.this, PersonBlumActivity.class);
//                        intent1.putExtra("FriendId",friendId);
//                        startActivity(intent1);
//                    }
//                }

                if (user.getSettings().getNotSeeFilterMyPhotos() == 1 && user.getIsMatch() == 1) {
                    ToastUtil.showToast(PersonInfoActivity.this, "您还不是对方好友，无法查看");
                } else {
                    Intent intent1=new Intent(PersonInfoActivity.this, PersonBlumActivity.class);
                    intent1.putExtra("FriendId",friendId);
                    startActivity(intent1);
                }
                break;


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(PersonInfoActivity.this).getAccessToken());
        params.put("userId", friendId);

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            user = result.getData();
                            setUiData(user);

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }

    public void setUiData(User user) {
if (user.getNickName()!=null&&TextUtils.isEmpty(user.getNickName())){
    nick_name.setText(user.getNickName());
}


//认证中心
        if (user.getFaceIdentity() == 1) {
            ll_renzheng.setVisibility(View.VISIBLE);
        } else {
            ll_renzheng.setVisibility(View.GONE);
        }


        //喜欢我的人数
        if (friendId.equals(user.getUserId())) {
            line.setVisibility(View.VISIBLE);
            rl_seelike.setVisibility(View.VISIBLE);
        } else {
            line.setVisibility(View.GONE);
            rl_seelike.setVisibility(View.GONE);
        }

        if (user.getAge() == 0) {
            tv_age.setVisibility(View.GONE);
        } else {
            tv_age.setVisibility(View.VISIBLE);
            tv_age.setText(String.valueOf(user.getAge()));
        }

        if (user.getSex() == 1) {
            img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_man));
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_qing));
        } else {
            img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_nv));
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_pink));
        }

        //相册
        List<String> imgesUrl = new ArrayList<>();
        if (user.getMyPhotos() != null && user.getMyPhotos().size() > 0) {
            for (PhotoEntity photo : user.getMyPhotos()) {
                imgesUrl.add(photo.getPhotoUtl());
            }
            banner.setVisibility(View.VISIBLE);



            int index = user.getMyPhotos().size() > 3 ? 3 : user.getMyPhotos().size();
            ll_my_blum.removeAllViews();
            for (int i = 0; i < index; i++) {
                ImageView imageView = new ImageView(this);


                int wid = ScreenUtils.dip2px(33, mContext);
                int hight = ScreenUtils.dip2px(33, mContext);

                LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(wid, hight);
                ls.setMargins(0, 0, 10, 0);
                imageView.setLayoutParams(ls);

                GlideImageUtils.setImageView(this, user.getMyPhotos().get(i).getPhotoUtl(), imageView);
                ll_my_blum.addView(imageView);

            }
        } else {
            String headUrl = AvatarHelper.getAvatarUrl(user.getUserId(), false);
            imgesUrl.add(headUrl);
        }

        banner.setData(imgesUrl, null);

        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                GlideImageUtils.setImageView(PersonInfoActivity.this, imgesUrl.get(position), (ImageView) view);
            }
        });


        private_wechat_like.setText(String.valueOf(user.getLikeMeCount()) + "人喜欢了你");


        //签名
        if (user.getDescription() == null || TextUtils.isEmpty(user.getDescription())) {
            person_sign.setText("这个人很懒，什么都没留下");
            person_sign.setTextColor(getResources().getColor(R.color.text_black_999));

        } else {
            person_sign.setText(user.getDescription());
            person_sign.setTextColor(getResources().getColor(R.color.text_black_333));
        }


        //我的信息

        if (user.getMyIndustry() != null && !TextUtils.isEmpty(user.getMyIndustry())) {
            ll_position.setVisibility(View.VISIBLE);
            tv_position.setText(user.getMyIndustry());
        } else {
            ll_position.setVisibility(View.GONE);
        }

        if (user.getMyCompany() != null && !TextUtils.isEmpty(user.getMyCompany())) {
            ll_company.setVisibility(View.VISIBLE);
            tv_company.setText(user.getMyCompany());
        } else {
            ll_company.setVisibility(View.GONE);
        }

        if (user.getMyWork() != null && !TextUtils.isEmpty(user.getMyWork())) {
            ll_work.setVisibility(View.VISIBLE);
            tv_work.setText(user.getMyWork());
        } else {
            ll_work.setVisibility(View.GONE);
        }

        if (user.getMyHometown() != null && !TextUtils.isEmpty(user.getMyHometown())) {
            ll_home_town.setVisibility(View.VISIBLE);
            tv_hometown.setText(user.getMyHometown());
        } else {
            ll_home_town.setVisibility(View.GONE);
        }
        if (user.getMyFrequentLocations() != null && !TextUtils.isEmpty(user.getMyFrequentLocations())) {
            ll_place.setVisibility(View.VISIBLE);
            tv_place.setText(user.getMyFrequentLocations());
        } else {
            ll_place.setVisibility(View.GONE);
        }


        //我的标签

        if (user.getMyTag() != null && !TextUtils.isEmpty(user.getMyTag())) {

            ll_mysign.setVisibility(View.VISIBLE);
            tv_my_sign.setVisibility(View.VISIBLE);
            line_sign.setVisibility(View.VISIBLE);

            List<String> list = new ArrayList<>();
            if (user.getMyTag().contains(",")) {
                list = StringUtils.getListString(user.getMyTag());
            } else {
                list.add(user.getMyTag());
            }
            initSkill(list);

        } else {

            ll_mysign.setVisibility(View.GONE);
            tv_my_sign.setVisibility(View.GONE);
            line_sign.setVisibility(View.GONE);


        }


        //我的兴趣

        List<MyInEntity> myInEntityList = new ArrayList<>();

        if (user.getMyTastes() != null && !TextUtils.isEmpty(user.getMyTastes())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_1);
            entity.setContextName(user.getMyTastes());
            entity.setBackground_type(1);
            myInEntityList.add(entity);
        }

        if (user.getMyMusic() != null && !TextUtils.isEmpty(user.getMyMusic())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_2);
            entity.setContextName(user.getMyMusic());
            entity.setBackground_type(2);
            myInEntityList.add(entity);
        }
        if (user.getMyFood() != null && !TextUtils.isEmpty(user.getMyFood())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_3);
            entity.setContextName(user.getMyFood());
            entity.setBackground_type(3);
            myInEntityList.add(entity);
        }
        if (user.getMyMovie() != null && !TextUtils.isEmpty(user.getMyMovie())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_4);
            entity.setContextName(user.getMyMovie());
            entity.setBackground_type(4);
            myInEntityList.add(entity);
        }
        if (user.getMyBookAndComic() != null && !TextUtils.isEmpty(user.getMyBookAndComic())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_5);
            entity.setContextName(user.getMyBookAndComic());
            entity.setBackground_type(5);
            myInEntityList.add(entity);
        }
        if (user.getMySports() != null && !TextUtils.isEmpty(user.getMySports())) {
            MyInEntity entity = new MyInEntity();
            entity.setResoure(R.drawable.me_intrest_6);
            entity.setContextName(user.getMySports());
            entity.setBackground_type(6);
            myInEntityList.add(entity);
        }

        if (myInEntityList != null && myInEntityList.size() > 0) {
            my_intresting.setVisibility(View.GONE);
            setMyInAdapter(myInEntityList);
        } else {
            my_intresting.setVisibility(View.GONE);
        }


        if (user.getUserQuestions() != null && user.getUserQuestions().size() > 0) {
            line_question.setVisibility(View.VISIBLE);
            rv_question.setVisibility(View.VISIBLE);
            tv_myquestion.setVisibility(View.VISIBLE);
            List<QuestEntity> entities = new ArrayList<>();
            for (int i = 0; i < user.getUserQuestions().size(); i++) {
                QuestEntity entity = new QuestEntity();
                entity.setQuestion(user.getUserQuestions().get(i).getQuestion());
                entity.setAnswer(user.getUserQuestions().get(i).getUserQuestionAnswer().getAnswer());
                entities.add(entity);
            }
            setAdapter(entities);
        } else {
            rv_question.setVisibility(View.GONE);
            line_question.setVisibility(View.GONE);
            tv_myquestion.setVisibility(View.GONE);
        }

    }

    public void setAdapter(List<QuestEntity> entities) {
        if (questionAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonInfoActivity.this);
            rv_question.setLayoutManager(linearLayoutManager);
            questionAdapter = new QuestionInfoAdapter(PersonInfoActivity.this, entities);
            rv_question.setAdapter(questionAdapter);

        } else {
            questionAdapter.notifyDataSetChanged();
        }
    }

    public void setMyInAdapter(List<MyInEntity> list) {
        if (adapter == null) {
            LinearLayoutManager linearLayout = new LinearLayoutManager(PersonInfoActivity.this);
            rv_myin.setLayoutManager(linearLayout);
            adapter = new MyInAdapter(list, PersonInfoActivity.this);
            rv_myin.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    private void initSkill(final List<String> mTagData) {
        flowLayoutTags.removeAllViews();
        if (mTagData != null) {
            for (int i = 0; i < mTagData.size(); i++) {
                String signName = mTagData.get(i);
                SkillTextView skillTextView = getSkillView();
                skillTextView.setText(signName);
                flowLayoutTags.addView(skillTextView);
            }

        }
    }

    /**
     * 获取标签tagview
     *
     * @return
     */
    private SkillTextView getSkillView() {
        SkillTextView skillTextView = new SkillTextView(PersonInfoActivity.this);
        skillTextView.setGravity(Gravity.CENTER);
        skillTextView.setTextColor(PersonInfoActivity.this.getResources().getColor(R.color.white));
        skillTextView.setTextSize(10);
        skillTextView.setEnabled(false);
        skillTextView.setChecked(false);
        skillTextView.setBackground(PersonInfoActivity.this.getResources().getDrawable(R.drawable.appdes_shape_f23f8f));
        int leftRightPadding = ScreenUtils.dip2px(8, mContext);
        int topBottomPadding = ScreenUtils.dip2px(4, mContext);
        skillTextView.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        return skillTextView;
    }
}
