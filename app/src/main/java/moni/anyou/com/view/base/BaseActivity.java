package moni.anyou.com.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.WindowDialog;
import moni.anyou.com.view.widget.dialog.MessgeDialog;
import moni.anyou.com.view.widget.utils.imageload.ImageLoadUtil;
import moni.anyou.com.view.widget.utils.view.SystemStatusManager;
import moni.anyou.com.view.widget.utils.view.ViewUtil;

/**
 * Created by Administrator on 2016/12/5.
 */

public class BaseActivity extends FragmentActivity {
    public static final int LEFT_IN = 0;
    public static final int LEFT_OUT = 1;
    public static final int RIGHT_IN = 2;
    public static final int RIGHT_OUT = 3;
    public static final int BOTTOM_IN = 4;
    public static final int BOTTOM_OUT = 5;
    public static final int TOP_IN = 6;
    public static final int TOP_OUT = 7;
    public ViewUtil mViewUtil;
    public Context mContext;
    public BaseActivity mBaseActivity;
    private MessgeDialog mMessgeDialog;
    protected static final String TAG = "BaseActivity";
    protected static NetProgressWindowDialog mLoadingDialog;
    public TextView tvTitle;
    public TextView ivBack;
    public TextView  tvRight;
    public CheckBox checkbox;

    public void initView() {
    }

    public void init() {
        init(true);
    }

    /**
     * 初始化
     */
    public void init(boolean set) {
        initBase();
        setTranslucentColor(set);
        initView();
        setData();
        setAction();
        sendHttp();
    }

    /**
     * 初始化全局变量
     */
    private void initBase() {
        mContext = this;
        mBaseActivity = this;
        mViewUtil = new ViewUtil(this);

        // mLoadingDialog = new NetProgressWindowDialog(this.getApplicationContext());
//        mListFooterView = LayoutInflater.from(mContext).inflate(
//                R.layout.view_list_footer, null);
//        mFooterTextView = (TextView) mListFooterView
//                .findViewById(R.id.text_nodata);
//        mFooterTextView.setVisibility(View.GONE);
//        JPushInterface.init(getApplicationContext());
    }

    public void activityAnimation(int TYPE) {
        switch (TYPE) {
            case LEFT_IN:
                overridePendingTransition(R.anim.activity_slide_left_in, 0);
                break;
            case LEFT_OUT:
                overridePendingTransition(0, R.anim.activity_slide_left_out);
                break;
            case RIGHT_IN:
                overridePendingTransition(R.anim.activity_slide_right_in, 0);
                break;
            case RIGHT_OUT:
                overridePendingTransition(0, R.anim.activity_slide_right_out);
                break;
            case BOTTOM_IN:
                overridePendingTransition(R.anim.activity_slide_bottom_in, 0);
                break;
            case BOTTOM_OUT:
                overridePendingTransition(0, R.anim.activity_slide_bottom_out);
                break;
            case TOP_IN:
                overridePendingTransition(R.anim.activity_slide_top_in, 0);
                break;
            case TOP_OUT:
                overridePendingTransition(0, R.anim.activity_slide_top_out);
                break;
            default:
                break;
        }
    }


    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);// 状态栏无背景
    }

    public void setTranslucentColor(boolean set) {
        if (!set) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.appcolor);// 通知栏所需颜色
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void showProgressBar() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new NetProgressWindowDialog(mContext);
        }
        mLoadingDialog.ShowWindow();
    }

    public void closeProgressBar() {
        mLoadingDialog.closeWindow();
    }


    /**
     * 请求处理过程中
     */
    public void onDoing() {
        closeProgressBar();
    }

    /**
     * 返回
     */
    public void onBack() {
        mBaseActivity.finish();
        activityAnimation(RIGHT_OUT);

    }

    /**
     * 初始化数据
     */
    public void setData(){

    }

    /**
     *
     */
    public void sendHttp(){

    }
    /**
     * 添加监听
     */
    public void setAction() {
    }



    /**
     * 加载图片 2:1
     *
     * @param url
     * @param imageview
     */
    public void setBitmaptoImageView21(String url, ImageView imageview) {
        setBitmaptoImageView(url, imageview, R.drawable.loading_21,
                R.drawable.loading_err_21, R.drawable.loading_null_21);
    }



    public void setBitmaptoImageView11(String url, ImageView imageview) {
        setBitmaptoImageView(url, imageview, R.drawable.loading_11,
                R.drawable.loading_err_11, R.drawable.loading_null_11);
    }
    /**
     * 加载图片
     *
     * @param url
     * @param imageview
     * @param loadingRes
     * @param errRes
     * @param failRes
     */
    public void setBitmaptoImageView(String url, ImageView imageview,
                                     int loadingRes, int errRes, int failRes) {
        ImageLoadUtil.getInstance(mContext).mImageLoader.displayImage(url,
                imageview,
                ImageLoadUtil.getOptions(loadingRes, errRes, failRes));
    }

    public void initTitle(){
         tvTitle=(TextView) findViewById(R.id.page_title);
         ivBack=(TextView) findViewById(R.id.iv_left);
         tvRight=(TextView) findViewById(R.id.right_tv);

    }
    public void initTitlewithCheckbox(){
        tvTitle=(TextView) findViewById(R.id.page_title);
        ivBack=(TextView) findViewById(R.id.iv_left);
        checkbox=(CheckBox) findViewById(R.id.checkbox);
    }

    public final static int ApplyPermission_Sign = 9999;

    public void requestPermission(String permissionName) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            //超过6.0以上需要授权
            int checkSelfPermission = PackageManager.PERMISSION_GRANTED;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(mBaseActivity, permissionName);
            } catch (RuntimeException e) {

            }
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //需要授权
                if (ActivityCompat.shouldShowRequestPermissionRationale(mBaseActivity, permissionName)) {
                    //拒绝过了,提示用户如果想要正常使用，要手动去设置中授权。
//              Toast.makeText(mContext, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
                    permissionAlreadyRefuse(permissionName);
                } else {
                    //进行授权
                    ActivityCompat.requestPermissions(mBaseActivity, new String[]{permissionName}, ApplyPermission_Sign);
                }
            } else {
                //不需要授权,进行正常操作
                permissionNoNeed(permissionName);
            }
        }else
        {
            permissionNoNeed(permissionName);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ApplyPermission_Sign) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 同意授权,进行正常操作。
                permissionSuccess(permissions[0]);
            } else {
                // 拒绝授权。
                permissionRefuse(permissions[0]);
            }
        }
    }


    //不需要授权
    public void permissionNoNeed(String permissionName) {

    }


    //权限授权成功
    public void permissionSuccess(String permissionName) {

    }

    //权限被拒绝
    public void permissionRefuse(String permissionName) {

    }

    //权限之前被拒绝
    public void permissionAlreadyRefuse(String permissionName) {


    }

    //打开应用设置页面
    public void openPermissionSettingPage(int requestCode)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mBaseActivity.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent,requestCode);
    }

    public void initMsgview()
    {
        if (mMessgeDialog==null) {
            mMessgeDialog = new MessgeDialog(mBaseActivity);
        }

    }



    public void showMsgDialog(String message,String left,String right,MessgeDialog.MsgDialogListener listener) {
        dismissMsgDialog();
        mMessgeDialog.setMessage(message);
        mMessgeDialog.setLeft(left);
        mMessgeDialog.setRight(right);
        mMessgeDialog.setMsgDialogListener(listener);
        mMessgeDialog.show();
    }

    public void dismissMsgDialog() {
        if(mMessgeDialog.isShowing())
        {
            mMessgeDialog.dismiss();
        }
    }

}
