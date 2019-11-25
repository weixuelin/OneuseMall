package com.qyc.wxl.oneusemall.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qyc.wxl.oneusemall.R;
import com.wt.weiutils.time.DatePicker;
import com.wt.weiutils.time.DateUtils;
import com.wt.weiutils.time.OnChangeLisener;
import com.wt.weiutils.time.OnSureTwoLisener;
import com.wt.weiutils.time.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.ContextCompat;


/**
 * Created by codbking on 2016/8/11.
 */
public class DatePickDialogTwo extends Dialog implements OnChangeLisener {

    private TextView text_start;
    private FrameLayout wheelLayout;
    private TextView text_team_cancel;
    private TextView text_team_success;
    private TextView text_stop;
    private View view_start;
    private View view_stop;

    private String title;
    private String format;
    private DateType type = DateType.TYPE_ALL;

    //开始时间
    private Date startDate = new Date();
    //年分限制，默认上下5年
    private int yearLimt = 5;

    private OnChangeLisener onChangeLisener;

    private OnSureTwoLisener onSureLisener;

    private DatePicker mDatePicker;

    //设置标题
    public void setTitle(String title) {
        this.title = title;
    }

    //设置模式
    public void setType(DateType type) {
        this.type = type;
    }

    //设置选择日期显示格式，设置显示message,不设置不显示message
    public void setMessageFormat(String format) {
        this.format = format;
    }

    //设置开始时间
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    //设置年份限制，上下年份
    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }

    //设置选择回调
    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    //设置点击确定按钮，回调
    public void setOnSureLisener(OnSureTwoLisener onSureLisener) {
        this.onSureLisener = onSureLisener;
    }

    public DatePickDialogTwo(Context context) {
        super(context, R.style.dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_team);

        initView();
        initParas();
    }

    private DatePicker getDatePicker() {
        DatePicker picker = new DatePicker(getContext(), type);
        picker.setStartDate(startDate);
        picker.setYearLimt(yearLimt);
        picker.setOnChangeLisener(this);
        picker.init();
        return picker;
    }

    int status = 1;
    Date start_time = null;
    Date end_time = null;

    private void initView() {
        this.text_team_success = findViewById(R.id.text_team_success);
        this.text_team_cancel = findViewById(R.id.text_team_cancel);
        this.wheelLayout = findViewById(R.id.wheelLayout);
        this.text_start = findViewById(R.id.text_start);
        this.text_stop = findViewById(R.id.text_stop);
        this.view_start = findViewById(R.id.view_start);
        this.view_stop = findViewById(R.id.view_stop);

        mDatePicker = getDatePicker();
        this.wheelLayout.addView(mDatePicker);

        this.text_team_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.text_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 2;
                text_stop.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                text_start.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_main));
                start_time = mDatePicker.getSelectDate();
                wheelLayout.removeAllViews();
                mDatePicker = getDatePicker();
                wheelLayout.addView(mDatePicker);
            }
        });
        this.text_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 1;
                text_start.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                text_stop.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_main));
                end_time = mDatePicker.getSelectDate();
                wheelLayout.removeAllViews();
                mDatePicker = getDatePicker();
                wheelLayout.addView(mDatePicker);
            }
        });

        this.text_team_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onSureLisener != null) {
                    if (start_time == null) {
                        start_time = mDatePicker.getSelectDate();
                    }
                    if (end_time == null) {
                        end_time = mDatePicker.getSelectDate();
                    }
                    onSureLisener.onSureTwo(start_time, end_time);
                }
            }
        });
    }

    private void initParas() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = DateUtils.getScreenWidth(getContext());
        getWindow().setAttributes(params);
    }

    @Override
    public void onChanged(Date date) {

        if (onChangeLisener != null) {
            onChangeLisener.onChanged(date);
        }

        if (!TextUtils.isEmpty(format)) {
            String messge = "";
            try {
                messge = new SimpleDateFormat(format).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (status == 1)
                text_start.setText(messge);
            else
                text_stop.setText(messge);
        }
    }


}
