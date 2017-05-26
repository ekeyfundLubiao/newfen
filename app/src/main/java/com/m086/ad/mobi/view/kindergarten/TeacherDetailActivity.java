package com.m086.ad.mobi.view.kindergarten;

import android.os.Bundle;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;

public class TeacherDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        init();
    }

    @Override
    public void initView() {
        super.initView();
        initTitle();
    }
}
