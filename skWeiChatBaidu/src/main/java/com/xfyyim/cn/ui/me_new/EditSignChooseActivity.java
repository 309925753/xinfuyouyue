package com.xfyyim.cn.ui.me_new;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xfyyim.cn.R;
import com.xfyyim.cn.bean.SginBean;
import com.xfyyim.cn.helper.DialogHelper;
import com.xfyyim.cn.sp.UserSp;
import com.xfyyim.cn.ui.base.BaseActivity;
import com.xfyyim.cn.ui.dialog.DialogView;
import com.xfyyim.cn.util.SkinUtils;
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

public class EditSignChooseActivity extends BaseActivity {
    private String textContent;
    private String type;
    String title;
    SginBean.DataBean dateEntity;


    Unbinder unbinder;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_add_text)
    TextView tv_add_text;

    @BindView(R.id.rv_chooselist)
    RecyclerView rv_chooselist;

    @BindView(R.id.iv_title_left)
    ImageView iv_title_left;
    @BindView(R.id.tv_title_center)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;

    List<String> list;
    LisetAdapter adapter;
    String text = "";

    DialogView dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        unbinder = ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        textContent = getIntent().getStringExtra("context");

        initActionBar();
        getListInfo();

        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSignleDialog("添加标签", "");
            }
        });
    }


    private void initActionBar() {
        getSupportActionBar().hide();
        iv_title_left.setOnClickListener(v -> finish());
        if (TextUtils.isEmpty(title)) {
            title = "个人资料修改";
        }
        tvTitle.setText(title);

        ViewCompat.setBackgroundTintList(tv_title_right, ColorStateList.valueOf(SkinUtils.getSkin(this).getAccentColor()));
        tv_title_right.setTextColor(getResources().getColor(R.color.white));
        tv_title_right.setText(getResources().getString(R.string.save));
        tv_title_right.setOnClickListener(v -> {


            if (text != null && !TextUtils.isEmpty(text)) {
                loadDescription(text);
            } else {
                ToastUtil.showToast(EditSignChooseActivity.this, "请选择一个标签");

            }
        });

    }


    private void loadDescription(String text) {
        DialogHelper.showDefaulteMessageProgressDialog(EditSignChooseActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditSignChooseActivity.this).getAccessToken());
        params.put("userId", coreManager.getSelf().getUserId());
        params.put(type, text);


        HttpUtils.get().url(coreManager.getConfig().UPDATE_USERINFO)
                .params(params)
                .build()
                .execute(new BaseCallback<Void>(Void.class) {

                    @Override
                    public void onResponse(ObjectResult<Void> result) {
                        DialogHelper.dismissProgressDialog();
                        if (Result.checkSuccess(mContext, result)) {
                            ToastUtil.showToast(EditSignChooseActivity.this, getString(R.string.save_success));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditSignChooseActivity.this);
                    }
                });
    }



    public void getListInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(EditSignChooseActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditSignChooseActivity.this).getAccessToken());

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
                                list = getList(dateEntity);

                                if (textContent != null && !TextUtils.isEmpty(textContent)) {
                                    if (!list.contains(textContent)){
                                        list.add(0,textContent);
                                    }
                                }



                                    setAdapter();
                            }


                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditSignChooseActivity.this);
                    }
                });

    }


    public List<String> getList(SginBean.DataBean dateEntity) {
        List<String> mlist = new ArrayList<>();
        if (type.equals("myWork")) {
            if (dateEntity.getWorkConfig() != null && !TextUtils.isEmpty(dateEntity.getWorkConfig())) {
                String[] content = dateEntity.getWorkConfig().split(",");
                for (String s : content) {
                    mlist.add(s);
                }
            }
        }else if (type.equals("myIndustry")){
            if (dateEntity.getIndustryConfig() != null && !TextUtils.isEmpty(dateEntity.getIndustryConfig())) {
                String[] content = dateEntity.getIndustryConfig().split(",");
                for (String s : content) {
                    mlist.add(s);
                }
            }
        }
        return mlist;
    }
    private int lastClickPosition = -1;
    public class LisetAdapter extends RecyclerView.Adapter<LisetAdapter.ListItemViewHolder> {

        private List<String> mList;

        Context context;

        public LisetAdapter(Context context, List<String> list) {
            mList = list;
            this.context = context;
        }


        public void singleChoose(int position) {
            lastClickPosition = position;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_signlist, parent, false);
            return new ListItemViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
            holder.name.setText(mList.get(position));


            if (position == lastClickPosition) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleChoose(position);
                    text = list.get(position);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            TextView name;
            RelativeLayout rootview;

            ListItemViewHolder(View view) {
                super(view);
                this.name = view.findViewById(R.id.tv_name);
                this.checkBox = view.findViewById(R.id.check_box);
                this.rootview = view.findViewById(R.id.root_view);

            }
        }
    }


    public void setAdapter() {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditSignChooseActivity.this);
            rv_chooselist.setLayoutManager(linearLayoutManager);
            adapter = new LisetAdapter(EditSignChooseActivity.this, list);
            rv_chooselist.setAdapter(adapter);

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
                    ToastUtil.showToast(EditSignChooseActivity.this, "添加标签不能为空");
                    return;
                }
                dialogView.getDialog().dismiss();

                list.add(0, name);
                lastClickPosition=0;
                adapter.notifyDataSetChanged();

            }
        });
        dialogView.show();

    }

}
