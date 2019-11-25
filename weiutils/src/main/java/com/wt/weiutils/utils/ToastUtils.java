package com.wt.weiutils.utils;


import android.content.Context;
import android.view.Gravity;

import com.sdsmdg.tastytoast.TastyToast;

public class ToastUtils {
    private static int LENGTH = TastyToast.LENGTH_LONG;

//    public static void showToast(Context context, String text) {
////        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
//        CustomToast.showToast(context, Gravity.CENTER, 0, text);
//    }

    /**
     * 错误吐司
     */
    public static void showErrorToast(Context context,String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.ERROR);
        CustomToast.showToast(context, Gravity.CENTER, 0, msg);
    }

    public static void showErrorToast(Context context,int msg) {
//        TastyToast.makeText(context, ValuesUtils.getString(Apps.getAppContext(), msg), LENGTH, TastyToast.ERROR);
        CustomToast.showToast(context, Gravity.CENTER, 0, ValuesUtils.getString(context, msg));
    }

//    /**
//     * 成功吐司
//     */
//    public static void showSuccessToast(String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.SUCCESS);
//    }
//
//    /**
//     * 警告吐司
//     */
//    public static void showWarningToast(String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.WARNING);
//    }
//
    /**
     * 困惑吐司
     */
    public static void showConfusingToast(Context context,String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.CONFUSING);
        CustomToast.showToast(context, Gravity.CENTER, 0, msg);
    }
//
//    /**
//     * 默认吐司
//     */
//    public static void showDefaultToast(String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.DEFAULT);
//    }

    /**
     * 信息吐司
     */
    public static void showInfoToast(Context context,String msg) {
//        TastyToast.makeText(context, msg, LENGTH, TastyToast.INFO);
        CustomToast.showToast(context, Gravity.CENTER, 0, msg);
    }


}
