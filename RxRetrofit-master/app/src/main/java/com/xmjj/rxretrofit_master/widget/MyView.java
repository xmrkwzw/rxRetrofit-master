package com.xmjj.rxretrofit_master.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xmjj.jujianglibrary.util.DisplayUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.adapter.MyDegreeAdapter;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/28
 */

public class MyView extends View implements View.OnTouchListener {
	private int padding = 20;
	private float centerX, centerY;
	private float radius;
	private float angel = 0; //旋转的角度
	private Context context;

	public MyView(Context context) {
		super(context);
		init(context);

	}

	public MyView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);

	}

	public void init(Context context) {
		setOnTouchListener(this);
		this.context = context;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

		setMeasuredDimension(width, width);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawCircle(canvas);
		drawLine(canvas);
		drawBitmap(canvas);
	}


	public void drawCircle(Canvas canvas) {

		centerX = canvas.getWidth() / 2;
		centerY = canvas.getHeight() / 2;
		padding = DisplayUtils.dip2px(context,30);

		radius = canvas.getWidth() / 2 - padding;

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(centerX, centerY, radius, paint);
	}


	public void drawBitmap(Canvas canvas) {
		canvas.rotate(angel, centerX, centerY);
		Bitmap hourBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.crow);
		canvas.drawBitmap(hourBitmap, canvas.getWidth() / 2, canvas.getHeight() / 2 - hourBitmap.getHeight() / 2, null);
	}

	private void drawLine(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(3);
		paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
		canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, paint);

		canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), paint);
	}

	/**
	 * @param x
	 * @param y
	 */
	public void calcDegree(int x, int y) {
		int rx = (int) (x - centerX);
		int ry = (int) -(y - centerY);

		Point point = new Point(rx, ry);
		angel = MyDegreeAdapter.GetRadianByPos(point);
		Log.d("angle","angle2 = "+angel);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				calcDegree((int) event.getX(), (int) event.getY());
				postInvalidate();

				break;
			case MotionEvent.ACTION_MOVE:

				calcDegree((int) event.getX(), (int) event.getY());
				postInvalidate();

				break;
			case MotionEvent.ACTION_UP:

				calcDegree((int) event.getX(), (int) event.getY());
				postInvalidate();

				break;
		}

		return true;
	}


}
