package moni.anyou.com.view.widget.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2017/4/1.
 */

public class MyRecycleView extends RecyclerView implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;
    private float ox;
    private float oy;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    private void init() {
        this.setWillNotDraw(false);
        this.childHelper = new NestedScrollingChildHelper(this);
        this.childHelper.setNestedScrollingEnabled(true);
    }

    public MyRecycleView(Context context) {
        super(context);
        this.init();
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }



    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.ox = ev.getX();
            this.oy = ev.getY();
            this.startNestedScroll(3);
        }

        if (ev.getAction() == 1 || ev.getAction() == 3) {
            this.stopNestedScroll();
        }

        if (ev.getAction() == 2) {
            float clampedX = ev.getX();
            float clampedY = ev.getY();
            int dx = (int) (this.ox - clampedX);
            int dy = (int) (this.oy - clampedY);
            if (this.dispatchNestedPreScroll(dx, dy, this.consumed, this.offsetInWindow)) {
                ev.setLocation(clampedX + (float) this.consumed[0], clampedY + (float) this.consumed[1]);
            }

            this.ox = ev.getX();
            this.oy = ev.getY();
        }

        return super.onTouchEvent(ev);
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        if (childHelper != null) {
            this.childHelper.setNestedScrollingEnabled(enabled);
        }

    }

    public boolean isNestedScrollingEnabled() {
        return this.childHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return this.childHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.childHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.childHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return this.childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return this.childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return this.childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return this.childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return this.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return this.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return super.fling(velocityX, velocityY);
    }
}
