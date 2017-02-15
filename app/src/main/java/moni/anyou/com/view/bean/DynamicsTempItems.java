package moni.anyou.com.view.bean;

import java.util.ArrayList;

/**
 * Created by lubiao on 2017/2/15.
 */

public class DynamicsTempItems {
    public ArrayList<SentPicBean> items;
    public String iconUrl;
    public String userName;
    public String dynamicsCotents;
    public String sentTime;
    public String StartMan;

    public DynamicsTempItems(ArrayList<SentPicBean> items, String iconUrl, String userName, String dynamicsCotents, String sentTime, String startMan) {
        this.items = items;
        this.iconUrl = iconUrl;
        this.userName = userName;
        this.dynamicsCotents = dynamicsCotents;
        this.sentTime = sentTime;
        StartMan = startMan;
    }
}
