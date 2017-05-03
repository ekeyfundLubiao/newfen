package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by lubiao on 2017/5/3.
 */

public class ReqFacebackBean extends RequestStandard {

    String content;

    public ReqFacebackBean(String cmd, String uid, String token, String content) {
        super(cmd, uid, token);
        this.content = content;

    }

    @Override
    public String ToJsonString() {
        String rmsg = "fail";
        if (this != null) {
            rmsg = new Gson().toJson(this, ReqFacebackBean.class);
        }
        return rmsg;
    }
}
