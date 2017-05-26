package com.m086.ad.mobi.bean.request;

import com.google.gson.Gson;
import com.m086.ad.mobi.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ReqSentTextAdsBean extends RequestStandard {


    /**
     * typeid : 0
     * adid : 0
     * content : ceshi
     * pic : 1.png
     * smallpic : 1.png
     * tel : 021-1111
     * mobile : 13444444444
     * wechatid : sssss
     * email : test@test.com
     * qq : 333333333
     * role : 营销总监
     * company : 上海司杰
     */

    public static String TYPEID_ADD = "0";
    public static String TYPEID_Eff = "1";
    public static String TYPEID_Del = "2";
    private String typeid;
    private String adid;
    private String content;
    private String pic;
    private String smallpic;
    private String tel;
    private String mobile;
    private String wechatid;
    private String email;
    private String qq;
    private String role;
    private String company;


    /**
     * @param cmd
     * @param uid
     * @param token
     * @param typeid
     * @param adid
     * @param content
     * @param pic
     * @param smallpic
     * @param tel
     * @param mobile
     * @param wechatid
     * @param email
     * @param qq
     * @param role
     * @param company
     */
    public ReqSentTextAdsBean(String cmd, String uid, String token, String typeid, String adid, String content, String pic,
                              String smallpic, String tel, String mobile, String wechatid, String email, String qq, String role, String company) {
        super(cmd, uid, token);
        this.typeid = typeid;
        this.adid = adid;
        this.content = content;
        this.pic = pic;
        this.smallpic = smallpic;
        this.tel = tel;
        this.mobile = mobile;
        this.wechatid = wechatid;
        this.email = email;
        this.qq = qq;
        this.role = role;
        this.company = company;
    }
    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, ReqSentTextAdsBean.class);
        }
        return rmsg;
    }


    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSmallpic() {
        return smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
