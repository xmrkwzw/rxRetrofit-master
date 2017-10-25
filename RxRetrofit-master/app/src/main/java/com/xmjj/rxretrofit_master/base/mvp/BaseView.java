package com.xmjj.rxretrofit_master.base.mvp;

/**
 * Created by wzw on 2017/3/13.
 * Description View：用于界面的显示与用户操作的接收，在Android里面View通常就是Activity，Fragment。
 */

public interface BaseView {
    void setData(String json, Object result, String method);
	void setError(String error);
}
