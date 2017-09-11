package com.xmjj.jujianglibrary.util;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/21
 */

public class WebViewUtils {
	public static WebViewUtils instance ;

	public static WebViewUtils getInstance(){
		if(instance==null){
			instance = new WebViewUtils();
		}
		return instance;
	}

	public void initWebView(WebView webView,String url){
		WebSettings webSettings = webView.getSettings();
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl(url);
	}
}
