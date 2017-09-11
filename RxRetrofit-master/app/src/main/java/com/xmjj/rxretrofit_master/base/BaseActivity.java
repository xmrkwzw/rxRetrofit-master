package com.xmjj.rxretrofit_master.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.util.ActivityManagerUtils;
import com.xmjj.rxretrofit_master.R;

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

	protected <T extends View> T findView(int id) {
		return (T) super.findViewById(id);
	}
	//切换Fragment
	public void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,fragment).commit();
	}

	// 获取当前fragment
	public Fragment getCurrentFragment(){
		Fragment currentFragment=getSupportFragmentManager().findFragmentById(R.id.frame_content);
		return  currentFragment;
	}
}
