package com.xmjj.rxretrofit_master.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xmjj.jujianglibrary.util.GlideImageLoader;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class GlideFragment extends BaseFragment implements View.OnClickListener {
	@BindView(R.id.btn_load_img)
	Button btnLoadImg;
	@BindView(R.id.btn_load_gif)
	Button btnLoadGif;
	@BindView(R.id.btn_load_round)
	Button btnLoadRound;
	@BindView(R.id.btn_load_round_corner)
	Button btnLoadRoundCorner;
	@BindView(R.id.iv_show)
	ImageView ivBg;


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_glide;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {
		btnLoadImg.setOnClickListener(this);
		btnLoadGif.setOnClickListener(this);
		btnLoadRound.setOnClickListener(this);
		btnLoadRoundCorner.setOnClickListener(this);
	}

	@OnClick({R.id.btn_load_img, R.id.btn_load_gif, R.id.btn_load_round, R.id.btn_load_round_corner})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_load_img:
				String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504082848617&di=4ce37a189839d25d47e4250beb54a8eb&imgtype=0&src=http%3A%2F%2Fwww.cncrk.com%2Fup%2F1705%2F201705181542228456.jpg";
				loadImg(url);
				break;
			case R.id.btn_load_gif:
				String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504082949678&di=0d35537ce12121fee2bff6f7d3e5fb64&imgtype=0&src=http%3A%2F%2Fdl.bbs.9game.cn%2Fattachments%2Fforum%2F201408%2F21%2F223649hz585v2sh0353gv6.gif";
				loadGifImg(url2);
				break;
			case R.id.btn_load_round:
				String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504082848617&di=4ce37a189839d25d47e4250beb54a8eb&imgtype=0&src=http%3A%2F%2Fwww.cncrk.com%2Fup%2F1705%2F201705181542228456.jpg";
				loadRoundImg(url3);
				break;

			case R.id.btn_load_round_corner:
				String url4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504082848617&di=4ce37a189839d25d47e4250beb54a8eb&imgtype=0&src=http%3A%2F%2Fwww.cncrk.com%2Fup%2F1705%2F201705181542228456.jpg";
				loadRoundCornerImg(url4);
				break;
		}
	}

	/*load img*/
	public void loadImg(String url) {
		GlideImageLoader.getInstance().setImage(getActivity(), url, ivBg, null);
	}

	public void loadGifImg(String url) {
		GlideImageLoader.getInstance().setGifImage(getActivity(), url, ivBg);
	}

	public void loadRoundImg(String url) {
		GlideImageLoader.getInstance().setRoundImage(getActivity(), url, ivBg);
	}

	public void loadRoundCornerImg(String url) {
		GlideImageLoader.getInstance().setRoundCornerImage(getActivity(), url, ivBg, 10);
	}



}
