package com.xmjj.rxretrofit_master.fragment;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.http.downlaod.DownInfo;
import com.xmjj.jujianglibrary.http.downlaod.DownState;
import com.xmjj.jujianglibrary.http.downlaod.HttpDownManager;
import com.xmjj.jujianglibrary.listener.HttpDownOnNextListener;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.jujianglibrary.util.ToastUtils;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.io.File;
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
	private static final int TYPE_DOWNLOAD = 3;
	private static final String DOWNLOAD_URL = "http://www.ijinbu.com/upload/brand/v1.0/banpai_V1.0.2_05-11.apk?tsecond=0.07560947055095824";
	private int type;
	ProgressBar progressBar;

	public BaseNetFragment(int type) {
		this.type = type;
	}

	@Override
	public int getLayoutResId() {
		return R.layout.fragment_object;
	}

	@Override
	public void initViews() {
		progressBar = new ProgressBar(activity);

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
			case TYPE_DOWNLOAD:
				download();
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

	/**
	 * download file
	 */
	public void download() {
		DownInfo downInfo = new DownInfo(DOWNLOAD_URL);
		File file = new File(Environment.getExternalStorageDirectory() + "/banpai/update/", "test.apk");

		downInfo.setId(1);
		downInfo.setState(DownState.START);
		downInfo.setSavePath(file.getAbsolutePath());
		downInfo.setListener(httpProgressOnNextListener);
		HttpDownManager.getInstance().startDown(downInfo);
	}

	/*下载回调*/
	HttpDownOnNextListener<DownInfo> httpProgressOnNextListener = new HttpDownOnNextListener<DownInfo>() {
		@Override
		public void onNext(DownInfo baseDownEntity) {
			tvShow.setText("提示：下载完成");
			Toast.makeText(getContext(), baseDownEntity.getSavePath(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStart() {
			tvShow.setText("提示:开始下载");
		}

		@Override
		public void onComplete() {
			tvShow.setText("提示：下载结束");
		}

		@Override
		public void onError(Throwable e) {
			super.onError(e);
			tvShow.setText("失败:" + e.toString());
		}


		@Override
		public void onPuase() {
			super.onPuase();
			tvShow.setText("提示:暂停");
		}

		@Override
		public void onStop() {
			super.onStop();
		}

		@Override
		public void updateProgress(long readLength, long countLength) {
			tvShow.setText("提示:下载中" + (int) countLength + "/" + (int) readLength);
			progressBar.setMax((int) countLength);
			progressBar.setProgress((int) readLength);

		}
	};

}
