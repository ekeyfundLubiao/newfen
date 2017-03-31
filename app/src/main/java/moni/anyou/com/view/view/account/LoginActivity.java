package moni.anyou.com.view.view.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.Tools;
import moni.anyou.com.view.tool.VerificationTools;
import moni.anyou.com.view.view.IndexActivity;
import moni.anyou.com.view.view.my.PersonInfoSettingActivity;
import moni.anyou.com.view.view.my.systemset.SelectGardenActivity;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

import static android.content.ContentValues.TAG;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etUserPwd;
    private TextView tvforgetPwd;
    private Button btnLogin;
    private NetProgressWindowDialog window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void setData() {
        super.setData();
        if (!SysConfig.prefs.getString("sUsername", "").equals("")) {
            etUserName.setText(SysConfig.prefs.getString("sUsername", ""));
            etUserPwd.setText(SysConfig.prefs.getString("sPassword", ""));
        }
    }

    @Override
    public void initView() {
        super.initView();
//        initTitle();
        etUserName = (EditText) findViewById(R.id.et_username);
        etUserPwd = (EditText) findViewById(R.id.et_userpwd);
        tvforgetPwd = (TextView) findViewById(R.id.tv_forgetpwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        window = new NetProgressWindowDialog(mContext);
//        tvTitle.setText("登录");
//        ivBack.setVisibility(View.GONE);
    }

    @Override
    public void setAction() {
        super.setAction();
        tvforgetPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgetpwd:
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(etUserName.getText())) {
                    ToastTools.showShort(mContext, "手机号不能为空！");
                    return;
                }
                if (VerificationTools.isPhone(etUserName.getText().toString().trim())) {
                    ToastTools.showShort(mContext, "请输入正确的手机号！！");
                    return;
                }
                if (TextUtils.isEmpty(etUserPwd.getText())) {
                    ToastTools.showShort(mContext, "密码不能为空！");
                    return;
                }
                getLoginPost();
                break;
        }
    }

    private void postLogin() {

        startActivity(new Intent(this, CompleteBaseInfoActivity.class));
    }

    public void getLoginPost() {

        KJHttp kjh = new KJHttp();
        kjh.setTimeout(60000);
        KJStringParams params = new KJStringParams();
        String cmdPara = "{\"cmd\":\"4\",\"mobile\":\"" + etUserName.getText().toString() + "\",\"password\":\"" + etUserPwd.getText().toString() + "\"}";
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
                        String temp = Tools.parseLoginMsg(jsonObject);
                        if (!temp.equals("")) {
                            SharedPreferences.Editor editor = SysConfig.prefs.edit();// 取得编辑器
                            editor.putString("sUsername", etUserName.getText().toString());// 存储配置 参数1 是key 参数2 是值
                            editor.putString("sPassword", etUserPwd.getText().toString());
                            editor.putString("sToken", jsonObject.get("token").toString());
                            editor.putString("sUser_id", SysConfig.userInfoJson.getString("user_id"));
                            editor.putString("nick", SysConfig.userInfoJson.getString("nick"));
                            editor.putString("sUserInfoJson", SysConfig.userInfoJson.toString());
                            editor.commit();// 提交刷新数据
                            onBack();

                            if (SysConfig.userInfoJson.getInt("recommendId") > 0) {
                                startActivity(new Intent(mContext, IndexActivity.class));
                            } else {
                                if (SysConfig.prefs.getInt("setBaseInfo"+SysConfig.uid, 0) == 0) {
                                    startActivity(new Intent(mContext, CompleteBaseInfoActivity.class));
                                } else {
                                    startActivity(new Intent(mContext, IndexActivity.class));
                                }

                            }
                        } else {
                            Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                        }

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
