package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqUnbindFBean extends RequestStandard{

    private String relativeid;

    public ReqUnbindFBean(String cmd, String uid, String token,String relativeid) {
        super(cmd,  uid,  token);
        this.relativeid = relativeid;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqUnbindFBean.class);
        }
        return rmsg;
    }
}
