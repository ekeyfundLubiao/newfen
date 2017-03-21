package moni.anyou.com.view.tool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.nostra13.universalimageloader.utils.StorageUtils;


import junit.runner.Version;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.contacts.LocalConstant;
import moni.anyou.com.view.view.account.LoginActivity;
import moni.anyou.com.view.widget.utils.imageload.ImageLoadUtil;

/**
 * 跟App相关的辅助类
 */
public class AppTools {

    private AppTools() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取uuid
     *
     * @param context
     * @return uuid
     */
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 判断是否登录
     *
     * @param context
     * @return boolean
     */
    public static boolean isLogin(BaseActivity context) {
        if (!hasUserId(context)) {
            Intent login_intent = new Intent(context, LoginActivity.class);
            context.startActivity(login_intent);
            context.overridePendingTransition(R.anim.activity_slide_right_in,
                    R.anim.activity_slide_left_out);
            return false;
        }
        return true;
    }

    /**
     * 判断是否有USERID
     *
     * @param context
     * @return boolean
     */
    public static boolean hasUserId(Context context) {
//		String userId = SharedpreferencesTools.getInstance(context)
//				.getStringValue(CacheKey.USERID_KEY);
//		if (VerificationTools.isNull(userId)) {
//			return false;
//		}
        if (SysConfig.userInfoJson == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否设置了手势密码
     *
     * @param context
     * @return boolean
     */
    public static boolean isSetGesture(BaseActivity context) {
//		String gesture = SharedpreferencesTools.getInstance(context)
//				.getStringValue(CacheKey.GESTURE_PASSWORD);
//		if (!VerificationTools.isNull(gesture)) {
//			return true;
//		}
        return false;
    }

    /**
     * 获取文件缓存大小
     *
     * @param mContext
     * @return boolean
     */
    public static String getFileCacheSize(BaseActivity mContext) {
        try {
            double allSize = 0.00;

            //网络获取的图片缓存
            File img_folder = StorageUtils.getOwnCacheDirectory(mContext,
                    LocalConstant.Relative_Path);
            File[] fileList = img_folder.listFiles();
            if ((img_folder != null) && (img_folder.exists()) && (null != fileList)) {
                for (int i = 0; i < fileList.length; i++) {
                    allSize += fileList[i].length();
                }
            }
            //裁剪的图片缓存
            File img_cutcachelder = StorageUtils.getOwnCacheDirectory(mContext,
                    LocalConstant.Local_Photo_Path);
            File[] cutcachel_file = img_cutcachelder.listFiles();
            if ((img_cutcachelder != null) && (img_cutcachelder.exists()) && (null != cutcachel_file)) {
                for (int i = 0; i < cutcachel_file.length; i++) {
                    allSize += cutcachel_file[i].length();
                }
            }

            //下载的安装包
            File down_folder = StorageUtils
                    .getOwnCacheDirectory(mContext, LocalConstant.Download_Path);
            File[] downList = down_folder.listFiles();
            if ((down_folder != null) && (down_folder.exists()) && (null != downList)) {
                for (int i = 0; i < downList.length; i++) {
                    allSize += downList[i].length();
                }
            }
            return getTwoDouble(allSize / (1024 * 1024))
                    + "M";
        } catch (Exception e) {
            ToastTools.showShort(mContext, "抓取缓存异常");
        }
        return "0M";
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 清除文件缓存
     *
     * @param mContext
     */
    public static void clearFileCache(BaseActivity mContext) {
        try {
            File img_folder = StorageUtils.getOwnCacheDirectory(mContext,
                    ImageLoadUtil.Relative_Path);
            if ((img_folder != null) && (img_folder.exists())) {
                if (img_folder.isDirectory()) {
                    deleteDir(img_folder);
                } else {
                    img_folder.delete();
                }
            }
            File down_folder = StorageUtils
                    .getOwnCacheDirectory(mContext, LocalConstant.Download_Path);
            if ((down_folder != null) && (down_folder.exists())) {
                if (down_folder.isDirectory()) {
                    deleteDir(down_folder);
                } else {
                    down_folder.delete();
                }
            }
        } catch (Exception e) {

        }
    }

    public static boolean isShowTopView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        }
        return false;
    }

    /**
     * 更新信息处理
     *
     * @param version
     * @param context
     * @return 0：不需要更新 1：小更新 2：大更新
     */
    public static int getUpdateType(Version version, Context context) {
//		if ((null != version) && (!VerificationTools.isNull(version.versionNo))) {
//			String now_version = AppTools.getVersionName(context).trim();
//			String server_version = version.versionNo.trim();
//			if (now_version.equals(server_version)) {
//				return 0;
//			}
//			String[] nowArr = now_version.split("\\.");
//			String[] serverArr = server_version.split("\\.");
//			if ((nowArr.length == 3) && (serverArr.length == 3)) {
//				String now_first = nowArr[0];
//				String server_first = serverArr[0];
//				if (!now_first.equals(server_first)) {
//					return 2;
//				}
//				String now_second = nowArr[1];
//				String server_second = serverArr[1];
//				if (!now_second.equals(server_second)) {
//					return 1;
//				}
//				String now_third = nowArr[2];
//				String server_third = serverArr[2];
//				if (!now_third.equals(server_third)) {
//					return 1;
//				}
//			}
//			return 0;
//		} else {
//			return 0;
//		}
        return 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 用时间戳生成照片名称
     *
     * @return
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }


    public static void ExitLogin() {
        SysConfig.userInfoJson = null;
        SharedPreferences.Editor editor = SysConfig.prefs.edit();// 取得编辑器
        editor.putString("sUsername", "");// 存储配置 参数1 是key 参数2 是值
        editor.putString("sPassword", "");
        editor.commit();
    }

    public static void jumptoLogin(BaseActivity mActivity) {
        ExitLogin();
        mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
    }

    public static String getTwoDouble(double number) {
        DecimalFormat df = new DecimalFormat("######0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(number);
    }
}
