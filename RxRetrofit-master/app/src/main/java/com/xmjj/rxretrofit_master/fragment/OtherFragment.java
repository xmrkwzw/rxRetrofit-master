package com.xmjj.rxretrofit_master.fragment;

import android.webkit.WebView;

import com.xmjj.jujianglibrary.util.WebViewUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class OtherFragment extends BaseFragment {
	@BindView(R.id.webview)
	WebView webView;
	private static final String URL = "http://www.jianshu.com/p/431f12648da0";

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_other;
	}

	@Override
	public void initViews() {
		webView = findView(R.id.webview);
	}

	@Override
	public void initData() {
		WebViewUtils.getInstance().initWebView(webView, URL);
	}

	public boolean canGoBack() {
		return webView != null && webView.canGoBack();
	}

	public void goBack() {
		if (webView != null) {
			webView.goBack();
		}
	}
}
