package moni.anyou.com.view.view.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class CompleteBaseInfoActivity extends BaseActivity {


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
    }
}
