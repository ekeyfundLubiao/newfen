package moni.anyou.com.view.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import moni.anyou.com.view.bean.RelationBean;
import moni.anyou.com.view.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResFamilyNumer extends ResponseStandard {

    /**
     * result : 1
     * totalCount : 0
     * list : [{"user_id":"1","recommendId":"0","status":"1","nick":"张萌萌的爸爸","mobile":"13671691505","icon":""}]
     */

    private String totalCount;
    private List<RelationBean> list;


    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<RelationBean> getList() {
        return list;
    }

    public void setList(List<RelationBean> list) {
        this.list = list;
    }

    public static class RelationBean {
        /**
         * user_id : 1
         * recommendId : 0
         * status : 1
         * nick : 张萌萌的爸爸
         * mobile : 13671691505
         * icon:
         */

        private String user_id;
        private String recommendId;
        private String status;
        private String nick;
        private String mobile;
        private String icon;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
