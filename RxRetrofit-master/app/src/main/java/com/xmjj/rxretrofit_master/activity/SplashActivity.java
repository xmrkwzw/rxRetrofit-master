package com.xmjj.rxretrofit_master.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/11
 */

public class SplashActivity extends BaseActivity {
	@BindView(R.id.iv_splash)
	ImageView ivSplash;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_splash;
	}

	@Override
	public void initViews() {
		onTwitter();
	}

	private void onTwitter() {
		final ScaleAnimation animation2 = new ScaleAnimation(0.7f, 10f, 0.7f, 10f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation2.setDuration(500);
		animation2.setFillAfter(true);
		animation2.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this, MdActivity.class));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});


		ScaleAnimation animation = new ScaleAnimation(1f, 0.7f, 1f, 0.7f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(900);
		animation.setFillBefore(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				ivSplash.startAnimation(animation2);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		ivSplash.startAnimation(animation);


	}

	@Override
	public void initData() {
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
					, Manifest.permission.WRITE_APN_SETTINGS};
			ActivityCompat.requestPermissions(this, mPermissionList, 123);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {


	}
}
