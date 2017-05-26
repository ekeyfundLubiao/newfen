package com.m086.ad.mobi.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */

public class SentPicBean implements Serializable{
    public String filePathName="";
    public Bitmap bitmap;
    public String Url;
    public String newFileNameMap;

    public SentPicBean() {
    }

    public SentPicBean(String filePathName, String newFileNameMap) {
        this.filePathName = filePathName;
        this.newFileNameMap = newFileNameMap;
    }

    public SentPicBean(String url) {
        Url = url;
    }
}
