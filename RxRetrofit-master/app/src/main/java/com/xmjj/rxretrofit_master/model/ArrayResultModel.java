package com.xmjj.rxretrofit_master.model;

import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.base.mvp.BaseModel;
import com.xmjj.rxretrofit_master.base.mvp.IBaseCallBack;
import com.xmjj.rxretrofit_master.base.mvp.IBaseModel;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.entity.params.RequestParams;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public class ArrayResultModel extends BaseModel implements IBaseModel,HttpOnNextListener {


	public ArrayResultModel(RxAppCompatActivity appCompatActivity, @Nullable String dialogMsg, IBaseCallBack baseCallBack) {
		super(appCompatActivity, dialogMsg, baseCallBack);
	}

	@Override
	public void getData(Map<String, Object> params) {
		BaseInfoApi baseInfoApi = new BaseInfoApi(this, appCompatActivity, RatingBean.class);
		baseInfoApi.getCivilization((String) params.get(RequestParams.Civilization.CLASS_ID),dialogMsg);
	}



}
