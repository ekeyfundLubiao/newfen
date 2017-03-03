package moni.anyou.com.view.view;

import android.os.Bundle;
import android.app.Activity;


import moni.anyou.com.view.MainActivity;
import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DAO_DATA_Info;
import moni.anyou.com.view.config.AnyouerApplication;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.FileHelper;
import moni.anyou.com.view.tool.PermissionTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.view.account.LoginActivity;
import moni.anyou.com.view.widget.dialog.MessgeDialog;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import org.json.JSONObject;

import java.util.List;

public class StartActivity extends BaseActivity {
    private static int RequestCode = 0x9128;
    MyHandler myHandler;
    Context mcontext;
    Intent intent;
    SharedPreferences share;
    String uid = "";
    String token = "";
    String lev = "";
    String lastUpateTime = "";
    String apkname = "";
    String language = "";
    // 上一次上传时间
    String lastupload = "";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        init();
        // ScreenUtil.Fullscreen(StartActivity.this);
        mcontext = this;
        share = this.getSharedPreferences("perference", MODE_PRIVATE);
        uid = share.getString("sUser_id", "");

        if (!uid.equals("")) {
            SysConfig.uid = uid;

        }
        lastUpateTime = share.getString("sLastUpateTime", "");
        if (!lastUpateTime.equals("")) {
            SysConfig.lastupdatetime = lastUpateTime;
        }
        token = share.getString("sToken", "");
        SysConfig.token = token;
        String json = share.getString("sUserInfoJson", "");
        try {
            if (!json.equals("")) {
                SysConfig.userInfoJson = new JSONObject(json);
            } else {
                SysConfig.userInfoJson = new JSONObject();
            }
        } catch (Exception ex) {

        }
        lastupload = share.getString("sLastupload", "");
        myHandler = new MyHandler();

        requestPermission(PermissionTools.readExternalStorage);
    }

    //得到APP版本号
    public String getVersion() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);// getPackageName()是你当前类的包名，0代表是获取版本信息
            String name = pi.versionName;
            return name;
        } catch (Exception ex) {
            return "1.0";
        }
    }

    class MyHandler extends Handler {

        public MyHandler() {

        }

        public MyHandler(Looper L) {
            super(L);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String result = b.getString("result");
            // Log.d("MyHandler", "handleMessage......" + result);
//            Toast.makeText(mcontext, SysConfig.dataJson.toString(), Toast.LENGTH_LONG).show();
            if (Integer.parseInt(result) >= 1) {
                if (uid.equals("") || token.equals("")) {
                    // 以下写代码跳转到登录界面
                    startActivity(new Intent(mcontext, LoginActivity.class));
                    finish();

                } else {
                    // 以下写代码跳转到主界面
                    startActivity(new Intent(mcontext, MainActivity.class));
                    finish();
                }
              //  StartActivity.this.finish();

            }

        }

        public String getVersion() {
            try {
                PackageManager pm = getPackageManager();
                PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);// getPackageName()是你当前类的包名，0代表是获取版本信息
                String name = pi.versionName;
                return name;
            } catch (Exception ex) {
                return "1.0";
            }
        }
    }

    class MyThread implements Runnable {

        public void run() {

            Message msg = new Message();
            Bundle b = new Bundle();//
            // System.out.println("Runnable running ");
            JSONObject jsonObject = null;
            b.putString("result", "-1");
            FileHelper fileHelper = new FileHelper(mcontext);
            try {
                String cmd = "{\"cmd\":\"1\",\"uid\":\"" + uid
                        + "\",\"version\":\"" + getVersion()
                        + "\",\"lastUpateTime\":\"" + lastUpateTime
                        + "\",\"token\":\"" + token + "\"}";

                String result = Tools.postMsg(SysConfig.ServerUrl, "sendMsg="
                        + cmd);

                jsonObject = new JSONObject(result);
                fileHelper.createDir(SysConfig.File_DIR);
                fileHelper.createDir(SysConfig.File_DIR, "images");
                String filename = jsonObject.getString("filename");
                boolean downResult = false;
                if (!filename.equals("")) {
                    System.out.println(filename);
                    downResult = fileHelper.DownloadFromUrlToData(
                            SysConfig.FileUrl + "/" + filename, filename);
                    if (downResult) {

                        fileHelper.initData(mcontext,
                                Tools.replace(filename, ".zip", ".json"));

                        if (SysConfig.dataJson != null) {
                            AnyouerApplication.db
                                    .delete_DAO_DATA_Info();
                            DAO_DATA_Info dao = new DAO_DATA_Info();
                            dao.setJson(SysConfig.dataJson.toString());
                            dao.setKey(1);
                            AnyouerApplication.db.add(dao);
                            fileHelper.deleteSDFile(filename, "");
                            fileHelper.deleteSDFile(Tools.replace(filename,
                                    ".zip", ".json"));
                        }
                    }
                } else {
                    List<DAO_DATA_Info> list = AnyouerApplication.db
                            .queryAll_DAO_DATA_Info("1=1");
                    SysConfig.dataJson = new JSONObject(list.get(0)
                            .getJsonData());

                }

                b.putString("result", "1");
            } catch (Exception ex) {

                try {
                    List<DAO_DATA_Info> list = AnyouerApplication.db
                            .queryAll_DAO_DATA_Info("1=1");
                    SysConfig.dataJson = new JSONObject(list.get(0)
                            .getJsonData());
                } catch (Exception ex2) {

                }
                b.putString("result", "1");
            }

            msg.setData(b);
            StartActivity.this.myHandler.sendMessage(msg);

        }

    }

    @Override
    public void initView() {
        super.initView();
        mPDialog = new MessgeDialog((StartActivity) mBaseActivity);
        mPDialog.setMRL("获取读写权限","取消","立即获取");
        mPDialog.setListener();
        mPDialog.setMsgDialogListener(new MessgeDialog.MsgDialogListener() {
            @Override
            public void OnMsgClick() {

            }

            @Override
            public void OnLeftClick() {
onBack();
            }

            @Override
            public void OnRightClick() {
openPermissionSettingPage(RequestCode);
            }

            @Override
            public void onDismiss() {
onBack();
            }
        });
    }

    MessgeDialog mPDialog ;
    @Override
    public void permissionAlreadyRefuse(String permissionName) {
        super.permissionAlreadyRefuse(permissionName);
            mPDialog.show();
    }

    @Override
    public void permissionRefuse(String permissionName) {
        super.permissionRefuse(permissionName);
        mPDialog.show();
    }

    @Override
    public void permissionSuccess(String permissionName) {
        super.permissionSuccess(permissionName);
        MyThread m = new MyThread();
        new Thread(m).start();
    }

    @Override
    public void permissionNoNeed(String permissionName) {
        super.permissionNoNeed(permissionName);
        MyThread m = new MyThread();
        new Thread(m).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RequestCode) {
            requestPermission(PermissionTools.readExternalStorage);
            mPDialog.dismiss();
        }
    }
}


