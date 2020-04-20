package com.xfyyim.cn.ui.me;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.User;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.account.RegisterUserBasicInfoActivity;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.company.CreateDepartment;
import com.xfyyim.cn.ui.dialog.DialogView;
import com.xfyyim.cn.util.DateFormatUtil;
import com.xfyyim.cn.util.DateSelectHelper;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.JsonCallback;
import com.xuan.xuanhttplibrary.okhttp.callback.ListCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ArrayResult;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import fm.jiecao.jcvideoplayer_lib.MessageEvent;
import okhttp3.Call;
import okhttp3.Callback;

public class PersonEditInfoActivity extends BaseActivity  implements View.OnClickListener {
    Unbinder unbinder;

    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_birthday)
    TextView tv_birthday;
    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    int sex;
    String nickName;
    long birthday;
    DialogView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit_info);
        unbinder = ButterKnife.bind(this);

        initActionBar();
        sex=getIntent().getIntExtra("Sex",0);
        nickName=getIntent().getStringExtra("NickName");
        birthday=getIntent().getLongExtra("Birthday",0);

        if (sex==0){
            tv_sex.setText("女");
        }else{
            tv_sex.setText("男");
        }

        tv_nickname.setText(nickName);

        tv_birthday.setText(DateFormatUtil.getFormatDate_ymd(birthday*1000));


        tv_sex.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);

    }


    private void initActionBar() {

        getSupportActionBar().hide();
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);
        tv_title_center.setText("个人信息");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_sex:
                showSelectSexDialog();
                break;
            case R.id.tv_birthday:

                showSelectBirthdayDialog();
                break;
            case R.id.tv_nickname:
                showSignleDialog(tv_nickname,"修改昵称",tv_nickname.getText().toString());

                break;
        }

    }


    public void showSignleDialog(TextView view, String title, String text) {

         dialogView = new DialogView(this, title, text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((EditText) v).getText().toString().trim();
                if (name==null|| TextUtils.isEmpty(name)){
                    ToastUtil.showToast(PersonEditInfoActivity.this,"修改昵称不能为空");
                    return;
                }
                view.setText(name);
                requestData();
            }
        });
        dialogView.show();

    }

    @SuppressWarnings("deprecation")
    private void showSelectBirthdayDialog() {
        DateSelectHelper dialog = DateSelectHelper.getInstance(PersonEditInfoActivity.this);
        dialog.setDateMin("1900-1-1");
        dialog.setDateMax(System.currentTimeMillis());
        dialog.setCurrentDate(birthday * 1000);
        dialog.setOnDateSetListener(new DateSelectHelper.OnDateResultListener() {
            @Override
            public void onDateSet(long time, String dateFromat) {
                birthday=(time / 1000);
                tv_birthday.setText(dateFromat);
                requestData();
            }
        });

        dialog.show();
    }


    private void showSelectSexDialog() {
        String[] sexs = new String[]{getString(R.string.sex_man), getString(R.string.sex_woman)};
        new AlertDialog.Builder(this).setTitle(R.string.select_sex)
                .setSingleChoiceItems(sexs, sex == 1 ? 0 : 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                          sex=1;
                            tv_sex.setText(R.string.sex_man);
                        } else {
                            sex=0;
                            tv_sex.setText(R.string.sex_woman);
                        }

                        dialog.dismiss();
                        requestData();
                    }
                }).setCancelable(true).create().show();
    }



    private void requestData() {
        DialogHelper.showDefaulteMessageProgressDialog(PersonEditInfoActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", UserSp.getInstance(PersonEditInfoActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());
        params.put("nickname", tv_nickname.getText().toString());
        params.put("birthday",String.valueOf( birthday));
        params.put("sex", String.valueOf( sex));



        HttpUtils.post().url(coreManager.getConfig().UPDATE_USERINFO)
                .params(params)
                .build()
               .execute(new BaseCallback<Void>(Void.class) {
                   @Override
                   public void onResponse(ObjectResult<Void> result) {
                       DialogHelper.dismissProgressDialog();
                       if (Result.checkSuccess(mContext, result)) {
                           Toast.makeText(PersonEditInfoActivity.this,"修改成功", Toast.LENGTH_SHORT).show();
                       }
                   }


                   @Override
                   public void onError(Call call, Exception e) {
                       DialogHelper.dismissProgressDialog();
                   }

                   @Override
                   public void onFailure(Call call, IOException e) {

                       DialogHelper.dismissProgressDialog();
                       super.onFailure(call, e);
                   }
               });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();

        if (  dialogView!=null){
            dialogView.getDialog().dismiss();
        }
    }
}

