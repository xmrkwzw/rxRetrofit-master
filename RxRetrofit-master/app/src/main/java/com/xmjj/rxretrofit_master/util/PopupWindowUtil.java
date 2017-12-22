package com.xmjj.rxretrofit_master.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xmjj.rxretrofit_master.R;


/**
 * @author wangZhiWei
 * @version V3.0.0 2016/9/18
 * @description 添加一个popupwindow的工具类
 */
public abstract class PopupWindowUtil {


	// PopupWindow
	private static final float BACKGROUND_ALPHA = 0.7f;
	private static final float TRANSLUCENT = 1f;

	private PopupWindow popupWindow;
	Context context;
	int layoutId;


	public PopupWindowUtil(Context context, int layoutId) {
		this.context = context;
		this.layoutId = layoutId;

	}


	/**
	 * @param changeAlpha 是否要让背景变暗
	 */
	public void createPopupWindow(boolean changeAlpha, boolean outSideTouchable) {
		try {
			if (popupWindow == null) {
				View view = ((Activity) context).getLayoutInflater().inflate(layoutId, null);
				view.measure(-1, -1);
				popupWindow = new PopupWindow(view, setWidth(view), setHeight(view));

				init(view, popupWindow);

			}
		} catch (Exception e) {

			//ToastUtils.showShortMes(context, "百分比布局bug" + e.toString());

		}
		if (outSideTouchable) {
			popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
		}
		setLocation(popupWindow);

		if (changeAlpha) {

			//背景变暗
			backgroundAlpha(context, BACKGROUND_ALPHA);
			popupWindow.setOnDismissListener(new OnPopupWindowDismissListener());
		}


	}

	public void backgroundAlpha(Context context, float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
		lp.alpha = bgAlpha;
		((Activity) context).getWindow().setAttributes(lp);
	}

	class OnPopupWindowDismissListener implements PopupWindow.OnDismissListener {


		@Override
		public void onDismiss() {
			backgroundAlpha(context, TRANSLUCENT);
		}
	}


	public abstract int setWidth(View view);

	public abstract int setHeight(View view);

	public abstract void init(View view, PopupWindow popupWindow);

	public abstract void setLocation(PopupWindow popupWindow);

	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void disMissPop() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();

		}
	}

}


