package com.xmjj.rxretrofit_master.activity;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/29
 */

public class TbsWebViewActivity extends BaseActivity {
	@BindView(R.id.webview)
	WebView webview;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_webview;
	}

	@Override
	public void initViews() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webview.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				webView.loadUrl(url);
				return true;
			}
		});

		webview.loadUrl("http://lol.qq.com/");
	}

	@Override
	public void initData() {

	}

	@Override
	public void onBackPressed() {

		if(webview.canGoBack()){
			webview.goBack();
		}else{
			finish();
		}
	}
}
