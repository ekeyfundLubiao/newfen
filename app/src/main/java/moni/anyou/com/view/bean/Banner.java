package moni.anyou.com.view.bean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class Banner {

    public String linkUrl;
    public String title;
    public String bannerUrl;
    public String sizeMark;

    public Banner() {
    }

    public Banner(String linkUrl, String title, String bannerUrl, String sizeMark) {
        this.linkUrl = linkUrl;
        this.title = title;
        this.bannerUrl = bannerUrl;
        this.sizeMark = sizeMark;
    }
}
