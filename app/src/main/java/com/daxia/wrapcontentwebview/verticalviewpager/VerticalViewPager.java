package com.daxia.wrapcontentwebview.verticalviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VerticalViewPager extends ViewPager implements ViewPager.PageTransformer {

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, this);
    }


    @Override
    public void transformPage(View view, float position) {
        view.setTranslationX(view.getWidth() * -position);
        float yPos = position * view.getHeight();
        view.setTranslationY(yPos);
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
        //Intercept touch event for be swapped.
        swapTouchEvent(event);
        return intercept;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ScrollWebView) {
            return ((ScrollWebView) v).canScrollHor(-dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

}
