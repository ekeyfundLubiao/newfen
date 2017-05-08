package moni.anyou.com.view.view.living;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseActivity;
import moni.anyou.com.view.bean.response.ResLiveBean;
import moni.anyou.com.view.widget.NetProgressWindowDialog;

public class ALivingActivity extends BaseActivity implements View.OnClickListener {

    WebView wvAlivinginfo;
    private ImageView ivStart;
    private ImageView ivZoon;
    private SurfaceView surfaceView;

    private boolean boolStart = false;
    ResLiveBean.LiveBean mBean;

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
        wvAlivinginfo = (WebView) findViewById(R.id.wv_alivinginfo);
        ivStart = (ImageView) findViewById(R.id.video_start);
        ivZoon = (ImageView) findViewById(R.id.iv_zone);
        surfaceView = (SurfaceView) findViewById(R.id.video);
    }


    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        ivZoon.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        tvTitle.setText("直播");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                break;
            case R.id.iv_zone:
                Intent i = new Intent();
                i.putExtra("data", mBean);
                i.setClass(mBaseActivity, VlcVideoActivity.class);
                startActivity(i);
                break;
            case R.id.video_start:
                boolStart = !boolStart;
                if (boolStart) {
                    ivStart.setBackgroundDrawable(getResources().getDrawable(R.mipmap.zanting));
                } else {
                    ivStart.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_start));
                }
                break;
        }
    }

    @Override
    public void setData() {
        super.setData();
        mBean = (ResLiveBean.LiveBean) getIntent().getSerializableExtra("data");
    }
}
