package moni.anyou.com.view.view.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.BaseInfo;
import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.bean.HomeItemBean;
import moni.anyou.com.view.bean.request.ReqUpdateChildBaseInfoBean;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.PermissionTools;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.UploadUtil;
import moni.anyou.com.view.view.my.systemset.UpdateLoginActivity;
import moni.anyou.com.view.view.photo.PhotoDialog;
import moni.anyou.com.view.widget.NetProgressWindowDialog;
import moni.anyou.com.view.widget.dialog.MessgeDialog;
import moni.anyou.com.view.widget.pikerview.TimeSelector;
import moni.anyou.com.view.widget.pikerview.view.RelationSeletor;

import static moni.anyou.com.view.tool.Tools.getBaseRelatenumberdatas;

public class PersonInfoSettingActivity extends BaseActivity implements View.OnClickListener {
    private static String TYPE_SEX = "sex";
    private static String TYPE_BIRTHDAY = "birthday";
    private static String TYPE_ROLE = "role";
    private static String TYPE_ICON = "icon";
    private static String TYPE_CONTETS = "contents"; //老师寄语

    private NetProgressWindowDialog window;

    private ImageView tvHeadIcon;
    private TextView tvChangepwd;
    private TextView tvSex;
    private TextView tvBrithday;
    private TextView tvGarden;
    private TextView tvAccount;
    private RelativeLayout rlSex;
    private RelativeLayout rlBrithday;
    private RelativeLayout rlUpdatepwd;
    private RelativeLayout rlGarden;
    private RelativeLayout rlAccount;
    //private RelationSeletor mRelationSeletor;
    private RelationSeletor mSexSeletor;
    private TimeSelector mTimeSelector;


    ArrayList<HomeItemBean> baseInfoList;
    ArrayList<String> mStringRelations = new ArrayList<>();
    ArrayList<String> mStringSexs = new ArrayList<>();

    private String mType;
    private   String mVaule;
    private File upLoadfile;
    private ArrayList<DataClassBean> baseFamily = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info_setting);
        init();
    }
    @Override
    public void initView() {
        super.initView();
        initTitle();
        baseFamily = getBaseRelatenumberdatas();
        mPhotoDialog = new PhotoDialog(mBaseActivity);
        window = new NetProgressWindowDialog(mBaseActivity);
        tvTitle.setText("个人资料");
        rlGarden = (RelativeLayout) findViewById(R.id.rl_garden);
        rlAccount = (RelativeLayout) findViewById(R.id.rl_accout);
        rlBrithday = (RelativeLayout) findViewById(R.id.rl_brith);
        rlSex = (RelativeLayout) findViewById(R.id.rl_sex);
        rlUpdatepwd = (RelativeLayout) findViewById(R.id.rl_updatepwd);

        tvHeadIcon = (ImageView) findViewById(R.id.tv_headIcon);
        tvChangepwd = (TextView) findViewById(R.id.tv_changepwd);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvBrithday = (TextView) findViewById(R.id.tv_brithday);
        tvAccount = (TextView) findViewById(R.id.tv_accout);
        tvGarden = (TextView) findViewById(R.id.tv_garden);
        rlBrithday.setVisibility(View.GONE);
        rlSex.setVisibility(View.GONE);

    }

    @Override
    public void setData() {
        super.setData();
        viewforFamiy();
        initSeleterData();
    }
    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        tvHeadIcon.setOnClickListener(this);
        tvChangepwd.setOnClickListener(this);
        tvBrithday.setOnClickListener(this);
        rlUpdatepwd.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        mPhotoDialog.setPhotoListener(mPhotoListener);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_headIcon:
                mType = TYPE_ICON;
                requestPermission(PermissionTools.writeExternalStorage);
