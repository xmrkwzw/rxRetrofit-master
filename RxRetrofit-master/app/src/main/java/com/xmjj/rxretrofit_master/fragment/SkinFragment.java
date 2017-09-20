package com.xmjj.rxretrofit_master.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.activity.MdActivity;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.entity.event.SkinEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/13
 */

public class SkinFragment extends BaseFragment implements View.OnClickListener {
	@BindView(R.id.ll_blue)
	LinearLayout llBlue;
	@BindView(R.id.ll_green)
	LinearLayout llGreen;
	@BindView(R.id.ll_black)
	LinearLayout llBlack;


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_skin;
	}

	@Override
	public void initViews() {

		llGreen.setOnClickListener(this);
		llBlack.setOnClickListener(this);
		llBlue.setOnClickListener(this);
	}

	@Override
	public void initData() {

	}

	@OnClick({R.id.ll_blue, R.id.ll_green, R.id.ll_black})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_blue:
				RxBus.getDefault().post(new SkinEvent(MdActivity.BLUE));
				break;
			case R.id.ll_green:
				RxBus.getDefault().post(new SkinEvent(MdActivity.GREEN));
				break;
			case R.id.ll_black:
				RxBus.getDefault().post(new SkinEvent(MdActivity.BLACK));
				break;

		}
	}


}
