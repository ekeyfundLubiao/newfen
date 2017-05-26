package com.m086.ad.mobi.bean.response;

import java.util.List;

import com.m086.ad.mobi.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResNoticeData extends ResponseStandard {


    /**
     * totalCount : 3
     * list : [{"id":"3","addtime":"2015-04-04 10:00:00.0","user_id":"0","title":"test3","touid":"0","flag":"0","contents":"test333","msgid":"0","srcnick":"","tonick":""},{"id":"2","addtime":"2015-04-04 10:00:00.0","user_id":"0","title":"test2","touid":"0","flag":"0","contents":"test222","msgid":"0","srcnick":"","tonick":""},{"id":"1","addtime":"2015-04-04 10:00:00.0","user_id":"0","title":"test","touid":"0","flag":"1","contents":"test","msgid":"0","srcnick":"","tonick":""}]
     */

    private String totalCount;
    /**
     * id : 3
     * addtime : 2015-04-04 10:00:00.0
     * user_id : 0
     * title : test3
     * touid : 0
     * flag : 0
     * contents : test333
     * msgid : 0
     * srcnick :
     * tonick :
     */

    private List<ListBean> list;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String id;
        private String addtime;
        private String user_id;
        private String title;
        private String touid;
        private String flag;
        private String contents;
        private String msgid;
        private String srcnick;
        private String tonick;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTouid() {
            return touid;
        }

        public void setTouid(String touid) {
            this.touid = touid;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getSrcnick() {
            return srcnick;
        }

        public void setSrcnick(String srcnick) {
            this.srcnick = srcnick;
        }

        public String getTonick() {
            return tonick;
        }

        public void setTonick(String tonick) {
            this.tonick = tonick;
        }
    }
}
