package com.m086.ad.mobi.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;


import java.util.List;

import com.m086.ad.mobi.R;
import com.m086.ad.mobi.tool.AppTools;

public class IndexAdapter implements RadioGroup.OnCheckedChangeListener {
	private List<Fragment> fragments;
	private RadioGroup rgs;
	private IndexActivity mBaseActivity;
	private int fragmentContentId;
	public static int currentTab = 0;
	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;

	public IndexAdapter(IndexActivity activity, List<Fragment> fragments,
						int fragmentContentId, RadioGroup rgs, int positon) {
		this.fragments = fragments;
		this.rgs = rgs;
		this.mBaseActivity = activity;
		this.fragmentContentId = fragmentContentId;
		FragmentTransaction ft = mBaseActivity.getSupportFragmentManager()
				.beginTransaction();
		ft.add(fragmentContentId, fragments.get(positon));
		ft.commitAllowingStateLoss();
		rgs.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		if (rgs.getChildAt(3).getId() == checkedId) {
			if (!AppTools.isLogin(mBaseActivity)) {
				rgs.check(rgs.getChildAt(currentTab).getId());
				return;
			}
		}
		for (int i = 0; i < rgs.getChildCount(); i++) {
			if (rgs.getChildAt(i).getId() == checkedId) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);
				getCurrentFragment().onPause();
				if (fragment.isAdded()) {
					fragment.onResume();
				} else {
					ft.add(fragmentContentId, fragment);
				}
				showTab(i);
				ft.commitAllowingStateLoss();
				if (null != onRgsExtraCheckedChangedListener) {
					onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(
							radioGroup, checkedId, i);
				}

			}
		}

	}

	public void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commitAllowingStateLoss();
		}
		currentTab = idx;
	}

	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = mBaseActivity.getSupportFragmentManager()
				.beginTransaction();
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.fragment_left_in,
					R.anim.fragment_left_out);
		} else {
			ft.setCustomAnimations(R.anim.fragment_right_in,
					R.anim.fragment_right_out);
		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(
			OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}

	public static class OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
				int checkedId, int index) {

		}
	}
}
