package com.xmjj.rxretrofit_master.activity;

import com.tencent.smtt.sdk.TbsVideo;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/29
 */

public class TbsVideoViewActivity extends BaseActivity {
	private String URL = "http://baobab.wdjcdn.com/145076769089714.mp4";
	@Override
	public int getLayoutResId() {
		return R.layout.activity_videoview;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {
		if(TbsVideo.canUseTbsPlayer(this)){
			TbsVideo.openVideo(this,URL);
		}
	}
}
