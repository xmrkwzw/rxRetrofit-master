package com.xmjj.rxretrofit_master.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.fragment.AboutFragment;
import com.xmjj.rxretrofit_master.fragment.DbFlowFragment;
import com.xmjj.rxretrofit_master.fragment.GlideFragment;
import com.xmjj.rxretrofit_master.fragment.NetFragment;
import com.xmjj.rxretrofit_master.fragment.OtherFragment;
import com.xmjj.rxretrofit_master.fragment.RxbusFragment;
import com.xmjj.rxretrofit_master.fragment.ViewFragment;

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


	@Override
	public int getLayoutResId() {
		return R.layout.activity_md;
	}

	@Override
	public void initViews() {
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

		switchFragment(netFragment);
	}
	private long lastBackKeyDownTick = 0;
	public static final long MAX_DOUBLE_BACK_DURATION = 1500;
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
			}
		}

		long currentTick = System.currentTimeMillis();
		if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
			ToastUtils.showShortMes(this,"再按一次退出");
			lastBackKeyDownTick = currentTick;
		} else {
			finish();
			System.exit(0);
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

						}
						switchFragment(f);
						menuItem.setChecked(true);
						drawerLayout.closeDrawers();
						return true;
					}
				});
	}



}
