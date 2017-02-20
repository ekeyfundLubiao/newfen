package moni.anyou.com.view.view.web;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.config.SysConfig;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class ShowInfoActivity extends BaseActivity implements OnClickListener {
    private NetProgressWindowDialog loadingDialog = null;
    private long exitTime = 0;
    Intent intent;
    Context mcontext = null;
    //private NetProgressWindowDialog window;
    WebView web;
    public int currIndex = 0;
    private RelativeLayout rlLeft;
    String title = "";
    String webUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);
        init();
        mcontext = this;
//		LogOut.getInstance().addActivity(this);

    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        webUrl = SysConfig.webUrl+intent.getStringExtra("url");
        tvTitle.setText(title.toString());

        loadingDialog = new NetProgressWindowDialog(this);
        web = (WebView) findViewById(R.id.webView);
        // web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 允许缩放
        // web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDefaultTextEncodingName("UTF-8");
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new WebChromeClient());

        web.setBackgroundColor(0);
        web.setFocusable(false);
        if (web != null) {
            web.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    //window.closeWindow();
                    if (loadingDialog != null) {
                        loadingDialog.closeWindow();
                    }
                }
            });

            loadUrl(webUrl);
        }
    }

    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void loadUrl(String url) {
        if (web != null) {
            web.loadUrl(url);
            if (loadingDialog != null) {
                loadingDialog.closeWindow();
            }
            web.reload();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
                break;
            default:
                break;
        }
    }

    private void showDialog() {

//    	   LoadingDialog.Builder dialogBuild = new LoadingDialog.Builder(mcontext);
//    	   loadingDialog = dialogBuild.create();
//    	   loadingDialog.setCanceledOnTouchOutside(false);// 点击外部区域关闭
//    	   loadingDialog.show();
    }
}
