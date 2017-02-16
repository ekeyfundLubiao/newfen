package moni.anyou.com.view.bean;

import moni.anyou.com.view.bean.response.ResFamilyNumer;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SelectFamily {
    public int positon;
    public  ResFamilyNumer.RelationBean bean;

    public SelectFamily() {
    }

    public SelectFamily(int positon,  ResFamilyNumer.RelationBean bean) {
        this.positon = positon;
        this.bean = bean;
    }
}
