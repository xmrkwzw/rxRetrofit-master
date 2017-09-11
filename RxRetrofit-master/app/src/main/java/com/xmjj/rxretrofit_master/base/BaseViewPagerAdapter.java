package com.xmjj.rxretrofit_master.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentLists ;
	private List<String> titleLists;
	public BaseViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentLists,List<String> titleLists) {
		super(fm);
		this.fragmentLists = fragmentLists ;
		this.titleLists = titleLists ;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentLists.get(position);
	}

	@Override
	public int getCount() {
		return fragmentLists.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleLists.get(position);
	}
}
