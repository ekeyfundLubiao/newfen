package moni.anyou.com.view.view.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.tool.ToastTools;
import moni.anyou.com.view.tool.VerificationTools;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etUserPwd;
    private TextView tvforgetPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void initView() {
        super.initView();
//        initTitle();
        etUserName = (EditText) findViewById(R.id.et_username);
        etUserPwd = (EditText) findViewById(R.id.et_userpwd);
        tvforgetPwd = (TextView) findViewById(R.id.tv_forgetpwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
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
                postLogin();
                break;
        }
    }

    private void postLogin() {
        ToastTools.showShort(mContext, "登录");
    }
}
