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

public class OtherFragment extends BaseFragment {
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
		URL="http://mp.weixin.qq.com/s?__biz=MzU4MDI3NDg5MQ==&mid=2247483924&idx=1&sn=f7a659b0a91bd829f53b1270689e72b4&chksm=fd581db0ca2f94a673cf315d334f84e5a991e3228f3aa829785c6a800bdc982f39d09252bbe9&mpshare=1&scene=23&srcid=1121LgxJQACNpqPhZa0b2c3G#rd";
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
