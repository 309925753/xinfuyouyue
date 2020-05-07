package com.xfyyim.cn.ui.me_new;

import android.content.Context;
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

public class EditListChooseActivity extends BaseActivity {
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

    List<HashMap<String, Object>> list;
    List<String> addList;
    ArrayList<String> selectList;
    LisetAdapter adapter;
    DialogView dialogView;

    public static int MAXNUM = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        unbinder = ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        textContent = getIntent().getStringExtra("context");

        if (textContent != null && !TextUtils.isEmpty(textContent)) {
            addList = new ArrayList<>();
            if (textContent.contains(",")) {
                addList = StringUtils.getListString(textContent);
            } else {
                addList.add(textContent);
            }

        }
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

            String text = "";
            if (selectList != null && selectList.size() > 0) {
                if (selectList.size() == 1) {
                    text = selectList.get(0);
                } else {
                    for (int i = 0; i < selectList.size(); i++) {
                        if (i == selectList.size() - 1) {
                            text = text + selectList.get(i);
                        } else {
                            text = text + selectList.get(i) + ",";
                        }
                    }
                }
            }

            if (text != null && !TextUtils.isEmpty(text)) {
                loadDescription(text);
            } else {
                ToastUtil.showToast(EditListChooseActivity.this, "请选择一个标签");
            }
        });

    }


    private void loadDescription(String text) {
        DialogHelper.showDefaulteMessageProgressDialog(EditListChooseActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditListChooseActivity.this).getAccessToken());
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
                            ToastUtil.showToast(EditListChooseActivity.this, getString(R.string.save_success));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditListChooseActivity.this);
                    }
                });
    }


    public void getListInfo() {
        DialogHelper.showDefaulteMessageProgressDialog(EditListChooseActivity.this);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", UserSp.getInstance(EditListChooseActivity.this).getAccessToken());

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
                                setAdapter();
                            }


                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissProgressDialog();
                        ToastUtil.showErrorNet(EditListChooseActivity.this);
                    }
                });

    }


    public List<HashMap<String, Object>> getList(SginBean.DataBean dateEntity) {
        List<HashMap<String, Object>> mlist = new ArrayList<>();

        if (type.equals("myTag")) {
            mlist = newList(dateEntity.getTagConfig());
        } else if (type.equals("myTastes")) {
            mlist = newList(dateEntity.getTastesConfig());
        } else if (type.equals("myMusic")) {
            mlist = newList(dateEntity.getMusicConfig());
        } else if (type.equals("myFood")) {
            mlist = newList(dateEntity.getFoodConfig());
        } else if (type.equals("myMovie")) {
            mlist = newList(dateEntity.getMovieConfig());
        } else if (type.equals("myBookAndComic")) {
            mlist = newList(dateEntity.getBookAndComicConfig());
        } else if (type.equals("mySports")) {
            mlist = newList(dateEntity.getSportsConfig());
        }
        return mlist;
    }


    public List<HashMap<String, Object>> newList(String ms) {
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        List<String> newList = new ArrayList<>();
        if (ms != null && !TextUtils.isEmpty(ms)) {
            String content[] = ms.split(",");
            for (String s : content) {

                newList.add(s);
            }
            if (addList != null && addList.size() > 0) {
                for (int i = 0; i < addList.size(); i++) {
                    if (!newList.contains(addList.get(i))) {
                        newList.add(i, addList.get(i));
                    }
                }
            }
        }

        for (int i = 0; i < newList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("isSelected", false);
            map.put("name",newList.get(i));
            if (i<=addList.size()-1){
                if (newList.contains(addList.get(i))) {
                    map.put("isSelected", true);
                    map.put("name",addList.get(i));
                }
            }

            mapList.add(map);

        }


        return mapList;
    }


    public class LisetAdapter extends RecyclerView.Adapter<LisetAdapter.ListItemViewHolder> {

        private List<HashMap<String, Object>> mList;

        private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
        private HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        //        private boolean mIsSelectable = false;
        Context context;

        public LisetAdapter(Context context, List<HashMap<String, Object>> list) {
            mList = list;
            this.context = context;
        }


//        //更新adpter的数据和选择状态
//        public void updateDataSet(ArrayList<String> list) {
//            this.mList = list;
//            mSelectedPositions = new SparseBooleanArray();
//        }


        //获得选中条目的结果
        public ArrayList<String> getSelectedItem() {
            selectList = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if ((boolean)mList.get(i).get("isSelected")) {
                    selectList.add(mList.get(i).get("name")+"");
                }
            }

            return selectList;
        }

        @NonNull
        @Override
        public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_signlist, parent, false);
            return new ListItemViewHolder(itemView);
        }


//        //设置给定位置条目的选择状态
//        private void setItemChecked(int position, boolean isChecked) {
//            mSelectedPositions.put(position, isChecked);
//        }
//
//        //根据位置判断条目是否选中
//        private boolean isItemChecked(int position) {
//            return mSelectedPositions.get(position);
//        }


//        //根据位置判断条目是否可选
//        private boolean isSelectable() {
//            return mIsSelectable;
//        }

//        //设置给定位置条目的可选与否的状态
//        private void setSelectable(boolean selectable) {
//            mIsSelectable = selectable;
//        }

        @Override
        public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
            HashMap<String, Object> map=mList.get(position);

            holder.name.setText(map.get("name")+"");
            holder.checkBox.setChecked((boolean)map.get("isSelected"));


            holder.rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    getSelectedItem();

                    if (getSelectedItem().size() > MAXNUM) {
                        ToastUtil.showLongToast(mContext, "最多只能选择7个标签");
                        return;
                    }else{

                        if ((boolean)map.get("isSelected")){
                            map.put("isSelected", false);
                        } else {
                            map.put("isSelected", true);
                        }

                        notifyDataSetChanged();
                    }




//                    if (isItemChecked(position)) {
//                        setItemChecked(position, false);
//                    } else {
//                        setItemChecked(position, true);
//                    }
//
//
//
//
//                    LogUtil.e("cjh 选择  " + getSelectedItem().size());
//                    if (getSelectedItem().size() > MAXNUM) {
//                        ToastUtil.showLongToast(mContext, "最多只能选择7个标签");
//                    } else {
//                        LogUtil.e("cjh 选择1111  " + getSelectedItem().size());
//                        notifyItemChanged(position);
//                    }
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditListChooseActivity.this);
            rv_chooselist.setLayoutManager(linearLayoutManager);
            adapter = new LisetAdapter(EditListChooseActivity.this, list);
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
                    ToastUtil.showToast(EditListChooseActivity.this, "添加标签不能为空");
                    return;
                }
                dialogView.getDialog().dismiss();

                HashMap<String ,Object> hashMap=new HashMap<>();
                hashMap.put("isSelected",true);
                hashMap.put("name",name);

                list.add(0, hashMap);
                adapter.notifyDataSetChanged();

            }
        });
        dialogView.show();

    }

}
