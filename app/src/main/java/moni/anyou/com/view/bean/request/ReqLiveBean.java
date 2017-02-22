package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqLiveBean extends ReqPageBean{

    private String classid;

    public ReqLiveBean(String cmd, String uid, String token, String pageNo, String pageSize,String classid) {
        super(cmd,  uid,  token,pageNo,pageSize);
        this.classid = classid;

    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqLiveBean.class);
        }
        return rmsg;
    }
}
