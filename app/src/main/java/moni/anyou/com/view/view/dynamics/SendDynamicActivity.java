package moni.anyou.com.view.view.dynamics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.request.ReqSentDynamicsBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.service.UpFileService;
import moni.anyou.com.view.tool.AppTools;
import moni.anyou.com.view.tool.PermissionTools;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.UploadUtil;
import moni.anyou.com.view.view.dynamics.adapter.SendPicAdapter;
import moni.anyou.com.view.view.photo.PhotoDialog;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.MessgeDialog;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {
    private NetProgressWindowDialog
            window;
    private RecyclerView rcPic;
    private EditText etContentDynamic;
    private SendPicAdapter mySentPicAdapter;


    public final static String ACTION_TYPE_SERVICE = "action.type.service";
    public final static String ACTION_TYPE_THREAD = "action.type.thread";

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mBaseActivity);
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
        etContentDynamic = (EditText) findViewById(R.id.et_content_dynamic);

        rcPic = (RecyclerView) findViewById(R.id.rc_pic);
        initBordcast();
    }

    @Override
    public void setAction() {
        tvRight.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        mPhotoDialog.setPhotoListener(mPhotoListener);

    }

    @Override
    public void setData() {
        super.setData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rcPic.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
        rcPic.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        rcPic.setLayoutManager(gridLayoutManager);
        mySentPicAdapter = new SendPicAdapter(this, new SentPicBean());
        mPhotoDialog = new PhotoDialog(mBaseActivity, mySentPicAdapter);
        rcPic.setAdapter(mySentPicAdapter);
        mySentPicAdapter.setmOnItemClickListener(new SendPicAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SentPicBean data, int position) {
//                if (position == mySentPicAdapter.getItemCount() - 1) {
                if ("".equals(data.filePathName)) {
                    requestPermission(PermissionTools.writeExternalStorage);
                }

            }
        });
    }

    int picSize = 0;
    ArrayList<String> picArry;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                onBack();
                break;
            case R.id.right_tv:
                if (TextUtils.isEmpty(etContentDynamic.getText())) {
                    ToastTools.showShort(mContext, "不能为空");
                    return;
                }
                HelpBean bean = getPics();
                if (bean != null) {
                    mPic = bean.sentpic;
                    showProgressBar();
                    picSize = bean.picArray.size();
                    picArry = bean.picArray;

                }

                Message fileInfomsg = new Message();
                fileInfomsg.obj = 0;
                upPicHandler.sendMessage(fileInfomsg);
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }


    private String mPic = "";

    public void postSentDynamics() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqSentDynamicsBean("18", SysConfig.uid, SysConfig.token, ReqSentDynamicsBean.TYPEID_ADD, ReqSentDynamicsBean.ARTICLEID_ADD, etContentDynamic.getText().toString(), mPic).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    //Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        showProgressBar();
                        Toast.makeText(mContext, "发送成功", Toast.LENGTH_LONG).show();
                        onBack();
                    } else {
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
                window.closeWindow();
            }
        });


    }

    private PhotoDialog mPhotoDialog = null;
    private PhotoDialog.PhotoListener mPhotoListener = new PhotoDialog.PhotoListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            if (null != photoList) {
                //  final File file = new File(photoList.get(0));
                for (String s : photoList) {
                    Log.i(TAG, s);
                    mySentPicAdapter.addPic(new SentPicBean(s, AppTools.comparese(mContext, s)), 0);
                }
                if (mySentPicAdapter.getItemCount() == 10) {
                    mySentPicAdapter.remove();
                }


            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError() {

        }

        @Override
        public void onFinish() {

        }
    };


    //不需要授权
    @Override
    public void permissionNoNeed(String permissionName) {
        mPhotoDialog.show();
    }

    //权限授权成功
    @Override
    public void permissionSuccess(String permissionName) {
        mPhotoDialog.show();
    }

    //权限被拒绝
    @Override
    public void permissionRefuse(String permissionName) {
        showMsgDialog("您拒绝了本应用访问相册的权限，无法上传头像，是否授权获取权限？", "暂不授权", "立即授权", new MessgeDialog.MsgDialogListener() {
            @Override
            public void OnMsgClick() {

            }

            @Override
            public void OnLeftClick() {

            }

            @Override
            public void OnRightClick() {
                openPermissionSettingPage(0x1111);
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    //权限之前被拒绝
    @Override
    public void permissionAlreadyRefuse(String permissionName) {
        showMsgDialog("您之前拒绝了本应用访问相册的权限，无法上传头像，是否授权获取权限？", "暂不授权", "立即授权", new MessgeDialog.MsgDialogListener() {
            @Override
            public void OnMsgClick() {

            }

            @Override
            public void OnLeftClick() {

            }

            @Override
            public void OnRightClick() {
                openPermissionSettingPage(0x1111);
            }

            @Override
            public void onDismiss() {

            }
        });
    }



    private HelpBean getPics() {
        ArrayList<String> picInfo = new ArrayList<>();
        String TempStr = "";
        ArrayList<SentPicBean> remps = mySentPicAdapter.getmItems();
        if (remps.size() > 1) {
            int size = remps.size();
            if (size!=9) {
                size = size - 1;
            }
            for (int i = 0; i < size; i++) {
                picInfo.add("anyou".equals(remps.get(i).newFileNameMap)?remps.get(i).newFileNameMap:remps.get(i).filePathName);
//                picInfo.add(remps.get(i).filePathName);
                String fileName = remps.get(i).newFileNameMap;
                String oldFileName = remps.get(i).filePathName;
                if (i == size - 1) {
                    TempStr = TempStr + (fileName.contains("anyou") ? new File(fileName).getName() : new File(oldFileName).getName());
                } else {
                    TempStr = TempStr + (fileName.contains("anyou") ? new File(fileName).getName() : new File(oldFileName).getName()) + ",";
                }
            }
            HelpBean helpBean = new HelpBean();
            helpBean.picArray = picInfo;

            for (String s:helpBean.picArray) {
                Log.d("TAG", s);
            }
            helpBean.sentpic = TempStr;
            Log.d("TAG", TempStr);
            return helpBean;
        }


        return null;
    }


    class HelpBean {
        ArrayList<String> picArray;
        String sentpic;
    }

    int picName;
    private Handler upPicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg != null) {
//                picName = (Integer) msg.obj;
//                UploadThread m = new UploadThread();
//                new Thread(m).start();
//            }
            window.isShowing();
            if (picArry != null) {
                Intent startIntent = new Intent(mContext, UpFileService.class);
                startIntent.putExtra("picArray", "123");
                startIntent.putStringArrayListExtra("pic", picArry);
                startService(startIntent);
            }

            rcPic.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postSentDynamics();
                }
            }, 500);
        }
    };

    public void notifyEnd() {
        ArrayList<SentPicBean> arrayPic = mySentPicAdapter.getmItems();
        int size = arrayPic.size();
        if (!arrayPic.get(arrayPic.size() - 1).filePathName.equals("") && size == 8) {
            mySentPicAdapter.addPic(new SentPicBean(), 8);
        }

    }


    public SendPicAdapter getMySentPicAdapter() {
        return mySentPicAdapter;
    }

    public void initBordcast() {

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_SERVICE);
        intentFilter.addAction(ACTION_TYPE_THREAD);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case ACTION_TYPE_SERVICE:
                    ToastTools.showShort(mContext, intent.getStringExtra("status"));
                    break;
                case ACTION_TYPE_THREAD:
                    ToastTools.showShort(mContext, intent.getStringExtra("status"));
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }
}
