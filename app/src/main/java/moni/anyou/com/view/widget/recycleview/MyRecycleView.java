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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltax = x - startX;
//                int deltay = y - startY;
//                if (Math.abs(deltax)>Math.abs(deltay)) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
//        startX = x;
//        startY = y;
//        return super.dispatchTouchEvent(ev);
//    }
//


    private float FistXLocation;
    private float FistYlocation;
    private boolean Istrigger = false;

    private final int TRIGER_LENTH = 20;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub


        int deltaX = 0;
        int deltaY = 0;

        final float x = ev.getX();
        final float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                deltaX = (int)(FistXLocation - x);
                deltaY = (int)(FistYlocation - y);
                if (Math.abs(deltaY) > TRIGER_LENTH) {

                    Istrigger = true;
                    return super.onInterceptTouchEvent(ev);
                    //拦截这个手势剩下的部分  ，使他不会响应viewpager的相关手势
                }

                return false;//没有触发拦截条件，不拦截事件，继续分发至viewpager

            case MotionEvent.ACTION_DOWN:
                FistXLocation = x;
                FistYlocation = y;
                if(getScaleY()<-400){
                    System.out.println(getScaleY());
                }
                requestDisallowInterceptTouchEvent(false);
                return  super.onInterceptTouchEvent(ev);

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (Istrigger) {

                    Istrigger = false;
                    return  super.onInterceptTouchEvent(ev);
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);

    }
}
