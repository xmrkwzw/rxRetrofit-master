package com.xmjj.rxretrofit_master.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.util.SpringScaleInterpolator;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/22
 */

public class AnimationActivity extends BaseActivity  {
	@BindView(R.id.cv_bg)
	ImageView cvBg;
	@BindView(R.id.btn_interpolator)
	Button btnInterpolator;
	@BindView(R.id.btn_round)
	Button btnRound;
	@BindView(R.id.btn_springAnimation)
	Button btnSpringAnimation;
	@BindView(R.id.btn_twitter)
	Button btnTwitter;
	@BindView(R.id.sb)
	SeekBar seekBar;

	private float factor;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_animation;
	}

	@Override
	public void initViews() {


		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _seekBar, int progress, boolean fromUser) {
				DecimalFormat df = new DecimalFormat("######0.00");
				String num = df.format((float)progress/seekBar.getMax());//返回的是String类型
				factor = Float.parseFloat(num);
				Logger.d("factor " + factor + "");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}

	@Override
	public void initData() {

	}

	@OnClick({R.id.btn_interpolator, R.id.btn_round, R.id.btn_springAnimation,R.id.btn_twitter})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_interpolator:
				onInterpolator();
				break;
			case R.id.btn_round:
				onRound();
				break;
			case R.id.btn_springAnimation:
				break;
			case R.id.btn_twitter:
				onTwitter();
				break;
		}
	}

	private void onInterpolator() {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(cvBg, "scaleX", 1.0f, 1.5f);
		ObjectAnimator animatorY = ObjectAnimator.ofFloat(cvBg, "scaleY", 1.0f, 1.5f);
		AnimatorSet set = new AnimatorSet();
		set.setDuration(2000);
		set.setInterpolator(new SpringScaleInterpolator(factor));
		set.playTogether(animatorX, animatorY);
		set.start();

	}

	private void onTwitter() {
		final ScaleAnimation animation2 = new ScaleAnimation(0.7f, 5f, 0.7f, 5f, Animation.RELATIVE_TO_SELF,0.5f ,Animation.RELATIVE_TO_SELF,0.5f);
		animation2.setDuration(500);


		ScaleAnimation animation = new ScaleAnimation(1f, 0.7f, 1f, 0.7f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animation.setDuration(900);
		animation.setFillBefore(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				cvBg.startAnimation(animation2);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		cvBg.startAnimation(animation);


	}

	private void onRound(){
		SpringSystem springSystem =SpringSystem.create();
		Spring spring = springSystem.createSpring();
		spring.setCurrentValue(0.5f);
		spring.setSpringConfig(new SpringConfig(50,6));
		spring.addListener(new SimpleSpringListener(){
			@Override
			public void onSpringUpdate(Spring spring) {
				super.onSpringUpdate(spring);
				float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
				cvBg.setScaleX(mappedValue);
				cvBg.setScaleY(mappedValue);

			}
		});

		spring.setEndValue(1.5f);
	}

	private void onSpringAnimation(){

	}
}
