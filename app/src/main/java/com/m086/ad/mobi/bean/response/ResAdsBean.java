package com.m086.ad.mobi.bean.response;

import com.google.gson.annotations.SerializedName;
import com.m086.ad.mobi.bean.response.base.ResponseStandard;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ResAdsBean extends ResponseStandard {

    /**
     * result : 1
     * totalCount : 1
     * list : [{"adid":"1","content":"udud","pic":"1495610717827.jpg","smallpic":"s_1495610717827.jpg","userid":"5","status":"0","addtime":"2017-05-24 15:26:02.0"}]
     */


    private String totalCount;
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
        /**
         * adid : 1
         * content : udud
         * pic : 1495610717827.jpg
         * smallpic : s_1495610717827.jpg
         * userid : 5
         * status : 0
         * addtime : 2017-05-24 15:26:02.0
         */

        private String adid;
        private String content;
        private String pic;
        private String smallpic;
        private String userid;
        private String status;
        private String addtime;

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSmallpic() {
            return smallpic;
        }

        public void setSmallpic(String smallpic) {
            this.smallpic = smallpic;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
