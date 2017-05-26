package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqsUpdateLoginPwdBean extends RequestStandard{

    private String oldpassword;
    private String newpassword;

    public ReqsUpdateLoginPwdBean(String cmd, String uid, String token,String oldpassword,String newpassword) {
        super(cmd,  uid,  token);
        this.newpassword = newpassword;
        this.oldpassword = oldpassword;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqsUpdateLoginPwdBean.class);
        }
        return rmsg;
    }
}
