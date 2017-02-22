package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqUpdateChildBaseInfoBean extends RequestStandard {
    private String type;
    private String value;

    public ReqUpdateChildBaseInfoBean(String cmd, String uid, String token,String type,String vaule) {
        super(cmd, uid, token);
        this.value = vaule;
        this.type = type;
    }

    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, ReqUpdateChildBaseInfoBean.class);
        }
        return rmsg;
    }
}
