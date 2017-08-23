package com.xmjj.jujianglibrary.http;

import android.util.Log;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.R;
import com.xmjj.jujianglibrary.api.BaseApi;
import com.xmjj.jujianglibrary.exception.RetryWhenNetworkException;
import com.xmjj.jujianglibrary.http.func.ExceptionFunc;
import com.xmjj.jujianglibrary.http.func.ResulteFunc;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.jujianglibrary.subscriber.ProgressSubscriber;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 */
public class HttpManager {
	/*软引用對象*/
	private SoftReference<HttpOnNextListener> onNextListener;
	private SoftReference<RxAppCompatActivity> appCompatActivity;
	private Class clazz;
	private static final int CONNECT_TIME_OUT = 10;//超时时间 10s
	private static final int WRITE_TIME_OUT = 10;
	private static final int READ_TIME_OUT = 10;
	private String dialogMsg;
	public static final String BASE_URL = "http://www.ijinbu.com/ijinbu/";


	public HttpManager(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity, Class clazz) {
		this.onNextListener = new SoftReference(onNextListener);
		this.appCompatActivity = new SoftReference(appCompatActivity);
		this.clazz = clazz;


	}


	/**
	 * 获取Retrofit对象
	 *
	 * @return
	 */
	public Retrofit getRetrofit() {
		//手动创建一个OkHttpClient并设置超时时间缓存等设置
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
		builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
		builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
		builder.addInterceptor(getHttpLoggingInterceptor());


        /*创建retrofit对象*/
		final Retrofit retrofit = new Retrofit.Builder()
				.client(builder.build())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl(BASE_URL)
				.build();
		return retrofit;
	}

	/**
	 * RxRetrofit处理
	 *
	 * @param observable
	 * @param basePar
	 */
	public void initHttp(Observable observable, BaseApi basePar) {
		  /*失败后的retry配置*/
		observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
				basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*异常处理*/
				.onErrorResumeNext(new ExceptionFunc())
                /*生命周期管理*/
				//.compose(appCompatActivity.get().bindToLifecycle())
				//Note:手动设置在activity onDestroy的时候取消订阅
				.compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                /*返回数据统一判断*/
				.map(new ResulteFunc())
                /*http请求线程*/
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
                /*回调线程*/
				.observeOn(AndroidSchedulers.mainThread());

        /*数据String回调*/
		if (onNextListener != null && null != onNextListener.get()) {
			ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity, clazz);
			String dialogMsg = getDialogMsg() == null ? appCompatActivity.get().getResources().getString(R.string.dialog_default_msg) : getDialogMsg();
			subscriber.setDialogMsg(dialogMsg);
			observable.subscribe(subscriber);
		}
	}

	/**
	 * 日志输出
	 * 自行判定是否添加
	 *
	 * @return
	 */
	private HttpLoggingInterceptor getHttpLoggingInterceptor() {
		//日志显示级别
		HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
		//新建log拦截器
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Log.i("RxRetrofit", "Retrofit====Message:" + message);
			}
		});
		loggingInterceptor.setLevel(level);
		return loggingInterceptor;
	}

	public String getDialogMsg() {
		return dialogMsg;
	}

	public void setDialogMsg(String dialogMsg) {
		this.dialogMsg = dialogMsg;
	}
}
