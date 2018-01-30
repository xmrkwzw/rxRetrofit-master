package com.xmjj.rxretrofit_master.activity;

import android.os.Build;

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
	private static final String URL = "file:///android_asset/mobile.html";


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
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBlockNetworkImage(true);
		webSettings.setLoadsImagesAutomatically(true);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		webview.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				webView.loadUrl(url);
				return true;
			}
		});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webview.evaluateJavascript(URL, null);
		} else {
			webview.loadUrl(URL);
		}

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
