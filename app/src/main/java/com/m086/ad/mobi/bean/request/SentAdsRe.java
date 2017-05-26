package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;
import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/5/24.
 */

public class SentAdsRe extends RequestStandard {

    public static String TYPEID_ADD = "0";
    public static String TYPEID_Eff = "1";
    public static String TYPEID_Del = "2";
    private String typeid;//":"0","
    private String adid;//":"0",
    private String content;//":"ceshi",
    private String pic;

    public SentAdsRe(String cmd, String uid, String token) {
        super(cmd, uid, token);
    }

    /**
     * @param cmd
     * @param uid
     * @param token
     * @param typeid
     * @param adid
     * @param content
     */
    public SentAdsRe(String cmd, String uid, String token, String typeid, String adid, String content, String pic) {
        super(cmd, uid, token);
        this.typeid = typeid;
        this.adid = adid;
        this.content = content;
        this.pic = pic;
    }

    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, SentAdsRe.class);
        }
        return rmsg;
    }
}
