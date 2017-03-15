package moni.anyou.com.view;

import org.junit.Test;

import moni.anyou.com.view.tool.Tools;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Tools.main("2017-01-11 11:00:00.0");
    }
}