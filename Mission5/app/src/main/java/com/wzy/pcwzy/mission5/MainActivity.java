package com.wzy.pcwzy.mission5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    RadioButton r1 = null;
    RadioButton r2 = null;
    RadioButton r3 = null;
    RadioButton r4 = null;
    RadioGroup radioGroup = null;
    //RadioButton currentRadioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        r1 = (RadioButton) findViewById(R.id.a);
        r2 = (RadioButton) findViewById(R.id.b);
        r3 = (RadioButton) findViewById(R.id.c);
        r4 = (RadioButton) findViewById(R.id.d);
        r1.setClickable(true);

        //radioGroup.setOnCheckedChangeListener(mChangeRadio);
        Button btn1_sure = (Button) findViewById(R.id.sure);
        Button btn2_cancel = (Button) findViewById(R.id.cancel);
        btn1_sure.setOnClickListener(new btn1_sure());
        btn2_cancel.setOnClickListener(new btn2_cancel());
    }

    class btn1_sure implements OnClickListener {
        @Override
        public void onClick(View v) {//实现OnClickListener接口中的onClick方法
            String ans = "";
            if (r1.isChecked()) {
                ans = "on";
            } else if (r2.isChecked()) {
                ans = "at";
            } else if (r3.isChecked()) {
                ans = "of";
            } else if (r4.isChecked()) {
                ans = "in";
            }
            /* new 一个Intent 对象，并指定class */
            Intent intent = new Intent();
            //设置Intent对象要启动的Activity
            intent.setClass(MainActivity.this, otherActivity.class);
            /* new 一个Bundle对象，并将要传递的数据传入 */
            Bundle bundle = new Bundle();
            bundle.putString("ans", ans);
			/* 将Bundle 对象assign 给Intent */
            intent.putExtras(bundle);
            //启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
            startActivityForResult(intent, 0);
        }
    }

    class btn2_cancel implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            radioGroup.clearCheck();
            setTitle("");
        }

    }



    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0
                && resultCode == 0) {



            Bundle bunde = data.getExtras();
            String ans = bunde.getString("ans");


        }
    }
}
