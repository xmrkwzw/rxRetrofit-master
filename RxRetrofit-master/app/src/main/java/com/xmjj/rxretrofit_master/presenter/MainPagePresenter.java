package com.xmjj.rxretrofit_master.presenter;

import com.xmjj.rxretrofit_master.model.MainPageDataMode;
import com.xmjj.rxretrofit_master.view.IMainPageView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/12/20
 */

public class MainPagePresenter {

	private MainPageDataMode mode;

	private IMainPageView view;

	public MainPagePresenter(IMainPageView view){
		this.view = view ;
	}

	public void createData() {
		mode = new MainPageDataMode();
		view.setData(mode.fillData());

	}
}
