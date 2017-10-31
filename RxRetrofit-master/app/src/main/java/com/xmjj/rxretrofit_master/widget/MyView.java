package com.xmjj.rxretrofit_master.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xmjj.rxretrofit_master.R;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/28
 */

public class MyView extends View {
	private int pedding =10;
	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
		setMeasuredDimension(width,width);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLUE);
		drawCircle(canvas);
		drawBitmap(canvas);
	}


	public void drawCircle(Canvas canvas){

		float centerX = canvas.getWidth()/2 ;
		float centerY = canvas.getHeight()/2;
		float radius = canvas.getWidth()/2 ;
		setPadding(pedding,pedding,pedding,pedding);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(centerX,centerY,radius,paint);
	}


	public void drawBitmap(Canvas canvas){
		Bitmap hourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour);
		canvas.drawBitmap(hourBitmap,canvas.getWidth()/2,canvas.getHeight()/2,null);
	}
}
