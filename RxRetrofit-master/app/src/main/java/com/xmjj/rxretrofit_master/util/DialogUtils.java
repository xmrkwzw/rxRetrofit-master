package com.xmjj.rxretrofit_master.util;

import android.content.Context;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/31
 */

public class DialogUtils {
	public static DialogUtils instance;
	public static MaterialDialog dialog;

	public static DialogUtils getInstance() {
		if (instance == null) {
			instance = new DialogUtils();
		}
		return instance;
	}


	public MaterialDialog showDialog(Context context, positiveListener listener) {
		dialog = new MaterialDialog(context);
		dialog.setTitle("提示")
				.setNegativeButton("取消",
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {

								dialog.dismiss();

							}
						})
				.setPositiveButton("确定", listener)
				.show();
		return dialog;

	}

	public static class positiveListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			dialog.dismiss();
		}
	}


}
