package com.xmjj.rxretrofit_master.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.util.ActivityManagerUtils;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.jujianglibrary.util.skinloader.attr.DynamicAttr;
import com.xmjj.jujianglibrary.util.skinloader.listener.IDynamicNewView;
import com.xmjj.jujianglibrary.util.skinloader.listener.ISkinUpdate;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinInflaterFactory;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;
import com.xmjj.jujianglibrary.util.skinloader.statusbar.StatusBarBackground;
import com.xmjj.rxretrofit_master.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/17
 */

public abstract class BaseActivity extends RxAppCompatActivity implements ISkinUpdate, IDynamicNewView {
	public int screenWidth, screenHeight;
	public int actionBarHeight;
	// 当前Activity是否需要响应皮肤更改需求
	private boolean isResponseOnSkinChanging = true;
	private SkinInflaterFactory mSkinInflaterFactory;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		mSkinInflaterFactory = new SkinInflaterFactory();
		LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		ActivityManagerUtils.getInstance().addActivity(this);
		getScreenSize();
		getActionBarHeight();
		ButterKnife.bind(this);
		initViews();
		initData();


	}


	public abstract int getLayoutResId();

	public abstract void initViews();

	public abstract void initData();

	protected <T extends View> T findView(int id) {
		return (T) super.findViewById(id);
	}

	//切换Fragment
	public void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
	}
	//切换Fragment
	public void switchFragment(android.app.Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.frame_content, fragment)
				.commit();
	}

	// 获取当前fragment
	public Fragment getCurrentFragment() {
		Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_content);
		return currentFragment;
	}

	/*获取屏幕大小*/
	public void getScreenSize() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获得手机的宽带和高度像素单位为px
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	public void getActionBarHeight() {
		TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
			Logger.d("actionBarHeight = "+actionBarHeight);
		}

	}
	@Override
	protected void onResume() {
		super.onResume();
		SkinManager.getInstance().attach(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SkinManager.getInstance().detach(this);
		mSkinInflaterFactory.clean();
	}

	@Override
	public void onThemeUpdate() {
		Log.i("SkinBaseActivity", "onThemeUpdate");
		if (!isResponseOnSkinChanging) {
			return;
		}
		mSkinInflaterFactory.applySkin();
		//  changeStatusColor();

//        //设置window的背景色
//        Drawable drawable = new ColorDrawable(SkinManager.getInstance().getColorPrimaryDark());
//        getWindow().setBackgroundDrawable(drawable);
	}

	public void changeStatusColor() {
		//如果当前的Android系统版本大于4.4则更改状态栏颜色
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Log.i("SkinBaseActivity", "changeStatus");
			int color = SkinManager.getInstance().getColorPrimaryDark();
			StatusBarBackground statusBarBackground = new StatusBarBackground(
					this, color);
			if (color != -1)
				statusBarBackground.setStatusBarbackColor();
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
	}

	public void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
	}

	protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
	}

	final protected void enableResponseOnSkinChanging(boolean enable) {
		isResponseOnSkinChanging = enable;
	}

}
