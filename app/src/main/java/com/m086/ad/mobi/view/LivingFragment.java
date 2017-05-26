package com.m086.ad.mobi.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.base.BaseFragment;
import com.m086.ad.mobi.view.fragmentadapter.FragmentAdapter;
import com.m086.ad.mobi.view.living.LivingChildFragment;
import com.m086.ad.mobi.view.living.LivingChildRightFragment;
import com.m086.ad.mobi.widget.utils.view.ViewUtil;


public class LivingFragment extends BaseFragment {

    private LivingChildFragment babyFragment;
    private LivingChildRightFragment publicVidioFragement;

    private FragmentAdapter mFragmentAdapter;
    private HorizontalScrollView title_view;
    ViewUtil viewUtil;
    private View mView;

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;
    ViewPager mViewPager;
    public LivingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_living, container, false);

        init(mView);
        return mView;
    }

    @Override
    public void initView() {
        super.initView();
        viewUtil = new ViewUtil(mBaseActivity);
        mRadioGroup = (RadioGroup) mView.findViewById(R.id.title_group);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_order_form);
        title_view = (HorizontalScrollView) mView.findViewById(R.id.title_view);
        viewUtil.setViewWidth(title_view,viewUtil.getScreenWidth()*2/3);
        mFragmentAdapter = new FragmentAdapter(mBaseActivity.getSupportFragmentManager());

        mViewPager.setOffscreenPageLimit(mRadioGroup.getChildCount());
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton itemView = (RadioButton) mRadioGroup.getChildAt(i);
            viewUtil.setViewWidth(itemView, viewUtil.getScreenWidth() /3);
        }
        babyFragment=new LivingChildFragment();
        publicVidioFragement=new LivingChildRightFragment();
        fragmentList.add(babyFragment);
        fragmentList.add(publicVidioFragement);


        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        mFragmentAdapter.setList(fragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int currentItem = 0;
                for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                    if (checkedId == mRadioGroup.getChildAt(i).getId()) {
                        currentItem = i;
                    }
                }
                if (currentItem < 3) {
                    title_view.scrollTo(
                            (0) * viewUtil.getScreenWidth() / 2, 0);
                } else {
                    title_view.scrollTo(
                            (7) * viewUtil.getScreenWidth() / 2, 0);
                }
                mViewPager.setCurrentItem(currentItem, false);
            }
        });
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            RadioButton selectButton=   ((RadioButton)mRadioGroup.getChildAt(0));
            RadioButton selectButton2=   ((RadioButton)mRadioGroup.getChildAt(1));
            if (arg0 == 0) {

                selectButton.setChecked(true);
                selectButton.setTextSize(18);
                selectButton2.setTextSize(16);

            } else {
               // publicVidioFragement.onAttach(getApplicationContext());
                selectButton2.setChecked(true);
                selectButton.setTextSize(16);
                selectButton2.setTextSize(18);

            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


}
