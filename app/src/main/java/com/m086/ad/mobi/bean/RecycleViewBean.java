package com.m086.ad.mobi.bean;

/**
 * Created by Administrator on 2016/12/6.
 */

public class RecycleViewBean {
    public String Url;
    public String teachearName;
    public String start;
    public int position;

    public RecycleViewBean() {
    }

    public RecycleViewBean(String url, String teachearName, String start) {
        Url = url;
        this.teachearName = teachearName;
        this.start = start;
    }
}
