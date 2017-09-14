package com.xmjj.rxretrofit_master.base;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;


public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initSkinLoader();
		FlowManager.init(this);


	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

	}
	/**
	 * Must call init first
	 */
	private void initSkinLoader() {
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();
	}

}
