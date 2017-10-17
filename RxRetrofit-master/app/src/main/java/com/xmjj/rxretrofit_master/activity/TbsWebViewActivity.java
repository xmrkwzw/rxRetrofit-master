package com.xmjj.rxretrofit_master.activity;

import com.tencent.smtt.sdk.WebView;
import com.xmjj.jujianglibrary.util.WebViewUtils;
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

	private static final String URL = "http://192.168.1.166:63343/untitled/index.html?_ijt=90r07r8v017kde0uli65kpkn71";

	@Override
	public int getLayoutResId() {
		return R.layout.activity_webview;
	}

	@Override
	public void initViews() {
		WebViewUtils.getInstance().initWebView(webview,URL);
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
