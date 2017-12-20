package com.xmjj.rxretrofit_master.fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.presenter.FileDownLoadPresenter;
import com.xmjj.rxretrofit_master.util.ToastUtils;
import com.xmjj.rxretrofit_master.view.IFileDownLoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/10/25
 */

public class FileDownLoadFragment extends BaseFragment implements IFileDownLoadView<DownInfo> {
	@BindView(R.id.tv_msg)
	TextView tvShow;
	@BindView(R.id.pb_load)
	ProgressBar progressBar;
	@BindView(R.id.tv_download)
	TextView tvDownload;
	@BindView(R.id.cb_pause)
	CheckBox cbPause;

	private FileDownLoadPresenter presenter;

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_file;
	}

	@Override
	public void initViews() {
		if(cbPause==null){
			return;
		}
		cbPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cbPause.setText("继续下载");
					//暂停下载
					pauseDownload();
				}else{
					cbPause.setText("暂停下载");
					//继续断点续传
					restartDownload();
				}
			}
		});


	}

	@Override
	public void initData() {

		presenter = new FileDownLoadPresenter(this);
	}


	/*download file*/
	public void download() {

		presenter.downLoadFile();
	}


	/*pauseLoad file*/
	public void pauseDownload() {
		presenter.pauseLoadFile();
	}

	/*restart download file*/
	public void restartDownload() {
		presenter.reLoadFile();
	}

	@Override
	public void onNext(DownInfo downInfo) {

		Toast.makeText(getContext(), downInfo.getSavePath(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMsg(String msg) {
		tvShow.setText(msg);
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

	@Override
	public void onError(Throwable e) {
		ToastUtils.showShortMes(activity, e.toString());
	}

	@Override
	public void onComplete() {
		tvShow.setText("提示：下载完成");
		cbPause.setEnabled(false);
		cbPause.setClickable(false);
		progressBar.setVisibility(View.GONE);
	}


	@OnClick(R.id.tv_download)
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_download:
				download();
				break;

		}
	}
}
