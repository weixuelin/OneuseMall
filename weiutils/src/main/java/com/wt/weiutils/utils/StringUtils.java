package com.wt.weiutils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    public static boolean isNotEmptyWithTrim(String pStr) {
        return (pStr != null && pStr.trim().length() > 0);
    }

    /**
     *
     * @param pStr
     * @return
     */
    public static boolean isEmptyWithTrim(String pStr) {
        return (pStr == null || pStr.trim().length() == 0);
    }

    /**
     * 替换字符串中空格
     *
     * @param pStr
     * @return "" is pStr is null
     */
    public static String replaceAllSpace(String pStr) {
        if (isNotEmptyWithTrim(pStr)) {
            return pStr.replace(" ", "");
        }
        return "";
    }

    /**
     * 测用户邮箱是否正确
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 测手机号(11)
     */
    public static boolean isPhone(String number) {
        if (number != null) {
            return number.replace(" ", "").matches("1[0-9]{10}");
        }
        return false;
    }

    public static boolean isNotEmptyString(String... s) {
        for (String v : s) {
            if (StringUtils.isNotEmptyWithTrim(v))
                return true;
        }
        return false;
    }


    public static String getMaskNumber(String phoneNumber){
       return phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7,phoneNumber.length());
    }

}
