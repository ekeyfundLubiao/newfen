package moni.anyou.com.view.webview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;

public class WebviewActivity extends BaseActivity {
    public WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        webview = (WebView) findViewById(R.id.webview);
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(RIGHT_OUT);
    }


    public void getIntentDatas(){

    }
}
