package com.wushuikeji.www.yuyubuyer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jack_chentao
 * @time 2016/10/19 0019 下午 2:03.
 * @des ${TODO}
 */
public class DateUtils {

    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     *
     * @return
     */
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
