package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqSentDynamicsBean extends RequestStandard{

    public static String TYPEID_ADD = "0";
    public static String TYPEID_DELETE = "2";
    public static String ARTICLEID_ADD = "0";
    public static String ARTICLEID_DELETE = "1";
    private String typeid;//":"0","
    private String articleid;//":"0",
    private String content;//":"ceshi","
    private String pic;//":"1.png,2.png,3.png"

    public ReqSentDynamicsBean(String cmd, String uid, String token,String typeid,String articleid ,String content,String pic) {
        super(cmd,  uid,  token);
        this.typeid = typeid;
        this.articleid = articleid;
        this.content = content;
        this.pic = pic;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqSentDynamicsBean.class);
        }
        return rmsg;
    }
}
