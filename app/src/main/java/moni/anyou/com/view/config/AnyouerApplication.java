package moni.anyou.com.view.config;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import moni.anyou.com.view.tool.DBUtils;

/**
 * Created by lubiao on 2016/10/28.
 */
public class AnyouerApplication extends Application {
    private static AnyouerApplication mAppApplication;
    public static DBUtils db;
    public static IWXAPI mIWXAPI = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (SysConfig.prefs == null) {
            SysConfig.prfsName = "Anyouer" + SysConfig.VersionId;
            SysConfig.prefs = getSharedPreferences(SysConfig.prfsName, 0);
        }
        mIWXAPI = WXAPIFactory.createWXAPI(this, "wxd930ea5d5a258f4f", true);
        mIWXAPI.registerApp("wxd930ea5d5a258f4f");
        mAppApplication = this;
        db = new DBUtils(this);
    }


}
