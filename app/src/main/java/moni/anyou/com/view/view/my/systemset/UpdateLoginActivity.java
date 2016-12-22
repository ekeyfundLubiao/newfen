package moni.anyou.com.view.view.my.systemset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class UpdateLoginActivity extends BaseActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {

    }
}
