package com.xmjj.rxretrofit_master.base.mvp;

/**
 * Created by wzw on 2017/3/13.
 * Description Presenter：是View跟Model的“中间人”，接收View的请求后，从Model获取数据交给View。
 */

public interface BasePresenter {
    void onCreate();
    void onDataCreate();
}
