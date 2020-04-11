package com.xfyyim.cn.ui.tool;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.qrcode.utils.DecodeUtils;
import com.google.zxing.Result;
import com.xfyyim.cn.AppConstant;
import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.db.dao.UserAvatarDao;
import com.xfyyim.cn.helper.AvatarHelper;
import com.xfyyim.cn.helper.ImageLoadHelper;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.message.HandleQRCodeScanUtil;
import com.xfyyim.cn.util.BitmapUtil;
import com.xfyyim.cn.util.FileUtil;
import com.xfyyim.cn.view.SaveWindow;
import com.xfyyim.cn.view.ZoomImageView;
import com.xfyyim.cn.view.chatHolder.MessageEventClickFire;
import com.xfyyim.cn.view.imageedit.IMGEditActivity;

import java.io.File;

import de.greenrobot.event.EventBus;
import pl.droidsonroids.gif.GifDrawable;

/**
 * 单张图片预览
 */
public class SingleImagePreviewActivity extends BaseActivity {
    public static final int REQUEST_IMAGE_EDIT = 1;

    private String mImageUri;
    private String mImagePath;
    private String mEditedPath;
    // 是否为阅后即焚类型   当前图片在消息内的位置
    private String delPackedId;

    private ZoomImageView mImageView;
    private Bitmap mBitmap;// 用于 识别图中二维码
    private SaveWindow mSaveWindow;
    private My_BroadcastReceiver my_broadcastReceiver = new My_BroadcastReceiver();

