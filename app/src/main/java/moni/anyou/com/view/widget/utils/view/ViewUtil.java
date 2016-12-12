package moni.anyou.com.view.widget.utils.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ViewUtil {
	private Activity mContext;
	private float defaultProportion = 0.6667f;
	
	public ViewUtil(Activity context) {
		this.mContext = context;
	}

	public void setViewHight(View viewiew, float width, float proportion) {
		LayoutParams lp = viewiew.getLayoutParams();
		if (proportion > 0) {
			lp.height = (int) (width * proportion);
		} else {
			lp.height = (int) (width * defaultProportion);
		}
		viewiew.setLayoutParams(lp);
	}

	public void setViewHight(View viewiew, int hight) {
		LayoutParams lp = viewiew.getLayoutParams();
			lp.height = hight;
			lp.width = getScreenWidth();
		viewiew.setLayoutParams(lp);
	}
	
	public void setViewWidth(View viewiew, int width) {
		LayoutParams lp = viewiew.getLayoutParams();
			lp.width = width;
		viewiew.setLayoutParams(lp);
	}
	public void setViewSize(View viewiew, int hight, int width) {
		LayoutParams lp = viewiew.getLayoutParams();
			lp.height = hight;
			lp.width = width;
		viewiew.setLayoutParams(lp);
	}
	
	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	public int getScreenHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	public float getDensity() {
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}
	
	public float getDensityDpi() {
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.densityDpi;
	}
	
	/**
	 * 获得状�?�栏的高�??
	 * 
	 * @param context
	 * @return
	 */
	public int getStatusHeight()
	{
		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = mContext.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public Bitmap snapShotWithStatusBar()
	{
		View view = mContext.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状�?�栏
	 * 
	 * @param activity
	 * @return
	 */
	public Bitmap snapShotWithoutStatusBar()
	{
		View view = mContext.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
}
