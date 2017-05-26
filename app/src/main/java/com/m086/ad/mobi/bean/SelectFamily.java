package com.m086.ad.mobi.bean;

import com.m086.ad.mobi.bean.response.ResFamilyNumer;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SelectFamily {
    public int positon;
    public  ResFamilyNumer.RelationBean bean;

    public SelectFamily() {
    }

    public SelectFamily(int positon,  ResFamilyNumer.RelationBean bean) {
        this.positon = positon;
        this.bean = bean;
    }
}
