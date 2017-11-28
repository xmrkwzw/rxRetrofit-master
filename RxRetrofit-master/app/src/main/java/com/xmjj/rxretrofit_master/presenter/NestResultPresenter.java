package com.xmjj.rxretrofit_master.presenter;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.rxretrofit_master.base.mvp.IBaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.IBasePresenter;
import com.xmjj.rxretrofit_master.base.mvp.IBaseView;
import com.xmjj.rxretrofit_master.entity.params.RequestParams;
import com.xmjj.rxretrofit_master.model.NestResultModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class NestResultPresenter implements IBasePresenter {
	private RxAppCompatActivity appCompatActivity;
	private String dialogMsg;
	private Map<String, Object> params;
	private IBaseView baseView;
	private NestResultModel model;

	public NestResultPresenter(RxAppCompatActivity appCompatActivity, IBaseView baseView, String dialogMsg) {
		this.appCompatActivity = appCompatActivity;
		this.dialogMsg = dialogMsg;
		this.baseView = baseView;
	}

	@Override
	public void onInit() {
		params = new HashMap<>();
		params.put(RequestParams.BaseInfo.SEQ_NUM,"e24e1d7d3c0b70e0");
	}

	@Override
	public void onDataCreate() {
		model = new NestResultModel(appCompatActivity, dialogMsg, new IBaseCallBack() {
			@Override
			public void onResult(String json, Object result, String method) {
				baseView.setData(json, result, method);
			}

			@Override
			public void onError(String error, String method) {
				baseView.setError(error);
			}
		});
		model.doRequest(params);
	}
}
