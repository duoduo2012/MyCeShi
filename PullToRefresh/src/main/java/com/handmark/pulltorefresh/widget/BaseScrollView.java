package com.handmark.pulltorefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class BaseScrollView extends ScrollView {
    private OnScrollChangedListener listener;

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.listener != null) {
            this.listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public static interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