//                ToastTools.showShort(mContext, "设置头像");
                break;
            case R.id.tv_changepwd:
                startActivity(new Intent(mBaseActivity, UpdateLoginActivity.class));
                // ToastTools.showShort(mContext,"修改密码");
                break;
            case R.id.tv_brithday:
                mTimeSelector.show();
                break;
            case R.id.tv_sex:
                mSexSeletor.show();
                break;
            case R.id.rl_updatepwd:
                break;
            default:
                break;
        }
    }


    //teacher headmaster
    public void viewforTeacheandMaster() {
        baseInfoList.add(new HomeItemBean("班级", "class", false));
    }

    //headermasterD
    public void viewforHMD() {
    }

    //family
    public void viewforFamiy() {
        //  baseInfoList.add(new HomeItemBean("班级", "class", false));
        rlBrithday.setVisibility(View.VISIBLE);
        rlSex.setVisibility(View.VISIBLE);
    }


    public void postUpdateInfo() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqUpdateChildBaseInfoBean("6", SysConfig.uid, SysConfig.token, mType, mVaule).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        if (mType.equals(TYPE_ICON)) {
                            SysConfig.userInfoJson.put("icon",mVaule);
                        } else  if (mType == TYPE_BIRTHDAY) {
                            SysConfig.userInfoJson.put("childbirthday",mVaule);
                        } else if (mType == TYPE_CONTETS) {
                            SysConfig.userInfoJson.put("icon",mVaule);
                        } else if (mType == TYPE_SEX) {
                            SysConfig.userInfoJson.put("childsex",mVaule);
                        } else if (mType == TYPE_ROLE) {
                            SysConfig.userInfoJson.put("role",mVaule);
                        }
                        SysConfig.userInfoJson.toString();
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
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

    private void initSeleterData() {

        mStringSexs.add("男");
        mStringSexs.add("女");
        mStringSexs.add("保密");
        mSexSeletor = new RelationSeletor(mBaseActivity, mStringSexs, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String sex) {

                mType = TYPE_SEX;

                if (sex == null) {
                    sex = tvSex.getText().toString();
                } else {
                    if ("男".equals(sex)) {
                        mVaule = "1";
                    } else if ("女".equals(sex)) {
                        mVaule = "2";
                    } else if ("保密".equals(sex)) {
                        mVaule = "3";
                    }
                    postUpdateInfo();
                    tvSex.setText(sex);
                }

            }

            @Override
            public void handle(int position) {
                ToastTools.showShort(mContext, "" + position);
            }
        });


        mTimeSelector  = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                if (time!=null) {
                    mType = TYPE_BIRTHDAY;
                    tvBrithday.setText(time);
                    mVaule = time;
                    postUpdateInfo();
                }

            }
        }, "2010-01-01 00:00", "2018-12-31 00:00");
        BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
        setBitmaptoImageView11(SysConfig.PicUrl + baseInfo.icon, tvHeadIcon);
        tvBrithday.setText(baseInfo.childbirthday);
        tvGarden.setText(baseInfo.gradename);
        tvAccount.setText(baseInfo.mobile);
        switch (baseInfo.childsex) {
            case 1:
                tvSex.setText("男");
                break;
            case 2:
                tvSex.setText("女");
                break;
            case 3:
                tvSex.setText("保密");
                break;
        }
    }

    private PhotoDialog mPhotoDialog = null;


    private PhotoDialog.PhotoListener mPhotoListener = new PhotoDialog.PhotoListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            if (null != photoList) {
               final File file = new File(photoList.get(0));

                if (file.exists()) {
                    mVaule = file.getName();
                    postUpdateInfo();
                    upLoadfile = file;
                    UploadThread m = new UploadThread();
                    new Thread(m).start();

                } else {
                    ToastTools.showShort(mContext, "头像文件不存在");
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

    class UploadThread implements Runnable {

        @Override
        public void run() {
            int code=UploadUtil.uploadFile(upLoadfile,SysConfig.UploadUrl);
            if (code==200) {
                Message msg = new Message();
                msg.obj = 1;
                changeIconHandler.sendMessage(msg);
            }
        }
    }

    private Handler changeIconHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null) {
                BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
                setBitmaptoImageView11(SysConfig.PicUrl + baseInfo.icon, tvHeadIcon);
            }

        }
    };

}


