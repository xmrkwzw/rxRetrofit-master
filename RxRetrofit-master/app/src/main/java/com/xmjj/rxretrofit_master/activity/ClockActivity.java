package com.xmjj.rxretrofit_master.activity;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.widget.MyClockView;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/28
 */

public class ClockActivity extends BaseActivity {

	@BindView(R.id.clock)
	MyClockView clock;

	public int getLayoutResId() {
		return R.layout.activity_clock;
	}

	@Override
	public void initViews() {
		clock.init(this);


	}

	@Override
	public void initData() {

	}


}
