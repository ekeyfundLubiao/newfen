package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqsLikeTeacherBean extends RequestStandard{

    //"toid":"3","module":"user"
    private String toid;
    private String module;

    public ReqsLikeTeacherBean(String cmd, String uid, String token,String toid, String module) {
        super(cmd,  uid,  token);
        this.module = module;
        this.toid = toid;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqsLikeTeacherBean.class);
        }
        return rmsg;
    }
}
