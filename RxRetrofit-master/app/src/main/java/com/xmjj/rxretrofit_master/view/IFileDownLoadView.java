package com.xmjj.rxretrofit_master.view;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public interface IFileDownLoadView<T> {
	/**
	 * 成功后回调方法
	 *
	 * @param t
	 */
	 void onNext(T t);

	/**
	 * 下载提示信息
	 */
	  void onMsg(String msg);


	/**
	 * 下载进度
	 *
	 * @param readLength
	 * @param countLength
	 */
	 void updateProgress(long readLength, long countLength);

	/**
	 * 失败或者错误方法
	 * 主动调用，更加灵活
	 *
	 * @param e
	 */
	 void onError(Throwable e);

	/*下载完成*/
	void onComplete();

}
