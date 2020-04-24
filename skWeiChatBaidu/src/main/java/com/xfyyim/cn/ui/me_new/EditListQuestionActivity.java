package com.xfyyim.cn.ui.me_new;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.j256.ormlite.stmt.query.In;
import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.QuestEntity;
import com.xfyyim.cn.bean.QuestionEntity;
import com.xfyyim.cn.bean.SginBean;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.dialog.DialogView;
import com.xfyyim.cn.util.SkinUtils;
import com.xfyyim.cn.util.StringUtils;
import com.xfyyim.cn.util.ToastUtil;
import com.xuan.xuanhttplibrary.okhttp.HttpUtils;
import com.xuan.xuanhttplibrary.okhttp.callback.BaseCallback;
import com.xuan.xuanhttplibrary.okhttp.result.ObjectResult;
import com.xuan.xuanhttplibrary.okhttp.result.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class EditListQuestionActivity extends BaseActivity {
    SginBean.DataBean dateEntity;


    Unbinder unbinder;

    @BindView(R.id.rv_questionlist)
    RecyclerView rv_questionlist;

    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;
    @BindView(R.id.tv_empty)
    TextView tv_empty;

    List<String> list;
    QuestionAdapter adapter;
    DialogView dialogView;

    List<QuestEntity> entities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        unbinder = ButterKnife.bind(this);

        entities = (List<QuestEntity>) getIntent().getSerializableExtra("ListQuestion");
        initActionBar();
        getListInfo();
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        iv_title_left.setOnClickListener(v -> finish());
        tvTitle.setText("我的问题");

        tv_title_right.setVisibility(View.GONE);

    }


    private void updateValue(String json) {
        DialogHelper.showDefaulteMessageProgressDialog(EditListQuestionActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditListQuestionActivity.this).getAccessToken());
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
                            ToastUtil.showToast(EditListQuestionActivity.this, getString(R.string.save_success));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditListQuestionActivity.this);
                    }
                });
    }


    public void getListInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(EditListQuestionActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditListQuestionActivity.this).getAccessToken());

        HttpUtils.get().url(coreManager.getConfig().GETUSERPERSONALCONFIG)
                .params(params)
                .build()
                .execute(new BaseCallback<SginBean.DataBean>(SginBean.DataBean.class) {

                    @Override
                    public void onResponse(ObjectResult<SginBean.DataBean> result) {
                        DialogHelper.dismissProgressDialog();
                        list = new ArrayList<>();
                        if (Result.checkSuccess(mContext, result)) {
                            dateEntity = result.getData();
                            if (dateEntity != null) {
                             getList(dateEntity);

                            }


                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditListQuestionActivity.this);
                    }
                });

    }


    public void getList(SginBean.DataBean dateEntity) {
        List<String> mlist = new ArrayList<>();
        List<String> mlist1 = new ArrayList<>();

        if (dateEntity.getQuestionsConfig() != null && !TextUtils.isEmpty(dateEntity.getQuestionsConfig())) {
            String content[] = dateEntity.getQuestionsConfig().split(",");

            for (String s : content) {
                mlist.add(s);
                mlist1.add(s);

            }

            for (int i = 0; i < mlist1.size(); i++) {
              for(int j=0;j<entities.size();j++){
                   if (mlist1.get(i).contains(entities.get(j).getQuestion())){
                       mlist.remove(0);
                   }
              }
            }
        }

        if (mlist!=null&&mlist.size()>0){
            rv_questionlist.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);
            setAdapter(mlist);
        }else{
            rv_questionlist.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
        }

    }


    class QuestionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        Context context;

        public QuestionAdapter(Context context, @Nullable List<String> data) {
            super(R.layout.item_question_list, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_question, item);
        }
    }


    public void setAdapter(List<String> list) {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditListQuestionActivity.this);
            rv_questionlist.setLayoutManager(linearLayoutManager);
            adapter = new QuestionAdapter(EditListQuestionActivity.this, list);
            rv_questionlist.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    showSignleDialog(list.get(position), "");
                }
            });

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void showSignleDialog(String title, String text) {

        dialogView = new DialogView(this, title, text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) v).getText().toString().trim();
                if (name == null || TextUtils.isEmpty(name)) {
                    ToastUtil.showToast(EditListQuestionActivity.this, "问题答案不能为空");
                    return;
                }
                try {


                    QuestEntity entity = new QuestEntity();
                    entity.setQuestion(title);
                    entity.setAnswer(name);
                    entities.add(entity);

                    String json = JSON.toJSONString(entities);
                    updateValue(json);


                } catch (Exception e) {
                }

            }
        });
        dialogView.show();

    }

}
