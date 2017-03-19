package moni.anyou.com.view.bean;

/**
 * Created by Administrator on 2017/2/10.
 */

public class DataClassBean {

    /**
     * id : 346
     * pic :
     * classID : father
     * className : 爸爸
     * classMemo :
     * parentID : 0000
     * classIDList : father,
     * parentName : 无
     * module : relative
     */

    private String id;
    private String pic;
    private String classID;
    private String className;
    private String classMemo;
    private String parentID;
    private String classIDList;
    private String parentName;
    private String module;

    public String tellPhoneNum="";
    public int status;
    public String nickname="";

    public DataClassBean(String id, String pic, String classID, String className, String classIDList, String tellPhoneNum, int status, String nickname) {
        this.id = id;
        this.pic = pic;
        this.classID = classID;
        this.className = className;
        this.classIDList = classIDList;
        this.tellPhoneNum = tellPhoneNum;
        this.status = status;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTellPhoneNum() {
        return tellPhoneNum;
    }

    public void setTellPhoneNum(String tellPhoneNum) {
        this.tellPhoneNum = tellPhoneNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassMemo() {
        return classMemo;
    }

    public void setClassMemo(String classMemo) {
        this.classMemo = classMemo;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getClassIDList() {
        return classIDList;
    }

    public void setClassIDList(String classIDList) {
        this.classIDList = classIDList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
