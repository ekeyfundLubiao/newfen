package com.m086.ad.mobi.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 */
public class ScreenTools {
    /**
     * 获得屏幕宽度
     */
    public static int getScreenWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     *获得屏幕宽度dpi
     */
    public static float getScreenWidthDpi(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.xdpi;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     *获得屏幕高度dpi
     */
    public static float getScreenHeightDpi(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.ydpi;
    }

    /**
     * 获得屏幕密度
     */
    public static float getDensity(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.density;
    }

    /**
     * 获得屏幕密度dpi
     */
    public static int getDensityDpi(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.densityDpi;
    }


    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context mContext) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity mContext) {
        View view = mContext.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(mContext);
        int height = getScreenHeight(mContext);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity mContext) {
        View view = mContext.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(mContext);
        int height = getScreenHeight(mContext);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 设置view宽高
     *
     * @param view
     * @param height
     * @param width
     */
    public static void setViewSize(View view,int height,int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(null==lp)
        {
            lp = new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if(height>0)
        {
            lp.height = height;
        }
        if(width>0)
        {
            lp.width = width;
        }
        view.setLayoutParams(lp);
    }

    /**
     * 设置view宽度
     *
     * @param view
     * @param width
     */
    public static void setViewWidth(View view,int width) {
        setViewSize(view,-1,width);
    }

    /**
     * 设置view高度
     *
     * @param view
     * @param height
     */
    public static void setViewHeight(View view,int height) {
        setViewSize(view,height,-1);
    }

    /**
     * 根据宽度设置view宽高
     *
     * @param view
     * @param width
     * @param proportion
     */
    public static void setViewSizeAsWidth(View view,int width,float proportion)
    {
        setViewSize(view,(int)(width*proportion),width);
    }

    /**
     * 根据高度设置view宽高
     *
     * @param view
     * @param height
     * @param proportion
     */
    public static void setViewSizeAsHeight(View view,int height,float proportion)
    {
        setViewSize(view,height,(int)(height*proportion));
    }

    /**
     * dp转px
     * @return int
     */
    public static int dp2px(float dpVal,Context mContext) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mContext.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     * @return int
     */
    public static int sp2px(float spVal,Context mContext) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, mContext.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     * @param pxVal
     * @return int
     */
    public static float px2dp(float pxVal,Context mContext) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     * @param pxVal
     * @return int
     */
    public static float px2sp(float pxVal,Context mContext) {
        return (pxVal / mContext.getResources().getDisplayMetrics().scaledDensity);
    }
}
