package moni.anyou.com.view.test.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CScrolView extends ScrollView {
    public CScrolView(Context context) {
        super(context);
    }

    public CScrolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CScrolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("TAG", "CScrolView=========dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("TAG", "CScrolView=========dispatchTouchEvent: ");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("TAG", "CScrolView=========dispatchTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }
}
