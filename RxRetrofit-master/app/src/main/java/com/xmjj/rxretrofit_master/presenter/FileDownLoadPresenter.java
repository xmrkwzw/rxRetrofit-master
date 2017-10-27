package com.xmjj.rxretrofit_master.presenter;

import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.rxretrofit_master.base.mvp.IBaseFileDownLoadCallBack;
import com.xmjj.rxretrofit_master.model.FileDownLoadModel;
import com.xmjj.rxretrofit_master.view.IFileDownLoadView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public class FileDownLoadPresenter {

	private FileDownLoadModel model;
	private IFileDownLoadView<DownInfo> downLoadView;

	public FileDownLoadPresenter(IFileDownLoadView<DownInfo> downLoadView) {
		this.downLoadView = downLoadView;
	}

	public void init() {
		model = new FileDownLoadModel(new IBaseFileDownLoadCallBack<DownInfo>() {
			@Override
			public void onNext(DownInfo downInfo) {
				downLoadView.onNext(downInfo);
			}

			@Override
			public void onMsg(String msg) {
				downLoadView.onMsg(msg);
			}

			@Override
			public void updateProgress(long readLength, long countLength) {
				downLoadView.updateProgress(readLength, countLength);
			}

			@Override
			public void onError(Throwable e) {
				downLoadView.onError(e);
			}
		});
		model.download();
	}


}
