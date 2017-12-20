package com.xmjj.rxretrofit_master.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.base.BaseViewPagerAdapter;
import com.xmjj.rxretrofit_master.skinloader.attr.AttrFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */


public class NetFragment extends BaseFragment {
	@BindView(R.id.tablayout_net)
	TabLayout tabLayout;
	@BindView(R.id.viewpager_net)
	ViewPager viewPager;

	private BaseViewPagerAdapter adapter;
	private List<Fragment> lists = new ArrayList<>();
	private List<String> titles = new ArrayList<>();


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_net;
	}

	@Override
	public void initViews() {
		tabLayout = findView(R.id.tablayout_net);
		viewPager = findView(R.id.viewpager_net);
		//dynamicAddSkinView(tabLayout, AttrFactory.BACKGROUND, R.color.color_white);//设置tablayout的背景
		//dynamicAddSkinView(tabLayout, AttrFactory.TAB_SELECT_TEXT_COLOR,R.color.colorAccent);//设置选中的颜色
		dynamicAddSkinView(tabLayout, AttrFactory.TAB_INDICATOR_COLOR, R.color.colorPrimary);
	}

	@Override
	public void initData() {
		titles.add("object结构");
		titles.add("array结构");
		titles.add("嵌套请求");

		for (int i = 0; i < titles.size(); i++) {
			lists.add(new BaseNetFragment(i));
		}
		adapter = new BaseViewPagerAdapter(getFragmentManager(), lists, titles);
		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);
		//tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

	}


}
