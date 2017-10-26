package com.xmjj.rxretrofit_master.model;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.base.mvp.BaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.BaseModel;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/20
 */

public class ObjectResultModel implements BaseModel,HttpOnNextListener{
	private RxAppCompatActivity appCompatActivity;
	private BaseCallBack callBack;
	private String dialogMsg ;


	public ObjectResultModel(RxAppCompatActivity appCompatActivity,String dialogMsg,BaseCallBack baseCallBack) {
		this.appCompatActivity = appCompatActivity;
		this.dialogMsg = dialogMsg;
		this.callBack = baseCallBack;

	}

	/**
	 * the result is JsonObject such as {"result":{"xxx"}}
	 */
	@Override
	public void getData( Map<String,Object> params) {
		BaseInfoApi baseInfoApi = new BaseInfoApi(this, appCompatActivity, BrandInfoDetailBean.class);
		baseInfoApi.getBaseInfo((String) params.get("seqNum"), dialogMsg);

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
