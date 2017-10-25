package com.xmjj.rxretrofit_master.model;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.base.mvp.BaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.BaseModel;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/25.
 */

public class NestResultModel implements BaseModel, HttpOnNextListener {
    private RxAppCompatActivity appCompatActivity;
    private BaseCallBack callBack;
    private String dialogMsg;


    public NestResultModel(RxAppCompatActivity appCompatActivity, String dialogMsg, BaseCallBack baseCallBack) {
        this.appCompatActivity = appCompatActivity;
        this.dialogMsg = dialogMsg;
        this.callBack = baseCallBack;

    }


    @Override
    public void getData(Map<String, Object> params) {
        BaseInfoApi baseInfoApi = new BaseInfoApi(this, appCompatActivity, BrandInfoDetailBean.class);
        baseInfoApi.doOther((String) params.get("seqNum"), dialogMsg);
    }

    @Override
    public void onNext(String json, Object result, String method) {
        callBack.onResult(json, result, method);
    }

    @Override
    public void onError(String exception, String method) {
        callBack.onError(exception, method);
    }
}
