package com.wt.weiutils.time.listener;


import com.wt.weiutils.time.bean.DateBean;
import com.wt.weiutils.time.bean.DateType;

/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
