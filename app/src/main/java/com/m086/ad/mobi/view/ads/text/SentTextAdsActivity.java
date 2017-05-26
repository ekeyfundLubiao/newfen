package com.m086.ad.mobi.view.ads.text;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.BaseInfo;
import com.m086.ad.mobi.bean.DataClassBean;
import com.m086.ad.mobi.bean.request.ReqCompleteFInishBabyInfoBean;
import com.m086.ad.mobi.bean.request.ReqSentTextAdsBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.tool.Tools;
import com.m086.ad.mobi.view.IndexActivity;
import com.m086.ad.mobi.view.account.LoginActivity;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.m086.ad.mobi.widget.pikerview.TimeSelector;
import com.m086.ad.mobi.widget.pikerview.Utils.TextUtil;
import com.m086.ad.mobi.widget.pikerview.view.RelationSeletor;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SentTextAdsActivity extends BaseActivity implements View.OnClickListener {
    private NetProgressWindowDialog window;
    private CircleImageView tv_headIcon;
    private EditText et_phone;
    private EditText et_cellphone;
    private EditText et_wchat;
    private EditText et_email;
    private EditText et_qq;
    private EditText et_role;
    private EditText et_company;
    private EditText et_content;
    private ImageView iv_sentpic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_text_ad);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mBaseActivity);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvTitle.setText("");
        tv_headIcon = (CircleImageView) findViewById(R.id.tv_headIcon);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_cellphone = (EditText) findViewById(R.id.et_cellphone);
        et_wchat = (EditText) findViewById(R.id.et_wchat);
        et_email = (EditText) findViewById(R.id.et_email);
        et_qq = (EditText) findViewById(R.id.et_qq);
        et_role = (EditText) findViewById(R.id.et_role);
        et_company = (EditText) findViewById(R.id.et_company);
        et_content = (EditText) findViewById(R.id.et_content);
        iv_sentpic = (ImageView) findViewById(R.id.iv_sentpic);
    }

    @Override
    public void setData() {
        super.setData();

    }

    @Override
    public void setAction() {
        super.setAction();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                getdata();

                break;
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_brithday:
                break;
            case R.id.tv_relatewithbaby:

                break;
            default:
                break;
        }

    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }

    String mpic;
    String smallpic;


    public void getdata() {
        if (TextUtils.isEmpty(et_content.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_phone.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_cellphone.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_wchat.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_qq.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_role.getText().toString())) {
        }
        if (TextUtils.isEmpty(et_company.getText().toString())) {
        }

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqSentTextAdsBean("13", SysConfig.uid,
                SysConfig.token,
                ReqSentTextAdsBean.TYPEID_ADD,
                "0",
                et_content.getText().toString(),
                mpic, smallpic,
                et_phone.getText().toString(),
                et_cellphone.getText().toString(),
                et_wchat.getText().toString(),
                et_email.getText().toString(),
                et_qq.getText().toString(),
                et_role.getText().toString(),
                et_company.getText().toString()).ToJsonString();
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
                        onBack();
                    } else {
                        AppTools.jumptoLogin(mBaseActivity, result);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        activityAnimation(RIGHT_IN);
    }
}
