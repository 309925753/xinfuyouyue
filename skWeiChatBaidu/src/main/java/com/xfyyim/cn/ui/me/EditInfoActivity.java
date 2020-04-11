package com.xfyyim.cn.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.j256.ormlite.stmt.query.In;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.customer.FlowLayout;
import com.xfyyim.cn.customer.SkillTextView;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.me_new.EditActivity;
import com.xfyyim.cn.util.ToastUtil;
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

public class EditInfoActivity extends BaseActivity implements View.OnClickListener {
    Unbinder unbinder;


    @BindView(R.id.flowLayout_tags)
    FlowLayout flowLayoutTags;


    @BindView(R.id.flowLayout_tags1)
    FlowLayout flowLayoutTags1;


    @BindView(R.id.flowLayout_tags2)
    FlowLayout flowLayoutTags2;


    @BindView(R.id.flowLayout_tags3)
    FlowLayout flowLayoutTags3;
    @BindView(R.id.flowLayout_tags4)
    FlowLayout flowLayoutTags4;
    @BindView(R.id.flowLayout_tags5)
    FlowLayout flowLayoutTags5;
    @BindView(R.id.flowLayout_tags6)
    FlowLayout flowLayoutTags6;


    @BindView(R.id.tv_add_sign)
    TextView tv_add_sign;


    @BindView(R.id.rv_question)
    RecyclerView rv_question;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;


    @BindView(R.id.nick_name)
    TextView nick_name;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_xingzuo)
    TextView tv_xingzuo;
    @BindView(R.id.tv_vip)
    TextView tv_vip;
    @BindView(R.id.person_sign)
    TextView person_sign;
    @BindView(R.id.tv_position)
    TextView tv_position;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_company)
    TextView tv_company;


    @BindView(R.id.tv_hometown)
    TextView tv_hometown;
    @BindView(R.id.tv_place)
    TextView tv_place;
    @BindView(R.id.img_sex)
    ImageView img_sex;
    @BindView(R.id.ll_sex_bg)
    LinearLayout ll_sex_bg;


    @BindView(R.id.tv_add_support)
    TextView tv_add_support;
    @BindView(R.id.tv_add_travel)
    TextView tv_add_travel;
    @BindView(R.id.tv_add_book)
    TextView tv_add_book;
    @BindView(R.id.tv_add_movie)
    TextView tv_add_movie;
    @BindView(R.id.tv_add_food)
    TextView tv_add_food;
    @BindView(R.id.tv_add_music)
    TextView tv_add_music;


    @BindView(R.id.ll_edit_personinfo)
    LinearLayout ll_edit_personinfo;


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        unbinder = ButterKnife.bind(this);
        initActionBar();
        initView();
    }


    public void initView() {
        ll_edit_personinfo.setOnClickListener(this);
        person_sign.setOnClickListener(this);
        tv_hometown.setOnClickListener(this);
        tv_place.setOnClickListener(this);
        tv_position.setOnClickListener(this);

    }

    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);
        tv_title_center.setText("个人信息");
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditInfoActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());

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

        nick_name.setText(user.getNickName());


        if (user.getAge() == 0) {
            tv_age.setVisibility(View.GONE);
        } else {
            tv_age.setVisibility(View.VISIBLE);
            tv_age.setText(String.valueOf(user.getAge()));
        }

        if (user.getSex() == 1) {
            img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_man));
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_zise));
        } else {
            img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_nv));
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_pink));
        }

