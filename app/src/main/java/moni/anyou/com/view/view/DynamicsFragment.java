package moni.anyou.com.view.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moni.anyou.com.view.R;
import moni.anyou.com.view.base.BaseFragment;


public class DynamicsFragment extends BaseFragment {

    private View mView;
    public DynamicsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamics, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        initTitle(mView);
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("动态");
    }
}
