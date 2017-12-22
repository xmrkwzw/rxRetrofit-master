package com.xmjj.rxretrofit_master.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.IOException;

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

	public static Uri bitmap2Uri(Context context, Bitmap bitmap){
		Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null,null));
		return uri ;
	}


	public static Bitmap uri2Bitmap(Context context , Uri uri) throws IOException {
		Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
		return bitmap;
	}
}
