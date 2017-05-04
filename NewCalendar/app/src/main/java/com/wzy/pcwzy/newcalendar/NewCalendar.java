package com.wzy.pcwzy.newcalendar;

import android.content.Context;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by PC WZY on 2017/5/4.
 */

public class NewCalendar extends LinearLayout {

    private ImageView btnLeft;
    private ImageView btnRight;
    private TextView  txtDate;
    private GridView  grid;

    private Calendar curDate = Calendar.getInstance();

    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initControl(context);
    }

    public NewCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initControl(context);
    }

    private void initControl(Context context)
    {
        bindControl(context);
        bindControlEvent();
        renderCalendar();
    }

    private void bindControl(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view,this,false);

        btnLeft  = (ImageView)findViewById(R.id.btnLeft);
        btnRight = (ImageView)findViewById(R.id.btnRight);
        txtDate  = (TextView)findViewById(R.id.txtDate);
        grid     = (GridView)findViewById(R.id.calendar_grid);
    }

    private  void bindControlEvent(){
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });

        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar(){

    }
}
