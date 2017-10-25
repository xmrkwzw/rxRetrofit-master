package com.xmjj.rxretrofit_master.base.mvp;

/**
 * Created by wzw on 2017/3/13.
 * Description Model层通知View的回调
 */

public interface BaseCallBack {
	void onResult(String json, Object result, String method);
	void onError(String error,String method);
}
