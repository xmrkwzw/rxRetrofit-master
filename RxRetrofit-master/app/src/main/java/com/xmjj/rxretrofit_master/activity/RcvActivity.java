package com.xmjj.rxretrofit_master.activity;

import android.widget.Button;
import android.widget.TextView;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.entity.event.StickyEvent;


/**
 * 功能描述：
 * Created by wzw
 * 2017/8/29
 */

public class RcvActivity extends BaseActivity {
	private Button btnRgSticky;
	private TextView tvShow;
	@Override
	public int getLayoutResId() {
		return R.layout.activity_rvt;
	}

	@Override
	protected void onStart() {
		super.onStart();
		RxBus.getDefault().register(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RxBus.getDefault().unRegister(this);

	}


	@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
	public void rcvStickyEvent(StickyEvent event){
		if(event!=null){
			tvShow.setText("粘性事件结果："+event.msg);
		}
	}

	@Override
	public void initViews() {
		tvShow = (TextView)findViewById(R.id.tv_shows);

	}

	@Override
	public void initData() {

	}
}
