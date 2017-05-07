package com.wzy.pcwzy.newcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC WZY on 2017/5/4.
 */

public class NewCalendar extends LinearLayout {

    private ImageView btnLeft;
    private ImageView btnRight;
    private TextView  txtDate;
    private GridView  grid;

    private Calendar curDate = Calendar.getInstance();
    private String displayFormat;

    public NewCalendarListener listener;

    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);

        initControl(context,attrs);
    }

    public NewCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initControl(context,attrs);
    }

    private void initControl(Context context , AttributeSet attrs)
    {
        bindControl(context);
        bindControlEvent();

        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.NewCalendar);

        try {
            String format = ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat = format;
            if(displayFormat == null)
            {
                displayFormat = "MMM yyyy";
            }
        }
        finally {
                ta.recycle();
        }
        renderCalendar();
    }

    private void bindControl(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view,this);

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
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);
        txtDate.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        int maxCellCount = 6*7;
        while (cells.size()<maxCellCount)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        grid.setAdapter(new CalendarAdapter(getContext(),cells));
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener== null)
                {
                    return false;
                }
                else
                {
                    listener.onItemLongPress((Date)parent.getItemAtPosition(position));
                    return true;
                }
            }
        });
    }

    private  class CalendarAdapter extends ArrayAdapter<Date>
    {
        LayoutInflater inflater;
        public CalendarAdapter(Context context,ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day,days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Date date = getItem(position);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.calendar_text_day,parent,false);
            }

            int day = date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));


            /*Calendar calendar = (Calendar)curDate.clone();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DATE);*/

            Date now = new Date();
            boolean isTheSameMonth = false;
            if(date.getMonth() == now.getMonth())
            {
                isTheSameMonth = true;
            }
            if(isTheSameMonth)
            {
                ((TextView)convertView).setTextColor(Color.parseColor("black"));
            }
            else
            {
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }


            if(now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
                    && now.getYear() == date.getYear())
            {
                ((TextView)convertView).setTextColor(Color.parseColor("red"));
                ((Canlendar_day_textView)convertView).isToday = true ;
            }
            return convertView;
        }
    }
            public interface NewCalendarListener
            {
                void onItemLongPress(Date day);
            }
}
