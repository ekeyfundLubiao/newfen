package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;

import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqDynamicsCommentBean extends RequestStandard {


    private String typeid;//":"0","
    private String articleid;//":"0",
    private String content;//":"ceshi","


    public ReqDynamicsCommentBean(String cmd, String uid, String token, String articleid, String content) {
        super(cmd, uid, token);
        this.articleid = articleid;
        this.content = content;

    }

    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, ReqDynamicsCommentBean.class);
        }
        return rmsg;
    }
}
