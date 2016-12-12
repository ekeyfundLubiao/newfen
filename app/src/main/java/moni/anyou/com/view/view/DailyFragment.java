package moni.anyou.com.view.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;


public class DailyFragment extends BaseFragment {

    private View mView;
    public DailyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_daily, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();

    }
}
