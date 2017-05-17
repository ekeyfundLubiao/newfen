package moni.anyou.com.view.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
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

    private int totalCount;
    private ArrayList<RelationBean> list;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<RelationBean> getList() {
        return list;
    }

    public void setList(ArrayList<RelationBean> list) {
        this.list = list;
    }

    public static class RelationBean implements Serializable {
        /**
         * user_id : 1
         * recommendId : 0主账户
         * status : 1
         * nick : 张萌萌的爸爸
         * mobile : 13671691505
         * icon:
         */

        private String user_id;
        private String recommendId;
        public int status=-1;
        private String nick = "匿名";
        private String mobile;
        private String icon;
        public boolean boolDelete = false;
        public String roleid;
        public String role;

        public void setBoolDelete(boolean boolDelete) {
            this.boolDelete = boolDelete;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public RelationBean(String user_id, String recommendId, int status, String nick, String mobile, String icon, String role) {

            this.user_id = user_id;
            this.recommendId = recommendId;
            this.status = status;
            this.nick = nick;
            this.mobile = mobile;
            this.icon = icon;
            this.role = role;

        }

        public RelationBean() {


        }
    }
}
