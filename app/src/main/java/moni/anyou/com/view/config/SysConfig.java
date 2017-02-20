package moni.anyou.com.view.config;

import android.content.SharedPreferences;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SysConfig {
    //远程服务端地址
    public final static String ServerUrl = "http://115.29.165.57:8080/anyouserver/mobileserver";
    //文件上传网址
    public final static String UploadUrl = "http://115.29.165.57:8080/anyoufileserver/httpserver";
    //文件下载地址
    public final static String FileUrl = "http://115.29.165.57:8080/anyoufileserver/upload/";
    //文件下载地址
    public final static String webUrl = "http://115.29.165.57:8080/anyouserver/m";
    public final static int port = 8080;
    public static final String File_DIR = "anyou";
    //
    public static String lastupdatetime = "";
    //
    public static String apkupdatetime = "";
    //uid
    public static String uid = "0";

    public static JSONObject dataJson = null;
    //
    public static JSONObject userInfoJson = null;
    //
    public static String token = "";
    //
    public static int VersionId = 0;
    public static SharedPreferences prefs;
    public static String prfsName = "";


}
