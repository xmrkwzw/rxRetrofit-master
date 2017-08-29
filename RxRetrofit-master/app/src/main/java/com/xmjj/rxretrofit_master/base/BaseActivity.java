package com.xmjj.rxretrofit_master.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.util.ActivityManagerUtils;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/17
 */

public abstract class BaseActivity extends RxAppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		ActivityManagerUtils.getInstance().addActivity(this);
		initViews();
		initData();
	}


	public abstract  int getLayoutResId() ;

	public abstract  void initViews();

	public abstract void initData();
}
