package com.m086.ad.mobi.view.living;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Html;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.http.KJStringParams;
import org.kymjs.aframe.http.StringCallBack;
import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.util.VLCInstance;


import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.bean.request.ReqGetLivingBean;
import com.m086.ad.mobi.bean.response.ResLiveBean;
import com.m086.ad.mobi.bean.response.UrlBean;
import com.m086.ad.mobi.config.SysConfig;
import com.m086.ad.mobi.tool.AppTools;
import com.m086.ad.mobi.widget.NetProgressWindowDialog;

public class ALivingActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback, IVideoPlayer {

    WebView wvAlivinginfo;
    private ImageView ivStart;
    private ImageView ivZoon;
    private SurfaceView mSurfaceView;
    private TextView tvBack;
    private TextView tvContants;
    private TextView tvGarden;
    NetProgressWindowDialog window;

    private LibVLC mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;

    private RelativeLayout mLoadingView;
    private RelativeLayout mReLoad;

    private int mVideoHeight;
    private int mVideoWidth;
    private int mVideoVisibleHeight;
    private int mVideoVisibleWidth;
    private int mSarNum;
    private int mSarDen;

    private boolean boolStart = false;
    ResLiveBean.LiveBean mBean;
    String mUrl = "";

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
        tvGarden = (TextView) findViewById(R.id.tv_garden);
        tvBack = (TextView) findViewById(R.id.iv_left);
        wvAlivinginfo = (WebView) findViewById(R.id.wv_alivinginfo);
        ivStart = (ImageView) findViewById(R.id.video_start);
        ivStart.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_stop));
        ivZoon = (ImageView) findViewById(R.id.iv_zone);
        mSurfaceView = (SurfaceView) findViewById(R.id.video);
        mReLoad = (RelativeLayout) findViewById(R.id.v_reload);
        mLoadingView = (RelativeLayout) findViewById(R.id.v_loading);
        tvContants = (TextView) findViewById(R.id.tv_contants);
        RelativeLayout layout_index_points = (RelativeLayout) findViewById(R.id.layout_index_points);
        layout_index_points.getBackground().setAlpha(180);

        try {
            mMediaPlayer = VLCInstance.getLibVlcInstance();
        } catch (LibVlcException e) {
            e.printStackTrace();
        }
        mReLoad.setVisibility(View.GONE);
        window = new NetProgressWindowDialog(mBaseActivity);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
        mSurfaceHolder.addCallback(this);

        mMediaPlayer.eventVideoPlayerActivityCreated(true);

        EventHandler em = EventHandler.getInstance();
        em.addHandler(mVlcHandler);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mSurfaceView.setKeepScreenOn(false);
        ResLiveBean.LiveBean mBean = (ResLiveBean.LiveBean) getIntent().getSerializableExtra("data");
        tvGarden.setText(mBean.gradename);
        tvContants.setText(mBean.contents);
        tvContants.setText(Html.fromHtml(mBean.contents));
    }


    @Override
    public void setAction() {
        super.setAction();
        ivBack.setOnClickListener(this);
        ivZoon.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        mReLoad.setOnClickListener(this);

        tvTitle.setText("直播");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                onBack();
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
                    // playMedia();
                    // showLoading();
                    mMediaPlayer.play();
                    ivStart.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_stop));
                } else {
                    ivStart.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_start));
                    mMediaPlayer.stop();
                    hideLoading();
                }
                break;
            case R.id.v_reload:
                getNewUrl();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mMediaPlayer.isPlaying()) {
            playMedia();
            timer.start();
        }


    }

    private void playMedia() {
        mMediaPlayer.playMRL(mUrl);
    }

    @Override
    public void setData() {
        super.setData();
        mBean = (ResLiveBean.LiveBean) getIntent().getSerializableExtra("data");
        mUrl = mBean.url;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mSurfaceView.setKeepScreenOn(false);
            timer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.eventVideoPlayerActivityCreated(false);
            EventHandler em = EventHandler.getInstance();
            em.removeHandler(mVlcHandler);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setSurfaceSize(mVideoWidth, mVideoHeight, mVideoVisibleWidth, mVideoVisibleHeight, mSarNum, mSarDen);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mSurfaceHolder = holder;
            mMediaPlayer.attachSurface(holder.getSurface(), this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
        if (mMediaPlayer != null) {
            mMediaPlayer.attachSurface(holder.getSurface(), this);//, width, height
        }
        if (width > 0) {
            mVideoHeight = height;
            mVideoWidth = width;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            mMediaPlayer.detachSurface();
        }
    }

    @Override
    public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num, int sar_den) {
        mVideoHeight = height;
        mVideoWidth = width;
        mVideoVisibleHeight = visible_height;
        mVideoVisibleWidth = visible_width;
        mSarNum = sar_num;
        mSarDen = sar_den;
        mHandler.removeMessages(HANDLER_SURFACE_SIZE);
        mHandler.sendEmptyMessage(HANDLER_SURFACE_SIZE);
    }

    private static final int HANDLER_BUFFER_START = 1;
    private static final int HANDLER_BUFFER_END = 2;
    private static final int HANDLER_SURFACE_SIZE = 3;

    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FIT_HORIZONTAL = 1;
    private static final int SURFACE_FIT_VERTICAL = 2;
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private int mCurrentSize = SURFACE_BEST_FIT;

    private Handler mVlcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null || msg.getData() == null)
                return;

            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerTimeChanged:
                    break;
                case EventHandler.MediaPlayerPositionChanged:
                    break;
                case EventHandler.MediaPlayerPlaying:
                    mHandler.removeMessages(HANDLER_BUFFER_END);
                    mHandler.sendEmptyMessage(HANDLER_BUFFER_END);
                    break;
                case EventHandler.MediaPlayerBuffering:
                    break;
                case EventHandler.MediaPlayerLengthChanged:
                    break;
                case EventHandler.MediaPlayerEndReached:
                    //播放完成
                    break;
            }

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_BUFFER_START:
                    showLoading();
                    break;
                case HANDLER_BUFFER_END:
                    hideLoading();
                    break;
                case HANDLER_SURFACE_SIZE:
                    changeSurfaceSize();
                    break;
            }
        }
    };

    private void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    private void changeSurfaceSize() {
        // get screen size
        int dw = getWindowManager().getDefaultDisplay().getWidth();
        int dh = getWindowManager().getDefaultDisplay().getHeight();

        // calculate aspect ratio
        double ar = (double) mVideoWidth / (double) mVideoHeight;
        // calculate display aspect ratio
        double dar = (double) dw / (double) dh;

        switch (mCurrentSize) {
            case SURFACE_BEST_FIT:
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_FIT_HORIZONTAL:
                dh = (int) (dw / ar);
                break;
            case SURFACE_FIT_VERTICAL:
                dw = (int) (dh * ar);
                break;
            case SURFACE_FILL:
                break;
            case SURFACE_16_9:
                ar = 16.0 / 9.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_4_3:
                ar = 4.0 / 3.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_ORIGINAL:
                dh = mVideoHeight;
                dw = mVideoWidth;
                break;
        }

        mSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
        lp.width = dw;
        lp.height = dh;
        mSurfaceView.setLayoutParams(lp);
        mSurfaceView.invalidate();
    }


    public void getNewUrl() {

        KJHttp kjh = new KJHttp();
        KJStringParams params = new KJStringParams();
        String cmdPara = new ReqGetLivingBean("31", SysConfig.uid, SysConfig.token, mBean.liveID).ToJsonString();
        params.put("sendMsg", cmdPara);
        window.ShowWindow();
        kjh.urlGet(SysConfig.ServerUrl, params, new StringCallBack() {
            @Override
            public void onSuccess(String t) {
                try {
                    JSONObject jsonObject = new JSONObject(t);
                    int result = Integer.parseInt(jsonObject.getString("result"));
                    if (result >= 1) {
                        UrlBean bean = new Gson().fromJson(t, UrlBean.class);
                        if (bean != null && bean.getList().size() > 0) {
                            mUrl = bean.getList().get(0).getUrl();
                            showLoading();
                            playMedia();
                            mReLoad.setVisibility(View.GONE);
                        }
                    } else {
                        AppTools.jumptoLogin(mBaseActivity, result);
                        Toast.makeText(mContext, jsonObject.get("retmsg").toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(mContext, "数据请求失败", Toast.LENGTH_LONG).show();

                }
                window.closeWindow();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();

                window.closeWindow();
            }
        });
    }

    CountDownTimer timer = new CountDownTimer(11000, 1000) {
        @Override
        public void onTick(long l) {
//            ToastTools.showShort(mContext, "" + (l + 15) / 1000 + "在播放" + mMediaPlayer.isPlaying());
        }

        @Override
        public void onFinish() {

            if (mMediaPlayer.isPlaying() == false) {
                mReLoad.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
            }
        }
    };

}
