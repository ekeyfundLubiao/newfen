package moni.anyou.com.view.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */

public class SentPicBean implements Serializable{
    public String filePathName="";
    public Bitmap bitmap;
    public String Url;

    public SentPicBean() {
    }

    public SentPicBean(String filePathName, Bitmap bitmap) {
        this.filePathName = filePathName;
        this.bitmap = bitmap;
    }

    public SentPicBean(String url) {
        Url = url;
    }
}
