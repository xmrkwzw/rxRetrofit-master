package com.xmjj.rxretrofit_master;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xmjj.jujianglibrary.base.BaseActivity;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, HttpOnNextListener {
    private Button btnObject;
    private Button btnArray;
	private Button btnIn;
    private TextView tvShow;
    private BaseInfoApi baseInfoApi;
    private Button addBtn;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        btnObject = (Button) findViewById(R.id.btn_object);
        btnArray = (Button) findViewById(R.id.btn_array);
        tvShow = (TextView) findViewById(R.id.tv_show);
		btnIn = (Button)findViewById(R.id.btn_in);
		addBtn = (Button) findViewById(R.id.add_btn);
		btnObject.setOnClickListener(this);
		btnArray.setOnClickListener(this);
        addBtn.setOnClickListener(this);
		btnIn.setOnClickListener(this);
    }

    @Override
    public void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_object:
                baseInfoApi = new BaseInfoApi(this, this, BrandInfoDetailBean.class);
                baseInfoApi.getBaseInfo("d6aa48251b85b045","数据加载中......");
                break;

            case R.id.btn_array:
                baseInfoApi = new BaseInfoApi(this, this, RatingBean.class);
                baseInfoApi.getCivilization("14871");
                break;
			case R.id.btn_in:
				baseInfoApi = new BaseInfoApi(this, this, BrandInfoDetailBean.class);
				baseInfoApi.doOther("d6aa48251b85b045","嵌套接口加载......");
				break;
            case R.id.add_btn://add DBFlow

                break;
            default:
                break;
        }
    }



	@Override
	public void onNext(Object result, String method) {
		if (BaseInfoApi.BASE_INFO_METHOD.equals(method)) {
			BrandInfoDetailBean bean = (BrandInfoDetailBean) result;

			tvShow.setText("info respond \n" + bean.getBrand().getSchoolName());

		} else if (BaseInfoApi.CIVILIZATION_METHOD.equals(method)) {
			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("civilization respond \n" + lists.get(0).getWeek());
		}else if(BaseInfoApi.IN.equals(method)){

			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("嵌套 respond \n" + lists.get(0).getWeek());
		}
	}

    @Override
    public void onError(ApiException e, String method) {
        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
    }


}
