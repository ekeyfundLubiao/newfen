package com.m086.ad.mobi.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/8.
 */

public class VideoBean implements Serializable{
    public String Url;
    public String aliveNum;
    public String className;

    public VideoBean(String url, String aliveNum, String className) {
        Url = url;
        this.aliveNum = aliveNum;
        this.className = className;
    }
}
