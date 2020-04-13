package com.xfyyim.cn.ui.me;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xfyyim.cn.MyApplication;
import com.xfyyim.cn.R;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.GetFileSizeUtil;
import com.xfyyim.cn.view.MergerStatus;
import com.xfyyim.cn.view.SkinImageView;
import com.xfyyim.cn.view.SkinTextView;
import com.xfyyim.cn.view.SwitchButton;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyDatCacheActivity extends BaseActivity implements View.OnClickListener {
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
    private TextView tvCacheData;
    private Context mContext;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dat_cache);
        unbinder=ButterKnife.bind(this);
        initView();
        mContext = this;
        initActionBar();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        ivTitleLeft.setVisibility(View.VISIBLE);
        ivTitleLeft.setOnClickListener(this);
        tvTitleCenter.setText("数据和缓存");
       /* ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.me_edit_pen));*/

    }

    private void initView() {
        SwitchButton sbAutoPlay = (SwitchButton) findViewById(R.id.sbAutoPlay);
        SwitchButton sbCleanUpData = (SwitchButton) findViewById(R.id.sbCleanUpData);
        tvCacheData = (TextView) findViewById(R.id.tvCacheData);

        long cacheSize = GetFileSizeUtil.getFileSize(new File(MyApplication.getInstance().mAppDir));
        tvCacheData.setText(GetFileSizeUtil.formatFileSize(cacheSize));

        sbAutoPlay.setChecked(true);
        //Wi-Fi下自动播放视频
        sbAutoPlay.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        //清理本地缓存数据
        sbCleanUpData.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                clearCache();
            }
        });


    }


    /**
     * 清楚缓存
     */
    private void clearCache() {
        String filePath = MyApplication.getInstance().mAppDir;
        new ClearCacheAsyncTaska(filePath).execute(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    private class ClearCacheAsyncTaska extends AsyncTask<Boolean, String, Integer> {

        private File rootFile;
        private ProgressDialog progressDialog;

        private int filesNumber = 0;
        private boolean canceled = false;
        private long notifyTime = 0;

        public ClearCacheAsyncTaska(String filePath) {
            this.rootFile = new File(filePath);
        }

        @Override
        protected void onPreExecute() {
            filesNumber = GetFileSizeUtil.getFolderSubFilesNumber(rootFile);
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.deleteing));
            progressDialog.setMax(filesNumber);
            progressDialog.setProgress(0);
            // 设置取消按钮
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    canceled = true;
                }
            });
            progressDialog.show();
        }

        /**
         * 返回true代表删除完成，false表示取消了删除
         */
        @Override
        protected Integer doInBackground(Boolean... params) {
            if (filesNumber == 0) {
                return 0;
            }
            // 是否删除已清空的子文件夹
            boolean deleteSubFolder = params[0];
            return deleteFolder(rootFile, true, deleteSubFolder, 0);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // String filePath = values[0];
            int progress = Integer.parseInt(values[1]);
            // progressDialog.setMessage(filePath);
            progressDialog.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!canceled && result == filesNumber) {
                //   ToastUtil.showToast(mContext, R.string.clear_completed);
            }
            long cacheSize = GetFileSizeUtil.getFileSize(rootFile);
            tvCacheData.setText(GetFileSizeUtil.formatFileSize(cacheSize));
        }

        /**
         * 是否删除完毕
         *
         * @param file
         * @param deleteSubFolder
         * @return
         */
        private int deleteFolder(File file, boolean rootFolder, boolean deleteSubFolder, int progress) {
            if (file == null || !file.exists() || !file.isDirectory()) {
                return 0;
            }
            File flist[] = file.listFiles();
            for (File subFile : flist) {
                if (canceled) {
                    return progress;
                }
                if (subFile.isFile()) {
                    subFile.delete();
                    progress++;
                    long current = System.currentTimeMillis();
                    if (current - notifyTime > 200) {// 200毫秒更新一次界面
                        notifyTime = current;
                        publishProgress(subFile.getAbsolutePath(), String.valueOf(progress));
                    }
                } else {
                    progress = deleteFolder(subFile, false, deleteSubFolder, progress);
                    if (deleteSubFolder) {
                        subFile.delete();
                    }
                }
            }
            return progress;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
