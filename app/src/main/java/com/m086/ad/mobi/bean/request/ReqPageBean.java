package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqPageBean extends RequestStandard{

    private String pageSize;
    private String pageNo;
    public ReqPageBean(String cmd, String uid, String token,String pageNo,String pageSize) {
        super(cmd,  uid,  token);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqPageBean.class);
        }
        return rmsg;
    }
}
