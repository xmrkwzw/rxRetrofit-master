package com.xmjj.jujianglibrary.util.skinloader.attr;

import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.xmjj.jujianglibrary.R;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:13:52
 */
public class TabLayoutTextAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TabLayout) {
            Log.i("TabLayoutAttr", "apply");
            TabLayout tl = (TabLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {

                int color = SkinManager.getInstance().getColor(attrValueRefId);
				int normalColor = view.getResources().getColor(R.color.card_background);

                tl.setTabTextColors(normalColor,color);
            } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                Log.i("TabLayoutAttr", "apply drawable");
                //  tv.setDivider(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }
}
