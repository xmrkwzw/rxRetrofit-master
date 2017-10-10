package com.xmjj.rxretrofit_master.util;


import android.view.animation.Interpolator;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/22
 */

public class SpringScaleInterpolator implements Interpolator {
	private float factor;

	public SpringScaleInterpolator(float factor) {
		this.factor = factor;
	}

	@Override
	public float getInterpolation(float input) {
		return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI / factor) + 1));
	}
}
