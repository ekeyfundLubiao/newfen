package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqHomeBean extends RequestStandard{

    public ReqHomeBean(String cmd, String uid, String token) {
        super(cmd,  uid,  token);
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqHomeBean.class);
        }
        return rmsg;
    }
}
