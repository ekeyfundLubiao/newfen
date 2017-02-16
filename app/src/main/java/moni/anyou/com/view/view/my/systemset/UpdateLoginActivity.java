package moni.anyou.com.view.view.my.systemset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.tool.ToastTools;

public class UpdateLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etConfrimPwd;
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
        etOldPwd = (EditText)findViewById(R.id.et_oldpwd);
        etNewPwd=(EditText)findViewById(R.id.et_newpwd);
        etConfrimPwd=(EditText)findViewById(R.id.et_renewpwd);
    }

    @Override
    public void onClick(View v) {

    }

    private String strOldPwd;
    private String strNewPwd;
    private String strConfrimPwd;

    private void postDate(){
        strOldPwd=etOldPwd.getText().toString();
        strNewPwd=etNewPwd.getText().toString();
        strConfrimPwd=etConfrimPwd.getText().toString();
        if (TextUtils.isEmpty(strOldPwd)) {
            ToastTools.showShort(mContext,"请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(etNewPwd.getText())) {
            ToastTools.showShort(mContext,"请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(etConfrimPwd.getText())) {
            ToastTools.showShort(mContext,"请再次输入新密码");
            return;
        }
        if (strNewPwd.equals(strConfrimPwd)) {
            ToastTools.showShort(mContext,"两次输入密码不一致");
            return;
        }
    }

//    {"cmd":"7","uid":"1000162","token":"20111222","oldpassword":"23232","newpassword":"1111"}


}
