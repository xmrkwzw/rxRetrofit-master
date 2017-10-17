package com.xmjj.rxretrofit_master.activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.umeng.social.tool.UMImageMark;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
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
import com.xmjj.rxretrofit_master.util.ShareUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class MdActivity extends BaseActivity {
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.navigation_view)
	NavigationView navigationView;
	@BindView(R.id.drawer_layout)
	DrawerLayout drawerLayout;
	@BindView(R.id.fb_share)
	FloatingActionButton fbShare;
	@BindView(R.id.fb_login)
	FloatingActionButton fbLogin;

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

	@OnClick({R.id.fb_share,R.id.fb_login})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fb_share:
				share();
				break;

			case R.id.fb_login:
				startActivity(new Intent(this,AuthActivity.class));
				break;
		}
	}

	private void share() {
		ShareBoardConfig config = new ShareBoardConfig();
		config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
		config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE);
		UMWeb umWeb = new UMWeb("http://www.jianshu.com/p/431f12648da0");
		/*添加图片水印*/
		UMImageMark umImageMark = new UMImageMark();
		umImageMark.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		umImageMark.setMarkBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.tuite));
		umImageMark.setAlpha(0.2f);//设置透明度

		UMImage thumb = new UMImage(this, R.drawable.sean, umImageMark);
		umWeb.setTitle("my app");//标题
		umWeb.setThumb(thumb);  //缩略图
		umWeb.setDescription("rxjava+retrofit二次封装");//描述
		new ShareAction(this)
				.withText("my app")
				.withMedia(umWeb)
				.setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.ALIPAY, SHARE_MEDIA.SMS)
				.setCallback(ShareUtils.getInstance().getListener())
				.open(config);
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
								//	f = cameraFragment;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingActivity.class));
		} else if (id == R.id.action_about) {
			switchFragment(aboutFragment);
			drawerLayout.closeDrawers();
		} else if (id == R.id.action_scan) {
			startActivity(new Intent(this, ZxingActivity.class));
		}


		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this**/
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 屏幕横竖屏切换时避免出现window leak的问题
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//shareAction.close();
	}
}
