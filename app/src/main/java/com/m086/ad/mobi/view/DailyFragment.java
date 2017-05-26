package com.m086.ad.mobi.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;


public class DailyFragment extends BaseFragment {

    private View mView;
    private TextView tvTitle;

    public DailyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_daily, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle = (TextView) mView.findViewById(R.id.page_title);
        tvTitle.setText("日常");

    }
}
