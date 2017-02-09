package moni.anyou.com.view.bean.request.base;

/**
 * Created by Administrator on 2017/2/9.
 */

public abstract class RequestStandard {
    private String cmd;
    private String uid;
    private String token;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RequestStandard(String cmd, String uid, String token) {
        this.cmd = cmd;
        this.uid = uid;
        this.token = token;
    }

    public abstract  String ToJsonString();
}
