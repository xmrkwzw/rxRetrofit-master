package com.xmjj.rxretrofit_master.model;

import android.support.v4.app.Fragment;

import com.xmjj.rxretrofit_master.fragment.AboutFragment;
import com.xmjj.rxretrofit_master.fragment.BlogFragment;
import com.xmjj.rxretrofit_master.fragment.DbFlowFragment;
import com.xmjj.rxretrofit_master.fragment.FileDownLoadFragment;
import com.xmjj.rxretrofit_master.fragment.GlideFragment;
import com.xmjj.rxretrofit_master.fragment.NetFragment;
import com.xmjj.rxretrofit_master.fragment.OtherFragment;
import com.xmjj.rxretrofit_master.fragment.RxbusFragment;
import com.xmjj.rxretrofit_master.fragment.SkinFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * Created by wzw
 * 2017/12/20
 */

public class MainPageDataMode {

	private NetFragment netFragment;
	private FileDownLoadFragment fileDownLoadFragment;
	private DbFlowFragment dbFlowFragment;
	private RxbusFragment rxbusFragment;
	private GlideFragment glideFragment;
	private OtherFragment otherFragment;
	private SkinFragment skinFragment;
	private AboutFragment aboutFragment;
	private BlogFragment blogFragment;

	private List<Fragment> fragmentList = new ArrayList<>();

	public List<Fragment> fillData() {
		netFragment = new NetFragment();
		fileDownLoadFragment = new FileDownLoadFragment();
		dbFlowFragment = new DbFlowFragment();
		rxbusFragment = new RxbusFragment();
		glideFragment = new GlideFragment();
		otherFragment = new OtherFragment();
		skinFragment = new SkinFragment();
		aboutFragment = new AboutFragment();
		blogFragment = new BlogFragment();


		fragmentList.add(netFragment);
		fragmentList.add(fileDownLoadFragment);
		fragmentList.add(dbFlowFragment);
		fragmentList.add(rxbusFragment);
		fragmentList.add(glideFragment);
		fragmentList.add(otherFragment);
		fragmentList.add(skinFragment);
		fragmentList.add(aboutFragment);
		fragmentList.add(blogFragment);
		return fragmentList;
	}


}
