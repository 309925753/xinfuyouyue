package com.xfyyim.cn.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.j256.ormlite.stmt.query.In;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.QuestionAdapter;
import com.xfyyim.cn.bean.QuestEntity;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.customer.FlowLayout;
import com.xfyyim.cn.customer.SkillTextView;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.dialog.DialogView;
import com.xfyyim.cn.ui.me_new.EditActivity;
import com.xfyyim.cn.ui.me_new.EditListChooseActivity;
import com.xfyyim.cn.ui.me_new.EditListQuestionActivity;
import com.xfyyim.cn.ui.me_new.EditSignChooseActivity;
import com.xfyyim.cn.util.CameraUtil;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.io.File;
import java.io.Serializable;
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
    @BindView(R.id.ll_add_support)
    LinearLayout ll_add_support;
    @BindView(R.id.tv_add_support)
    TextView tv_add_support;

    @BindView(R.id.flowLayout_tags2)
    FlowLayout flowLayoutTags2;
    @BindView(R.id.ll_add_music)
    LinearLayout ll_add_music;
    @BindView(R.id.tv_add_music)
    TextView tv_add_music;


    @BindView(R.id.flowLayout_tags3)
    FlowLayout flowLayoutTags3;
    @BindView(R.id.ll_add_food)
    LinearLayout ll_add_food;
    @BindView(R.id.tv_add_food)
    TextView tv_add_food;


    @BindView(R.id.flowLayout_tags4)
    FlowLayout flowLayoutTags4;
    @BindView(R.id.ll_add_movie)
    LinearLayout ll_add_movie;
    @BindView(R.id.tv_add_movie)
    TextView tv_add_movie;


    @BindView(R.id.flowLayout_tags5)
    FlowLayout flowLayoutTags5;
    @BindView(R.id.ll_add_book)
    LinearLayout ll_add_book;
    @BindView(R.id.tv_add_book)
    TextView tv_add_book;


    @BindView(R.id.flowLayout_tags6)
    FlowLayout flowLayoutTags6;
    @BindView(R.id.ll_add_travel)
    LinearLayout ll_add_travel;

    @BindView(R.id.tv_add_travel)
    TextView tv_add_travel;




    @BindView(R.id.tv_add_sign)
    TextView tv_add_sign;
    @BindView(R.id.ll_add_sign)
    LinearLayout ll_add_sign;


    @BindView(R.id.rv_question)
    RecyclerView rv_question;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;

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





    @BindView(R.id.ll_edit_personinfo)
    LinearLayout ll_edit_personinfo;


    @BindView(R.id.ll_work)
    LinearLayout ll_work;


    @BindView(R.id.ll_head)
    RelativeLayout img_head;
    @BindView(R.id.img_header)
    ImageView img_header;


    User user;
    List<QuestEntity> allQuestion=new ArrayList<>();
    QuestionAdapter adapter;
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
        tv_position.setOnClickListener(this);
        ll_work.setOnClickListener(this);
        tv_company.setOnClickListener(this);
        tv_hometown.setOnClickListener(this);
        tv_place.setOnClickListener(this);
        ll_add_sign.setOnClickListener(this);
        ll_add_support.setOnClickListener(this);
        ll_add_music.setOnClickListener(this);
        ll_add_food.setOnClickListener(this);
        ll_add_movie.setOnClickListener(this);
        ll_add_book.setOnClickListener(this);
        ll_add_travel.setOnClickListener(this);


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
                starToActivity("添加行业信息", "myIndustry", context);
                break;
            case R.id.tv_hometown:
                String home = tv_hometown.getText().toString();
                starToActivity("添加家乡信息", "myHometown", home);
                break;
            case R.id.tv_place:
                String place = tv_place.getText().toString();
                starToActivity("添加常去的地点", "myFrequentLocations", place);
                break;

            case R.id.ll_work:
                String workty = tv_work.getText().toString();
                starToSinglesListActivity("添加工作领域", "myWork", workty);
                break;
            case R.id.tv_company:
                String company = tv_company.getText().toString();
                starToActivity("添加公司信息", "myCompany", company);
                break;

            case R.id.ll_add_sign:
                starToListActivity("添加标签", "myTag", user.getMyTag());
                break;
            case R.id.ll_add_support:

                starToListActivity("添加标签", "myTastes", user.getMyTastes());
                break;
            case R.id.ll_add_music:

                starToListActivity("添加标签", "myMusic", user.getMyMusic());
                break;
            case R.id.ll_add_food:
                starToListActivity("添加标签", "myFood", user.getMyFood());
                break;
            case R.id.ll_add_movie:
                starToListActivity("添加标签", "myMovie", user.getMyMovie());

            case R.id.ll_add_book:
                starToListActivity("添加标签", "myBookAndComic", user.getMyBookAndComic());

                break;
            case R.id.ll_add_travel:
                starToListActivity("添加标签", "mySports", user.getMySports());
                break;


            case R.id.ll_edit_personinfo:
                Intent intent2 = new Intent(EditInfoActivity.this, PersonEditInfoActivity.class);
                intent2.putExtra("Age", user.getSex());
                intent2.putExtra("NickName", user.getNickName());
                intent2.putExtra("Birthday", user.getBirthday());
                startActivity(intent2);
                break;
        }
    }

    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);
        tv_title_center.setText("个人信息");
        tv_title_right.setText("完成");
        tv_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    private void getUserInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(EditInfoActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditInfoActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());

        HttpUtils.get().url(coreManager.getConfig().USER_GET_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<User>(User.class) {
                    @Override
                    public void onResponse(ObjectResult<User> result) {

                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1 && result.getData() != null) {
                            user = result.getData();
                            setUiData(user);

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();

                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }


    public void setUiData(User user) {


        AvatarHelper.getInstance().displayAvatar(user.getUserId(), img_header, false);

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

        if (user.getStarSign() != null || !TextUtils.isEmpty(user.getStarSign())) {
            tv_xingzuo.setText(user.getStarSign());
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
        if (user.getMyFrequentLocations() != null && !TextUtils.isEmpty(user.getMyFrequentLocations())) {
            tv_place.setText(user.getMyFrequentLocations());
        }


        //我的标签

        if (user.getMyTag() != null && !TextUtils.isEmpty(user.getMyTag())) {
            showSign(user.getMyTag(), flowLayoutTags, tv_add_sign,0);
        }


        //我的兴趣


        if (user.getMyTastes() != null && !TextUtils.isEmpty(user.getMyTastes())) {
            showSign(user.getMyTastes(), flowLayoutTags1, tv_add_support,1);
        }


        if (user.getMyMusic() != null && !TextUtils.isEmpty(user.getMyMusic())) {
            showSign(user.getMyMusic(), flowLayoutTags2, tv_add_music,2);
        }


        if (user.getMyFood() != null && !TextUtils.isEmpty(user.getMyFood())) {

            showSign(user.getMyFood(), flowLayoutTags3, tv_add_food,3);
        }
        if (user.getMyMovie() != null && !TextUtils.isEmpty(user.getMyMovie())) {
            showSign(user.getMyMovie(), flowLayoutTags4, tv_add_movie,4);

        }
        if (user.getMyBookAndComic() != null && !TextUtils.isEmpty(user.getMyBookAndComic())) {
            showSign(user.getMyBookAndComic(), flowLayoutTags5, tv_add_book,5);
        }

        if (user.getMySports() != null && !TextUtils.isEmpty(user.getMySports())) {
            showSign(user.getMySports(), flowLayoutTags6, tv_add_travel,6);
        }


        if (user.getUserQuestions()!=null&&user.getUserQuestions().size()>0){
            allQuestion.clear();
                List<QuestEntity> entities=new ArrayList<>();
                for (int i=0;i<user.getUserQuestions().size();i++){
                    QuestEntity entity=new QuestEntity();
                    entity.setQuestion(user.getUserQuestions().get(i).getQuestion());
                    entity.setAnswer(user.getUserQuestions().get(i).getUserQuestionAnswer().getAnswer());
                    entities.add(entity);
                }
                 allQuestion.addAll(entities);
                setAdapter(allQuestion);
        }




    }

    public void setAdapter(List<QuestEntity> entities) {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditInfoActivity.this);
            rv_question.setLayoutManager(linearLayoutManager);
            adapter = new QuestionAdapter(EditInfoActivity.this, entities);
            rv_question.setAdapter(adapter);

         adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {

                 if(position==(entities.size())){
                     Intent intent1=new Intent(EditInfoActivity.this, EditListQuestionActivity.class);
                     intent1.putExtra("ListQuestion",(Serializable) entities);
                     startActivity(intent1);
                 }else{
                     showSignleDialog(position);

                 }
             }
         });
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


    public void starToActivity(String title, String type, String context) {
        Intent intent = new Intent(EditInfoActivity.this, EditActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("context", context);
        startActivity(intent);


    }

    public void starToSinglesListActivity(String title, String type, String context) {
        Intent intent = new Intent(EditInfoActivity.this, EditSignChooseActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("context", context);
        startActivity(intent);


    }


    public void starToListActivity(String title, String type, String context) {
        Intent intent = new Intent(EditInfoActivity.this, EditListChooseActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("context", context);
        startActivity(intent);


    }

    private void initSkill(final List<String> mTagData, FlowLayout flowLayout,int type) {
        flowLayout.removeAllViews();
        if (mTagData != null) {
            for (int i = 0; i < mTagData.size(); i++) {
                String signName = mTagData.get(i);
                SkillTextView skillTextView = getSkillView(type);
                skillTextView.setText(signName);
                flowLayout.addView(skillTextView);
            }

        }
    }


    public void showSign(String sign, FlowLayout fl, TextView tv,int type) {
        if (sign != null && !TextUtils.isEmpty(sign)) {
            fl.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            List<String> list = new ArrayList<>();
            if (sign.contains(",")) {
                list = StringUtils.getListString(sign);
            } else {
                list.add(sign);
            }
            initSkill(list, fl,type);
        } else {
            fl.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取标签tagview
     *
     * @return
     */
    private SkillTextView getSkillView(int type) {
        SkillTextView skillTextView = new SkillTextView(EditInfoActivity.this);
        skillTextView.setGravity(Gravity.CENTER);
        skillTextView.setTextColor(EditInfoActivity.this.getResources().getColor(R.color.white));
        skillTextView.setTextSize(10);
        skillTextView.setEnabled(false);
        skillTextView.setChecked(false);

        switch (type) {
            case 0:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.appdes_shape_f23f8f));
                break;
                case 1:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_support));
                break;
            case 2:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_music));
                break;
            case 3:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_food));
                break;
            case 4:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_movie));
                break;
            case 5:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_book));
                break;
            case 6:
                skillTextView.setBackground(EditInfoActivity.this.getResources().getDrawable(R.drawable.share_sign_travel));
                break;
        }
        int leftRightPadding = ScreenUtils.dip2px(8, mContext);
        int topBottomPadding = ScreenUtils.dip2px(4, mContext);
        skillTextView.setPadding(leftRightPadding, topBottomPadding, leftRightPadding, topBottomPadding);
        return skillTextView;
    }

    private static final int REQUEST_CODE_PICK_CROP_PHOTO = 2;
    private static final int REQUEST_CODE_CROP_PHOTO = 3;
    private File mCurrentFile;
    private Uri mNewPhotoUri;

    private void selectPhoto() {
        CameraUtil.pickImageSimple(this, REQUEST_CODE_PICK_CROP_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CROP_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri o = Uri.fromFile(new File(CameraUtil.parsePickImageResult(data)));
                    mNewPhotoUri = CameraUtil.getOutputMediaFileUri(this, CameraUtil.MEDIA_TYPE_IMAGE);
                    mCurrentFile = new File(mNewPhotoUri.getPath());
                    CameraUtil.cropImage(this, o, mNewPhotoUri, REQUEST_CODE_CROP_PHOTO, 1, 1, 300, 300);
                } else {
                    ToastUtil.showToast(this, R.string.c_photo_album_failed);
                }
            }
        } else if (requestCode == REQUEST_CODE_CROP_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (mNewPhotoUri != null) {
                    mCurrentFile = new File(mNewPhotoUri.getPath());
//                   AvatarHelper.getInstance().displayUrl(mNewPhotoUri.toString(), mAvatarImg);
//                   // 上传头像
//                   uploadAvatar(mCurrentFile);
                } else {
                    ToastUtil.showToast(this, R.string.c_crop_failed);
                }
            }
        }

    }

    DialogView dialogView;
    public void showSignleDialog(int position) {

        String title=allQuestion.get(position).getQuestion();
        String text=allQuestion.get(position).getAnswer();
        dialogView = new DialogView(this, title, text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) v).getText().toString().trim();
                if (name == null || TextUtils.isEmpty(name)) {
                    ToastUtil.showToast(EditInfoActivity.this, "问题答案不能为空");
                    return;
                }
                try {


                 allQuestion.get(position).setAnswer(name);

                    String json = JSON.toJSONString(allQuestion);
                    updateValue(json);


                } catch (Exception e) {
                }

            }
        });
        dialogView.show();

    }

    private void updateValue(String json) {
        DialogHelper.showDefaulteMessageProgressDialog(EditInfoActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditInfoActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("userQuestions", json);


        HttpUtils.get().url(coreManager.getConfig().UPDATE_USERINFO)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(mContext, result)) {
                         getUserInfo();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditInfoActivity.this);
                    }
                });
    }
}
