package moni.anyou.com.view.bean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class VideoBean {
    public String Url;
    public String aliveNum;
    public String className;

    public VideoBean(String url, String aliveNum, String className) {
        Url = url;
        this.aliveNum = aliveNum;
        this.className = className;
    }
}
