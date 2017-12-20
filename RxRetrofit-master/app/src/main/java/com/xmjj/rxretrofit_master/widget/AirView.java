package com.xmjj.rxretrofit_master.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.adapter.MyDegreeAdapter;
import com.xmjj.rxretrofit_master.util.DisplayUtils;

/**
 * 功能描述：
 * Created by wzw
 * 2017/11/30
 */

public class AirView extends View implements View.OnTouchListener {
	private Context context;
	private int lightBlue;
	private float mAngel = 0; //旋转的角度
	private float centerX, centerY;
	private RectF rectF = new RectF();
	private int[] colors;
	private static final float CURSOR_START_ANGLE = 30;//圆形游标开始的位置
	private static final float START_ANGLE= -150;//进度条开始的位置
	private static final float SWEEP_ANGLE = 120;//进度条扫过的角度

	public AirView(Context context) {
		super(context);
		init(context);
	}

	public AirView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AirView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}


	private void init(Context context) {
		this.context = context;
		setOnTouchListener(this);
		lightBlue = getResources().getColor(R.color.light_blue);
		colors = new int[]{Color.rgb(231, 249, 255),
				Color.rgb(206, 241, 255),
				Color.rgb(163, 229, 255),
				Color.rgb(133, 221, 255),
				Color.rgb(73, 204, 255)};

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

		setMeasuredDimension(width, width);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//取消硬件加速阴影效果才有效
		setLayerType(View.LAYER_TYPE_SOFTWARE,null);
		initRect(canvas);
		drawBg(canvas);
		drawCursor(canvas);

	}

	private void initRect(Canvas canvas) {
		int padding = DisplayUtils.dip2px(context, 20);
		rectF.set(padding, padding, canvas.getWidth() - 2 * padding, canvas.getHeight() - 2 * padding);

		centerX = rectF.centerX();
		centerY = rectF.centerY();
	}

	private void drawBg(Canvas canvas) {

		int strokeWidth = DisplayUtils.dip2px(context, 7);
		Paint paint = new Paint();
		paint.setStrokeWidth(strokeWidth);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.LTGRAY);

		/*draw bg*/
		canvas.drawArc(rectF, START_ANGLE, SWEEP_ANGLE, false, paint);
		/*draw progress*/
		paint.setColor(lightBlue);
		paint.setStyle(Paint.Style.STROKE);

		paint.setShader(new LinearGradient(rectF.left, (float) (canvas.getHeight() / 2), rectF.right, (float) (canvas.getHeight() / 2), colors, null, Shader.TileMode.MIRROR));
		canvas.drawArc(rectF, START_ANGLE, SWEEP_ANGLE+ mAngel, false, paint);


	}

	private void drawCursor(Canvas canvas) {

		/*draw white circle*/
		int radius = DisplayUtils.dip2px(context, 11);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setShadowLayer(10f,1f,1f,Color.DKGRAY);
		//
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(5);


		canvas.drawLine(rectF.left,centerY,rectF.right,centerY,paint);
		canvas.drawLine(centerX,rectF.top,centerX,rectF.bottom,paint);
		canvas.drawCircle(centerX,centerY,10,paint);
		canvas.rotate(mAngel - CURSOR_START_ANGLE, centerX, centerY);
		canvas.drawCircle(rectF.right, canvas.getHeight() / 2, radius, paint);

	}

	/**
	 * @param x
	 * @param y
	 */
	public void calcDegree(float x, float y) {
		float rx = (x - centerX);
		float ry = -(y - centerY);

		float disX= (float) (rectF.width()/4*Math.sqrt(3));
		Logger.d("rx ="+rx+"---ry ="+ry+"---disX ="+disX);
		if(ry>0){
			if(rx<=0&&rx>=-disX||rx>0&&rx<disX){
				Point point = new Point((int)rx,(int) ry);
				mAngel = MyDegreeAdapter.getAngle(point) + CURSOR_START_ANGLE;
				postInvalidate();
				Logger.d("degree = after" + mAngel);
			}
		}


	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				calcDegree(event.getX(),event.getY());


				break;
			case MotionEvent.ACTION_MOVE:

				calcDegree(event.getX(), event.getY());


				break;
			case MotionEvent.ACTION_UP:

				calcDegree( event.getX(),event.getY());


				break;
		}

		return true;
	}
}
