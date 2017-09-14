package com.xmjj.jujianglibrary.util.skinloader.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/4
 */

public class ThumbAttr extends SkinAttr {
	@Override
	public void apply(View view) {
		if (view instanceof SeekBar) {
			if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
				Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);
				SeekBar seekBar = (SeekBar) view;
				seekBar.setThumb(drawable);
			}
		}
	}
}
