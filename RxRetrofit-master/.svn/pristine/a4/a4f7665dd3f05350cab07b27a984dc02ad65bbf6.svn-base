package com.xmjj.jujianglibrary.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Types;
import com.xmjj.jujianglibrary.api.BaseApi;
import com.xmjj.jujianglibrary.http.entity.BaseRespond;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/22
 */

public class GsonUtils {
	public static GsonUtils instance;
	private static final String SUCCESS = "1";
	private static final String FAIL = "2";

	public static GsonUtils getInstance() {
		if (instance == null) {
			instance = new GsonUtils();
		}

		return instance;
	}


	public void format(String respondStr, BaseApi mApi, Class clazz, SoftReference<HttpOnNextListener> mHttpOnNextListener) {
		BaseRespond baseRespond = new Gson().fromJson(respondStr, BaseRespond.class);
		String status = baseRespond.getStatus();
		String msg = baseRespond.getMsg();
		if (TextUtils.isEmpty(status)) {
			return;
		}
		if (SUCCESS.equals(status)) {
			JsonElement result = baseRespond.getResult();
			if (result != null) {
				if (result.isJsonArray()) {
					ArrayList _list = new Gson().fromJson(result.getAsJsonArray().toString(), getListTypeFromType(clazz));
					mHttpOnNextListener.get().onNext(_list, mApi.getMethod());
				} else if (result.isJsonObject()) {
					mHttpOnNextListener.get().onNext(new Gson().fromJson(result.getAsJsonObject(), clazz), mApi.getMethod());
				}
			} else {
				mHttpOnNextListener.get().onNext(status, mApi.getMethod());
			}
		} else if (FAIL.equals(status)) {
			mHttpOnNextListener.get().onNext(msg, mApi.getMethod());
		}
	}

	public Object format(String respondStr, Class clazz) {
		BaseRespond baseRespond = new Gson().fromJson(respondStr, BaseRespond.class);
		String status = baseRespond.getStatus();
		String msg = baseRespond.getMsg();
		if (TextUtils.isEmpty(status)) {
			return "服务器异常";
		}
		if (SUCCESS.equals(status)) {
			JsonElement result = baseRespond.getResult();
			if (result != null) {
				if (result.isJsonArray()) {
					ArrayList _list = new Gson().fromJson(result.getAsJsonArray().toString(), getListTypeFromType(clazz));
					return _list;

				} else if (result.isJsonObject()) {
					return new Gson().fromJson(result.getAsJsonObject(), clazz);
				}
			} else {
				return status;
			}
		} else if (FAIL.equals(status)) {
			return msg;
		}
		return "";
	}

	/**
	 * 通过已有的 {@link Type} 获取到泛型对应的  List 列表的Type，以便Gson可以解析
	 *
	 * @param type
	 * @return
	 */
	public static Type getListTypeFromType(Type type) {
		return $Gson$Types.newParameterizedTypeWithOwner(null, List.class, type);
	}
}
