package com.xmjj.jujianglibrary.util;


import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/21
 */

public class WebViewUtils {
	public static WebViewUtils instance;

	public static WebViewUtils getInstance() {
		if (instance == null) {
			instance = new WebViewUtils();
		}
		return instance;
	}

	public void initWebView(WebView webview, String url) {

		WebSettings webSettings = webview.getSettings();
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setDisplayZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				webView.loadUrl(url);
				return true;
			}
		});

		webview.loadUrl(url);
	}
}
