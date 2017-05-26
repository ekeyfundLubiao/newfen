package com.m086.ad.mobi.bean.response;

import java.util.List;

import com.m086.ad.mobi.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResHomeData extends ResponseStandard {


    /**
     * articleid : 29394
     * title : 记者调查发现
     * url : m/newsShow.jsp?id=29394
     * pic : news1.png
     * module : news
     */

    private List<TopNewsBean> topNews;
    /**
     * user_id : 3
     * url : m/teacherShow.jsp?id=3
     * nick : 陈红
     * icon : icon.png
     * likes : 1
     */

    private List<TopTeachersBean> topTeachers;
    /**
     * companyID : 3
     * company : 未来强者分园1
     * icon_tel :
     * address : 11
     * summary : 日 - 百度贴吧是以兴趣主题聚合志同道合者的互动平台，同好网友聚集在这里交流话题、展示自我、结交朋友。贴吧主题涵盖了娱乐、游戏、小说、地区、生活等各方面 ...
     * username :
     * url : m/companyShow.jsp?id=3
     */

    private List<CompanyInfoBean> companyInfo;

    public List<TopNewsBean> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<TopNewsBean> topNews) {
        this.topNews = topNews;
    }

    public List<TopTeachersBean> getTopTeachers() {
        return topTeachers;
    }

    public void setTopTeachers(List<TopTeachersBean> topTeachers) {
        this.topTeachers = topTeachers;
    }

    public List<CompanyInfoBean> getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(List<CompanyInfoBean> companyInfo) {
        this.companyInfo = companyInfo;
    }

    public static class TopNewsBean {
        private String articleid;
        private String title;
        private String url;
        private String pic;
        private String module;

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }


    }

    public static class TopTeachersBean {
        private String user_id;
        private String url;
        private String nick;
        private String icon;
        private String likes;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public TopTeachersBean(String user_id, String url, String nick, String icon, String likes) {
            this.user_id = user_id;
            this.url = url;
            this.nick = nick;
            this.icon = icon;
            this.likes = likes;
        }
    }

    public static class CompanyInfoBean {
        private String companyID;
        private String company;
        private String tel;
        private String address;
        private String summary;
        private String username;
        private String url;

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
