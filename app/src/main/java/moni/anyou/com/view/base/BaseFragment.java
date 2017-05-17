package moni.anyou.com.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.utils.imageload.ImageLoadUtil;
import moni.anyou.com.view.widget.utils.view.ViewUtil;

/**
 * Created by Administrator on 2016/12/5.
 */

public class BaseFragment extends Fragment {
    public static final int LEFT_IN = 0;
    public static final int LEFT_OUT = 1;
    public static final int RIGHT_IN = 2;
    public static final int RIGHT_OUT = 3;
    public static final int BOTTOM_IN = 4;
    public static final int BOTTOM_OUT = 5;
    public static final int TOP_IN = 6;
    public static final int TOP_OUT = 7;
    public Context mContext;
    public BaseActivity mBaseActivity;
    protected static final String TAG = "BaseFragment";
    public ViewUtil mViewUtil;
    public NetProgressWindowDialog mLoadingDialog = null;
    public boolean isCreate = false;
    public TextView tvTitle;
    public TextView ivBack;
    public TextView  tvRight;

    public void activityAnimation(int TYPE) {
        switch (TYPE) {
            case LEFT_IN:
                getActivity().overridePendingTransition(R.anim.activity_slide_left_in, 0);
                break;
            case LEFT_OUT:
                getActivity().overridePendingTransition(0, R.anim.activity_slide_left_out);
                break;
            case RIGHT_IN:
                getActivity().overridePendingTransition(R.anim.activity_slide_right_in, 0);
                break;
            case RIGHT_OUT:
                getActivity().overridePendingTransition(0, R.anim.activity_slide_right_out);
                break;
            case BOTTOM_IN:
                getActivity().overridePendingTransition(R.anim.activity_slide_bottom_in, 0);
                break;
            case BOTTOM_OUT:
                getActivity().overridePendingTransition(0, R.anim.activity_slide_bottom_out);
                break;
            case TOP_IN:
                getActivity().overridePendingTransition(R.anim.activity_slide_top_in, 0);
                break;
            case TOP_OUT:
                getActivity().overridePendingTransition(0, R.anim.activity_slide_top_out);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < 23) {
            mContext = activity;
            mBaseActivity = (BaseActivity) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            mContext = context;
            mBaseActivity = (BaseActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 初始化
     */
    public void init(View view) {
        initBase(view);
        initView();
        setData();
        setAction();
        sendHttp();
    }


    /**
     * 初始化view
     */
    public void initView() {

    }

    /**
     * 设置默认值
     */
    public void setData() {
    }

    /**
     * 添加监听
     */
    public void setAction() {
    }

    /**
     * 发送网络请求
     */
    public void sendHttp() {

    }

    public void showProgressBar() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new NetProgressWindowDialog(mBaseActivity);
        }
        mLoadingDialog.show();
    }

    public void closeProgressBar() {
        mLoadingDialog.dismiss();
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
    }

    /**
     * 初始化全局变量
     */
    private void initBase(View view) {
        isCreate = true;
        mViewUtil = new ViewUtil(getActivity());
        mLoadingDialog = new NetProgressWindowDialog(mBaseActivity);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    /**
     * 加载图片 2:1
     *
     * @param url
     * @param imageview
     */
    public void setBitmaptoImageView11(String url, ImageView imageview) {
        setBitmaptoImageView(url, imageview, R.drawable.loading_11,
                R.drawable.loading_err_11, R.drawable.loading_null_11);
    }
    /**
     * 加载图片 2:1
     *
     * @param url
     * @param imageview
     */
    public void setBitmaptoImageView21(String url, ImageView imageview) {
        setBitmaptoImageView(url, imageview, R.drawable.loading_bg_rect,
                R.drawable.loading_err_21, R.drawable.loading_null_21);
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

    public void initTitle(View view){
        tvTitle=(TextView) view.findViewById(R.id.page_title);
        ivBack=(TextView) view.findViewById(R.id.iv_left);
        tvRight=(TextView) view.findViewById(R.id.right_tv);
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
    public void openPermissionSettingPage()
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mBaseActivity.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}
