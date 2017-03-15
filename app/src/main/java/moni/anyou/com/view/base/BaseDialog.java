package moni.anyou.com.view.base;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import moni.anyou.com.view.R;
import moni.anyou.com.view.config.AnyouerApplication;
import moni.anyou.com.view.tool.ScreenTools;
import moni.anyou.com.view.view.StartActivity;

/**
 * Created by Administrator on 2017/2/16.
 */
public abstract class BaseDialog extends Dialog{
    public AnyouerApplication mApplication;
    public Context mContext;
    public BaseActivity mActivity;

    private LinearLayout mLayout;
    private RelativeLayout mTitleBar;
    private TextView mLeftView;
    private TextView mCenterView;
    private TextView mRightView;
    private FrameLayout mViewGroup;

    public BaseDialog(BaseActivity activity) {
        super(activity, R.style.dialog);
        mContext = activity.mContext;
        mActivity = activity;
        mApplication = (AnyouerApplication) mActivity.getApplication();
        super.setContentView(R.layout.base_layout);
        initBaseView();
    }

    public BaseDialog(BaseActivity activity, int styleId) {
        super(activity, styleId);
        mContext = activity.mContext;
        mActivity = activity;
        mApplication = (AnyouerApplication) mActivity.getApplication();
        super.setContentView(R.layout.base_layout);
        initBaseView();
    }

    private void initBaseView()
    {
        mLayout = (LinearLayout)findViewById(R.id.layout_bg);
        mTitleBar = (RelativeLayout)findViewById(R.id.layout_title);
        mLeftView = (TextView)findViewById(R.id.text_left);
        mCenterView = (TextView)findViewById(R.id.title_center);
        mRightView = (TextView)findViewById(R.id.text_right);
        mTitleBar.setOnClickListener(mTitleClickListener);
        mLeftView.setOnClickListener(mTitleClickListener);
        mCenterView.setOnClickListener(mTitleClickListener);
        mRightView.setOnClickListener(mTitleClickListener);
        mViewGroup = (FrameLayout)findViewById(R.id.content_layout);
    }

    public void setContentView(int layoutResId)
    {
        View contentView = mActivity.getLayoutInflater().inflate(layoutResId,null);
        mViewGroup.addView(contentView);
    }

    public void init(int width,int height,int animationStyle,int gravity,boolean outIsDissmis) {
        setCanceledOnTouchOutside(outIsDissmis);
        initSizeAndAnim(width,height,animationStyle,gravity);
        initView();
        setData();
        setListener();
        sendHttp();
    }

    private void initSizeAndAnim(int width,int height,int animationStyle,int gravity)
    {
        Window window = this.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = height;
        p.width = width;
        p.gravity = gravity;
        window.setAttributes(p);
        window.setWindowAnimations(animationStyle);
    }

    public void initView(){};

    public void setData(){};

    public void setListener(){};

    public void sendHttp(){};

    public void onBack(){
        dismiss();
    }
     /**
     *物理返回
      */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initFullRightin(boolean outIsDissmis,int gravity)
    {
        init(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,R.style.dialog_r_r,gravity,outIsDissmis);
    }

    public void initRightin(int width,int height,boolean outIsDissmis,int gravity)
    {
        init(width,height,R.style.dialog_r_r,gravity,outIsDissmis);
    }

    public void initWRAPDialog(int animId,boolean outIsDissmis)
    {
        init(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,animId, Gravity.CENTER,outIsDissmis);
    }

    public void initNomalDialog(int animId,boolean outIsDissmis)
    {
        init(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,animId, Gravity.CENTER,outIsDissmis);
    }

    public void initBottomDialog()
    {
        init(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,R.style.dialog_animation_bottom, Gravity.CENTER,true);
    }

    public void initWidthDialog(int animId,boolean outIsDissmis,float w)
    {
        init((int)(ScreenTools.getScreenWidth(mContext)*w),ViewGroup.LayoutParams.WRAP_CONTENT,animId, Gravity.CENTER,outIsDissmis);
    }

