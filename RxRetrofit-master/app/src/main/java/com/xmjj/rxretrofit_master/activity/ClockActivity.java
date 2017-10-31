package com.xmjj.rxretrofit_master.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/28
 */

public class ClockActivity extends BaseActivity {

	public int getLayoutResId() {
		return R.layout.activity_clock;
	}

	@Override
	public void initViews() {
		Bitmap clockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
		Bitmap hourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour);
		Bitmap minuteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minute);


	}

	@Override
	public void initData() {

	}




}
