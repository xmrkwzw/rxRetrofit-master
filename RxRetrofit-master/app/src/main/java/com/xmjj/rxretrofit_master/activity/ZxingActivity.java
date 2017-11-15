package com.xmjj.rxretrofit_master.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xmjj.jujianglibrary.util.StringUtil;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.util.ToastUtils;
import com.xmjj.rxretrofit_master.zxing.CaptureActivity;
import com.xmjj.rxretrofit_master.zxing.util.ZXingUtil;

import java.util.Hashtable;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/21
 */

public class ZxingActivity extends BaseActivity {
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
	@BindView(R.id.iv_zxing)
	ImageView ivZxing;

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
				Bitmap bitmap = ZXingUtil.getInstance().create2Code(ZxingActivity.this, etContent.getText().toString().trim(),
						R.mipmap.ic_launcher);
				ivShow.setImageBitmap(bitmap);
				ivShow2.setImageBitmap(bitmap);
				break;
			case R.id.iv_scan:
				scannerCode();
				break;

		}
	}

	@OnLongClick(R.id.iv_zxing)
	public boolean onLongClick(View view) {
		switch (view.getId()) {
			case R.id.iv_zxing:
				Bitmap source = ((BitmapDrawable) ivZxing.getDrawable()).getBitmap();
				Result result = parseQRcodeBitmap(source);
				if (result == null) {
					ToastUtils.showShortMes(ZxingActivity.this, "无二维码");

				} else {

					ToastUtils.showShortMes(ZxingActivity.this, "扫描结果：" + result.getText());
				}
				break;
		}


		return false;


	}

	/*扫描二维码*/
	public void scannerCode() {

		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
	}

	//解析二维码图片,返回结果封装在Result对象中
	private Result parseQRcodeBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] data = new int[width * height];
		bitmap.getPixels(data, 0, width, 0, 0, width, height);
		//解析转换类型UTF-8
		Hashtable<DecodeHintType, String> hints = new Hashtable<>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		//新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
		RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(width, height, data);
		//将图片转换成二进制图片
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
		//初始化解析对象
		QRCodeReader reader = new QRCodeReader();
		//开始解析
		Result result = null;
		try {
			result = reader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					//显示扫描到的内容
					String result = bundle.getString("result");
					tvShow.setText(result);
					if (StringUtil.isUrl(result)) {
						Uri uri = Uri.parse(result);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					}


				}
				break;
		}
	}
}
