package com.xmjj.rxretrofit_master.base.mvp;

import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;

import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public class BaseModel implements IBaseModel,HttpOnNextListener {
	public  RxAppCompatActivity appCompatActivity;
	public IBaseCallBack callBack;
	public String dialogMsg ;

	public BaseModel(RxAppCompatActivity appCompatActivity, @Nullable String dialogMsg, IBaseCallBack baseCallBack) {
		this.appCompatActivity = appCompatActivity;
		this.dialogMsg = dialogMsg;
		this.callBack = baseCallBack;

	}

	@Override
	public void getData(Map<String, Object> params) {

	}

	@Override
	public void onNext(String json, Object result, String method) {
		callBack.onResult(json,result,method);
	}

	@Override
	public void onError(String exception, String method) {
		callBack.onError(exception,method);
	}
}
