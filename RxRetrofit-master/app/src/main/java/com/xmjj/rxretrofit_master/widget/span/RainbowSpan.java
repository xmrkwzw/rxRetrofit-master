package com.xmjj.rxretrofit_master.widget.span;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * 功能描述：
 * Created by wzw
 * 2017/11/27
 */
public class RainbowSpan extends CharacterStyle implements UpdateAppearance {
	private final int[] colors = new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.CYAN,Color.MAGENTA};

	@Override
	public void updateDrawState(TextPaint paint) {
		paint.setStyle(Paint.Style.FILL);
		Shader shader = new LinearGradient(0, 0, 0, paint.getTextSize() * colors.length, colors, null,
				Shader.TileMode.MIRROR);
		Matrix matrix = new Matrix();
		matrix.setRotate(90);
		shader.setLocalMatrix(matrix);
		paint.setShader(shader);
	}
}