//        //相册
//        List<String> imgesUrl = new ArrayList<>();
//        if (user.getPhoto() != null && user.getPhoto().size() > 0) {
//            for (Photo photo : user.getPhoto()) {
//                imgesUrl.add(photo.getoUrl());
//            }
////            banner.setVisibility(View.VISIBLE);
//            rl_blum.setVisibility(View.VISIBLE);
//
//
//            int index = user.getPhoto().size() > 3 ? 3 : user.getPhoto().size();
//            for (int i = 0; i < index; i++) {
//                ImageView imageView = new ImageView(this);
//                GlideImageUtils.setImageView(this, user.getPhoto().get(index).getoUrl(), imageView);
//                LinearLayout.LayoutParams ls = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                ll_my_blum.addView(imageView, ls);
//
//            }
//        } else {
//            String  headUrl= AvatarHelper.getAvatarUrl(friendId,false);
//            imgesUrl.add(headUrl);
//
////            banner.setVisibility(View.GONE);
//            rl_blum.setVisibility(View.GONE);
//        }
//
//        banner.setData(imgesUrl, null);
//        banner.setmAdapter(new XBanner.XBannerAdapter() {
//            @Override
//            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                GlideImageUtils.setImageView(PersonInfoActivity.this, imgesUrl.get(position), (ImageView) view);
//            }
//        });


        //签名
        if (user.getDescription() == null || TextUtils.isEmpty(user.getDescription())) {
            person_sign.setText("这个人很懒，什么都没留下");
            person_sign.setTextColor(getResources().getColor(R.color.text_black_999));

        } else {
            person_sign.setText(user.getDescription());
            person_sign.setTextColor(getResources().getColor(R.color.text_black_333));
        }


        if (user.getMyIndustry() != null && !TextUtils.isEmpty(user.getMyIndustry())) {
            tv_position.setText(user.getMyIndustry());
        }

        if (user.getMyCompany() != null && !TextUtils.isEmpty(user.getMyCompany())) {
            tv_company.setText(user.getMyCompany());
        }

        if (user.getMyWork() != null && !TextUtils.isEmpty(user.getMyWork())) {
            tv_work.setText(user.getMyWork());
        }

        if (user.getMyHometown() != null && !TextUtils.isEmpty(user.getMyHometown())) {
            tv_hometown.setText(user.getMyHometown());
        }
        if (user.getMyPlayground() != null && !TextUtils.isEmpty(user.getMyPlayground())) {
            tv_place.setText(user.getMyPlayground());
        }


        //我的标签

        if (user.getMyCharacter() != null && !TextUtils.isEmpty(user.getMyCharacter())) {


            List<String> list = new ArrayList<>();
            if (user.getMyCharacter().contains(",")) {
                String sign[] = user.getMyCharacter().split(",");
                for (String si : sign) {
                    list.add(si);
                }
            } else {
                list.add(user.getMyCharacter());
            }
            initSkill(list, flowLayoutTags);

        }


        //我的兴趣

        if (user.getMySports() != null && !TextUtils.isEmpty(user.getMySports())) {
        }

        if (user.getMyMusic() != null && !TextUtils.isEmpty(user.getMyMusic())) {
        }
        if (user.getMyFood() != null && !TextUtils.isEmpty(user.getMyFood())) {
        }
        if (user.getMyMovie() != null && !TextUtils.isEmpty(user.getMyMovie())) {
        }
        if (user.getMyBookAndComic() != null && !TextUtils.isEmpty(user.getMyBookAndComic())) {
        }
        if (user.getMyTravel() != null && !TextUtils.isEmpty(user.getMyTravel())) {
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_title_left:
                finish();
                break;
            case R.id.person_sign:
                String desc = person_sign.getText().toString();
                Intent intent = new Intent(EditInfoActivity.this, DescriptionActivity.class);
                startActivity(intent);

                break;


                case R.id.tv_position:
                String context = tv_position.getText().toString();
               starToActivity("添加行业信息","industry",context);
                break;
  case R.id.tv_hometown:
                String home = tv_hometown.getText().toString();
               starToActivity("添加行业信息","hometown",home);
                break;
            case R.id.tv_place:
                String place = tv_hometown.getText().toString();
                starToActivity("添加行业信息","playground",place);
                break;


            case R.id.ll_edit_personinfo:
                Intent intent1 = new Intent(EditInfoActivity.this, PersonEditInfoActivity.class);
                intent1.putExtra("Age", user.getSex());
                intent1.putExtra("NickName", user.getNickName());
                intent1.putExtra("Birthday", user.getBirthday());
                startActivity(intent1);
                break;
        }
    }

    public void starToActivity(String title, String type, String context) {
        Intent intent = new Intent(EditInfoActivity.this, EditActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("context", context);
        startActivity(intent);


    }

    private void initSkill(final List<String> mTagData, FlowLayout flowLayout) {

        if (mTagData != null) {
            for (int i = 0; i < mTagData.size(); i++) {
                String signName = mTagData.get(i);
                SkillTextView skillTextView = getSkillView();
                skillTextView.setText(signName);
                flowLayout.addView(skillTextView);
            }

        }
    }

    /**
     * 获取标签tagview
     *
     * @return
     */
    private SkillTextView getSkillView() {
        SkillTextView skillTextView = new SkillTextView(EditInfoActivity.this);
        skillTextView.setGravity(Gravity.CENTER);
        skillTextView.setTextColor(EditInfoActivity.this.getResources().getColor(R.color.white));
        skillTextView.setTextSize(10);
        skillTextView.setEnabled(false);
        skillTextView.setChecked(false);
        skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.appdes_shape_f23f8f));
        int leftRightPadding = ScreenUtils.dip2px(8, mContext);
        int topBottomPadding = ScreenUtils.dip2px(4, mContext);
        skillTextView.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        return skillTextView;
    }


}
