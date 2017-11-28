package com.xmjj.rxretrofit_master.util;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

	public void initWebView(WebView webview, String url, boolean isHtml) {

		WebSettings webSettings = webview.getSettings();

		webSettings.setLoadWithOverviewMode(true);
		webSettings.setDisplayZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				webView.loadUrl(url);
				return true;
			}
		});

		if (isHtml) {
			webview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
		} else {

			webview.loadUrl(url);
		}
	}
}
