package com.xmjj.rxretrofit_master.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.activity.RcvActivity;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.entity.event.CommonEvent;
import com.xmjj.rxretrofit_master.entity.event.StickyEvent;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class RxbusFragment extends BaseFragment implements View.OnClickListener{
	private Button btnPost;
	private Button btnStickyPost;
	private TextView tvShow;
	@Override
	public int getLayoutResId() {
		return R.layout.fragment_rxbus;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RxBus.getDefault().unRegister(this);
		RxBus.getDefault().removeAllStickyEvents();
	}

	@Override
	public void initViews() {
		btnPost = findView(R.id.btn_rx_post);
		btnStickyPost =findView(R.id.btn_rx_sticky_post);
		tvShow = findView(R.id.tv_show);
	}

	@Override
	public void initData() {
		RxBus.getDefault().register(this);
		btnStickyPost.setOnClickListener(this);
		btnPost.setOnClickListener(this);
	}
	/*post a common event by rxbus*/
	public void post() {
		RxBus.getDefault().post(new CommonEvent("common event"));
	}

	/*post a sticky event by rxbus*/
	public void stickyPost() {
		RxBus.getDefault().post(new StickyEvent("sticky event"));
		new CountDownTimer(3050,1000){

			@Override
			public void onTick(long millisUntilFinished) {
				Log.d("tag",millisUntilFinished+"");
				tvShow.setText(millisUntilFinished/1000+"秒后调整接收页面");
			}

			@Override
			public void onFinish() {
				startActivity(new Intent(getActivity(), RcvActivity.class));
			}
		}.start();

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void rcvCommonEvent(CommonEvent event) {
		if (event != null) {
			tvShow.setText("一般事件结果：" + event.msg);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_rx_post:
				post();
				break;
			case R.id.btn_rx_sticky_post:
				stickyPost();

				break;
		}
	}
}
