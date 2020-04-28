package com.xfyyim.cn.ui.me_new;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.UploadFileResult;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.helper.LoginHelper;
import com.xfyyim.cn.helper.UploadService;
import com.xfyyim.cn.ui.account.LoginActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.tool.MultiImagePreviewActivity;
import com.xfyyim.cn.util.ToastUtil;
import com.xfyyim.cn.video.EasyCameraActivity;
import com.xfyyim.cn.view.photopicker.PhotoPickerActivity;
import com.xfyyim.cn.view.photopicker.SelectModel;
import com.xfyyim.cn.view.photopicker.intent.PhotoPickerIntent;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.xfyyim.cn.xmpp.CoreService.getIntent;

public class ReportActivity extends BaseActivity {
    private static final int REQUEST_CODE_CAPTURE_PHOTO = 1;  // 拍照
    private static final int REQUEST_CODE_PICK_PHOTO = 2;     // 图库

    private TextView tv_pic_count;
    private TextView tv_word_count;
    private EditText et_report_content;
    private RecyclerView rv_Img;
    private PosReportImgAdapter reportImgAdapter;
    private ArrayList<String> mPhotoList;
//    private GridViewAdapter mAdapter;
    private Button mNextStepBtn;
    // 拍照和图库，获得图片的Uri
    private Uri mNewPhotoUri;
    private String mImageData;

