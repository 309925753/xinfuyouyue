package com.xfyyim.cn.ui.me_new;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.xfyyim.cn.R;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.MainActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.util.SkinUtils;
import com.xfyyim.cn.util.ToastUtil;
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

public class EditActivity extends BaseActivity {
    private String textContent;
    private String type;
    String title;



    Unbinder unbinder;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        unbinder= ButterKnife.bind(this);
        type=getIntent().getStringExtra("type");
        title=getIntent().getStringExtra("title");
        textContent=getIntent().getStringExtra("context");

        if(textContent!=null&&!TextUtils.isEmpty(textContent)){
            et_desc.setText(textContent);
        }
        initActionBar();

    }


    private void initActionBar() {
        getSupportActionBar().hide();
        iv_title_left.setOnClickListener(v -> finish());
        if (TextUtils.isEmpty(title)){
            title="个人资料修改";
        }
        tvTitle.setText(title);

        ViewCompat.setBackgroundTintList(tv_title_right, ColorStateList.valueOf(SkinUtils.getSkin(this).getAccentColor()));
        tv_title_right.setTextColor(getResources().getColor(R.color.white));
        tv_title_right.setText(getResources().getString(R.string.save));
        tv_title_right.setOnClickListener(v -> {
            textContent = (et_desc.getText().toString().trim()).replaceAll("\r|\n*","").replaceAll(" " ,"");

            if (TextUtils.isEmpty(textContent)) {
                ToastUtil.showToast(mContext, "输入信息不能为空");
                et_desc.requestFocus();
                return;
            }
            loadDescription(textContent);
        });

    }


    private void loadDescription(String description) {
        DialogHelper.showDefaulteMessageProgressDialog(EditActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());
        params.put(type, textContent);

        HttpUtils.get().url(coreManager.getConfig().UPDATE_USERINFO)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(mContext, result)) {
                            ToastUtil.showToast(EditActivity.this, getString(R.string.save_success));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditActivity.this);
                    }
                });
    }
}
