package moni.anyou.com.view.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import moni.anyou.com.view.bean.response.base.ResponseStandard;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ResLiveBean extends ResponseStandard {

    /**
     * result : 1
     * totalCount : 1
     * list : [{"liveID":"1","liveName":"跳跳班","addtime":"2018-02-12 10:00:00.0","status":"1","cid":"3","classid":"babylive"}]
     */

    private String totalCount;
    private List<LiveBean> list;
    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<LiveBean> getList() {
        return list;
    }

    public void setList(List<LiveBean> list) {
        this.list = list;
    }

    public static class LiveBean implements Serializable{
        /**
         * liveID : 1
         * liveName : 跳跳班
         * addtime : 2018-02-12 10:00:00.0
         * status : 1
         * cid : 3
         * classid : babylive
         */

        public String liveID;
        public String liveName;
        public String addtime;
        public String status;
        public String cid;
        public String classid;


    }
}
