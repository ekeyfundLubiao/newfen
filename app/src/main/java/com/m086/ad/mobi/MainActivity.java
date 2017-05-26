package com.m086.ad.mobi;

import android.os.PersistableBundle;
import android.os.Bundle;

import com.m086.ad.mobi.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void initView() {
        super.initView();
    }
}
