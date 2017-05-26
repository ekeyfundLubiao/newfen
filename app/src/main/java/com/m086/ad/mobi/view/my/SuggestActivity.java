package com.m086.ad.mobi.view.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.request.ReqFacebackBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.tool.ToastTools;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;

public class SuggestActivity extends BaseActivity implements View.OnClickListener {

    private Button btnCommit;
    private EditText etSuggestion;
    private NetProgressWindowDialog window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        window = new NetProgressWindowDialog(mBaseActivity);
        tvTitle.setText("意见反馈");
        btnCommit = (Button) findViewById(R.id.btn_commit);
        etSuggestion = (EditText) findViewById(R.id.et_suggestion);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.btn_commit:
                postaceback();
                break;

        }
    }


    public void postaceback() {
        if (etSuggestion.getText().toString().equals("")) {
            ToastTools.showShort(mContext, "反馈内容不能空");
            return;
        }
        if (etSuggestion.getText().toString().length() > 1000) {
            ToastTools.showShort(mContext, "反馈内容不能超过1000个字符");
            return;
        }
        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqFacebackBean("23", SysConfig.uid, SysConfig.token, etSuggestion.getText().toString()).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        Toast.makeText(mContext, "反馈成功", Toast.LENGTH_LONG).show();
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

}
