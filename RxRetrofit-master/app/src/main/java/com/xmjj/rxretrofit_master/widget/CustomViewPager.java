package com.xmjj.rxretrofit_master.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * @description 自定义ViewPager
 * @version v1.00 2015年3月14日 caishuxing 初版
 */
public class CustomViewPager extends ViewPager {
	


	public CustomViewPager(Context context) {
		super(context);
	}

	@SuppressWarnings("deprecation")
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}

	private boolean isSlipping = true;//可滑动标志位

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!isSlipping) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!isSlipping) {
			return false;
		}
		return super.onTouchEvent(arg0);
	}

	/**
	 *@Title: setSlipping
	 *@Description: TODO设置ViewPager是否可滑动
	 *@param isSlipping
	 */
	public void setSlipping(boolean isSlipping) {
		this.isSlipping = isSlipping;
	}
}
