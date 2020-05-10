package com.xfyyim.cn.ui.circle.range;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.Reporter;
import com.xfyyim.cn.adapter.SendRedTopicAdapter;
import com.xfyyim.cn.adapter.SendTopicAdapter;
import com.xfyyim.cn.bean.Area;
import com.xfyyim.cn.bean.UploadFileResult;
import com.xfyyim.cn.bean.VideoFile;
import com.xfyyim.cn.bean.circle.TopicEntity;
import com.xfyyim.cn.bean.event.MessageVideoFile;
import com.xfyyim.cn.bean.event.SendTextSucc;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.ChoosePop;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.ImageLoadHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.helper.UploadService;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.circle.util.SendTextFilter;
import com.xfyyim.cn.ui.map.MapAddressListActivity;
import com.xfyyim.cn.ui.me.LocalVideoActivity;
import com.xfyyim.cn.ui.tool.MultiImagePreviewActivity;
import com.xfyyim.cn.util.BitmapUtil;
import com.xfyyim.cn.util.CameraUtil;
import com.xfyyim.cn.util.DeviceInfoUtil;
import com.xfyyim.cn.util.RecorderUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.util.UploadCacheUtils;
import com.xfyyim.cn.util.VideoCompressUtil;
import com.xfyyim.cn.video.EasyCameraActivity;
import com.xfyyim.cn.video.MessageEventGpu;
import com.xfyyim.cn.video.VideoRecorderActivity;
import com.xfyyim.cn.view.SquareCenterFrameLayout;
import com.xfyyim.cn.view.photopicker.PhotoPickerActivity;
import com.xfyyim.cn.view.photopicker.SelectModel;
import com.xfyyim.cn.view.photopicker.intent.PhotoPickerIntent;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Jni.VideoUitls;
import VideoHandle.OnEditorListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class SendTextPicActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CAPTURE_PHOTO = 1;  // 拍照
    private static final int REQUEST_CODE_PICK_PHOTO = 2;     // 图库
    private static final int REQUEST_CODE_CAPTURE_VIDEO = 4;     // 拍视频
    private static final int REQUEST_CODE_PICK_VIDEO = 5;     // 选择视频
    private static final int REQUEST_CODE_SELECT_LOCATE = 3;  // 位置


    @BindView(R.id.rl_main)
    RelativeLayout rl_main;
    Unbinder unbinder;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.tv_Location)
    TextView tv_Location;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.rcv_img)
    RecyclerView rcvImg;
    @BindView(R.id.rv_topic)
    RecyclerView rv_topic;

    @BindView(R.id.img_blum)
    LinearLayout img_blum;

    @BindView(R.id.img_camera)
    LinearLayout img_camera;

    @BindView(R.id.img_locotion)
    ImageView img_locotion;

    @BindView(R.id.rv_add_topic)
    RecyclerView rv_add_topic;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;

    @BindView(R.id.et_content)
    EditText editText;

    private ChoosePop mChoosePop;
    private ChoosePop mChoosePop1;
    private int mSelectedId;
    @BindView(R.id.float_layout)
    FrameLayout mFloatLayout;
    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.img_delete_video)
    ImageView img_delete_video;
    @BindView(R.id.icon_image_view)
    ImageView mIconImageView;

    @BindView(R.id.tv_topic_name)
            TextView tv_topic_name;

    SendTopicAdapter adapter;
    SendRedTopicAdapter adapter1;


    PostArticleImgAdapter postArticleImgAdapter;
    private ArrayList<String> mPhotoList;
    // 拍照和图库，获得图片的Uri
    private Uri mNewPhotoUri;

    private String address;

    private ItemTouchHelper itemTouchHelper;

    private String mImageData;
    private String mVideoData;

    List<TopicEntity.DataBean.ListBean> selectTopicList = new ArrayList<>();
    public int isSeLectPic = 0;
    String topicType = "0";
    String topicId="";
    String topicName="";


    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_show);
        unbinder = ButterKnife.bind(this);

        topicType = getIntent().getStringExtra("topicType");
        topicId = getIntent().getStringExtra("topicId");
        topicName = getIntent().getStringExtra("topicName");

        initActionBar();
        mPhotoList = new ArrayList<>();
        postArticleImgAdapter = new PostArticleImgAdapter(this, mPhotoList);
        initRcv();
        EventBus.getDefault().register(this);


        // 解决EditText与ScrollView嵌套的问题
        editText.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        // 限制了EditText输入最大长度为300，到达限制时提示
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().trim().length() >= 300) {
                    Toast.makeText(mContext, getString(R.string.tip_edit_max_input_length, 10000), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(topicType.equals("1")){
            tv_topic_name.setVisibility(View.VISIBLE);
            rv_add_topic.setVisibility(View.GONE);

            if (topicName.contains("#")){
                tv_topic_name.setText(topicName);
            }else{
                tv_topic_name.setText("#"+topicName);
            }
        }else{
            getTopicList();
            tv_topic_name.setVisibility(View.GONE);
            rv_add_topic.setVisibility(View.VISIBLE);
        }

    }


    private void initActionBar() {
        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);

        tv_title_center.setText("写动态");
        tv_title_right.setText("发布");
        tv_title_right.setTextColor(getResources().getColor(R.color.white));
        tv_title_right.setOnClickListener(this);
        img_blum.setOnClickListener(this);
        img_camera.setOnClickListener(this);
        img_locotion.setOnClickListener(this);
        img_delete_video.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_delete_video:
                mVideoFilePath = "";
                mImageView.setImageBitmap(null);
                img_delete_video.setVisibility(View.GONE);
                break;
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_title_right:

                if (mPhotoList.size() <= 0 && TextUtils.isEmpty(editText.getText().toString()) && TextUtils.isEmpty(mVideoFilePath)) {
                    ToastUtil.showToast(this, "发布的内容不能为空!");
                    return;
                }
                if (mVideoFilePath != null && !TextUtils.isEmpty(mVideoFilePath)) {
                    compress(new File(mVideoFilePath));
                } else {
                    if (mPhotoList.size() <= 0) {
                        // 发布文字
                        sendShuoshuo();
                    } else {
                        // 发布图片+文字
                        new UploadPhoto().execute();
                    }

                }


                break;

            case R.id.img_blum:
                if (mPhotoList.size() <= 0 && TextUtils.isEmpty(mVideoFilePath)) {
                    isSeLectPic = 0;
                } else if (mPhotoList.size() <= 0) {
                    isSeLectPic = 2;
                } else if (TextUtils.isEmpty(mVideoFilePath)) {
                    isSeLectPic = 1;
                }
                showDialog();
                break;
            case R.id.img_camera:
                if (mPhotoList.size() <= 0 && TextUtils.isEmpty(mVideoFilePath)) {
                    isSeLectPic = 0;
                } else if (mPhotoList.size() <= 0) {
                    isSeLectPic = 2;
                } else if (TextUtils.isEmpty(mVideoFilePath)) {
                    isSeLectPic = 1;
                }
                showDialog1();
                break;
            case R.id.img_locotion:
                // 所在位置
                Intent intent1 = new Intent(this, MapAddressListActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT_LOCATE);
                break;
        }
    }

    private void selectVideo() {
        Intent intent = new Intent(SendTextPicActivity.this, LocalVideoActivity.class);
        intent.putExtra(AppConstant.EXTRA_ACTION, AppConstant.ACTION_SELECT);
        // 这里选择视频不支持多选，
        intent.putExtra(AppConstant.EXTRA_MULTI_SELECT, false);
        if (mSelectedId != 0) {
            intent.putExtra(AppConstant.EXTRA_SELECT_ID, mSelectedId);
        }
        SendTextPicActivity.this.startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
    }

    public void takeVideo() {
        VideoRecorderActivity.start(this, true);


    }

    private void compress(File file) {
        String path = file.getPath();
        DialogHelper.showMessageProgressDialog(this, MyApplication.getContext().getString(R.string.compressed));
        final String out = RecorderUtils.getVideoFileByTime();
        String[] cmds = RecorderUtils.ffmpegComprerssCmd(path, out);
        long duration = VideoUitls.getDuration(path);

        VideoCompressUtil.exec(cmds, duration, new OnEditorListener() {
            public void onSuccess() {
                DialogHelper.dismissProgressDialog();
                File outFile = new File(out);
                runOnUiThread(() -> {
                    if (outFile.exists()) {
                        sendVideo(outFile);
                    } else {
                        sendVideo(file);
                    }
                });
            }

            public void onFailure() {
                DialogHelper.dismissProgressDialog();
                runOnUiThread(() -> {
                    sendVideo(file);
                });
            }

            public void onProgress(float progress) {

            }
        });
    }

    private void sendVideo(File file) {
        new UploadTask().execute(file.getPath());
    }

    View.OnClickListener choose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.select_video:

                    selectVideo();
                    mChoosePop.dismiss();

                    break;


                case R.id.select_pic:
                    ll_video.setVisibility(View.GONE);
                    rcvImg.setVisibility(View.VISIBLE);
                    selectPhoto();
                    mChoosePop.dismiss();
                    break;


                default:
                    break;
            }
        }
    };

    View.OnClickListener choose1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.select_video:

                    takeVideo();
                    mChoosePop1.dismiss();
                    break;


                case R.id.select_pic:
                    ll_video.setVisibility(View.GONE);
                    rcvImg.setVisibility(View.VISIBLE);
                    takePhoto();
                    mChoosePop1.dismiss();
                    break;


                default:
                    break;
            }
        }
    };

    // 发布一条说说
    public void sendShuoshuo() {
        double latitude = MyApplication.getInstance().getBdLocationHelper().getLatitude();
        double longitude = MyApplication.getInstance().getBdLocationHelper().getLongitude();

        if (TextUtils.isEmpty(editText.getText().toString().trim()) && mPhotoList.size() == 0) {
            DialogHelper.tip(mContext, getString(R.string.leave_select_image_or_edit_text));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(SendTextPicActivity.this).getAccessToken());
        // 消息类型：1=文字消息；2=图文消息；3=语音消息；4=视频消息；
        if (TextUtils.isEmpty(mImageData)) {
            params.put("type", "1");
        } else {
            params.put("type", "2");
        }
        // 消息标记：1：求职消息；2：招聘消息；3：普通消息；
        params.put("flag", "3");
        String topicStr = "";
        String topicSelectId="";

        if (selectTopicList != null && selectTopicList.size() > 0) {
            if (selectTopicList.size() == 1) {
                topicStr = selectTopicList.get(0).getTitle();
                topicSelectId = selectTopicList.get(0).getId();
            } else {
                for (int i = 0; i < selectTopicList.size(); i++) {
                    if (i == selectTopicList.size() - 1) {

                        topicStr = topicStr + selectTopicList.get(i).getTitle();
                        topicSelectId = topicSelectId + selectTopicList.get(i).getId();

                    } else {
                        topicStr = topicStr + selectTopicList.get(i).getTitle() + ",";
                        topicSelectId = topicSelectId + selectTopicList.get(i).getId() + ",";
                    }
                }
            }
            params.put("topicStr", topicStr);
            params.put("parentTopicId", topicSelectId);
            topicType = "1";

        }


        if (topicName!=null&&!TextUtils.isEmpty(topicName)){
            if (topicName.contains("#")){
                topicStr=topicName;
            }else{
                topicStr="#"+topicName;
            }


            params.put("topicStr", topicStr);
        }

        if (topicId!=null&&!TextUtils.isEmpty(topicId)){
            params.put("parentTopicId", topicId);
        }
        params.put("topicType", topicType);
//        // 消息隐私范围：1=公开；2=私密；3=部分选中好友可见；4=不给谁看
//        params.put("visible", String.valueOf(visible));
//        if (visible == 3) {
//            // 谁可以看
//            params.put("userLook", lookPeople);
//        } else if (visible == 4) {
//            // 不给谁看
//            params.put("userNotLook", lookPeople);
//        }
//        // 提醒谁看
//        if (!TextUtils.isEmpty(atlookPeople)) {
//            params.put("userRemindLook", atlookPeople);
//        }

        // 消息内容
        params.put("text", SendTextFilter.filter(editText.getText().toString()));
        if (!TextUtils.isEmpty(mImageData)) {
            // 图片
            params.put("images", mImageData);
        }

        params.put("isAllowComment", String.valueOf(1));
        /**
         * 所在位置
         */
        if (!TextUtils.isEmpty(address)) {
            // 纬度
            params.put("latitude", String.valueOf(latitude));
            // 经度
            params.put("longitude", String.valueOf(longitude));
            // 位置
            params.put("location", address);
        }
        // 纬度
        params.put("latitude", String.valueOf(latitude));
        // 经度
        params.put("longitude", String.valueOf(longitude));
        // 位置


        // 必传，之前删除该字段，发布说说，服务器返回接口内部异常
        Area area = Area.getDefaultCity();
        if (area != null) {
            // 城市id
            params.put("cityId", String.valueOf(area.getId()));
        } else {
            params.put("cityId", "0");
        }

        /**
         * 附加信息
         */
        // 手机型号
        params.put("model", DeviceInfoUtil.getModel());
        // 手机操作系统版本号
        params.put("osVersion", DeviceInfoUtil.getOsVersion());
        if (!TextUtils.isEmpty(DeviceInfoUtil.getDeviceId(mContext))) {
            // 设备序列号
            params.put("serialNumber", DeviceInfoUtil.getDeviceId(mContext));
        }

        DialogHelper.showDefaulteMessageProgressDialog(this);

        HttpUtils.post().url(coreManager.getConfig().MSG_ADD_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {

                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        DialogHelper.dismissProgressDialog();
                        if (com.xuan.xuanhttplibrary.okhttp.result.Result.checkSuccess(mContext, result)) {
                            Intent intent = new Intent();
                            intent.putExtra(AppConstant.EXTRA_MSG_ID, result.getData());
                            setResult(RESULT_OK, intent);
                            EventBus.getDefault().post(new SendTextSucc());

                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(SendTextPicActivity.this);
                    }
                });
    }

    //发表图文
    private class UploadPhoto extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogHelper.showDefaulteMessageProgressDialog(SendTextPicActivity.this);
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

            String result = new UploadService().uploadFile(coreManager.getConfig().UPLOAD_URL, mapParams, mPhotoList);
            if (TextUtils.isEmpty(result)) {
                return 2;
            }

            UploadFileResult recordResult = JSON.parseObject(result, UploadFileResult.class);
            boolean success = Result.defaultParser(SendTextPicActivity.this, recordResult, true);
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
                startActivity(new Intent(SendTextPicActivity.this, LoginActivity.class));
            } else if (result == 2) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(SendTextPicActivity.this, getString(R.string.upload_failed));
            } else {
                sendShuoshuo();
            }
        }
    }


    private void initRcv() {
        rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rcvImg.setAdapter(postArticleImgAdapter);

    }


    /**
     * 相册
     * 可以多选的图片选择器
     */
    private void selectPhoto() {
        ArrayList<String> imagePaths = new ArrayList<>();
        PhotoPickerIntent intent = new PhotoPickerIntent(SendTextPicActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        // 是否显示拍照， 默认false
        intent.setShowCarema(false);
        // 最多选择照片数量，默认为9
        intent.setMaxTotal(9 - mPhotoList.size());
        // 已选中的照片地址， 用于回显选中状态
        intent.setSelectedPaths(imagePaths);
        // intent.setImageConfig(config);
        // 是否加载视频，默认true
        intent.setLoadVideo(false);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(final MessageEventGpu message) {
        photograph(new File(message.event));
    }


    private void showPictureActionDialog(final int position) {
        String[] items = new String[]{getString(R.string.look_over), getString(R.string.delete)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(getString(R.string.image))
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // 查看
                            Intent intent = new Intent(SendTextPicActivity.this, MultiImagePreviewActivity.class);
                            intent.putExtra(AppConstant.EXTRA_IMAGES, mPhotoList);
                            intent.putExtra(AppConstant.EXTRA_POSITION, position);
                            intent.putExtra(AppConstant.EXTRA_CHANGE_SELECTED, false);
                            startActivity(intent);
                        } else {
                            // 删除
                            deletePhoto(position);
                        }
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void deletePhoto(final int position) {
        mPhotoList.remove(position);
        postArticleImgAdapter.notifyDataSetChanged();
    }

    // 拍照
    private void takePhoto() {
        Intent intent = new Intent(this, EasyCameraActivity.class);
        startActivity(intent);
    }

    private String mVideoFilePath;
    private String mThumbPath;
    private Bitmap mThumbBmp;
    private long mTimeLen;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE_PHOTO) {
            // 拍照返回 Todo 已更换拍照方式
            if (resultCode == Activity.RESULT_OK) {
                if (mNewPhotoUri != null) {
                    photograph(new File(mNewPhotoUri.getPath()));
                } else {
                    ToastUtil.showToast(this, R.string.c_take_picture_failed);
                }
            }
        } else if (requestCode == REQUEST_CODE_PICK_PHOTO) {
            // 选择图片返回
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    boolean isOriginal = data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
                    album(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT), isOriginal);
                } else {
                    ToastUtil.showToast(this, R.string.c_photo_album_failed);
                }
            }
        } else if (requestCode == REQUEST_CODE_PICK_VIDEO) {
            // 选择视频的返回
            if (data == null) {
                return;
            }

            ll_video.setVisibility(View.VISIBLE);
            rcvImg.setVisibility(View.GONE);
            String json = data.getStringExtra(AppConstant.EXTRA_VIDEO_LIST);
            List<VideoFile> fileList = JSON.parseArray(json, VideoFile.class);
            if (fileList == null || fileList.size() == 0) {
                // 不可到达，列表里有做判断，
                Reporter.unreachable();
                return;
            }
            VideoFile videoFile = fileList.get(0);

            String filePath = videoFile.getFilePath();
            if (TextUtils.isEmpty(filePath)) {
                ToastUtil.showToast(this, R.string.select_failed);
                return;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                ToastUtil.showToast(this, R.string.select_failed);
                return;
            }
            // 返回成功，隐藏录制图标
            img_delete_video.setVisibility(View.VISIBLE);

            mVideoFilePath = filePath;
            mThumbBmp = AvatarHelper.getInstance().displayVideoThumb(filePath, mImageView);
            mTimeLen = videoFile.getFileLength();
            mSelectedId = videoFile.get_id();
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SELECT_LOCATE) {
            // 选择位置返回
            latitude = data.getDoubleExtra(AppConstant.EXTRA_LATITUDE, 0);
            longitude = data.getDoubleExtra(AppConstant.EXTRA_LONGITUDE, 0);
            address = data.getStringExtra(AppConstant.EXTRA_ADDRESS);
            if (latitude != 0 && longitude != 0 && !TextUtils.isEmpty(address)) {
                Log.e("zq", "纬度:" + latitude + "   经度：" + longitude + "   位置：" + address);
                tv_Location.setText(address);
            } else {
                ToastUtil.showToast(mContext, getString(R.string.loc_startlocnotice));
            }
        }


    }


    // 单张图片压缩 拍照
    private void photograph(final File file) {
        Log.e("zq", "压缩前图片路径:" + file.getPath() + "压缩前图片大小:" + file.length() / 1024 + "KB");
        // 拍照出来的图片Luban一定支持，
        Luban.with(this)
                .load(file)
                .ignoreBy(100)     // 原图小于100kb 不压缩
                // .putGear(2)     // 设定压缩档次，默认三挡
                // .setTargetDir() // 指定压缩后的图片路径
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        Log.e("zq", "开始压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e("zq", "压缩成功，压缩后图片位置:" + file.getPath() + "压缩后图片大小:" + file.length() / 1024 + "KB");
                        mPhotoList.add(file.getPath());
                        postArticleImgAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zq", "压缩失败,原图上传");
                        mPhotoList.add(file.getPath());
                        postArticleImgAdapter.notifyDataSetChanged();
                    }
                }).launch();// 启动压缩
    }

    // 多张图片压缩 相册
    private void album(ArrayList<String> stringArrayListExtra, boolean isOriginal) {
        if (isOriginal) {// 原图发送，不压缩
            Log.e("zq", "原图上传，不压缩，选择原文件路径");
            for (int i = 0; i < stringArrayListExtra.size(); i++) {
                mPhotoList.add(stringArrayListExtra.get(i));
                postArticleImgAdapter.notifyDataSetChanged();
            }
            return;
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < stringArrayListExtra.size(); i++) {
            // Luban只处理特定后缀的图片，不满足的不处理也不走回调，
            // 只能挑出来不压缩，
            // todo luban支持压缩.gif图，但是压缩之后的.gif图用glide加载与转换为gifDrawable都会出问题，所以,gif图不压缩了
            List<String> lubanSupportFormatList = Arrays.asList("jpg", "jpeg", "png", "webp");
            boolean support = false;
            for (int j = 0; j < lubanSupportFormatList.size(); j++) {
                if (stringArrayListExtra.get(i).endsWith(lubanSupportFormatList.get(j))) {
                    support = true;
                    break;
                }
            }
            if (!support) {
                list.add(stringArrayListExtra.get(i));
            }
        }

        if (list.size() > 0) {
            for (String s : list) {// 不压缩的部分，直接发送
                mPhotoList.add(s);
                postArticleImgAdapter.notifyDataSetChanged();

            }
        }

        // 移除掉不压缩的图片
        stringArrayListExtra.removeAll(mPhotoList);

        Luban.with(this)
                .load(stringArrayListExtra)
                .ignoreBy(100)// 原图小于100kb 不压缩
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        mPhotoList.add(file.getPath());
                        postArticleImgAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();// 启动压缩
    }


    class PostArticleImgAdapter extends RecyclerView.Adapter<PostArticleImgAdapter.MyViewHolder> {
        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private List<String> mDatas;

        public PostArticleImgAdapter(Context context, List<String> datas) {
            this.mDatas = datas;
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public PostArticleImgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostArticleImgAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(final PostArticleImgAdapter.MyViewHolder holder, final int position) {
            holder.squareCenterFrameLayout.setVisibility(View.GONE);
            ImageLoadHelper.showImageWithSizeError(
                    mContext,
                    mDatas.get(position),
                    R.drawable.pic_error,
                    150, 150,
                    holder.imageView
            );

            holder.img_delete_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoList.remove(position);
                    notifyDataSetChanged();


                }
            });
        }

        @Override
        public int getItemCount() {
            if (mDatas.size() >= 9) {
                return 9;
            }
            return mDatas.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ImageView img_delete_pic;
            SquareCenterFrameLayout squareCenterFrameLayout;

            MyViewHolder(View itemView) {
                super(itemView);
                squareCenterFrameLayout = itemView.findViewById(R.id.add_sc);
                imageView = itemView.findViewById(R.id.sdv);
                img_delete_pic = itemView.findViewById(R.id.img_delete_pic);
            }
        }
    }


    public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private GestureDetectorCompat mGestureDetector;
        private RecyclerView recyclerView;

        public OnRecyclerItemClickListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new OnRecyclerItemClickListener.ItemTouchHelperGestureListener());
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            if (mGestureDetector.onTouchEvent(e)) {
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView.ViewHolder vh);

        public abstract void onItemLongClick(RecyclerView.ViewHolder vh);

        private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemClick(vh);
                    return true;
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemLongClick(vh);
                }
            }
        }
    }


    interface DragListener {
        /**
         * 用户是否将 item拖动到删除处，根据状态改变颜色
         *
         * @param delete
         */
        void deleteState(boolean delete);

        /**
         * 是否于拖拽状态
         *
         * @param start
         */
        void dragState(boolean start);

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         */
        void clearView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onResume() {
        super.onResume();



    }

    public void setTopicAdapter(List<TopicEntity.DataBean.ListBean> list) {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SendTextPicActivity.this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

            rv_add_topic.setLayoutManager(linearLayoutManager);
            adapter = new SendTopicAdapter(list, SendTextPicActivity.this);
            rv_add_topic.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    TextView tv_address = view.findViewById(R.id.tv_topic_name);
                    if (!list.get(position).isSelectTopic()) {
                        list.get(position).setSelectTopic(true);
                        tv_address.setTextColor(getResources().getColor(R.color.main_color_red1));
                        selectTopicList.add(list.get(position));
                    } else {
                        tv_address.setTextColor(getResources().getColor(R.color.text_black_999));
                        list.get(position).setSelectTopic(false);
                        selectTopicList.remove(list.get(position));
                    }


                    if (selectTopicList.size() > 0) {
                        rv_topic.setVisibility(View.VISIBLE);
                        setTopicAdapter11(selectTopicList);
                    } else {
                        rv_topic.setVisibility(View.GONE);
                    }
                }
            });

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void setTopicAdapter11(List<TopicEntity.DataBean.ListBean> list) {
        if (adapter1 == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SendTextPicActivity.this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

            rv_topic.setLayoutManager(linearLayoutManager);
            adapter1 = new SendRedTopicAdapter(list, SendTextPicActivity.this);
            rv_topic.setAdapter(adapter1);


        } else {
            adapter1.notifyDataSetChanged();
        }
    }


    private class UploadTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogHelper.showDefaulteMessageProgressDialog(SendTextPicActivity.this);
        }

        /**
         * 上传的结果： <br/>
         * return 1 Token过期，请重新登陆 <br/>
         * return 2 视频为空，请重新录制 <br/>
         * return 3 上传出错<br/>
         * return 4 上传成功<br/>
         */
        @Override
        protected Integer doInBackground(String... params) {
            String mVideoFilePath = params[0];
            if (!LoginHelper.isTokenValidation()) {
                return 1;
            }
            if (TextUtils.isEmpty(mVideoFilePath)) {
                return 2;
            }

            // 保存视频缩略图至sd卡
            String imageSavePsth;
            if (TextUtils.isEmpty(mThumbPath)) {
                imageSavePsth = CameraUtil.getOutputMediaFileUri(SendTextPicActivity.this, CameraUtil.MEDIA_TYPE_IMAGE).getPath();
                if (!BitmapUtil.saveBitmapToSDCard(mThumbBmp, imageSavePsth)) {// 保存缩略图失败
                    return 3;
                }
            } else {
                imageSavePsth = mThumbPath;
            }

            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("access_token", coreManager.getSelfStatus().accessToken);
            mapParams.put("userId", coreManager.getSelf().getUserId() + "");
            mapParams.put("validTime", "-1");// 文件有效期

            List<String> dataList = new ArrayList<String>();
            dataList.add(mVideoFilePath);
            if (!TextUtils.isEmpty(imageSavePsth)) {
                dataList.add(imageSavePsth);
            }
            String result = new UploadService().uploadFile(coreManager.getConfig().UPLOAD_URL, mapParams, dataList);
            if (TextUtils.isEmpty(result)) {
                return 3;
            }

            UploadFileResult recordResult = JSON.parseObject(result, UploadFileResult.class);
            boolean success = Result.defaultParser(SendTextPicActivity.this, recordResult, true);
            if (success) {
                if (recordResult.getSuccess() != recordResult.getTotal()) {// 上传丢失了某些文件
                    return 3;
                }
                if (recordResult.getData() != null) {
                    UploadFileResult.Data data = recordResult.getData();
                    if (data.getVideos() != null && data.getVideos().size() > 0) {
                        while (data.getVideos().size() > 1) {// 因为正确情况下只有一个视频，所以要保证只有一个视频
                            data.getVideos().remove(data.getVideos().size() - 1);
                        }
                        data.getVideos().get(0).setSize(new File(mVideoFilePath).length());
                        data.getVideos().get(0).setLength(mTimeLen);
                        // 记录本机上传，用于快速读取，
                        UploadCacheUtils.save(SendTextPicActivity.this, data.getVideos().get(0).getOriginalUrl(), mVideoFilePath);
                        mVideoData = JSON.toJSONString(data.getVideos(), UploadFileResult.sAudioVideosFilter);
                    } else {
                        return 3;
                    }
                    if (data.getImages() != null && data.getImages().size() > 0) {
                        mImageData = JSON.toJSONString(data.getImages(), UploadFileResult.sImagesFilter);
                    }
                    return 4;
                } else {// 没有文件数据源，失败
                    return 3;
                }
            } else {
                return 3;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                DialogHelper.dismissProgressDialog();
                startActivity(new Intent(SendTextPicActivity.this, LoginActivity.class));
            } else if (result == 2) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(SendTextPicActivity.this, getString(R.string.alert_not_have_file));
            } else if (result == 3) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(SendTextPicActivity.this, R.string.upload_failed);
            } else {
                sendAudio();
            }
        }
    }

    public void sendAudio() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        // 消息类型：1=文字消息；2=图文消息；3=语音消息；4=视频消息；
        params.put("type", "4");
        // 消息标记：1：求职消息；2：招聘消息；3：普通消息；
        params.put("flag", "3");

        // 消息内容
        params.put("text", SendTextFilter.filter(editText.getText().toString()));
        params.put("videos", mVideoData);
        if (!TextUtils.isEmpty(mImageData) && !mImageData.equals("{}") && !mImageData.equals("[{}]")) {
            params.put("images", mImageData);
        }


        params.put("isAllowComment", String.valueOf(1));

        // 必传，之前删除该字段，发布说说，服务器返回接口内部异常
        Area area = Area.getDefaultCity();
        if (area != null) {
            params.put("cityId", String.valueOf(area.getId()));// 城市Id
        } else {
            params.put("cityId", "0");
        }

        /**
         * 附加信息
         */
        // 手机型号
        params.put("model", DeviceInfoUtil.getModel());
        // 手机操作系统版本号
        params.put("osVersion", DeviceInfoUtil.getOsVersion());
        if (!TextUtils.isEmpty(DeviceInfoUtil.getDeviceId(mContext))) {
            // 设备序列号
            params.put("serialNumber", DeviceInfoUtil.getDeviceId(mContext));
        }

        DialogHelper.showDefaulteMessageProgressDialog(SendTextPicActivity.this);

        HttpUtils.post().url(coreManager.getConfig().MSG_ADD_URL)
                .params(params)
                .build()
                .execute(new BaseCallback<String>(String.class) {

                    @Override
                    public void onResponse(ObjectResult<String> result) {
                        DialogHelper.dismissProgressDialog();
                        if (com.xuan.xuanhttplibrary.okhttp.result.Result.checkSuccess(mContext, result)) {
                            Intent intent = new Intent();
                            intent.putExtra(AppConstant.EXTRA_MSG_ID, result.getData());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(SendTextPicActivity.this);
                    }
                });
    }

    public void showDialog() {
        mChoosePop = new ChoosePop(SendTextPicActivity.this, choose, isSeLectPic);
        mChoosePop.showAtLocation(rl_main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    public void showDialog1() {
        mChoosePop1 = new ChoosePop(SendTextPicActivity.this, choose1, isSeLectPic);
        mChoosePop1.showAtLocation(rl_main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    public void getTopicList() {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(SendTextPicActivity.this).getAccessToken());

        DialogHelper.showDefaulteMessageProgressDialog(SendTextPicActivity.this);

        HttpUtils.post().url(coreManager.getConfig().FILTER_TOPIC_LIST)
                .params(params)
                .build()
                .execute(new BaseCallback<TopicEntity.DataBean>(TopicEntity.DataBean.class) {
                    @Override
                    public void onResponse(ObjectResult<TopicEntity.DataBean> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(SendTextPicActivity.this, result)) {
                            List<TopicEntity.DataBean.ListBean> listBeans = result.getData().getList();
                            setTopicAdapter(listBeans);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(final MessageVideoFile message) {
        img_delete_video.setVisibility(View.VISIBLE);
        ll_video.setVisibility(View.VISIBLE);
        rcvImg.setVisibility(View.GONE);
        mVideoFilePath = message.path;
        mThumbBmp = AvatarHelper.getInstance().displayVideoThumb(mVideoFilePath, mImageView);
        mTimeLen = message.length;
    }
}
