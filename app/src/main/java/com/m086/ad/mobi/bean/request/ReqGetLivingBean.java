package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/5/18.
 */

public class ReqGetLivingBean extends RequestStandard {

    public String id;

    public ReqGetLivingBean(String cmd, String uid, String token, String id) {
        super(cmd, uid, token);
        this.id = id;
    }

    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, ReqGetLivingBean.class);
        }
        return rmsg;
    }
}
