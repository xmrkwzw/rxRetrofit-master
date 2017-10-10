package com.xmjj.rxretrofit_master.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmjj.jujianglibrary.util.StringUtil;
import com.xmjj.jujianglibrary.zxing.CaptureActivity;
import com.xmjj.jujianglibrary.zxing.util.ZXingUtil;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/21
 */

public class ZxingActivity extends BaseActivity  {
	@BindView(R.id.iv_show)
	ImageView ivShow;
	@BindView(R.id.iv_scan)
	ImageView ivScan;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	@BindView(R.id.et_content)
	EditText etContent;
	@BindView(R.id.tv_show)
	TextView tvShow;
	@BindView(R.id.iv_show2)
	ImageView ivShow2;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_zxings;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {

	}

	@OnClick({R.id.iv_show, R.id.iv_scan})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_show:
				Bitmap bitmap = ZXingUtil.getInstance().create2Code(ZxingActivity.this,etContent.getText().toString().trim(),
						R.mipmap.ic_launcher);
				ivShow.setImageBitmap(bitmap);
				ivShow2.setImageBitmap(bitmap);
				break;
			case R.id.iv_scan:
				scannerCode();
				break;
		}
	}

	public void scannerCode() {

		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					//显示扫描到的内容
					String result =bundle.getString("result");
					tvShow.setText(result);
					if(StringUtil.isUrl(result)){
						Uri uri = Uri.parse(result);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					}


				}
				break;
		}
	}
}
