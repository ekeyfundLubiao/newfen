package moni.anyou.com.view.view.my.invitefamily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class InviteFamilyActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_family);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("邀请家人");
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
        }
    }
}