    /**
     * 获取layout
     *
     * @return RelativeLayout
     */
    public LinearLayout getLayout()
    {
        return mLayout;
    }
    /**
     * 获取layout
     *
     * @return RelativeLayout
     */
    public void setLayoutBg(int resId)
    {
        mLayout.setBackgroundResource(resId);
    }
    /**
     * 获取标题栏
     *
     * @return RelativeLayout
     */
    public RelativeLayout getTitleBar()
    {
        return mTitleBar;
    }

    /**
     * 获取标题栏左侧view
     *
     * @return TextView
     */
    public TextView getTitleLeftView()
    {
        return mLeftView;
    }

    /**
     * 获取标题栏中间view
     *
     * @return TextView
     */
    public TextView getTitleCenterView()
    {
        return mCenterView;
    }

    /**
     * 获取标题栏右侧view
     *
     * @return TextView
     */
    public TextView getTitleRightView()
    {
        return mRightView;
    }

    /**
     * 设置标题栏背景
     *
     * @param  bgResId
     */
    public void setTitleBarBg(int bgResId)
    {
        if(bgResId>0)
        {
            getTitleBar().setVisibility(View.VISIBLE);
            getTitleBar().setBackgroundResource(bgResId);
        }
    }

    /**
     * 设置标题栏左侧view 图标与文字
     *
     * @param  iconResId
     * @param  text
     */
    public void setTitleLeftTextAndIcon(int iconResId,String text)
    {
        getTitleLeftView().setVisibility(View.VISIBLE);
        if(iconResId>0)
        {
            Drawable drawable= ContextCompat.getDrawable(mContext,iconResId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            getTitleLeftView().setCompoundDrawables(drawable,null,null,null);
        }
        getTitleLeftView().setText(text);
    }

    /**
     * 设置标题栏中间view 文字
     *
     * @param  text
     */
    public void setTitleCenterText(String text)
    {
        getTitleCenterView().setVisibility(View.VISIBLE);
        getTitleCenterView().setText(text);
    }

    /**
     * 设置标题栏右侧view 图标与文字
     *
     * @param  iconResId
     * @param  text
     */
    public void setTitleRightTextAndIcon(int iconResId,String text)
    {
        getTitleRightView().setVisibility(View.VISIBLE);
        if(iconResId>0)
        {
            Drawable drawable= ContextCompat.getDrawable(mContext,iconResId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            getTitleRightView().setCompoundDrawables(drawable,null,null,null);
        }
        getTitleRightView().setText(text);
    }

    /**
     * 设置返回标题栏
     */
    public void initBackTitleBar(String centerText,String rightText)
    {
        initTitleBar(R.color.appcolor,R.drawable.arrow_left,"",centerText,-1,rightText);
    }

    /**
     * 设置返回标题栏
     */
    public void initBackTitleBar(String centerText)
    {
        initBackTitleBar(centerText,"");
    }

    /**
     * 设置标题栏
     */
    public void initTitleBar(int bgResId, int leftResId, String leftText,String centerText, int rightResId, String rightText)
    {
        setTitleBarBg(bgResId);
        setTitleLeftTextAndIcon(leftResId,leftText);
        setTitleCenterText(centerText);
        setTitleRightTextAndIcon(rightResId,rightText);
    }
    /**
     * 标题栏点击监听
     * @param  view
     */
    private View.OnClickListener mTitleClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.layout_title:
                    titleBarClick();
                    break;
                case R.id.text_left:
                    titleLeftClick();
                    break;
                case R.id.title_center:
                    titleCenterClick();
                    break;
                case R.id.text_right:
                    titleRightClick();
                    break;
            }
        }
    };
    /**
     * 标题栏点击
     */
    public void titleBarClick(){}

    /**
     * 标题栏左View点击
     */
    public void titleLeftClick(){
        onBack();
    }
    /**
     * 标题栏标题点击
     */
    public void titleCenterClick(){}

    /**
     * 标题栏右View点击
     */
    public void titleRightClick(){}


}
