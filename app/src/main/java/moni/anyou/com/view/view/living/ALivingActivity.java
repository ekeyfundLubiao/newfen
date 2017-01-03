package moni.anyou.com.view.view.living;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class ALivingActivity extends BaseActivity implements View.OnClickListener {

    WebView wvAlivinginfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliving);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        wvAlivinginfo= (WebView) findViewById(R.id.wv_alivinginfo);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        tvTitle.setText("直播");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void setData() {
        super.setData();
    }
}
