package com.xmjj.rxretrofit_master.activity;

import android.webkit.WebView;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.util.WebViewUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LayImActivity extends BaseActivity {
    private static final String SETUP_HTML = "file:///android_asset/mobile.html";
    @BindView(R.id.webView)
    WebView webView ;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_layim;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        WebViewUtils.getInstance().initWebView(webView, SETUP_HTML,false);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }
}
