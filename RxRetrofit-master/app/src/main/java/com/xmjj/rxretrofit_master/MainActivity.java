package com.xmjj.rxretrofit_master;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.db.DBFlowModel;
import com.xmjj.rxretrofit_master.db.DBFlowModel_Table;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.http.api.BaseInfoApi;
import com.xmjj.rxretrofit_master.util.FlowManagerUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements HttpOnNextListener, View.OnClickListener {
	private Button btnObject;
	private Button btnArray;
	private Button btnIn;
	private TextView tvShow;
	private Button btnDbAdd;
	private Button btnDbDelete;
	private Button btnDbUpdate;
	private Button btnDbQuery;

	private BaseInfoApi baseInfoApi;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	


	

	@Override
	public void initViews() {
		btnObject = (Button)findViewById( R.id.btn_object );
		btnArray = (Button)findViewById( R.id.btn_array );
		btnIn = (Button)findViewById( R.id.btn_in );
		tvShow = (TextView)findViewById( R.id.tv_show );
		btnDbAdd = (Button)findViewById( R.id.btn_db_add );
		btnDbDelete = (Button)findViewById( R.id.btn_db_delete );
		btnDbUpdate = (Button)findViewById( R.id.btn_db_update );
		btnDbQuery = (Button)findViewById( R.id.btn_db_query );

		btnObject.setOnClickListener( this );
		btnArray.setOnClickListener( this );
		btnIn.setOnClickListener( this );
		btnDbAdd.setOnClickListener( this );
		btnDbDelete.setOnClickListener( this );
		btnDbUpdate.setOnClickListener( this );
		btnDbQuery.setOnClickListener( this );

	}

	@Override
	public void initData() {


	}

	/*retrofit*/
	@Override
	public void onNext(Object result, String method) {
		if (BaseInfoApi.BASE_INFO_METHOD.equals(method)) {
			BrandInfoDetailBean bean = (BrandInfoDetailBean) result;

			tvShow.setText("info respond \n" + bean.getBrand().getSchoolName());

		} else if (BaseInfoApi.CIVILIZATION_METHOD.equals(method)) {
			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("civilization respond \n" + lists.get(0).getWeek());
		} else if (BaseInfoApi.IN.equals(method)) {

			List<RatingBean> lists = (List<RatingBean>) result;

			tvShow.setText("嵌套 respond \n" + lists.get(0).getWeek());
		}
	}

	@Override
	public void onError(ApiException e, String method) {
		Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
	}


	/**
	 * the result is JsonObject such as {"result":{"xxx"}}
	 */
	public void objectResult() {
		baseInfoApi = new BaseInfoApi(this, this, BrandInfoDetailBean.class);
		baseInfoApi.getBaseInfo("d6aa48251b85b045", "数据加载中......");
	}

	/**
	 * the result is JsonArray such as {"result":[{"xxx"},{"xxx"}]}
	 */
	public void arrayResult() {
		baseInfoApi = new BaseInfoApi(this, this, RatingBean.class);
		baseInfoApi.getCivilization("14871");
	}

	/**
	 * Nested request interface :a result doing after the other result
	 */
	public void nestedRequest() {
		baseInfoApi = new BaseInfoApi(this, this, BrandInfoDetailBean.class);
		baseInfoApi.doOther("d6aa48251b85b045", "嵌套接口加载......");
	}

	/**
	 * dbflow operate insert a array by flowManager
	 */
	public void insert() {
		ArrayList<DBFlowModel> array = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			DBFlowModel dbFlowModel = new DBFlowModel();
			dbFlowModel.setAge((i + 1));
			dbFlowModel.setContent("content" + (i + 1));
			array.add(dbFlowModel);
		}
		FlowManagerUtil.getInstance().saveAll(array);

	}

	public void delete() {

	}

	public void update() {

	}

	public void query() {
		List<DBFlowModel> lists =
				SQLite.select().from(DBFlowModel.class)
				.where(DBFlowModel_Table.age.greaterThan(5))
				.queryList();

		tvShow.setText(lists.get(0).getName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_object:
				objectResult();
				break;
			case R.id.btn_array:
				arrayResult();
				break;
			case R.id.btn_in:
				nestedRequest();
				break;
			case R.id.tv_show:
				break;
			case R.id.btn_db_add:
				insert();
				break;
			case R.id.btn_db_delete:
				delete();
				break;
			case R.id.btn_db_update:
				update();
				break;
			case R.id.btn_db_query:
				query();
				break;
		}
	}

}
