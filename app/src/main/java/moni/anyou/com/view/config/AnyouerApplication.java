package moni.anyou.com.view.config;

import android.app.Application;

import moni.anyou.com.view.tool.DBUtils;

/**
 * Created by lubiao on 2016/10/28.
 */
public class AnyouerApplication extends Application{
    private static AnyouerApplication mAppApplication;
    public static DBUtils db;


    @Override
    public void onCreate() {
        super.onCreate();

        if (SysConfig.prefs == null) {
            SysConfig.prfsName = "Anyouer" + SysConfig.VersionId;
            SysConfig.prefs = getSharedPreferences(SysConfig.prfsName, 0);
        }

        mAppApplication = this;
        db=new  DBUtils(this);
    }



}
