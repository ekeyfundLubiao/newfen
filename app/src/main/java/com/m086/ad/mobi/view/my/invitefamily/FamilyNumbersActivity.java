package com.m086.ad.mobi.view.my.invitefamily;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.DataClassBean;
import com.m086.ad.mobi.bean.InvitedInfo;
import com.m086.ad.mobi.bean.SelectFamily;
import com.m086.ad.mobi.bean.request.ReqUnbindFBean;
import com.m086.ad.mobi.bean.request.ReqsFaimilyNunbersBean;
import com.m086.ad.mobi.bean.response.ResFamilyNumer;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.PermissionTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.tool.Tools;
import com.m086.ad.mobi.view.my.invitefamily.adapter.FamilyNumberAdapter;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.m086.ad.mobi.widget.dialog.MessgeDialog;
import com.m086.ad.mobi.widget.dialog.PopAddSuccess;
import com.m086.ad.mobi.widget.dialog.PopunbindFamily;
import com.m086.ad.mobi.wxapi.wxutil.Util;

public class FamilyNumbersActivity extends BaseActivity implements View.OnClickListener, IWXAPIEventHandler {

    RecyclerView rcFamilyNumbers;
    private FamilyNumberAdapter MyAdapter;
    private ArrayList<ResFamilyNumer.RelationBean> numberBeans;
    private PopAddSuccess mPopAddFamilySuccess;
    private PopunbindFamily mPopunbindFamily;
    private NetProgressWindowDialog window;
    private ArrayList<DataClassBean> baseFamily = null;
    private String relativeId;
    //权限
    private int RequestCode = 0x1983;
    MessgeDialog mPDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_family);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initMsg();
        initTitlewithCheckbox();
        checkbox.setText("解绑");
        try {
            if (SysConfig.userInfoJson.getInt("recommendId") > 0) {
                checkbox.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        window = new NetProgressWindowDialog(mBaseActivity);
        rcFamilyNumbers = (RecyclerView) findViewById(R.id.rc_numbers);
        tvTitle.setText("邀请家人");
        ivBack.setOnClickListener(this);
        getdata();


    }

    @Override
    public void setData() {
        super.setData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rcFamilyNumbers.setLayoutManager(gridLayoutManager);
        numberBeans = new ArrayList<>();
        MyAdapter = new FamilyNumberAdapter(this, numberBeans);
        rcFamilyNumbers.setAdapter(MyAdapter);
        initData();
        MyAdapter.notifyDataSetChanged();
        api = WXAPIFactory.createWXAPI(mContext, "wxcaa9e574ddbd2cf2", false);
        wxSdkVersion = api.getWXAppSupportAPI();

    }

    @Override
    public void setAction() {
        super.setAction();
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkbox.setText("取消");
                } else {
                    checkbox.setText("解绑");

                }
                int size = getList(isChecked).size();

                for (int i = 0; i < size; i++) {
                    SelectFamily tempBean = (SelectFamily) checkBeans.get(i);
                    MyAdapter.notifyItemChanged(tempBean.positon, tempBean.bean);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.btn_commit:
                MyAdapter.notifyItemChanged(mBean.positon, mBean.bean);
                mPopunbindFamily.dismiss();
                break;
            case R.id.btn_unbindcommit:
                getUnbindF(relativeId);
                mPopunbindFamily.dismiss();
                break;
            case R.id.llMsg:
                mPopAddFamilySuccess.dismiss();
                requestPermission(PermissionTools.sendSms);
                break;
            case R.id.llWechat:
                if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
                    shareWechat(new ShareMsgResBean());
                } else {
                    ToastTools.showShort(mContext, "您还未安装微信，该功能暂不能使用");
                }

                break;
        }
    }

    public void initData() {
    }

    SelectFamily mBean;

    /**
     * 解绑成员
     *
     * @param bean
     */
    public void removeFamoilyNumbers(SelectFamily bean) {
        mBean = bean;
        relativeId = bean.bean.getUser_id();
        mPopunbindFamily = new PopunbindFamily(this, this);
        mPopunbindFamily.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
        mPopunbindFamily.isShowing();


    }

    public void addFamoilyNumbers(SelectFamily bean, int position) {

        Intent addNumntent = new Intent();
        addNumntent.putExtra("bean", numberBeans.get(position));
        addNumntent.setClass(mBaseActivity, InviteFamilyActivity.class);
        startActivityForResult(addNumntent, 9111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9111 && resultCode == 0x1111) {
            if (data != null) {
                data.getExtras().getString("account");
                data.getExtras().getString("pwd");
            }
            mPopAddFamilySuccess = new PopAddSuccess(this, new InvitedInfo(data.getExtras().getString("role"), data.getExtras().getString("account"), data.getExtras().getString("pwd")), this);
            mPopAddFamilySuccess.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
            mPopAddFamilySuccess.isShowing();
        }

        if (requestCode == RequestCode) {
            requestPermission(PermissionTools.readExternalStorage);
            mPDialog.dismiss();
        }
        if (requestCode == AppTools.SENT_MSG_REQUEST_CODE) {
            mPopAddFamilySuccess.dismiss();
            getdata();
        }

    }


    ArrayList<SelectFamily> checkBeans = new ArrayList<>();

    /**
     * 获取可删除集合
     *
     * @param isCheck
     * @return
     */
    public ArrayList<SelectFamily> getList(boolean isCheck) {
        checkBeans.clear();
        int size = numberBeans.size();
        for (int i = 0; i < size; i++) {
            ResFamilyNumer.RelationBean tempBean = numberBeans.get(i);
            if (tempBean.status == 1||tempBean.status == 0) {
                tempBean.boolDelete = isCheck;
                checkBeans.add(new SelectFamily(i, tempBean));
            }
        }
        return checkBeans;
    }

    public void getdata() {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsFaimilyNunbersBean("10", SysConfig.uid, SysConfig.token).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    // Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        numberBeans.clear();
                        ResFamilyNumer Fnumber = new Gson().fromJson(t, ResFamilyNumer.class);
                        ArrayList<ResFamilyNumer.RelationBean> changdataArray = new ArrayList<ResFamilyNumer.RelationBean>();
                        baseFamily = Tools.replaceNum(Fnumber.getList());
                        for (int i = 0, size = baseFamily.size(); i < size; i++) {

                            DataClassBean tempbean = baseFamily.get(i);
                            changdataArray.add(new ResFamilyNumer.RelationBean(
                                    tempbean.getId(),
                                    "",
                                    tempbean.status,
                                    (tempbean.nickname == null || tempbean.nickname.equals("") ? "匿名" : tempbean.getNickname()),
                                    (tempbean.tellPhoneNum == null || tempbean.tellPhoneNum.equals("") ? "" : tempbean.getTellPhoneNum()),
                                    (tempbean.getPic().equals("")
                                            ? Tools.getRoledefaultIcon(tempbean.getClassID()) : tempbean.getPic()),
                                    Tools.getRole(tempbean.getClassID())));
                        }
//                        Log.d(TAG, "numbeansize:" + numberBeans.size());
//                        ToastTools.showShort(mContext, "numbeansize:" + numberBeans.size());
                        numberBeans = changdataArray;
                        MyAdapter.setDatas(numberBeans);
                    } else {
                        AppTools.jumptoLogin(mBaseActivity,result);
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

    public void getUnbindF(String id) {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqUnbindFBean("11", SysConfig.uid, SysConfig.token, id).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.d(TAG, "onSuccess: " + t);
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        getdata();
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


    private void initMsg() {
        super.initView();
        mPDialog = new MessgeDialog((FamilyNumbersActivity) mBaseActivity);
        mPDialog.setMRL("获取读写权限", "取消", "立即获取");
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
        PopAddSuccess.SentInfoBean bean = mPopAddFamilySuccess.getsentInf();
        AppTools.sendMsg(mBaseActivity, bean.getPhoneNum(), bean.getMsgcontants());
    }

    @Override
    public void permissionNoNeed(String permissionName) {
        super.permissionNoNeed(permissionName);
        PopAddSuccess.SentInfoBean bean = mPopAddFamilySuccess.getsentInf();
        AppTools.sendMsg(mBaseActivity, bean.getPhoneNum(), bean.getMsgcontants());
    }

    public void testpop() {
        mPopAddFamilySuccess = new PopAddSuccess(this, new InvitedInfo("父亲", "13909807689", "087689"), this);
        mPopAddFamilySuccess.showAtLocation(this.findViewById(R.id.pop_need), Gravity.CENTER, 0, 0);
        mPopAddFamilySuccess.isShowing();
    }



    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    private IWXAPI api;
    int wxSdkVersion = 0;
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    public void shareWechat(ShareMsgResBean bean) {
        bean.title = "宝宝邀请";
        bean.content = "欢迎叔叔";
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = bean.title;
        msg.description = bean.content;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 90, 90, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    //w微信回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //w微信回调
    @Override
    public void onResp(BaseResp baseResp) {
        mPopAddFamilySuccess.dismiss();
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                ToastTools.showShort(mContext, "发送成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastTools.showShort(mContext, "取消发送");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastTools.showShort(mContext, "错误定义");
                break;
            default:
                //ToastTools.showShort(mContext, R.string.errcode_unknown);
                break;
        }
        finish();
    }

    class ShareMsgResBean {
        //分享url
        public String url;
        //分享标题
        public String title;
        //分享内容
        public String content;
        //分享图片
        public String image;
    }

}
