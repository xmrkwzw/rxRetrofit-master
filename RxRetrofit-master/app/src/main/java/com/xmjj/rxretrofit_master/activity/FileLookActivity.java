package com.xmjj.rxretrofit_master.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/29
 */

public class FileLookActivity extends BaseActivity  {
	@BindView(R.id.btn_doc)
	Button btnDoc;
	@BindView(R.id.btn_txt)
	Button btnTxt;
	@BindView(R.id.btn_excel)
	Button btnExcel;
	@BindView(R.id.btn_ppt)
	Button btnPpt;
	@BindView(R.id.btn_pdf)
	Button btnPdf;
	@BindView(R.id.btn_file)
	Button btnFile;

	private static final int REQUEST_CODE = 1;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_file;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {

	}

	@OnClick({R.id.btn_doc, R.id.btn_txt, R.id.btn_excel, R.id.btn_ppt, R.id.btn_pdf,R.id.btn_file})
	public void onClick(View view) {
		String filePath = Environment.getExternalStorageDirectory().getPath();

		switch (view.getId()) {

			case R.id.btn_doc:
				filePath += "/test.docx";
				break;
			case R.id.btn_txt:
				filePath += "/test.txt";
				break;
			case R.id.btn_excel:
				filePath += "/test.xlsx";
				break;
			case R.id.btn_ppt:
				filePath += "/test.pptx";
				break;
			case R.id.btn_pdf:
				filePath += "/test.pdf";
				break;
			case R.id.btn_file:
				openAssignFolder();
				break;
		}
		Logger.d("filePath = " + filePath);
		if(view==btnFile){
			return;
		}
		FileDisplayActivity.show(FileLookActivity.this, filePath);

	}

	private void openAssignFolder() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, REQUEST_CODE);


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE) {
				Uri uri = data.getData();
				Toast.makeText(this, "文件路径：" + uri.getPath().toString(), Toast.LENGTH_SHORT).show();
				FileDisplayActivity.show(FileLookActivity.this, uri.getPath().toString().trim());
			}
		}
	}
}
