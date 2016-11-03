package com.huyunit.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 滑动隐藏View
 * author: bobo
 * create time: 2016/11/3 15:05
 * Email: jqbo84@163.com
 */
public class SlideHideScrollView extends ScrollView {
    private BottomListener bottomListener;

    private onScrollListener scrollListener;

    public SlideHideScrollView(Context context) {
        this(context, null);
    }

    public SlideHideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
            if (null != bottomListener) {
                bottomListener.onBottom();
            }
        }

        if (null != scrollListener) {
            scrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setBottomListener(BottomListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public void setScrollListener(onScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface onScrollListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public interface BottomListener {
        public void onBottom();
    }
}
