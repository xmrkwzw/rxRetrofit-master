package com.xmjj.jujianglibrary.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Types;
import com.xmjj.jujianglibrary.api.BaseApi;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.http.entity.BaseRespond;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/18
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
	private ProgressDialog dialog;
	private SoftReference<HttpOnNextListener> mHttpOnNextListener;
	private SoftReference<Context> mActivity;
	private BaseApi mApi;
	private Class clazz;

	private static final String SUCCESS = "1";
	private static final String FAIL = "2";


	public ProgressSubscriber(BaseApi mApi, SoftReference<HttpOnNextListener> mHttpOnNextListener, SoftReference<Context> mActivity, Class clazz) {
		this.mHttpOnNextListener = mHttpOnNextListener;
		this.mActivity = mActivity;
		this.mApi = mApi;
		this.clazz = clazz;
		initDialog(mApi.isCancel());
	}

	@Override
	public void onCompleted() {
		disMissDialog();
	}

	@Override
	public void onError(Throwable e) {
		if (mHttpOnNextListener.get() != null) {
			mHttpOnNextListener.get().onError((ApiException) e, mApi.getMethod());
		}
	}

	@Override
	public void onNext(T t) {
		if (mHttpOnNextListener.get() != null) {
			BaseRespond respond = new Gson().fromJson((String) t, BaseRespond.class);
			String status = respond.getStatus();
			String msg = respond.getMsg();
			if (TextUtils.isEmpty(status)) {
				return;
			}
			if (SUCCESS.equals(status)) {
				JsonElement result = respond.getResult();
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

	@Override
	public void onStart() {
		super.onStart();
		showDialog();
	}

	/*初始化dialog*/
	public void initDialog(boolean canCancel) {
		Context context = mActivity.get();
		if (dialog == null && context != null) {
			dialog = new ProgressDialog(mActivity.get());
			dialog.setCancelable(canCancel);
			if (canCancel) {
				onCancelProgress();
			}
		}

	}

	/*取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求*/
	public void onCancelProgress() {
		if (!this.isUnsubscribed()) {
			this.unsubscribe();
		}
	}

	/*showDialog*/
	public void showDialog() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	/*dismissDialog*/
	public void disMissDialog() {
		if (dialog != null || dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	/*设置提示内容*/
	public void setDialogMsg(String msg){
		if (dialog != null || dialog.isShowing()) {
			dialog.setMessage(msg);
		}
	}
}
