package com.xmjj.rxretrofit_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xmjj.rxretrofit_master.util.DisplayUtils;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.adapter.MyDegreeAdapter;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/28
 */

public class MyClockView extends View implements View.OnTouchListener {
	private int padding = 20;
	private float centerX, centerY;
	private float radius;
	private float mAngel = 0; //分针旋转的角度
	private float hAngel = 0;//时针旋转的角度
	private Context context;

	public MyClockView(Context context) {
		super(context);
		init(context);

	}

	public MyClockView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
		drawLines(canvas);
		drawHours(canvas);
		drawMinute(canvas);
		drawCenter(canvas);
		//drawXY(canvas);
	}


	/*绘制圆形表盘*/
	public void drawCircle(Canvas canvas) {

		centerX = canvas.getWidth() / 2;
		centerY = canvas.getHeight() / 2;
		padding = DisplayUtils.dip2px(context, 30);

		radius = canvas.getWidth() / 2 - padding;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(20);
		paint.setStyle(Paint.Style.STROKE);//设置非填充的样式
		canvas.drawCircle(centerX, centerY, radius, paint);
	}

	/*画表盘刻度*/
	public void drawLines(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);

		int count = 12;
		int angle = 360 / 12;
		float startX = canvas.getWidth() - padding;
		float startY = centerY;
		float stopX = startX - DisplayUtils.dip2px(context, 30);
		float stopY = startY;

		/*画时钟刻度*/
		for (int i = 0; i < count; i++) {
			canvas.drawLine(startX, startY, stopX, stopY, paint);
			//通过旋转画布的方式循环绘画
			canvas.rotate(angle, centerX, centerY);
		}
		/*将角度调整*/
		for (int i = 0; i < count; i++) {
			canvas.rotate(-angle, centerX, centerY);
		}

		float startX2 = canvas.getWidth() - padding;
		float startY2 = centerY;
		float stopX2 = startX - DisplayUtils.dip2px(context, 15);
		float stopY2 = startY;
		paint.setStrokeWidth(3);
		/*画分钟刻度*/
		int angle2 = 360 / 60;
		int count2 = 60;
		for (int i = 0; i < count2; i++) {
			canvas.drawLine(startX2, startY2, stopX2, stopY2, paint);
			canvas.rotate(angle2, centerX, centerY);
		}
		for (int i = 0; i < count2; i++) {
			canvas.rotate(-angle2, centerX, centerY);
		}


	}

	/*画时针*/
	public void drawHours(Canvas canvas) {
		canvas.rotate(hAngel, centerX, centerY);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(15);
		float length = canvas.getWidth() - DisplayUtils.dip2px(context, 90);
		canvas.drawLine(centerX, centerY, length, centerY, paint);
	}

	/*画分针*/
	public void drawMinute(Canvas canvas) {
		canvas.rotate(mAngel - hAngel, centerX, centerY);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10);
		float length = canvas.getWidth() - DisplayUtils.dip2px(context, 70);
		canvas.drawLine(centerX, centerY, length, centerY, paint);
	}


	/*画中间的圆形*/
	private void drawCenter(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		float x = centerX;
		float y = centerY;
		float radius = DisplayUtils.dip2px(context, 10);
		canvas.drawCircle(x, y, radius, paint);
	}

	/*画x y轴*/
	private void drawXY(Canvas canvas) {
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
		mAngel = MyDegreeAdapter.getAngle(point);
		//还需要处理bug,只是做个简单的设置。
		hAngel = mAngel/12;
		Logger.d("degree = after" + mAngel+"-----"+hAngel);
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