    String roomId;
    String rePortId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().hide();
        roomId=getIntent().getStringExtra("RoomId");
        rePortId=getIntent().getStringExtra("ReportIdKey");
        initActionBar();
        initView();
        onBindLinstener();
    }

    private void initView(){
        tv_pic_count = findViewById(R.id.tv_pic_count);
        tv_word_count = findViewById(R.id.tv_word_count);
        et_report_content = findViewById(R.id.et_report_content);
        rv_Img = findViewById(R.id.rv_report);
        mNextStepBtn = (Button) findViewById(R.id.next_step_btn);

        mPhotoList = new ArrayList<>();
        reportImgAdapter = new PosReportImgAdapter(this, mPhotoList);
        rv_Img.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rv_Img.setAdapter(reportImgAdapter);
        et_report_content.requestFocus();
    }
    private void initActionBar(){
        findViewById(R.id.iv_title_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = (TextView) findViewById(R.id.tv_title_center);
        tvTitle.setText("投诉");
    }

public void onBindLinstener(){
    et_report_content.setOnTouchListener((v, event) -> {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    });
    et_report_content.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length()>200){
                ToastUtil.showToast(ReportActivity.this,"最多输入200个字符");
                tv_word_count.setText("200/200");
            }else{
                tv_word_count.setText(s.length()+"/200");
            }
        }
    });

    mNextStepBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String remark=et_report_content.getText().toString().trim();
            if (remark==null|| TextUtils.isEmpty(remark)){
                ToastUtil.showToast(ReportActivity.this,"投诉原因不能为空!");
                et_report_content.requestFocus();
                return;
            }
            if (rePortId==null||TextUtils.isEmpty(rePortId)){
                ToastUtil.showToast(ReportActivity.this,"投诉原因不能为空!");
                return;
            }

            if (mPhotoList.size() <= 0) {

                report(roomId,rePortId,"",remark);
            } else {

                new  UploadPhoto().execute();
            }

        }
    });

}

    public class PosReportImgAdapter extends RecyclerView.Adapter<PosReportImgAdapter.MyViewHolder> {

        private final LayoutInflater mLayoutInflater;
        private final Context mContext;
        private List<String> mDatas;

        public PosReportImgAdapter(Context context, List<String> datas) {
            this.mDatas = datas;
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public PosReportImgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new PosReportImgAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_post_activity, parent, false));
            return new PosReportImgAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_report_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(final PosReportImgAdapter.MyViewHolder holder, final int position) {
            if (getItemViewType(position) == 0) { // 普通的视图
                Glide.with(ReportActivity.this)
                        .load(mDatas.get(position))
                        .override(150, 150)
                        .error(R.drawable.pic_error)
                        .into(holder.imageView);
            } else {
                holder.imageView.setImageResource(R.drawable.send_image);
            }
            holder.fr_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewType = reportImgAdapter.getItemViewType(holder.getAdapterPosition());
                    if (viewType == 1) {
                        showSelectPictureDialog();
                    } else {
                        showPictureActionDialog(holder.getAdapterPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            if (mDatas.size() >= 9) {
                tv_pic_count.setText("9张/9");
                return 9;
            }
            tv_pic_count.setText(mDatas.size()+"张/9");
            return mDatas.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mDatas.size() == 0) {
                // View Type 1代表添加更多的视图
                return 1;
            } else if (mDatas.size() < 9) {
                if (position < mDatas.size()) {
                    // View Type 0代表普通的ImageView视图
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            FrameLayout fr_report;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.sdv);
                fr_report=itemView.findViewById(R.id.fr_report);
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
//                        mAdapter.notifyDataSetInvalidated();
                        reportImgAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zq", "压缩失败,原图上传");
                        mPhotoList.add(file.getPath());
//                        mAdapter.notifyDataSetInvalidated();
                        reportImgAdapter.notifyDataSetChanged();
                    }
                }).launch();// 启动压缩
    }

    // 多张图片压缩 相册
    private void album(ArrayList<String> stringArrayListExtra, boolean isOriginal) {
        if (isOriginal) {// 原图发送，不压缩
            Log.e("zq", "原图上传，不压缩，选择原文件路径");
            for (int i = 0; i < stringArrayListExtra.size(); i++) {
                mPhotoList.add(stringArrayListExtra.get(i));
                reportImgAdapter.notifyDataSetChanged();
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
//                mAdapter.notifyDataSetInvalidated();
                reportImgAdapter.notifyDataSetChanged();

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
//                        mAdapter.notifyDataSetInvalidated();
                        reportImgAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();// 启动压缩
    }


    private void showSelectPictureDialog() {
        String[] items = new String[]{getString(R.string.photograph), getString(R.string.album)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            takePhoto();
                        } else {
                            selectPhoto();
                        }
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void showPictureActionDialog(final int position) {
        String[] items = new String[]{getString(R.string.look_over), getString(R.string.delete)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(getString(R.string.image))
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // 查看
                            Intent intent = new Intent(ReportActivity.this, MultiImagePreviewActivity.class);
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

    // 拍照
    private void takePhoto() {
        Intent intent = new Intent(this, EasyCameraActivity.class);
        startActivity(intent);
    }


    /**
     * 相册
     * 可以多选的图片选择器
     */
    private void selectPhoto() {
        ArrayList<String> imagePaths = new ArrayList<>();
        PhotoPickerIntent intent = new PhotoPickerIntent(ReportActivity.this);
        intent.setSelectModel(SelectModel.MULTI);
        // 是否显示拍照， 默认false
        intent.setShowCarema(false);
        // 最多选择照片数量，默认为9
        intent.setMaxTotal(9 - mPhotoList.size());
        // 已选中的照片地址， 用于回显选中状态
        intent.setSelectedPaths(imagePaths);
        // intent.setImageConfig(config);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

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
        }
    }
    private void deletePhoto(final int position) {
        mPhotoList.remove(position);
//        mAdapter.notifyDataSetInvalidated();
        reportImgAdapter.notifyDataSetChanged();
    }

    private class UploadPhoto extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogHelper.showDefaulteMessageProgressDialog(ReportActivity.this);
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
//            String result = new UploadService().uploadFile(coreManager.getConfig().UPLOAD_REPOORT_URl, mapParams, mPhotoList);
            if (TextUtils.isEmpty(result)) {
                return 2;
            }

            UploadFileResult recordResult = JSON.parseObject(result, UploadFileResult.class);
            boolean success = Result.defaultParser(ReportActivity.this, recordResult, true);
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
                startActivity(new Intent(ReportActivity.this, LoginActivity.class));
            } else if (result == 2) {
                DialogHelper.dismissProgressDialog();
                ToastUtil.showToast(ReportActivity.this, getString(R.string.upload_failed));
            } else {
                report(roomId,rePortId,mImageData,et_report_content.getText().toString().trim());
            }
        }
    }

    /*
  举报
   */
    private void report(String roomId,String reportId,String picUrl,String remark) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", coreManager.getSelfStatus().accessToken);
        params.put("roomId", roomId);
        params.put("reason", reportId);
        params.put("Imgs", picUrl);
        params.put("remark", remark);
        DialogHelper.showDefaulteMessageProgressDialog(this);

        HttpUtils.get().url(coreManager.getConfig().USER_REPORT)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (result.getResultCode() == 1) {
                            ToastUtil.showToast(ReportActivity.this, R.string.report_success);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                    }
                });
    }



}


