package com.m086.ad.mobi.tool;

import android.Manifest;

/**
 * Created by Administrator on 2017/1/17.
 */

public class PermissionTools {
    /*
    *  Android 6.0以上需要动态设置权限
    * */
    //联系人
    public static final int writeContacts_Code = 0x0001;
    public static final String writeContacts = Manifest.permission.WRITE_CONTACTS;
    public static final int getAccounts_Code = 0x0002;
    public static final String getAccounts = Manifest.permission.GET_ACCOUNTS;
    public static final int readContacts_Code = 0x0003;
    public static final String readContacts = Manifest.permission.READ_CONTACTS;
    //电话
    public static final int readCallLog_Code = 0x0004;
    public static final String readCallLog = Manifest.permission.READ_CALL_LOG;
    public static final int writeCallLog_Code = 0x0005;
    public static final String writeCallLog = Manifest.permission.WRITE_CALL_LOG;
    public static final int readPhoneState_Code = 0x0006;
    public static final String readPhoneState = Manifest.permission.READ_PHONE_STATE;
    public static final int callPhone_Code = 0x0007;
    public static final String callPhone = Manifest.permission.CALL_PHONE;
    public static final int useSip_Code = 0x0008;
    public static final String useSip = Manifest.permission.USE_SIP;
    public static final int processOutgoingCalls_Code = 0x0009;
    public static final String processOutgoingCalls = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final int addVoicemail_Code = 0x000a;
    public static final String addVoicemail = Manifest.permission.ADD_VOICEMAIL;
    //日历
    public static final int readCalendar_Code = 0x000b;
    public static final String readCalendar = Manifest.permission.READ_CALENDAR;
    public static final int writeCalendar_Code = 0x000c;
    public static final String writeCalendar = Manifest.permission.WRITE_CALENDAR;
    //摄像机
    public static final int camera_Code = 0x000d;
    public static final String camera = Manifest.permission.CAMERA;
    //传感器
    public static final int bodySensors_Code = 0x000e;
    public static final String bodySensors = Manifest.permission.BODY_SENSORS;
    //位置
    public static final int accessFineLocation_Code = 0x000f;
    public static final String accessFineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int accessCoarseLocation_Code = 0x0010;
    public static final String accessCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    //内部存储
    public static final int readExternalStorage_Code = 0x0011;
    public static final String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final int writeExternalStorage_Code = 0x0012;
    public static final String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //麦克风（录音）
    public static final int recordAudio_Code = 0x0013;
    public static final String recordAudio = Manifest.permission.RECORD_AUDIO;
    //短信
    public static final int readSms_Code = 0x0014;
    public static final String readSms = Manifest.permission.READ_SMS;
    public static final int receiveWapPush_Code = 0x0015;
    public static final String receiveWapPush = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final int receiveMms_Code = 0x0016;
    public static final String receiveMms = Manifest.permission.RECEIVE_MMS;
    public static final int receiveSms_Code = 0x0017;
    public static final String receiveSms = Manifest.permission.RECEIVE_SMS;
    public static final int sendSms_Code = 0x0018;
    public static final String sendSms = Manifest.permission.SEND_SMS;
}
