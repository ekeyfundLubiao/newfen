package com.m086.ad.mobi.test.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by Administrator on 2016/12/14.
 */

public class HSrollView extends HorizontalScrollView {
    public HSrollView(Context context) {
        super(context);
    }

    public HSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("TAG", "HSrollView=========dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("TAG", "HSrollView=========dispatchTouchEvent: ");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("TAG", "HSrollView=========dispatchTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }
}
