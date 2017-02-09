package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqsFaimilyNunbersBean extends RequestStandard{


    public ReqsFaimilyNunbersBean(String cmd, String uid, String token) {
        super(cmd,  uid,  token);
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqsFaimilyNunbersBean.class);
        }
        return rmsg;
    }
}
