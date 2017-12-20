package com.xmjj.rxretrofit_master.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 功能描述：
 * Created by wzw
 * 2017/12/20
 */

public class CustomViewpagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> lists ;
	public CustomViewpagerAdapter(FragmentManager fm, List<Fragment> lists) {
		super(fm);
		this.lists = lists ;
	}

	@Override
	public Fragment getItem(int position) {
		return lists.get(position);
	}

	@Override
	public int getCount() {
		return lists==null? 0 : lists.size();
	}
}
