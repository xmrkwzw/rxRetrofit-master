package com.xmjj.jujianglibrary.util.skinloader.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/1
 */

public class SrcAttr extends SkinAttr {
	@Override
	public void apply(View view) {
		if (view instanceof ImageView) {
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(drawable);
			}
		}
	}
}
