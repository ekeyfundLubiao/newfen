package moni.anyou.com.view.widget.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2017/4/1.
 */

public class MyRecycleView extends RecyclerView {

    private boolean mIsVpDragger;
    private int mTouchSlop;
    private int startY;
    private int startX;

    public MyRecycleView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltax = x - startX;
                int deltay = y - startY;
                if (Math.abs(deltax)>Math.abs(deltay)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        startX = x;
        startY = y;
        return super.dispatchTouchEvent(ev);
    }
}
