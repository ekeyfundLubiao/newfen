package com.m086.ad.mobi.view.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.BaseInfo;
import com.m086.ad.mobi.bean.DataClassBean;
import com.m086.ad.mobi.bean.request.ReqCompleteFInishBabyInfoBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.tool.Tools;
import com.m086.ad.mobi.view.IndexActivity;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;
import com.m086.ad.mobi.widget.pikerview.TimeSelector;
import com.m086.ad.mobi.widget.pikerview.Utils.TextUtil;
import com.m086.ad.mobi.widget.pikerview.view.RelationSeletor;

public class CompleteBaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup radiogroup_sex;
    private TextView tvBrithday;
    private TextView tvRelatetobaby;
    private NetProgressWindowDialog window;

    private TimeSelector timeSelector = null;
    RelationSeletor mRelationSeletor = null;
    List<String> mStringRelations = new ArrayList<>();
    String relateArray;
    String Typerole = "1";
    ArrayList<DataClassBean> relateArrays;
    String sexType = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_base_info);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mBaseActivity);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvTitle.setText("完善信息");
        radiogroup_sex = (RadioGroup) findViewById(R.id.radiogroup_sex);
        tvBrithday = (TextView) findViewById(R.id.tv_brithday);
        tvRelatetobaby = (TextView) findViewById(R.id.tv_relatewithbaby);
        timeSelector = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                tvBrithday.setText(time);
            }
        }, "2010-01-01 00:00", Tools.getYeartoMiniteNowtime());
    }

    @Override
    public void setData() {
        super.setData();
        relateArray = Tools.getModuleJsonArray("role","4");
        BaseInfo baseInfo = new Gson().fromJson(SysConfig.userInfoJson.toString(), BaseInfo.class);
        tvRelatetobaby.setText(Tools.getRole(baseInfo.role));
        tvBrithday.setText(Tools.getYeartoDaytime());
    }

    @Override
    public void setAction() {
        super.setAction();
        tvRight.setOnClickListener(this);
        tvBrithday.setOnClickListener(this);
        tvRelatetobaby.setOnClickListener(this);
        radiogroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boy) {
                    sexType = "1";
                } else if (checkedId == R.id.girl) {
                    sexType = "2";
                } else if (checkedId == R.id.secret) {
                    sexType = "3";
                }

            }
        });

        relateArrays = new Gson().fromJson(relateArray.toString(), new TypeToken<List<DataClassBean>>() {
        }.getType());

        for (int i = 0, size = relateArrays.size(); i < size; i++) {
            mStringRelations.add(relateArrays.get(i).getClassName());
        }
        mRelationSeletor = new RelationSeletor(this, mStringRelations, new RelationSeletor.ResultHandler() {
            @Override
            public void handle(String text) {
                tvRelatetobaby.setText(text);
            }

            @Override
            public void handle(int position) {

                try {
                    Typerole = relateArrays.get(position).getClassID();
                    ToastTools.showShort(mContext, mStringRelations.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                if (TextUtil.isEmpty((String) tvBrithday.getText()) || "".equals(((String) tvBrithday.getText()).toString())) {
                    ToastTools.showShort(mContext, "请选择生日");
                    return;
                }
                if (TextUtil.isEmpty((String) tvRelatetobaby.getText()) || "".equals(((String) tvRelatetobaby.getText()).toString())) {
                    ToastTools.showShort(mContext, "请选择关系");
                    return;
                }
                getdata();

                break;
            case R.id.iv_left:
                onBack();
                break;
            case R.id.tv_brithday:
                timeSelector.show();
                break;
            case R.id.tv_relatewithbaby:
                mRelationSeletor.show();

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

    public void getdata() {
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqCompleteFInishBabyInfoBean("13", SysConfig.uid, SysConfig.token, Typerole, sexType, tvBrithday.getText().toString()).ToJsonString();
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
                        SysConfig.userInfoJson.put("childbirthday", tvBrithday.getText().toString());
                        SharedPreferences.Editor editor = SysConfig.prefs.edit();
                        editor.putInt("setBaseInfo" + SysConfig.uid, 1);
                        editor.commit();
                        startActivity(new Intent(mContext, IndexActivity.class));
                        activityAnimation(RIGHT_OUT);
                        onBack();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
        activityAnimation(RIGHT_IN);
    }
}
