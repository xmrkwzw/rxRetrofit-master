package com.xmjj.rxretrofit_master.fragment;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.presenter.FileDownLoadPresenter;
import com.xmjj.rxretrofit_master.view.IFileDownLoadView;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class FileDownLoadFragment extends BaseFragment implements IFileDownLoadView<DownInfo>{
	@BindView(R.id.tv_msg)
	TextView tvShow;

	ProgressBar progressBar;


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_file;
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
		FileDownLoadPresenter presenter = new FileDownLoadPresenter(this);
		presenter.init();
	}


	@Override
	public void onNext(DownInfo downInfo) {
		tvShow.setText("提示：下载完成");
		Toast.makeText(getContext(), downInfo.getSavePath(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMsg(String msg) {
		tvShow.setText(msg);
	}

	@Override
	public void updateProgress(long readLength, long countLength) {
		if(tvShow==null){
			ToastUtils.showShortMes(activity,"null");
			return;
		}
		tvShow.setText("提示:下载中" + (int) countLength + "/" + (int) readLength);
		progressBar.setMax((int) countLength);
		progressBar.setProgress((int) readLength);
	}

	@Override
	public void onError(Throwable e) {
		ToastUtils.showShortMes(activity,e.toString());
	}
}
