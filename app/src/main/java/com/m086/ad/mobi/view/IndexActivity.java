package com.m086.ad.mobi.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseActivity;
import com.m086.ad.mobi.tool.ToastTools;

public class IndexActivity extends BaseActivity {


    public static final String Position_Key = "position_key";
    public static int position = 0;
    public static IndexActivity getInstance = null;
    private RadioGroup mRadioGroup;
    private IndexAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private KindergartenFragment mKindergartenFragment;
    //    private LivingFragment mLivingFragment;
    private AdsFragment mAdsFragment;
    private DailyFragment mDailyFragment;
    private DynamicsFragment mDynamicsFragment;
    private MyFragment mMyFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_index);
        init();
    }

    @Override
    public void initView() {
        super.initView();

        getInstance = this;
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_main);
        mKindergartenFragment = new KindergartenFragment();
//        mLivingFragment = new LivingFragment();
        mAdsFragment = new AdsFragment();
        mDynamicsFragment = new DynamicsFragment();
        mDailyFragment = new DailyFragment();
        mMyFragment = new MyFragment();

    }

    @Override
    public void setData() {
        super.setData();
        mFragmentList.add(mKindergartenFragment);
        mFragmentList.add(mAdsFragment);
        mFragmentList.add(mDynamicsFragment);
        mFragmentList.add(mDailyFragment);
        mFragmentList.add(mMyFragment);

        mFragmentAdapter = new IndexAdapter(IndexActivity.this, mFragmentList,
                R.id.index_main, mRadioGroup, 0);

        checkPosition(getIntent().getIntExtra(Position_Key, 0));
    }

    @Override
    public void onBack() {
        super.onBack();
        activityAnimation(TOP_OUT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                doExit(false);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isExit = false;

    public void doExit(boolean skip) {
        if (!isExit && !skip) {
            isExit = true;
            ToastTools.showShort(mContext, "再按一次退出程序");
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void checkPosition(int position) {
        switch (position) {
            case 0:
                IndexAdapter.currentTab = 0;
                findViewById(R.id.radio_kindergarden).performClick();
                break;
            case 1:
                IndexAdapter.currentTab = 1;
                findViewById(R.id.radio_living).performClick();
                break;
            case 2:
                IndexAdapter.currentTab = 2;
                findViewById(R.id.radio_dynamics).performClick();
                break;
            case 3:
                IndexAdapter.currentTab = 3;
                findViewById(R.id.radio_daily).performClick();
                break;
            case 4:
                IndexAdapter.currentTab = 4;
                findViewById(R.id.radio_my).performClick();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((position > 0) && (position < 5)) {
            checkPosition(position - 1);
            position = 0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getInstance = null;
    }
}
