package moni.anyou.com.view.view.my.systemset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.DataClassBean;
import moni.anyou.com.view.bean.request.ReqsFaimilyNunbersBean;
import moni.anyou.com.view.bean.request.ReqsUpdateLoginPwdBean;
import moni.anyou.com.view.bean.response.ResFamilyNumer;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class UpdateLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etConfrimPwd;
    private Button btnCommit;
    private NetProgressWindowDialog window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("修改密码");
        etOldPwd = (EditText) findViewById(R.id.et_oldpwd);
        etNewPwd = (EditText) findViewById(R.id.et_newpwd);
        etConfrimPwd = (EditText) findViewById(R.id.et_renewpwd);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        window = new NetProgressWindowDialog(mContext);
    }

    @Override
    public void setAction() {
        super.setAction();
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                postDate();
                break;
        }
    }

    private String strOldPwd;
    private String strNewPwd;
    private String strConfrimPwd;

    private void postDate() {
        strOldPwd = etOldPwd.getText().toString();
        strNewPwd = etNewPwd.getText().toString();
        strConfrimPwd = etConfrimPwd.getText().toString();
        if (TextUtils.isEmpty(strOldPwd)) {
            ToastTools.showShort(mContext, "请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(etNewPwd.getText())) {
            ToastTools.showShort(mContext, "请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(etConfrimPwd.getText())) {
            ToastTools.showShort(mContext, "请再次输入新密码");
            return;
        }
        if (!strNewPwd.equals(strConfrimPwd)) {
            ToastTools.showShort(mContext, "两次输入密码不一致");
            return;
        }
        updatePwdPost(strOldPwd, strNewPwd);
    }

//    {"cmd":"7","uid":"1000162","token":"20111222","oldpassword":"23232","newpassword":"1111"}

    public void updatePwdPost(String strOldPwd, String strNewPwd) {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqsUpdateLoginPwdBean("7", SysConfig.uid, SysConfig.token, strOldPwd, strNewPwd).ToJsonString();
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
                        Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_LONG).show();
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
}
