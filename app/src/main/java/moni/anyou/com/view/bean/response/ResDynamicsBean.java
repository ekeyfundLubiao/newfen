package moni.anyou.com.view.bean.response;

import java.util.List;

import moni.anyou.com.view.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResDynamicsBean extends ResponseStandard {


    /**
     * totalCount : 1
     * list : [{"articleid":"1","content":"test","pic":"1.png,2.png,3.png","smallpic":"s_1.png,s_2.png,s_3.png","userid":"1","cid":"3","gid":"1","likeuser":"刘晓红"}]
     */

    private String totalCount;
    /**
     * articleid : 1
     * content : test
     * pic : 1.png,2.png,3.png
     * smallpic : s_1.png,s_2.png,s_3.png
     * userid : 1
     * cid : 3
     * gid : 1
     * likeuser : 刘晓红
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
        private String articleid;
        private String content;
        private String pic;
        private String smallpic;
        private String userid;
        private String cid;
        private String gid;
        private String likeuser;

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getLikeuser() {
            return likeuser;
        }

        public void setLikeuser(String likeuser) {
            this.likeuser = likeuser;
        }
    }
}
