package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import android.util.AttributeSet;
import android.view.View;

import androidx.core.widget.NestedScrollView;

import com.handmark.pulltorefresh.view.RefreshInnerNestedScrollView;

public class PullToRefreshNestedScrollView extends PullToRefreshBase<RefreshInnerNestedScrollView> {
    public PullToRefreshNestedScrollView(Context context) {
        super(context);
    }

    public PullToRefreshNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshNestedScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshNestedScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RefreshInnerNestedScrollView createRefreshableView(Context context, AttributeSet attrs) {
        RefreshInnerNestedScrollView scrollView;
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            scrollView = new InternalScrollViewSDK9(context, attrs);
        } else {
            scrollView = new RefreshInnerNestedScrollView(context, attrs);
        }

        scrollView.setId(R.id.scrollview);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView arg0, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                onInnerScrollViewScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
            }
        });

        return scrollView;
    }

    protected void onInnerScrollViewScrollChanged(int l, int t, int oldl, int oldt) {
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }

    @TargetApi(9)
    final class InternalScrollViewSDK9 extends RefreshInnerNestedScrollView {

        public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                       int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                    maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshNestedScrollView.this, deltaX, scrollX, deltaY, scrollY, getScrollRange(),
                    isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }
}
