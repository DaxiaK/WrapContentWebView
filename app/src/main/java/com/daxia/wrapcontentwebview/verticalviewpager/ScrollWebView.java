package com.daxia.wrapcontentwebview.verticalviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by daxia on 2016/6/28.
 */
public class ScrollWebView extends WebView {

    interface EndCallback {
        void isEnd();
    }

    private boolean isEnd = false;
    private EndCallback endCallback;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEndCallBack(EndCallback endCallback) {
        this.endCallback = endCallback;
    }

    public boolean canScrollHor(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            isEnd = false;
            return offset > 0;
        } else {
            if (isEnd) {
                if (endCallback != null) {
                    endCallback.isEnd();
                }
                return false;
            } else {
                return offset < range - 1;
//                return true;
            }
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        int height = (int) Math.floor(this.getContentHeight() * getResources().getDisplayMetrics().density);
        int webViewHeight = this.getMeasuredHeight();
        if (this.getScrollY() + webViewHeight >= height) {
//            Log.e("test","onScrollChanged end");
            isEnd = true;
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
