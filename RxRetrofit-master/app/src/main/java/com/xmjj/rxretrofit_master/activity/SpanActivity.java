package com.xmjj.rxretrofit_master.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Property;
import android.widget.TextView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.view.span.FrameSpan;
import com.xmjj.rxretrofit_master.view.span.MutableForegroundColorSpan;
import com.xmjj.rxretrofit_master.view.span.RainbowSpan;
import com.xmjj.rxretrofit_master.view.span.TypeWriterSpanGroup;
import com.xmjj.rxretrofit_master.view.span.VerticalImageSpan;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/11/27
 */

public class SpanActivity extends BaseActivity {
	@BindView(R.id.tv_frame)
	TextView tvFrame;
	@BindView(R.id.tv_vertical)
	TextView tvVertical;
	@BindView(R.id.tv_rainbow)
	TextView tvRainbow;
	@BindView(R.id.tv_write)
	TextView tvWrite;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_span;
	}

	@Override
	public void initViews() {
		setTvFrame();
		setTvVertical();
		setTvRainbow();
		setTvWrite();
	}

	@Override
	public void initData() {

	}


	private void setTvFrame() {
		SpannableString spannableString = new SpannableString(tvFrame.getText());

		spannableString.setSpan(new FrameSpan(), 0, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tvFrame.setText(spannableString);
	}

	private void setTvVertical() {
		SpannableString spannableString = new SpannableString(tvVertical.getText());

		Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
		drawable.setBounds(0, 0, 50, 50);
		spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tvVertical.setText(spannableString);

	}


	private void setTvRainbow() {
		SpannableString spannableString = new SpannableString(
				tvRainbow.getText());
		spannableString.setSpan(new RainbowSpan(), 4, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tvRainbow.setText(spannableString);
	}

	private void setTvWrite(){
		String val = tvWrite.getText().toString();
		final SpannableString spannableString = new SpannableString(val);
		// 添加Span
		final TypeWriterSpanGroup group = new TypeWriterSpanGroup(0);
		for(int index = 0 ; index <= val.length()-1 ; index++) {
			MutableForegroundColorSpan span = new MutableForegroundColorSpan();
			group.addSpan(span);
			spannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		spannableString.setSpan(new RainbowSpan(),
				0, val.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		tvWrite.setText(spannableString);
		// 添加动画
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(group, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f);
		objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//refresh
				tvWrite.setText(spannableString);
			}
		});
		objectAnimator.setDuration(5000);
		objectAnimator.setRepeatCount(10);
		objectAnimator.start();
	}

	private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY =
			new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
				@Override
				public void set(TypeWriterSpanGroup spanGroup, Float value) {
					spanGroup.setAlpha(value);
				}
				@Override
				public Float get(TypeWriterSpanGroup spanGroup) {
					return spanGroup.getAlpha();
				}
			};
}
