package com.lintzuhsiu.tools.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by lintzuhsiu on 14/11/15.
 */
public class TSLScrollView extends ScrollView {

    private View firstView;
    private View headerView;

    private int currentScrollY;
    private boolean isHeaderFixed;

    public TSLScrollView(Context context) {
        this(context, null);
    }

    public TSLScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSLScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (headerView != null && firstView != null) {
            if (isHeaderFixed) {
                headerView.layout(0, currentScrollY, headerView.getWidth(),
                        currentScrollY + headerView.getHeight());
            } else {
                int firstViewHeight = firstView.getMeasuredHeight();
                headerView.layout(0, firstViewHeight, headerView.getWidth(), firstViewHeight + headerView.getHeight());
            }
        }

        invalidate();
    }

    /*
     * 當 internal scroll view 改變
     * @param l Current horizontal scroll origin.
     * @param t Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        ViewGroup viewGroup = (ViewGroup) this.getChildAt(0);
        firstView = viewGroup.getChildAt(0);
        headerView = viewGroup.getChildAt(2);

        currentScrollY = t;
        firstView.scrollTo(0, -(int)(t * 0.2));
        isHeaderFixed = t >= firstView.getMeasuredHeight() ? true : false;
    }

}
