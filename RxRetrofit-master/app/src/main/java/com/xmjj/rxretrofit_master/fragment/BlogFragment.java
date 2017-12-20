package com.xmjj.rxretrofit_master.fragment;

import android.webkit.WebView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.util.WebViewUtils;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class BlogFragment extends BaseFragment {
	@BindView(R.id.webview)
	WebView webView;
	private  String URL = "http://www.jianshu.com/p/fde3eeb4e778";

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

		WebViewUtils.getInstance().initWebView(webView, URL,false);
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
