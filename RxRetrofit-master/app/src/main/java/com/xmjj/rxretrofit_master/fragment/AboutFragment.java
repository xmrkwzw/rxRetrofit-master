package com.xmjj.rxretrofit_master.fragment;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class AboutFragment extends BaseFragment {
	@Override
	public int getLayoutResId() {
		return R.layout.fragment_about;
	}
	@Override
	public void initViews() {
		TextView tv_content = findView(R.id.tv_content);
		tv_content.setAutoLinkMask(Linkify.ALL);
		tv_content.setMovementMethod(LinkMovementMethod
				.getInstance());
	}

	@Override
	public void initData() {

	}
}
