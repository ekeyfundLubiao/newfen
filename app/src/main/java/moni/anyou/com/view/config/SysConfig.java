package moni.anyou.com.view.config;

import android.content.SharedPreferences;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SysConfig {
    //后台服务网址
    public final static String ServerUrl = "http://115.29.165.57:8080/anyouserver/mobileserver";
    //上传网址
    public final static String UploadUrl = "http://115.29.165.57:8080/anyouserver/httpserver";
    //文件服务器地址
    public final static String FileUrl = "http://115.29.165.57:8080/anyouserver/upload/";
    public final static int port = 8080;
    public static String NewMsgCount = "0";
    public static final String File_DIR = "newcloud";
    //
    public static String lastupdatetime = "";
    //uid
    public static String uid = "0";
    //身份

    public static int lev = 1;

    public static String username = "";
    //语言版本
    public static String language = "zh";
    public static JSONObject dataJson = null;
//    public static ArrayList<EPModel.ListBean> GroupItems = null;
    //
    public static JSONObject userInfoJson = null;
    //
    public static String token = "";
    //
    public static int VersionId = 0;
    //onekeyset
    public static JSONObject onkeyset = null;
    //baseInfo
    public static JSONObject configInfo = null;
    public static SharedPreferences prefs = null;
    public static String prfsName = null;
    public static String appId;

}
