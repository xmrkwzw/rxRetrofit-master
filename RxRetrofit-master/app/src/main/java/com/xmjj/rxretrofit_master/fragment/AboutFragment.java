package com.xmjj.rxretrofit_master.fragment;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import butterknife.BindView;

import static com.xmjj.rxretrofit_master.R.id.tv_content;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class AboutFragment extends BaseFragment {
	@BindView(tv_content)
	TextView tvContent;

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_about;
	}

	@Override
	public void initViews() {
		tvContent = findView(tv_content);
		tvContent.setAutoLinkMask(Linkify.ALL);
		tvContent.setMovementMethod(LinkMovementMethod
				.getInstance());
	}

	@Override
	public void initData() {

	}
}
