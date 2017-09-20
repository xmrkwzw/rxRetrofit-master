package com.xmjj.rxretrofit_master.fragment;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.util.List;

import butterknife.BindView;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */
@SuppressLint("ValidFragment")
public class BaseNetFragment extends BaseFragment implements HttpOnNextListener {
	@BindView(R.id.tv_content)
	TextView tvShow;
	private BaseInfoApi baseInfoApi;
	private static final int TYPE_OBJECT = 0;
	private static final int TYPE_ARRAY = 1;
	private static final int TYPE_INNER = 2;
	private int type;

	public BaseNetFragment(int type) {
		this.type = type;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_object;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {
		switch (type) {
			case TYPE_OBJECT:
				objectResult();
				break;
			case TYPE_ARRAY:
				arrayResult();
				break;
			case TYPE_INNER:
				nestedRequest();
				break;

		}


	}

	@Override
	public void onNext(String json, Object result, String method) {
		if (BaseInfoApi.BASE_INFO_METHOD.equals(method)) {
			BrandInfoDetailBean bean = (BrandInfoDetailBean) result;


			tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + bean.getBrand().getSchoolName() + "\n");

		} else if (BaseInfoApi.CIVILIZATION_METHOD.equals(method)) {
			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + lists.get(0).getWeek() + "\n");
		} else if (BaseInfoApi.IN.equals(method)) {

			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + lists.get(0).getWeek() + "\n");
		} else if (BaseInfoApi.MSG_CODE_METHOD.equals(method)) {

			tvShow.setText((String) result);
		}
	}

	@Override
	public void onError(ApiException e, String method) {
		ToastUtils.showShortMes(getActivity(), e.toString());
	}

	/**
	 * the result is JsonObject such as {"result":{"xxx"}}
	 */
	public void objectResult() {
		baseInfoApi = new BaseInfoApi(this, (RxAppCompatActivity) getActivity(), BrandInfoDetailBean.class);
		baseInfoApi.getBaseInfo("d6aa48251b85b045", "数据加载中......");
	}

	/**
	 * the result is JsonArray such as {"result":[{"xxx"},{"xxx"}]}
	 */
	public void arrayResult() {
		baseInfoApi = new BaseInfoApi(this, (RxAppCompatActivity) getActivity(), RatingBean.class);
		baseInfoApi.getCivilization("14871");
	}

	/**
	 * Nested request interface :a result doing after the other result
	 */
	public void nestedRequest() {
		baseInfoApi = new BaseInfoApi(this, (RxAppCompatActivity) getActivity(), BrandInfoDetailBean.class);
		baseInfoApi.doOther("d6aa48251b85b045", "嵌套接口加载......");
	}

}
