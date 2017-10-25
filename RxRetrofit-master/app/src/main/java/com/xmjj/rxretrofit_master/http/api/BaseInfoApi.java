package com.xmjj.rxretrofit_master.http.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.api.BaseApi;
import com.xmjj.jujianglibrary.http.HttpManager;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.jujianglibrary.subscriber.ProgressSubscriber;
import com.xmjj.jujianglibrary.util.GsonUtils;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.http.HttpApiService;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/18
 */
public class BaseInfoApi extends BaseApi {
	public static final String BASE_INFO_METHOD = "brand/brandBaseInfo";
	public static final String CIVILIZATION_METHOD = "brand/getMoralRateData";
	public static final String MSG_CODE_METHOD="getVerifyCode";
	public static final String IN = "in";
	private HttpApiService httpApiService;
	private HttpManager httpManager;
	private String dialogContent;
	private SoftReference<HttpOnNextListener> onNextListener;
	private SoftReference<RxAppCompatActivity> appCompatActivity;

	public BaseInfoApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity, Class clazz) {

		this.onNextListener = new SoftReference(onNextListener);
		this.appCompatActivity = new SoftReference(appCompatActivity);
		httpManager = new HttpManager(onNextListener, appCompatActivity, clazz);
		httpApiService = httpManager.getRetrofit().create(HttpApiService.class);

	}

	/*请求1*/
	public void getBaseInfo(String seqNum, String dialogContent) {
		setMethod(BASE_INFO_METHOD);
		Observable observable = httpApiService.getBaseInfo(seqNum);
		httpManager.setDialogMsg(dialogContent);
		setCancel(true);
		httpManager.initHttp(observable, BaseInfoApi.this);
	}

	/*请求2*/
	public void getCivilization(String classId) {
		setMethod(CIVILIZATION_METHOD);
		Observable observable = httpApiService.getCivilization(classId);
		setCancel(true);
		httpManager.initHttp(observable, BaseInfoApi.this);
	}

	/*请求3*/
	public void getMscCode (String mobilePhone){
		setMethod(MSG_CODE_METHOD);
		Observable observable = httpApiService.getMscCode(mobilePhone);
		setCancel(true);
		httpManager.initHttp(observable,BaseInfoApi.this);

	}

	/*嵌套接口*/
	public void doOther(String num, String dialogContent) {
		Observable observable = httpApiService.getBaseInfo(num).flatMap(new Func1<String, Observable<String>>() {

			@Override
			public Observable<String> call(String respond) {

				BrandInfoDetailBean bean = (BrandInfoDetailBean) GsonUtils.getInstance().format(respond, BrandInfoDetailBean.class);
				bean.setTime("14871");
				//.......do something by others method like "callback"
				return httpApiService.getCivilization(bean.getTime());
			}
		});
		setMethod(IN);
		setCancel(true);
		ProgressSubscriber progressSubscriber=new ProgressSubscriber(this, onNextListener, appCompatActivity, RatingBean.class);
		progressSubscriber.setDialogMsg(dialogContent);
		observable
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				/*回调线程*/
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(progressSubscriber);


	}




}
