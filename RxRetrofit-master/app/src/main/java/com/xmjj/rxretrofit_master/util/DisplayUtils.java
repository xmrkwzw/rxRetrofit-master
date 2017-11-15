package com.xmjj.rxretrofit_master.util;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.TextView;

/**
 * 功能描述:屏幕工具类
 * Created by huangxy on 2016/10/8.
 */

public class DisplayUtils {
    private static float mMinTextSize = 20;

    // Text view line spacing multiplier
    private static float mSpacingMult = 1.0f;

    // Text view additional line spacing
    private static float mSpacingAdd = 0.0f;
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /** dip转换px */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static float findNewTextSize(TextView textView, int width, int height, CharSequence text) {
        TextPaint textPaint = new TextPaint(textView.getPaint());

        float targetTextSize = textPaint.getTextSize();

        int textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        while(textHeight > height && targetTextSize > mMinTextSize) {
            targetTextSize = Math.max(targetTextSize - 1, mMinTextSize);
            textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        }
        return targetTextSize;
    }

    private static int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        paint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(source, paint, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getHeight();
    }
}
