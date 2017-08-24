package com.xmjj.rxretrofit_master.base;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		FlowManager.init(this);

	}
}
