package com.m086.ad.mobi.bean;

/**
 * Created by Administrator on 2016/12/16.
 */

public class RelationBean {
    public String name;
    public String url;
    public int mark;
    public String relation;
    public String phoneNym;
    public boolean boolDelete = false;



    public RelationBean(String name, String url, int mark, String relation, String phoneNym) {
        this.name = name;
        this.url = url;
        this.mark = mark;
        this.relation = relation;
        this.phoneNym = phoneNym;
    }
}
