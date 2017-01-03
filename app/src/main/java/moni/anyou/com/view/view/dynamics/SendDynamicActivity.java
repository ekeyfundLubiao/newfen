package moni.anyou.com.view.view.dynamics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class SendDynamicActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        tvTitle.setText("");
        tvRight.setText("发送");
        tvRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAction() {
        tvRight.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            case R.id.right_tv:
                break;
        }
    }
    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }
}
