package com.example.administrator.cbkproject.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/22.
 */
public class StringUtils {
    public static String parseTime(long sec) {

        Log.e("time",sec+"");
        String time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        time = dateFormat.format(new Date(sec));
        return time;
    }
}