    @SuppressWarnings("unused")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_preview);

        if (getIntent() != null) {
            mImageUri = getIntent().getStringExtra(AppConstant.EXTRA_IMAGE_URI);
            mImagePath = getIntent().getStringExtra("image_path");
            boolean isReadDel = getIntent().getBooleanExtra("isReadDel", false);
            if (isReadDel) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            }

            // 这个是阅后即焚消息的 packedId
            delPackedId = getIntent().getStringExtra("DEL_PACKEDID");
        }

        initView();
        register();
    }

    public void doBack() {
        if (!TextUtils.isEmpty(delPackedId)) {
            // 发送广播去更新聊天界面，移除该message
            EventBus.getDefault().post(new MessageEventClickFire("delete", delPackedId));
        }
        finish();
        overridePendingTransition(0, 0);// 关闭过场动画
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    @Override
    protected boolean onHomeAsUp() {
        doBack();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (my_broadcastReceiver != null) {
            unregisterReceiver(my_broadcastReceiver);
        }
    }

    private void initView() {
        getSupportActionBar().hide();
        mImageView = findViewById(R.id.image_view);
        if (TextUtils.isEmpty(mImageUri)) {
            Toast.makeText(mContext, R.string.image_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        加载图片 || 头像
         */
        if (mImageUri.contains("http")) {// 图片 头像的mImageUri为UserId
            boolean isExists = false;
            if (!TextUtils.isEmpty(mImagePath)) {
                File file = new File(mImagePath);
                if (file.exists()) {
                    isExists = true;
                }
            }
            if (isExists) {// 本地加载
                if (mImageUri.endsWith(".gif")) {
                    try {
                        GifDrawable gifDrawable = new GifDrawable(new File(mImagePath));
                        mImageView.setImageDrawable(gifDrawable);
                    } catch (Exception e) {
                        mImageView.setImageResource(R.drawable.image_download_fail_icon);
                        e.printStackTrace();
                    }
                } else {
                    ImageLoadHelper.loadBitmapCenterCropDontAnimateWithError(
                            mContext,
                            mImagePath,
                            R.drawable.image_download_fail_icon,
                            b -> {
                                mBitmap = b;
                                mImageView.setImageBitmap(b);
                            }, e -> {
                                mImageView.setImageResource(R.drawable.image_download_fail_icon);
                            });
                }
            } else {// 网络加载
                if (mImageUri.endsWith(".gif")) {
                    ImageLoadHelper.showGifWithError(
                            mContext,
                            mImageUri,
                            R.drawable.image_download_fail_icon,
                            mImageView
                    );
                } else {
                    ImageLoadHelper.loadBitmapCenterCropDontAnimateWithError(
                            mContext,
                            mImageUri,
                            R.drawable.image_download_fail_icon,
                            b -> {
                                mBitmap = b;
                                mImageView.setImageBitmap(b);
                            }, e -> {
                                mImageView.setImageResource(R.drawable.image_download_fail_icon);
                            });
                }
            }
        } else {// 头像
            String time = UserAvatarDao.getInstance().getUpdateTime(mImageUri);
            mImageUri = AvatarHelper.getAvatarUrl(mImageUri, false);// 为头像重新赋值，用于保存功能
            if (TextUtils.isEmpty(mImageUri)) {
                mImageView.setImageResource(R.drawable.avatar_normal);
                return;
            }
            ImageLoadHelper.showImageSignature(
                    MyApplication.getContext(),
                    mImageUri,
                    R.drawable.avatar_normal,
                    time,
                    mImageView
            );
        }
    }

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(com.xfyyim.cn.broadcast.OtherBroadcast.singledown);
        filter.addAction(com.xfyyim.cn.broadcast.OtherBroadcast.longpress);
        registerReceiver(my_broadcastReceiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_EDIT:
                    mImagePath = mEditedPath;
                    mImageUri = new File(mEditedPath).toURI().toString();
                    ImageLoadHelper.loadBitmapCenterCropDontAnimateWithError(
                            mContext,
                            mImagePath,
                            R.drawable.image_download_fail_icon,
                            b -> {
                                mBitmap = b;
                                mImageView.setImageBitmap(b);
                            },
                            e -> {
                                mImageView.setImageResource(R.drawable.image_download_fail_icon);
                            }
                    );
                    // 模拟那个长按，弹出菜单，
                    Intent intent = new Intent(com.xfyyim.cn.broadcast.OtherBroadcast.longpress);
                    sendBroadcast(intent);
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    class My_BroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(com.xfyyim.cn.broadcast.OtherBroadcast.singledown)) {
                // 轻触屏幕，退出预览
                doBack();
            } else if (intent.getAction().equals(com.xfyyim.cn.broadcast.OtherBroadcast.longpress)) {
                // 长按屏幕，弹出菜单
                mSaveWindow = new SaveWindow(SingleImagePreviewActivity.this, BitmapUtil.getImageIsQRcode(SingleImagePreviewActivity.this, mImagePath), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSaveWindow.dismiss();
                        switch (v.getId()) {
                            case R.id.save_image:
                                if (!TextUtils.isEmpty(delPackedId)) {
                                    Toast.makeText(SingleImagePreviewActivity.this, R.string.tip_burn_image_cannot_save, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (mImageUri.toLowerCase().endsWith("gif")) {// 保存Gif
                                    FileUtil.downImageToGallery(SingleImagePreviewActivity.this, mImagePath);
                                } else {// 保存图片
                                    FileUtil.downImageToGallery(SingleImagePreviewActivity.this, mImageUri);
                                }
                                break;
                            case R.id.edit_image:
                                ImageLoadHelper.loadFile(
                                        SingleImagePreviewActivity.this,
                                        mImageUri,
                                        f -> {
                                            mEditedPath = FileUtil.createImageFileForEdit().getAbsolutePath();
                                            IMGEditActivity.startForResult(SingleImagePreviewActivity.this, Uri.fromFile(f), mEditedPath, REQUEST_IMAGE_EDIT);
                                        });
                                break;
                            case R.id.identification_qr_code:
                                // 识别图中二维码
                                if (mBitmap != null) {
                                    new Thread(() -> {
                                        final Result result = DecodeUtils.decodeFromPicture(mBitmap);
                                        mImageView.post(() -> {
                                            if (result != null && !TextUtils.isEmpty(result.getText())) {
                                                HandleQRCodeScanUtil.handleScanResult(mContext, result.getText());
                                            } else {
                                                Toast.makeText(SingleImagePreviewActivity.this, R.string.decode_failed, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }).start();
                                } else {
                                    Toast.makeText(SingleImagePreviewActivity.this, R.string.unrecognized, Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
                mSaveWindow.show();
            }
        }
    }
}
