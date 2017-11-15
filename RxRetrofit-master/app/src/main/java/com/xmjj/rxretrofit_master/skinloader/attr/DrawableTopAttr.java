package com.xmjj.rxretrofit_master.skinloader.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.xmjj.rxretrofit_master.skinloader.load.SkinManager;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/3
 */

public class DrawableTopAttr extends SkinAttr {
	@Override
	public void apply(View view) {
		if (view instanceof TextView) {
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
				TextView textView = (TextView) view;
				textView.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);

			}
		}
	}
}
