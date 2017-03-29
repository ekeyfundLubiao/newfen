package moni.anyou.com.view;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import moni.anyou.com.view.tool.Tools;

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

    //    assertEquals("moni.anyou.com.a", appContext.getPackageName());
        Tools.main("2017-01-11 11:00:00.0");
        sustring();

    }


    public void sustring(){
        String str = "/storage/emulated/0/Pictures/Screenshots/Screenshot_2017-02-24-14-22-52.png";
        int lastxie=str.lastIndexOf("/");
        String newstr= str.substring(lastxie+1, str.length());

    }
}
