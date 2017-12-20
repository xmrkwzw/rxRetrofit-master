package com.xmjj.rxretrofit_master.model;

import android.os.Environment;

import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.jujianglibrary.http.downlaod.DownState;
import com.xmjj.jujianglibrary.http.downlaod.HttpDownManager;
import com.xmjj.jujianglibrary.listener.HttpDownOnNextListener;
import com.xmjj.rxretrofit_master.base.mvp.IBaseFileDownLoadCallBack;

import java.io.File;
import java.io.IOException;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/26
 */

public class FileDownLoadModel {
	private DownInfo downInfo;
	private static final String DOWNLOAD_URL = "http://www.ijinbu.com/upload/brand/v1.0/banpai_V1.0.2_05-11.apk?tsecond=0.07560947055095824";

	private IBaseFileDownLoadCallBack callBack;

	public FileDownLoadModel(IBaseFileDownLoadCallBack callBack) {
		this.callBack = callBack;
		initInfo();
	}

	/*init downinfo*/
	public void initInfo() {
		downInfo = new DownInfo(DOWNLOAD_URL);
		File file = new File(Environment.getExternalStorageDirectory() + "/banpai/update/", "test.apk");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		downInfo.setId(1);
		downInfo.setState(DownState.START);
		downInfo.setSavePath(file.getAbsolutePath());
		downInfo.setListener(httpProgressOnNextListener);
	}

	/*download file*/
	public void download() {

		HttpDownManager.getInstance().startDown(downInfo);
	}

	/*pause download file*/
	public void pauseLoad() {
		HttpDownManager.getInstance().pause(downInfo);
	}

	/*reload file*/
	public void reLoad(){
		download();
	}

	/*下载回调*/
	HttpDownOnNextListener<DownInfo> httpProgressOnNextListener = new HttpDownOnNextListener<DownInfo>() {
		@Override
		public void onNext(DownInfo baseDownEntity) {
			callBack.onNext(baseDownEntity);
		}

		@Override
		public void onStart() {
			callBack.onMsg("开始下载");
		}

		@Override
		public void onComplete() {
			callBack.onMsg("下载完成");
		}

		@Override
		public void onError(Throwable e) {
			super.onError(e);
			callBack.onError(e);
		}


		@Override
		public void onPause() {
			super.onPause();
			callBack.onMsg("提示:暂停");
		}

		@Override
		public void onStop() {
			super.onStop();
			callBack.onMsg("暂停下载");
		}

		@Override
		public void updateProgress(long readLength, long countLength) {
			callBack.updateProgress(readLength, countLength);

		}
	};
}
