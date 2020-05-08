package com.xfyyim.cn.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xfyyim.cn.R;
import com.xfyyim.cn.adapter.QuestionAdapter;
import com.xfyyim.cn.bean.PhotoEntity;
import com.xfyyim.cn.bean.QuestEntity;
import com.xfyyim.cn.bean.UploadFileResult;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.bean.event.EventAvatarUploadSuccess;
import com.xfyyim.cn.customer.FlowLayout;
import com.xfyyim.cn.customer.SkillTextView;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.helper.UploadService;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.dialog.DialogView;
import com.xfyyim.cn.ui.me_new.EditActivity;
import com.xfyyim.cn.ui.me_new.EditListChooseActivity;
import com.xfyyim.cn.ui.me_new.EditListQuestionActivity;
import com.xfyyim.cn.ui.me_new.EditSignChooseActivity;
import com.xfyyim.cn.util.CameraUtil;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.glideUtil.GlideImageUtils;
import com.xfyyim.cn.view.Tip2Dialog;
import com.xfyyim.cn.view.cjt2325.cameralibrary.util.ScreenUtils;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
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

    @BindView(R.id.img_blum_1)
    ImageView img_blum_1;
    @BindView(R.id.img_blum_2)
    ImageView img_blum_2;
    @BindView(R.id.img_blum_3)
    ImageView img_blum_3;
    @BindView(R.id.img_blum_4)
    ImageView img_blum_4;
    @BindView(R.id.img_blum_5)
    ImageView img_blum_5;


    @BindView(R.id.img_position)
    ImageView img_position;

    @BindView(R.id.ll_mySign)
    LinearLayout ll_mySign;
    @BindView(R.id.img_work)
    ImageView img_work;
    @BindView(R.id.img_company)
    ImageView img_company;
    @BindView(R.id.img_hometown)
    ImageView img_hometown;
    @BindView(R.id.img_place)
    ImageView img_place;

    List<ImageView> listImage = new ArrayList<>();
    User user;
    List<QuestEntity> allQuestion = new ArrayList<>();
    QuestionAdapter adapter;


    List<PhotoEntity> photoEntityList = new ArrayList<>();
    HashMap<ImageView, Integer> map = new HashMap<>();
    List<String> listPhoto;
    private String mImageData;
    boolean isHeader = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        unbinder = ButterKnife.bind(this);
        initActionBar();
        initView();

        listImage.add(img_blum_1);
        listImage.add(img_blum_2);
        listImage.add(img_blum_3);
        listImage.add(img_blum_4);
        listImage.add(img_blum_5);
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
        img_blum_1.setOnClickListener(this);
        img_blum_2.setOnClickListener(this);
        img_blum_3.setOnClickListener(this);
        img_blum_4.setOnClickListener(this);
        img_blum_5.setOnClickListener(this);
        img_header.setOnClickListener(this);


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
                starToSinglesListActivity("添加行业信息", "myIndustry", context);
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
                break;
            case R.id.ll_add_book:
                starToListActivity("添加标签", "myBookAndComic", user.getMyBookAndComic());

                break;
            case R.id.ll_add_travel:
                starToListActivity("添加标签", "mySports", user.getMySports());
                break;
            case R.id.img_blum_1:
                if (map.get(img_blum_1) == 2) {
                    showTip(photoEntityList.get(0).getId());
                } else {
                    selectPhoto(false);
                }
                break;
            case R.id.img_blum_2:
                if (map.get(img_blum_2) == 2) {
                    showTip(photoEntityList.get(1).getId());
                } else {
                    selectPhoto(false);
                }
                break;
            case R.id.img_blum_3:
                if (map.get(img_blum_3) == 2) {

                    showTip(photoEntityList.get(2).getId());
                } else {
                    selectPhoto(false);
                }
                break;
            case R.id.img_blum_4:
                if (map.get(img_blum_4) == 2) {
                    showTip(photoEntityList.get(3).getId());
                } else {
                    selectPhoto(false);
                }
                break;
            case R.id.img_blum_5:
                if (map.get(img_blum_5) == 2) {
                    showTip(photoEntityList.get(4).getId());
                } else {
                    selectPhoto(false);
                }
                break;

            case R.id.img_header:
                selectPhoto(true);


                break;


            case R.id.ll_edit_personinfo:
                Intent intent2 = new Intent(EditInfoActivity.this, PersonEditInfoActivity.class);
                intent2.putExtra("Sex", user.getSex());
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
        tv_title_center.setText("个人资料修改");

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
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_qing));
        } else {
            img_sex.setImageDrawable(getResources().getDrawable(R.drawable.sex_nv));
            ll_sex_bg.setBackground(getResources().getDrawable(R.drawable.share_sign_pink));
        }

        if (user.getStarSign() != null || !TextUtils.isEmpty(user.getStarSign())) {
            tv_xingzuo.setText(user.getStarSign());
        }


        //签名
        if (user.getDescription() != null || !TextUtils.isEmpty(user.getDescription())) {
            person_sign.setText(user.getDescription());
            person_sign.setTextColor(getResources().getColor(R.color.text_black_333));
        }


        if (user.getMyIndustry() != null && !TextUtils.isEmpty(user.getMyIndustry())) {
            tv_position.setText(user.getMyIndustry());
            img_position.setVisibility(View.GONE);
        }

        if (user.getMyCompany() != null && !TextUtils.isEmpty(user.getMyCompany())) {
            tv_company.setText(user.getMyCompany());
            img_company.setVisibility(View.GONE);
        }

        if (user.getMyWork() != null && !TextUtils.isEmpty(user.getMyWork())) {
            tv_work.setText(user.getMyWork());
            img_work.setVisibility(View.GONE);
        }

        if (user.getMyHometown() != null && !TextUtils.isEmpty(user.getMyHometown())) {
            tv_hometown.setText(user.getMyHometown());
            img_hometown.setVisibility(View.GONE);
        }
        if (user.getMyFrequentLocations() != null && !TextUtils.isEmpty(user.getMyFrequentLocations())) {
            tv_place.setText(user.getMyFrequentLocations());
            img_place.setVisibility(View.GONE);
        }

        setPhotoBlum();

        //我的标签

        if (user.getMyTag() != null && !TextUtils.isEmpty(user.getMyTag())) {
            showSign(user.getMyTag(), flowLayoutTags, tv_add_sign, 0);
            ll_mySign.setVisibility(View.GONE);
        }


        //我的兴趣


        if (user.getMyTastes() != null && !TextUtils.isEmpty(user.getMyTastes())) {
            showSign(user.getMyTastes(), flowLayoutTags1, tv_add_support, 1);
        }


        if (user.getMyMusic() != null && !TextUtils.isEmpty(user.getMyMusic())) {
            showSign(user.getMyMusic(), flowLayoutTags2, tv_add_music, 2);
        }


        if (user.getMyFood() != null && !TextUtils.isEmpty(user.getMyFood())) {

            showSign(user.getMyFood(), flowLayoutTags3, tv_add_food, 3);
        }
        if (user.getMyMovie() != null && !TextUtils.isEmpty(user.getMyMovie())) {
            showSign(user.getMyMovie(), flowLayoutTags4, tv_add_movie, 4);

        }
        if (user.getMyBookAndComic() != null && !TextUtils.isEmpty(user.getMyBookAndComic())) {
            showSign(user.getMyBookAndComic(), flowLayoutTags5, tv_add_book, 5);
        }

        if (user.getMySports() != null && !TextUtils.isEmpty(user.getMySports())) {
            showSign(user.getMySports(), flowLayoutTags6, tv_add_travel, 6);
        }


        if (user.getUserQuestions() != null && user.getUserQuestions().size() > 0) {
            allQuestion.clear();
            List<QuestEntity> entities = new ArrayList<>();
            for (int i = 0; i < user.getUserQuestions().size(); i++) {
                QuestEntity entity = new QuestEntity();
                entity.setQuestion(user.getUserQuestions().get(i).getQuestion());
                entity.setAnswer(user.getUserQuestions().get(i).getUserQuestionAnswer().getAnswer());
                entities.add(entity);
            }
            allQuestion.addAll(entities);
            setAdapter(allQuestion);
        } else {
            setAdapter(allQuestion);

        }


    }


    public void setPhotoBlum() {
        photoEntityList = new ArrayList<>();
        map.put(img_blum_1, 1);
        map.put(img_blum_2, 1);
        map.put(img_blum_3, 1);
        map.put(img_blum_4, 1);
        map.put(img_blum_5, 1);
        if (user.getMyPhotos() != null && user.getMyPhotos().size() > 0) {


            photoEntityList = user.getMyPhotos();

            int index = photoEntityList.size() > 5 ? 5 : photoEntityList.size();
            for (int i = 0; i < index; i++) {
                map.put(listImage.get(i), 2);
            }
            for (int i = 0; i < 5; i++) {
                if (map.get(listImage.get(i)) == 1) {
                    GlideImageUtils.setImageDrawable(EditInfoActivity.this, R.drawable.blum_bg, listImage.get(i));
                    map.put(listImage.get(i), 1);
                } else {
                    GlideImageUtils.setImageView(EditInfoActivity.this, photoEntityList.get(i).getPhotoUtl(), listImage.get(i));
                    map.put(listImage.get(i), 2);
                }
            }
        } else {
            GlideImageUtils.setImageDrawable(EditInfoActivity.this, R.drawable.blum_bg, img_blum_1);
        }
    }

    List<QuestEntity> removelist;

    public void setAdapter(List<QuestEntity> entities) {
        removelist = entities;
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditInfoActivity.this);
            rv_question.setLayoutManager(linearLayoutManager);
            adapter = new QuestionAdapter(EditInfoActivity.this, entities);
            rv_question.setAdapter(adapter);

            adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    if (position == (entities.size())) {
                        Intent intent1 = new Intent(EditInfoActivity.this, EditListQuestionActivity.class);
                        intent1.putExtra("ListQuestion", (Serializable) removelist);
                        startActivity(intent1);
                    } else {
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

    private void initSkill(final List<String> mTagData, FlowLayout flowLayout, int type) {
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


    public void showSign(String sign, FlowLayout fl, TextView tv, int type) {
        if (sign != null && !TextUtils.isEmpty(sign)) {
            fl.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            List<String> list = new ArrayList<>();
            if (sign.contains(",")) {
                list = StringUtils.getListString(sign);
            } else {
                list.add(sign);
            }
            initSkill(list, fl, type);
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

    private void selectPhoto(boolean flag) {
        isHeader = flag;
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
                    photoUrl = mNewPhotoUri.getPath();


                    if (isHeader) {
                        AvatarHelper.getInstance().displayUrl(mNewPhotoUri.toString(), img_header);
                        // 上传头像
                        uploadAvatar(mCurrentFile);
                    } else {
                        listPhoto = new ArrayList<>();
                        listPhoto.add(photoUrl);
                        new UploadPhoto().execute();
                    }

                } else {
                    ToastUtil.showToast(this, R.string.c_crop_failed);
                }
            }
        }

    }


    public String photoUrl;
    DialogView dialogView;

    public void showSignleDialog(int position) {

        String title = allQuestion.get(position).getQuestion();
        dialogView = new DialogView(this, title, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = ((EditText) v).getText().toString().trim();

                    if (name == null || TextUtils.isEmpty(name)) {
                        allQuestion.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        allQuestion.get(position).setAnswer(name);
                    }

                    String json = JSON.toJSONString(allQuestion);
                    updateValue(json);
                } catch (Exception e) {

                }



                dialogView.getDialog().

            dismiss();
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


    private void uploadPhoto(String photoUtl) {

        DialogHelper.showDefaulteMessageProgressDialog(EditInfoActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditInfoActivity.this).getAccessToken());
        params.put("photoUtl", photoUtl);

        HttpUtils.get().url(coreManager.getConfig().UPLOADPHOTO)
                .params(params)
                .build()
                .execute(new BaseCallback<PhotoEntity>(PhotoEntity.class) {
                    @Override
                    public void onResponse(ObjectResult<PhotoEntity> result) {

                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1) {
                            getUserInfo();

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();

                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }

    private void deletePhoto(String imageId) {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditInfoActivity.this).getAccessToken());
        params.put("id", imageId);

        HttpUtils.get().url(coreManager.getConfig().DELETEPHOTO)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {
                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        if (result.getResultCode() == 1) {
                            getUserInfo();

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                        ToastUtil.showErrorNet(mContext);
                    }
                });
    }

    private class UploadPhoto extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogHelper.showDefaulteMessageProgressDialog(EditInfoActivity.this);
        }

        /**
         * 上传的结果： <br/>
         * return 1 Token过期，请重新登陆 <br/>
         * return 2 上传出错<br/>
         * return 3 上传成功<br/>
         */
        @Override
        protected Integer doInBackground(Void... params) {
            if (!LoginHelper.isTokenValidation()) {
                return 1;
            }
            Map<String, String> mapParams = new HashMap<>();
            mapParams.put("access_token", coreManager.getSelfStatus().accessToken);
            mapParams.put("userId", coreManager.getSelf().getUserId() + "");
            mapParams.put("validTime", "-1");// 文件有效期

            String result = new UploadService().uploadFile(coreManager.getConfig().UPLOAD_URL, mapParams, listPhoto);
            if (TextUtils.isEmpty(result)) {
                return 2;
            }

            UploadFileResult recordResult = JSON.parseObject(result, UploadFileResult.class);
            boolean success = Result.defaultParser(EditInfoActivity.this, recordResult, true);
            if (success) {
                if (recordResult.getSuccess() != recordResult.getTotal()) {
                    // 上传丢失了某些文件
                    return 2;
                }
                if (recordResult.getData() != null) {
                    UploadFileResult.Data data = recordResult.getData();
                    if (data.getImages() != null && data.getImages().size() > 0) {
                        mImageData = JSON.toJSONString(data.getImages(), UploadFileResult.sImagesFilter);
                    } else {
                        return 2;
                    }
                    return 3;
                } else {
                    // 没有文件数据源，失败
                    return 2;
                }
            } else {
                return 2;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                DialogHelper.dismissProgressDialog();
                startActivity(new Intent(EditInfoActivity.this, LoginActivity.class));
            } else if (result == 2) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(EditInfoActivity.this, getString(R.string.upload_failed));
            } else {
                try {

                    JSONArray jsonArray = new JSONArray(mImageData);
                    JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
                    String url = jsonObject.getString("oUrl");

                    uploadPhoto(url);

                } catch (Exception e) {

                }
            }
        }
    }


    private void uploadAvatar(File file) {
        if (!file.exists()) {
            // 文件不存在
            return;
        }
        // 显示正在上传的ProgressDialog
        DialogHelper.showDefaulteMessageProgressDialog(this);
        RequestParams params = new RequestParams();
        final String loginUserId = coreManager.getSelf().getUserId();
        params.put("userId", loginUserId);
        try {
            params.put("file1", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(coreManager.getConfig().AVATAR_UPLOAD_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                DialogHelper.dismissProgressDialog();
                boolean success = false;
                if (arg0 == 200) {
                    Result result = null;
                    try {
                        result = JSON.parseObject(new String(arg2), Result.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result != null && result.getResultCode() == Result.CODE_SUCCESS) {
                        success = true;
                    }
                }

                if (success) {
                    ToastUtil.showToast(EditInfoActivity.this, R.string.upload_avatar_success);
                    AvatarHelper.getInstance().updateAvatar(loginUserId);// 更新时间
                    EventBus.getDefault().post(new EventAvatarUploadSuccess(true));
                } else {
                    ToastUtil.showToast(EditInfoActivity.this, R.string.upload_avatar_failed);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(EditInfoActivity.this, R.string.upload_avatar_failed);
            }
        });
    }

    public void showTip(String ImageID) {
        Tip2Dialog dialog = new Tip2Dialog(EditInfoActivity.this);
        dialog.setmConfirmOnClickListener("是否删除该相片", new Tip2Dialog.ConfirmOnClickListener() {
            @Override
            public void confirm() {
                deletePhoto(ImageID);
            }
        });
        dialog.show();
    }


}
