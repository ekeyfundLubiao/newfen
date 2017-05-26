package com.m086.ad.mobi.bean;

/**
 * Created by Administrator on 2016/12/7.
 */

public class HomeItemBean {
    public int IconRes;
    public String title;
    public String value;
    public boolean iSHowArr=true;

    public HomeItemBean(int iconRes, String title) {
        IconRes = iconRes;
        this.title = title;
    }

    public HomeItemBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public HomeItemBean(String title, String value, boolean iSHowArr) {
        this.title = title;
        this.value = value;
        this.iSHowArr = iSHowArr;
    }

    public HomeItemBean(int iconRes, String title, String value, boolean iSHowArr) {
        IconRes = iconRes;
        this.title = title;
        this.value = value;
        this.iSHowArr = iSHowArr;
    }
}
