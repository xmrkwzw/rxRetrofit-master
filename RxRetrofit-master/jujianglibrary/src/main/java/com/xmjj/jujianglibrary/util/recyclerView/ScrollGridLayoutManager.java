package com.xmjj.jujianglibrary.util.recyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;


/**
 * @author wangzhiwei.
 * @create on 2017/1/12.
 * @description
 */

public class ScrollGridLayoutManager extends GridLayoutManager {
    private boolean isScrollEnabled = true;



    public ScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }


    public ScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }


    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
