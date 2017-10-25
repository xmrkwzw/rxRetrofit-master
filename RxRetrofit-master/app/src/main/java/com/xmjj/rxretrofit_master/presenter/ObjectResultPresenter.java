package com.xmjj.rxretrofit_master.presenter;


import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.rxretrofit_master.base.mvp.BaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.BasePresenter;
import com.xmjj.rxretrofit_master.base.mvp.BaseView;
import com.xmjj.rxretrofit_master.model.ObjectResultModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class ObjectResultPresenter implements BasePresenter {
	private RxAppCompatActivity appCompatActivity;
	private String dialogMsg;
	private Map<String, Object> params;
	private BaseView baseView;
	private ObjectResultModel model;

	public ObjectResultPresenter(RxAppCompatActivity appCompatActivity, BaseView baseView, String dialogMsg) {
		this.appCompatActivity = appCompatActivity;
		this.dialogMsg = dialogMsg;
		this.baseView = baseView;
	}

	@Override
	public void onInit() {
		params = new HashMap<>();
		params.put("seqNum","e24e1d7d3c0b70e0");
	}

	@Override
	public void onDataCreate() {
		model = new ObjectResultModel(appCompatActivity, dialogMsg, new BaseCallBack() {
			@Override
			public void onResult(String json, Object result, String method) {
				baseView.setData(json, result, method);
			}

			@Override
			public void onError(String error, String method) {
				baseView.setError(error);
			}
		});
		model.getData(params);
	}
}
