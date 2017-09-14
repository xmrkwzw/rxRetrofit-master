package com.xmjj.rxretrofit_master.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;
import com.xmjj.jujianglibrary.util.ActivityManagerUtils;
import com.xmjj.jujianglibrary.util.DialogUtils;
import com.xmjj.jujianglibrary.util.FileUtils;
import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.jujianglibrary.util.skinloader.listener.ILoaderListener;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.entity.event.SkinEvent;
import com.xmjj.rxretrofit_master.fragment.AboutFragment;
import com.xmjj.rxretrofit_master.fragment.CameraFragment;
import com.xmjj.rxretrofit_master.fragment.DbFlowFragment;
import com.xmjj.rxretrofit_master.fragment.GlideFragment;
import com.xmjj.rxretrofit_master.fragment.NetFragment;
import com.xmjj.rxretrofit_master.fragment.OtherFragment;
import com.xmjj.rxretrofit_master.fragment.RxbusFragment;
import com.xmjj.rxretrofit_master.fragment.SkinFragment;
import com.xmjj.rxretrofit_master.fragment.ViewFragment;

import java.io.File;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class MdActivity extends BaseActivity {
	private Toolbar toolbar;
	private NavigationView navigationView;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
	private BaseFragment f = null;
	private NetFragment netFragment;
	private DbFlowFragment dbFlowFragment;
	private GlideFragment glideFragment;
	private RxbusFragment rxbusFragment;
	private OtherFragment otherFragment;
	private AboutFragment aboutFragment;
	private ViewFragment viewFragment;
	private CameraFragment cameraFragment;
	private SkinFragment skinFragment;

	private static String SKIN_DIR;
	/*皮肤名*/
	private static final String SKIN_GREEN = "skin_green.skin";
	private static final String SKIN_BLACK = "skin_black.skin";
	public static final int GREEN = 0;
	public static final int BLUE = 1;
	public static final int BLACK = 2;


	@Override
	public int getLayoutResId() {
		return R.layout.activity_md;
	}

	@Override
	public void initViews() {
		RxBus.getDefault().register(this);
		toolbar = findView(R.id.toolbar);
		navigationView = findView(R.id.navigation_view);
		drawerLayout = findView(R.id.drawer_layout);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		setupDrawerContent(navigationView);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawerLayout.setDrawerListener(mDrawerToggle);
		toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
		dynamicAddSkinEnableView(toolbar, "background", R.color.colorPrimary);
		dynamicAddSkinEnableView(navigationView.getHeaderView(0), "background", R.color.colorPrimary);
		dynamicAddSkinEnableView(navigationView, "navigationViewMenu", R.color.colorPrimary);

	}

	@Override
	public void initData() {
		netFragment = new NetFragment();
		glideFragment = new GlideFragment();
		dbFlowFragment = new DbFlowFragment();
		aboutFragment = new AboutFragment();
		otherFragment = new OtherFragment();
		rxbusFragment = new RxbusFragment();
		viewFragment = new ViewFragment();
		cameraFragment = new CameraFragment();
		skinFragment = new SkinFragment();
		switchFragment(netFragment);
		SKIN_DIR = FileUtils.getSkinDirPath(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RxBus.getDefault().unRegister(this);
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
			drawerLayout.closeDrawer(Gravity.LEFT);
			return;
		}
		Fragment mCurrentFragment = getCurrentFragment();
		if (mCurrentFragment instanceof OtherFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
			OtherFragment otherFragment = (OtherFragment) mCurrentFragment;
			if (otherFragment.canGoBack()) {
				otherFragment.goBack();
				return;
			} else {
				exit();
			}
		} else {
			exit();
		}

	}

	public void exit() {
		DialogUtils.getInstance()
				.showDialog(this, new Listener())
				.setMessage("确认要退出程序吗?");


	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void changeSkin(SkinEvent event) {
		if (event != null) {
			int skin = event.skin;
			switch (skin) {
				case GREEN:
					changeSkin(SKIN_GREEN);
					break;
				case BLUE:
					SkinManager.getInstance().restoreDefaultTheme();
					break;

				case BLACK:
					changeSkin(SKIN_BLACK);
					break;
			}
		}
	}

	public class Listener extends DialogUtils.positiveListener {
		@Override
		public void onClick(View v) {
			super.onClick(v);
			ActivityManagerUtils.getInstance().AppExit(MdActivity.this);
		}
	}

	private void setupDrawerContent(NavigationView navigationView) {

		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {

						switch (menuItem.getItemId()) {

							case R.id.model_net:
								f = netFragment;
								break;
							case R.id.model_sql:
								f = dbFlowFragment;
								break;
							case R.id.model_rxbus:
								f = rxbusFragment;
								break;
							case R.id.model_view:
								f = viewFragment;
								break;
							case R.id.model_glide:
								f = glideFragment;
								break;
							case R.id.model_about:
								f = aboutFragment;
								break;
							case R.id.model_other:
								f = otherFragment;
								break;
							case R.id.model_camera2:
								f = cameraFragment;
								break;
							case R.id.model_skin:
								f = skinFragment;
								break;

						}
						switchFragment(f);
						menuItem.setChecked(true);
						drawerLayout.closeDrawers();
						return true;
					}
				});
	}

	public void changeSkin(String skinFile) {
		String skinFullName = SKIN_DIR + File.separator + skinFile;
		FileUtils.moveRawToDir(this, skinFile, skinFullName);
		File skin = new File(skinFullName);
		if (!skin.exists()) {
			ToastUtils.showShortMes(this, "请检查" + skinFullName + "是否存在");
			return;
		}
		SkinManager.getInstance().load(skin.getAbsolutePath(),
				new ILoaderListener() {
					@Override
					public void onStart() {
						Logger.d("onStart");
					}

					@Override
					public void onSuccess() {
						Logger.d("onSuccess");

					}

					@Override
					public void onFailed() {

						Logger.d("onFailed");
					}
				});
	}


}
