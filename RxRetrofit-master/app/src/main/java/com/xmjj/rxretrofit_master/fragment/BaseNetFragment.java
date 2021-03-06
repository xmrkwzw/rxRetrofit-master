package com.xmjj.rxretrofit_master.fragment;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmjj.jujianglibrary.util.logger.Logger;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.base.mvp.IBaseView;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.entity.WeatherBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;
import com.xmjj.rxretrofit_master.presenter.ArrayResultPresenter;
import com.xmjj.rxretrofit_master.presenter.GetWeatherResultPresenter;
import com.xmjj.rxretrofit_master.presenter.NestResultPresenter;
import com.xmjj.rxretrofit_master.presenter.ObjectResultPresenter;
import com.xmjj.rxretrofit_master.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnLongClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

/**
 * 架构思想
 * user            view              presenter             			model
 * |请求        	   |setData			|onDataCreate(callback)		   |getdata
 * |-------------->|<---------------|----------------------------->|
 * <p>
 * view与model并不直接交互，而是通过presenter的中间桥梁，数据请求的业务逻辑写在model层
 * 通过callback的回调方式返回给view处理页面逻辑
 */
@SuppressLint("ValidFragment")
public class BaseNetFragment extends BaseFragment implements IBaseView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_content)
    TextView tvShow;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;

    private static final int TYPE_OBJECT = 0;
    private static final int TYPE_ARRAY = 1;
    private static final int TYPE_INNER = 2;

    private int type;


    public BaseNetFragment() {

    }

    public BaseNetFragment(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_object;
    }

    @Override
    public void initViews() {
        //refreshLayout.setOnRefreshListener(this);
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

    /**
     * the result is JsonObject such as {"result":{"xxx"}}
     */
    public void objectResult() {
        ObjectResultPresenter objectResultPresenter = new ObjectResultPresenter((RxAppCompatActivity) getActivity(), this, "objcet数据加载");
        objectResultPresenter.onInit();
        objectResultPresenter.onDataCreate();
    }

    /*get weather result */
    public void getWeatherRequest() {
        GetWeatherResultPresenter weatherResultPresenter = new GetWeatherResultPresenter((RxAppCompatActivity) getActivity(), this, null);
        weatherResultPresenter.onInit();
        weatherResultPresenter.onDataCreate();

    }

    /**
     * the result is JsonArray such as {"result":[{"xxx"},{"xxx"}]}
     */
    public void arrayResult() {
        ArrayResultPresenter arrayResultPresenter = new ArrayResultPresenter((RxAppCompatActivity) getActivity(), this, null);
        arrayResultPresenter.onInit();
        arrayResultPresenter.onDataCreate();
    }

    /**
     * Nested request interface :a result doing after the other result
     */
    public void nestedRequest() {
        NestResultPresenter nestResultPresenter = new NestResultPresenter((RxAppCompatActivity) getActivity(), this, "嵌套接口数据加载...");
        nestResultPresenter.onInit();
        nestResultPresenter.onDataCreate();
    }

    @OnLongClick(R.id.tv_content)
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tv_content:

                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tvShow.getText().toString());
                break;
        }
        return false;
    }


    @Override
    public void setData(String json, Object result, String method) {
        if (BaseInfoApi.BASE_INFO_METHOD.equals(method)) {
            Logger.d("setData" + BaseInfoApi.BASE_INFO_METHOD);
            BrandInfoDetailBean bean = (BrandInfoDetailBean) result;

            tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + bean.getBrand().getSchoolName() + "\n");

        } else if (BaseInfoApi.CIVILIZATION_METHOD.equals(method)) {
            Log.d(TAG, BaseInfoApi.CIVILIZATION_METHOD);
            List<RatingBean> lists = (List<RatingBean>) result;

            tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + lists.get(0).getWeek() + "\n");
        } else if (BaseInfoApi.IN.equals(method)) {
            Log.d(TAG, BaseInfoApi.IN);
            WeatherBean bean = (WeatherBean) result;

            tvShow.setText("原数据 \n" + Logger.formatJson(json) + "\n" + bean.getCity() + "\n");
        } else {
            tvShow.setText("暂无数据");
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setError(String error) {
        ToastUtils.showShortMes(getActivity(), error);
    }

    @Override
    public void onRefresh() {
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
}
