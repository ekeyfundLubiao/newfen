package moni.anyou.com.view.view.dynamics;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.SentPicBean;
import moni.anyou.com.view.bean.request.ReqPageBean;
import moni.anyou.com.view.bean.request.ReqSentDynamicsBean;
import moni.anyou.com.view.bean.request.ReqsLikeTeacherBean;
import moni.anyou.com.view.bean.response.ResDynamicsBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.KeyBoardTools;
import moni.anyou.com.view.tool.PermissionTools;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.tool.UploadUtil;
import moni.anyou.com.view.tool.contacts.LocalConstant;
import moni.anyou.com.view.view.dynamics.adapter.SendPicAdapter;
import moni.anyou.com.view.view.my.PersonInfoSettingActivity;
import moni.anyou.com.view.view.photo.PhotoDialog;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.MessgeDialog;
import moni.anyou.com.view.widget.dialog.PopSelectPicture;
import moni.anyou.com.view.widget.pikerview.Utils.TextUtil;
import moni.anyou.com.view.widget.recycleview.DividerItemDecoration;

import static moni.anyou.com.view.widget.dialog.PopSelectPicture.IMAGE_OPEN_2;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {
    private NetProgressWindowDialog window;
    private RecyclerView rcPic;
    private EditText etContentDynamic;
    private SendPicAdapter mySentPicAdapter;
    private File upLoadfile;

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
        window = new NetProgressWindowDialog(mContext);
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
        etContentDynamic = (EditText) findViewById(R.id.et_content_dynamic);

        rcPic = (RecyclerView) findViewById(R.id.rc_pic);

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
                    Message fileInfomsg = new Message();
                    fileInfomsg.obj = 0;
                    upPicHandler.sendMessage(fileInfomsg);
                }


                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }


    private String mPic;

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
                final File file = new File(photoList.get(0));
                for (String s : photoList) {
                    Log.i(TAG, s);
                    mySentPicAdapter.addPic(new SentPicBean(s, null),0);
                }
                if (mySentPicAdapter.getItemCount() == 10) {
                    mySentPicAdapter.remove();
                }
//                if (file.exists()) {
//                    mVaule = file.getName();
//                    upLoadfile = file;
//                    SentPicBean pic = new SentPicBean(file.getName(), null);
//                    mySentPicAdapter.addPic(pic);
//                } else {
//                    ToastTools.showShort(mContext, "头像文件不存在");
//                }


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

    class UploadThread implements Runnable {

        @Override
        public void run() {
//            int success = UploadUtil.uploadFile(new File(Environment.getExternalStorageDirectory() + LocalConstant.Local_Photo_Path + "/crop/" + picArry.get(picName)), SysConfig.UploadUrl);
              int success = UploadUtil.uploadFile(new File(picArry.get(picName)), SysConfig.UploadUrl);

            if (success == 200) {
                picName++;
                if (picSize > picName) {
                    Message fileInfomsg = new Message();
                    fileInfomsg.obj = picName;
                    upPicHandler.sendMessage(fileInfomsg);

                } else {
                    postSentDynamics();
                    closeProgressBar();
                    return;
                }

            }
        }
    }

    private HelpBean getPics() {
        ArrayList<String> picInfo = new ArrayList<>();
        String TempStr = "";
        ArrayList<SentPicBean> remps = mySentPicAdapter.getmItems();
        if (remps.size() > 0) {
            for (int i = 0, size = remps.size() - 1; i < size; i++) {
                picInfo.add(remps.get(i).filePathName);
                if (i == size - 1) {
                    TempStr = TempStr + remps.get(i).filePathName;
                } else {
                    TempStr = TempStr + remps.get(i).filePathName + ",";
                }
            }
            HelpBean helpBean = new HelpBean();
            helpBean.picArray = picInfo;
            helpBean.sentpic = TempStr;
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
            if (msg != null) {
                picName = (Integer) msg.obj;
                upLoadfile = new File(Environment.getExternalStorageDirectory() + LocalConstant.Local_Photo_Path + "/crop/" + picName);
                UploadThread m = new UploadThread();
                new Thread(m).start();
            }
        }
    };

    public void notifyEnd() {
       ArrayList<SentPicBean> arrayPic= mySentPicAdapter.getmItems();
        int size = arrayPic.size();
        if (!arrayPic.get(arrayPic.size()-1).filePathName.equals("")&&size==8) {
            mySentPicAdapter.addPic(new SentPicBean(),8);
        }

    }


    public SendPicAdapter getMySentPicAdapter() {
        return mySentPicAdapter;
    }
}
