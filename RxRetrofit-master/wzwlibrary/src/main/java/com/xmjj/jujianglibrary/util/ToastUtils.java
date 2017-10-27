package com.xmjj.jujianglibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 功能描述:
 * Created by huangxy on 2016/8/9.
 */

public class ToastUtils {
    public static void showShortMes(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }public static void showLongMes(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
