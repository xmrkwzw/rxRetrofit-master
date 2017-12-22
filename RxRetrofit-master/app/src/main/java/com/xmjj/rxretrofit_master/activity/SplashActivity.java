package com.xmjj.rxretrofit_master.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.service.DaemonService;
import com.xmjj.rxretrofit_master.service.PlayerMusicService;
import com.xmjj.rxretrofit_master.util.CommonUtils;
import com.xmjj.rxretrofit_master.util.JpushUtil;
import com.xmjj.rxretrofit_master.util.Md5Tool;
import com.xmjj.rxretrofit_master.util.SpringScaleInterpolator;

import java.util.HashSet;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/11
 */

public class SplashActivity extends BaseActivity {
	@BindView(R.id.iv_splash)
	ImageView ivSplash;
	@BindView(R.id.tv_welcome)
	TextView tvWelcome;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_splash;
	}

	@Override
	public void initViews() {
		CommonUtils.setFont(CommonUtils.getTypeface(this, "font/fonts.ttf"), tvWelcome);
		setAnimation();
	}
	/*注册极光推送的别名*/
	private void registerJPush() {
		JPushInterface.resumePush(getApplicationContext());
		String alias = JpushUtil.getAppKey(this);
		JpushUtil.setAlias(this, Md5Tool.hashKey(alias));

		java.util.Set<String> tags = new HashSet<>();
		tags.add("tags1");
		tags.add("tags2");
		tags.add("tags3");
		JpushUtil.setTags(this, tags);
	}
	/*保活服务启动*/
	private void keepLive() {
		startService(new Intent(this, PlayerMusicService.class));
		startService(new Intent(this, DaemonService.class));
	}

	private void setAnimation() {

		AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
		alphaAnimation.setDuration(1500);
		alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				onInterpolator();

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		ivSplash.startAnimation(alphaAnimation);
		tvWelcome.startAnimation(alphaAnimation);

	}

	private void onInterpolator() {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(tvWelcome, "scaleX", 1.0f, 1.5f);
		ObjectAnimator animatorY = ObjectAnimator.ofFloat(tvWelcome, "scaleY", 1.0f, 1.5f);
		AnimatorSet set = new AnimatorSet();
		set.setDuration(2000);
		set.setInterpolator(new SpringScaleInterpolator(0.2f));
		set.playTogether(animatorX, animatorY);
		set.start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, MdActivity.class));
				finish();
			}
		}, 2000);


	}

	@Override
	public void initData() {
		registerJPush();
		keepLive();
		if (Build.VERSION.SDK_INT >= 23) {
			String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
					, Manifest.permission.ACCESS_FINE_LOCATION
					, Manifest.permission.CALL_PHONE
					, Manifest.permission.READ_LOGS
					, Manifest.permission.READ_PHONE_STATE
					, Manifest.permission.READ_EXTERNAL_STORAGE
					, Manifest.permission.SET_DEBUG_APP
					, Manifest.permission.SYSTEM_ALERT_WINDOW
					, Manifest.permission.GET_ACCOUNTS
					, Manifest.permission.WRITE_APN_SETTINGS
					, Manifest.permission.CAMERA
					, Manifest.permission.RECEIVE_BOOT_COMPLETED};
			ActivityCompat.requestPermissions(this, mPermissionList, 123);

		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {


	}

	
}
