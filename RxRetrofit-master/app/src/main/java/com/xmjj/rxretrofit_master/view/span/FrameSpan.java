package com.xmjj.rxretrofit_master.view.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * 功能描述：
 * Created by wzw
 * 2017/11/27
 */

public class FrameSpan extends ReplacementSpan {
	private final Paint mPaint;
	private int mWidth;
	public FrameSpan() {
		mPaint = new Paint();
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
	}
	@Override
	public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
		//return text with relative to the Paint
		mWidth = (int) paint.measureText(text, start, end);
		return mWidth;
	}
	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
		//draw the frame with custom Paint
		canvas.drawRect(x, top, x + mWidth, bottom, mPaint);
		canvas.drawText(text, start, end, x, y, paint);
	}
}
