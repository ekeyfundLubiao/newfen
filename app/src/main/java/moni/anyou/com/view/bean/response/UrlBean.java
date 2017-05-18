package moni.anyou.com.view.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import moni.anyou.com.view.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/5/18.
 */

public class UrlBean extends ResponseStandard {


    /**
     * cmd : 31
     * result : 1
     * totalCount : 1
     * list : [{"url":"rtmp://7ae2b572.server.topvdn.com:1935/live/537015780_134283008_1494721409_f49a50f8e992bd2b6035d4568c6bc45e"}]
     */


    private List<ListBean> list;




    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
