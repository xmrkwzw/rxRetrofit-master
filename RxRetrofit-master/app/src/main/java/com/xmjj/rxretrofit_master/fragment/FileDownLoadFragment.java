package com.xmjj.rxretrofit_master.fragment;

import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.jujianglibrary.http.downlaod.DownState;
import com.xmjj.jujianglibrary.http.downlaod.HttpDownManager;
import com.xmjj.jujianglibrary.listener.HttpDownOnNextListener;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import java.io.File;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class FileDownLoadFragment extends BaseFragment {
	@BindView(R.id.tv_content)
	TextView tvShow;

	ProgressBar progressBar;
	private static final String DOWNLOAD_URL = "http://www.ijinbu.com/upload/brand/v1.0/banpai_V1.0.2_05-11.apk?tsecond=0.07560947055095824";

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_object;
	}

	@Override
	public void initViews() {
		progressBar = new ProgressBar(activity);
	}

	@Override
	public void initData() {
		download();
	}

	/**
	 * download file
	 */
	public void download() {
		DownInfo downInfo = new DownInfo(DOWNLOAD_URL);
		File file = new File(Environment.getExternalStorageDirectory() + "/banpai/update/", "test.apk");

		downInfo.setId(1);
		downInfo.setState(DownState.START);
		downInfo.setSavePath(file.getAbsolutePath());
		downInfo.setListener(httpProgressOnNextListener);
		HttpDownManager.getInstance().startDown(downInfo);
	}

	/*下载回调*/
	HttpDownOnNextListener<DownInfo> httpProgressOnNextListener = new HttpDownOnNextListener<DownInfo>() {
		@Override
		public void onNext(DownInfo baseDownEntity) {
			if (tvShow == null) {
				return;
			}
			tvShow.setText("提示：下载完成");
			Toast.makeText(getContext(), baseDownEntity.getSavePath(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStart() {
			if (tvShow == null) {
				return;
			}
			tvShow.setText("提示:开始下载");
		}

		@Override
		public void onComplete() {
			if (tvShow == null) {
				return;
			}
			tvShow.setText("提示：下载结束");
		}

		@Override
		public void onError(Throwable e) {
			super.onError(e);
			if (tvShow == null) {
				return;
			}
			tvShow.setText("失败:" + e.toString());
		}


		@Override
		public void onPuase() {
			super.onPuase();
			if (tvShow == null) {
				return;
			}
			tvShow.setText("提示:暂停");
		}

		@Override
		public void onStop() {
			super.onStop();
		}

		@Override
		public void updateProgress(long readLength, long countLength) {
			if (tvShow == null) {
				return;
			}
			tvShow.setText("提示:下载中" + (int) countLength + "/" + (int) readLength);
			progressBar.setMax((int) countLength);
			progressBar.setProgress((int) readLength);

		}
	};
}
