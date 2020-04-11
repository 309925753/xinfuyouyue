package com.xfyyim.cn.customer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.xfyyim.cn.R;

import java.util.ArrayList;
import java.util.List;

public class SkillSignView {

    public static void AddTextViewData(List<String> labeList, int type, Context context, LinearLayout contentView){
        int size = labeList.size(); // 添加TextView的个数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(15, 3, 15, 3);

        ArrayList<TextView> childBtns = new ArrayList<TextView>();
        int totoalBtns = 0;

        for(int i = 0; i < size; i++){
            String item = labeList.get(i);
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 40);
            int length= item.length();

            if(length < 5){  // 根据字数来判断按钮的空间长度, 少于4个当一个按钮
                itemParams.weight = 1;
                totoalBtns=1;
            }else if(length >5&&length<8){ // <8个两个按钮空间
                itemParams.weight = 0;
                totoalBtns=2;
            }else if(length > 8){ // <8个两个按钮空间
                itemParams.weight = 1;
                totoalBtns+=1;
            }else{
                itemParams.weight = 5;
                totoalBtns+=5;
            }

            itemParams.width = 0;
            itemParams.setMargins(0, 0, 10, 0);
            TextView childBtn = (TextView) LayoutInflater.from(context).inflate(R.layout.button_view, null);
            switch (type){
                case  0:
                    //F23F8F
                    childBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.appdes_shape_f23f8f));
                    Log.e("e","data = " +type);
                    break;
                case  1:
                    //#71E05D
                    childBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.appdes_shape_71e05d));
                    Log.e("e","data = " +type);
                    break;
                case  2:
                    //#FF8E42
                    childBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.appdes_shape_ff8e42));
                    Log.e("e","data = " +type);
                    break;
            }
            childBtn.setText(item);
            childBtn.setTag(item);
            childBtn.setLayoutParams(itemParams);
            childBtns.add(childBtn);

            if(totoalBtns >= 4){
                LinearLayout horizLL = new LinearLayout(context);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(TextView addBtn:childBtns){
                    horizLL.addView(addBtn);
                }
                contentView.addView(horizLL);
                childBtns.clear();
                totoalBtns = 0;
            }
        }
        //最后一行添加一下
        if(!childBtns.isEmpty()){
            LinearLayout horizLL = new LinearLayout(context);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for(TextView addBtn:childBtns){
                horizLL.addView(addBtn);
            }
            contentView.addView(horizLL);
            childBtns.clear();
            totoalBtns = 0;
        }



    }
}
