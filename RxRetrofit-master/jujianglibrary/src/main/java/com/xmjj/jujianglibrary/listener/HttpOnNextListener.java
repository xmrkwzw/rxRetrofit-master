package com.xmjj.jujianglibrary.listener;


/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface HttpOnNextListener {
    /**
     * 成功后回调方法
     * @param json 原数据转为json字符串
     * @param result
     * @param method
     */
    void onNext(String json,Object result, String method);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     *
     * @param exception
     * @param method
     */
    void onError(String exception, String method);
}
