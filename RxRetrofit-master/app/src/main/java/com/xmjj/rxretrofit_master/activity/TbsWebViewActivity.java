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
	private static final String URL = "http://mp.weixin.qq.com/s?__biz=MzU4MDI3NDg5MQ==&mid=2247483924&idx=1&sn=f7a659b0a91bd829f53b1270689e72b4&chksm=fd581db0ca2f94a673cf315d334f84e5a991e3228f3aa829785c6a800bdc982f39d09252bbe9&mpshare=1&scene=23&srcid=1121LgxJQACNpqPhZa0b2c3G#rd";


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

		webview.loadUrl(URL);
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
