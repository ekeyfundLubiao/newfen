package moni.anyou.com.view.bean.request;

import com.google.gson.Gson;

import moni.anyou.com.view.bean.request.base.RequestStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ReqCompleteFInishBabyInfoBean extends RequestStandard{
    private String sex;
    private String birthday;//":"2014-01-12","
    private String role;//":"father"}

    public ReqCompleteFInishBabyInfoBean(String cmd, String uid, String token,String sex,String birthday,String role) {
        super(cmd,  uid,  token);
        this.sex = sex;
        this.birthday = birthday;
        this.role = role;
    }

    @Override
    public String ToJsonString() {
        String rmsg="fail";
        if (this != null) {
            rmsg=new Gson().toJson(this,ReqCompleteFInishBabyInfoBean.class);
        }
        return rmsg;
    }
}
