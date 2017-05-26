package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqAddFamilyBean extends RequestStandard{
    private String mobile;
    private String role;
    public ReqAddFamilyBean(String cmd, String uid, String token, String mobile, String role) {
        super(cmd,  uid,  token);
        this.role = role;
        this.mobile = mobile;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqAddFamilyBean.class);
        }
        return rmsg;
    }
}
