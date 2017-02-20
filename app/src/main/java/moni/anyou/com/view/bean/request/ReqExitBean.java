package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqExitBean extends RequestStandard{

    public ReqExitBean(String cmd, String uid) {
        super(cmd,  uid);
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqExitBean.class);
        }
        return rmsg;
    }
}
