package com.xmjj.rxretrofit_master.presenter;


import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.rxretrofit_master.base.mvp.IBaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.IBasePresenter;
import com.xmjj.rxretrofit_master.base.mvp.IBaseView;
import com.xmjj.rxretrofit_master.entity.params.RequestParams;
import com.xmjj.rxretrofit_master.model.ArrayResultModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class ArrayResultPresenter implements IBasePresenter {
	private RxAppCompatActivity appCompatActivity;
	private String dialogMsg;
	private Map<String, Object> params;
	private IBaseView baseView;
	private ArrayResultModel model;

	public ArrayResultPresenter(RxAppCompatActivity appCompatActivity, IBaseView baseView, @Nullable String dialogMsg) {
		this.appCompatActivity = appCompatActivity;
		this.dialogMsg = dialogMsg;
		this.baseView = baseView;
	}



	@Override
	public void onInit() {
		params = new HashMap<>();
		params.put(RequestParams.Civilization.CLASS_ID,"14871");
	}

	@Override
	public void onDataCreate() {
		model = new ArrayResultModel(appCompatActivity, dialogMsg, new IBaseCallBack() {
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
