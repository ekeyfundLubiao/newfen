package moni.anyou.com.view.config;

import android.app.Application;

/**
 * Created by lubiao on 2016/10/28.
 */
public class AnyouerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        if (SysConfig.prefs == null) {
            SysConfig.prfsName = "Anyouer" + SysConfig.appId;
            SysConfig.prefs = getSharedPreferences(SysConfig.prfsName, 0);
        }
    }


}
