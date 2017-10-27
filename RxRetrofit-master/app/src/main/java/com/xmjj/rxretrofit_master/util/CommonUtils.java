package com.xmjj.rxretrofit_master.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public class CommonUtils {

	public static Typeface getTypeface(Context context,String font) {
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), font);
		return typeface;
	}

	/**
	 * 设置字体
	 *
	 * @param typeface
	 * @param textViews
	 */
	public static void setFont(Typeface typeface, TextView... textViews) {
		for (TextView textView : textViews) {
			textView.setTypeface(typeface);
		}
	}
}
