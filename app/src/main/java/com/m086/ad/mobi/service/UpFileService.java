package com.m086.ad.mobi.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.util.ArrayList;

import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.UploadUtil;
import com.m086.ad.mobi.view.account.LoginActivity;

/**
 * Created by Administrator on 2017/4/6.
 */

public class UpFileService extends IntentService {


    private boolean isRunning = true;
    private int count = 0;
    private LocalBroadcastManager mLocalBroadcastManager;

    public UpFileService() {
        super("UpFileService");
    }

    public UpFileService(String name) {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sendServiceStatus("进入onHandleIntent");
        String[] arrayList = {"/storage/emulated/0/news_article/686126f92843fcb2bcf2412f6de69725.jpg", "/storage/emulated/0/news_article/686126f92843fcb2bcf2412f6de69725.jpg"};
        String Stringss = intent.getExtras().getString("array");

        ArrayList<String> picArry = intent.getStringArrayListExtra("pic");
        if (picArry == null)
            return;
        int success = 0;
        for (int i = 0, size = picArry.size(); i < size; i++) {
            if (picArry.get(i).contains("storage")) {
                success = UploadUtil.uploadFile(new File(picArry.get(i)), SysConfig.UploadUrl);
            } else {
                success = UploadUtil.uploadFile(new File(getApplication().getCacheDir() + "/anyouFile/" + picArry.get(i)), SysConfig.UploadUrl);
            }
            sendServiceStatus("" + success);
        }
    }


    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Intent intent = new Intent(LoginActivity.ACTION_TYPE_SERVICE);
        intent.putExtra("status", status);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    // 发送线程状态信息
    private void sendThreadStatus(int progress) {
        Intent intent = new Intent(LoginActivity.ACTION_TYPE_SERVICE);
        intent.putExtra("status", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendServiceStatus("结束");
    }
}
