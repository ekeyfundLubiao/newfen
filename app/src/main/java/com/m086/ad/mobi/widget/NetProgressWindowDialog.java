package com.m086.ad.mobi.widget;

import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.base.BaseDialog;


/**
 * Created by Administrator on 2017/2/17.
 */
public class NetProgressWindowDialog extends BaseDialog {
    private ProgressBar pb1;
    private Animation mLoadingAnimation;

    public NetProgressWindowDialog(BaseActivity activity) {
        super(activity, R.style.loading_dialog);
        setContentView(R.layout.progress_loading);
        initWRAPDialog(R.style.dialog_animation, false);
    }

    @Override
    public void initView() {
        super.initView();
        pb1 = (ProgressBar) findViewById(R.id.pb1);
//        mLoadingAnimation = AnimationUtils.loadAnimation(mContext,
//                R.anim.loading_animation);
    }

    @Override
    public void setData() {
        super.setData();
        setLayoutBg(R.color.transparent);
    }

    @Override
    public void setListener() {
        super.setListener();
    }

    @Override
    public void dismiss() {
        //  ProgressBar.clearAnimation();
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    public void ShowWindow() {
        this.show();
    }

    public void closeWindow(){
        this.dismiss();
    }
}
