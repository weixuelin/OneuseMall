package com.wt.weiutils.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


public class ValuesUtils {

    public static String getString(Context context, int id) {
        String stringValue = context.getResources().getString(id);
        return StringUtils.isNotEmptyWithTrim(stringValue) ? stringValue : "";
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static int getDimen(Context context, int id) {
        return (int) context.getResources().getDimension(id);
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

}
