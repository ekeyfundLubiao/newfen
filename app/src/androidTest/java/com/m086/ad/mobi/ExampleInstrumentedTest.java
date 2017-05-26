package com.m086.ad.mobi;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.m086.ad.mobi.tool.Tools;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("moni.anyou.com.a", appContext.getPackageName());
        Tools.main("2017-01-11 11:00:00.0");
//        likeusers(new ResDynamicsBean.ListBean());
    }


//    public String likeusers(ResDynamicsBean.ListBean bean) throws Exception {
//        ArrayList<Tools.KeyVaule> tempArray = Tools.getLikeNikeName(bean.getLikeuser());
//        StringBuilder templikes = new StringBuilder();
//        String userlike = "12:::小红|||56:::小吕";
//        String result;
//        templikes.append(SysConfig.uid).append(":::").append(SysConfig.userInfoJson.getString("nick"));
//        if (bean.getLikeuser().contains("12")) {
//            result = userlike.replace(templikes, "");
//        } else {
//            result = templikes.append(bean.getLikeuser()).toString();
//        }
//        return result;
//    }
}
