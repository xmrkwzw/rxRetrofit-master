package com.xmjj.rxretrofit_master.model;

import com.xmjj.jujianglibrary.listener.HttpOnNextListener;

import java.util.Map;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/20
 */

public interface NetModel extends HttpOnNextListener{
	interface  ObjectResultModel{
		void getData( Map<String,Object> params);
	}

	interface ArrayResultModel{
		void getData();
	}

	interface  InnerResultModel{
		void getData();
	}
}
