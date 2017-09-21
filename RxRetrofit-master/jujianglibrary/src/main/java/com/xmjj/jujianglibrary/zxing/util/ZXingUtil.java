package com.xmjj.jujianglibrary.zxing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/21
 */

public class ZXingUtil {
	public static ZXingUtil instance;

	public static ZXingUtil getInstance() {
		if (instance == null) {
			instance = new ZXingUtil();
		}

		return instance;

	}


	//生成二维码
	public Bitmap create2Code(Context context, String content,int logoResId) {

		if (TextUtils.isEmpty(content)) {
			content = "hello world!";
		}
		Bitmap bmp = null;
		try {
			bmp = createTwoCode(content);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		Bitmap logoBmp = small(BitmapFactory.decodeResource(context.getResources(), logoResId));
		Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bmp, 0, 0, null);

		canvas.drawBitmap(logoBmp, bmp.getWidth() / 2 - logoBmp.getWidth() / 2, bmp.getHeight() / 2 - logoBmp.getHeight() / 2, null);
		return bitmap;
	}

	private static Bitmap small(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(0.4f, 0.4f);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	/**
	 * 方法说明：生成无图片二维码
	 */

	private Bitmap createTwoCode(String content) throws WriterException {
		BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 方法说明：生成有图片二维码
	 */

	private Bitmap createTwoCode(String str, int widthAndHeight) throws WriterException {
		Hashtable<DecodeHintType, String> hints = new Hashtable<>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

}
