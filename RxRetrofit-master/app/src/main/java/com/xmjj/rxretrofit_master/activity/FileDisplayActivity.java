package com.xmjj.rxretrofit_master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.xmjj.jujianglibrary.view.SuperFileView2;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.util.Md5Tool;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FileDisplayActivity extends AppCompatActivity {
	@BindView(R.id.mSuperFileView)
	SuperFileView2 mSuperFileView;
	String filePath;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_display);
		ButterKnife.bind(this);
		init();
	}


	public void init() {
		mSuperFileView = (SuperFileView2) findViewById(R.id.mSuperFileView);
		mSuperFileView.setOnGetFilePathListener(new SuperFileView2.OnGetFilePathListener() {
			@Override
			public void onGetFilePath(SuperFileView2 mSuperFileView2) {
				getFilePathAndShowFile(mSuperFileView2);
			}
		});

		Intent intent = this.getIntent();
		String path = (String) intent.getSerializableExtra("path");
		if (!TextUtils.isEmpty(path)) {

			setFilePath(path);
		}
		mSuperFileView.show();
	}


	private void getFilePathAndShowFile(SuperFileView2 mSuperFileView2) {

		mSuperFileView2.displayFile(new File(getFilePath()));

	}


	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mSuperFileView != null) {
			mSuperFileView.onStopDisplay();
		}
	}

	public static void show(Context context, String url) {
		Intent intent = new Intent(context, FileDisplayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("path", url);
		intent.putExtras(bundle);
		context.startActivity(intent);

	}

	public void setFilePath(String fileUrl) {
		this.filePath = fileUrl;
	}

	private String getFilePath() {
		return filePath;
	}


	/***
	 * 获取缓存目录
	 *
	 * @param url
	 * @return
	 */
	private File getCacheDir(String url) {

		return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/007/");

	}

	/***
	 * 绝对路径获取缓存文件
	 *
	 * @param url
	 * @return
	 */
	private File getCacheFile(String url) {
		File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/10086/"
				+ getFileName(url));

		return cacheFile;
	}

	/***
	 * 根据链接获取文件名（带类型的），具有唯一性
	 *
	 * @param url
	 * @return
	 */
	private String getFileName(String url) {
		String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
		return fileName;
	}

	/***
	 * 获取文件类型
	 *
	 * @param paramString
	 * @return
	 */
	private String getFileType(String paramString) {
		String str = "";

		if (TextUtils.isEmpty(paramString)) {

			return str;
		}

		int i = paramString.lastIndexOf('.');
		if (i <= -1) {

			return str;
		}


		str = paramString.substring(i + 1);

		return str;
	}


}
