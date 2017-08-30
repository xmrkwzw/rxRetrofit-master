package com.xmjj.rxretrofit_master.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;
import com.xmjj.jujianglibrary.exception.ApiException;
import com.xmjj.jujianglibrary.listener.HttpOnNextListener;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.db.DBFlowModel;
import com.xmjj.rxretrofit_master.db.DBFlowModel_Table;
import com.xmjj.rxretrofit_master.entity.BrandInfoDetailBean;
import com.xmjj.rxretrofit_master.entity.RatingBean;
import com.xmjj.rxretrofit_master.entity.event.CommonEvent;
import com.xmjj.rxretrofit_master.entity.event.StickyEvent;
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
	private Button btnMsgCode;
	private Button btnDeleteTable;
	private BaseInfoApi baseInfoApi;
	private Button btnGo;
	private Button btnPost;
	private Button btnStickyPost;

	@Override
	public int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	public void initViews() {
		btnObject = (Button) findViewById(R.id.btn_object);
		btnArray = (Button) findViewById(R.id.btn_array);
		btnIn = (Button) findViewById(R.id.btn_in);
		tvShow = (TextView) findViewById(R.id.tv_show);
		btnDbAdd = (Button) findViewById(R.id.btn_db_add);
		btnDbDelete = (Button) findViewById(R.id.btn_db_delete);
		btnDbUpdate = (Button) findViewById(R.id.btn_db_update);
		btnDbQuery = (Button) findViewById(R.id.btn_db_query);
		btnMsgCode = (Button) findViewById(R.id.btn_msg_code);
		btnDeleteTable = (Button) findViewById(R.id.btn_db_delete_table);
		btnGo = (Button) findViewById(R.id.btn_go);
		btnPost = (Button) findViewById(R.id.btn_rx_post);
		btnStickyPost = (Button) findViewById(R.id.btn_rx_sticky_post);


		btnObject.setOnClickListener(this);
		btnArray.setOnClickListener(this);
		btnIn.setOnClickListener(this);
		btnDbAdd.setOnClickListener(this);
		btnDbDelete.setOnClickListener(this);
		btnDbUpdate.setOnClickListener(this);
		btnDbQuery.setOnClickListener(this);
		btnMsgCode.setOnClickListener(this);
		btnDeleteTable.setOnClickListener(this);
		btnStickyPost.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		btnGo.setOnClickListener(this);
	}

	@Override
	public void initData() {


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
		RxBus.getDefault().removeAllStickyEvents();
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
		} else if (BaseInfoApi.MSG_CODE_METHOD.equals(method)) {

			tvShow.setText((String) result);
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
	 * get verify code
	 */
	public void getMsgCode() {
		baseInfoApi = new BaseInfoApi(this, this, String.class);
		baseInfoApi.getMscCode("18750931912");
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
			dbFlowModel.setName("name:" + i);
			dbFlowModel.setAge((i));
			dbFlowModel.setContent("content" + i);
			dbFlowModel.setGrade(i);
			array.add(dbFlowModel);
		}
		FlowManagerUtil.getInstance().saveAll(array);
		query();

	}

	/**
	 * dbflow operate delete a array by flowManager
	 */
	public void delete() {
		SQLite.delete().from(DBFlowModel.class)
				.where(DBFlowModel_Table.age.eq(5))
				.execute();
		query();
	}

	/**
	 * remove all data from table
	 */
	public void deleteTable() {
		FlowManagerUtil.getInstance().deleteTable(DBFlowModel.class);
	}

	/**
	 * dbflow operate update a array by flowManager
	 */
	public void update() {
		SQLite.update(DBFlowModel.class).set(DBFlowModel_Table.grade.eq(2))
				.where(DBFlowModel_Table.grade.eq(1))
				.execute();
		query();
	}

	public void query() {
		tvShow.setText("");
		List<DBFlowModel> lists =
				SQLite.select().from(DBFlowModel.class).queryList();
		for (int i = 0; i < lists.size(); i++) {
			DBFlowModel dbFlowModel = lists.get(i);
			tvShow.setText(tvShow.getText().toString() + dbFlowModel.getContent() + "--" + dbFlowModel.getName()
					+ "--" + dbFlowModel.getGrade()
					+ "--" + dbFlowModel.getAge() + "\n");
		}
	}

	/*post a common event by rxbus*/
	public void post() {
		RxBus.getDefault().post(new CommonEvent("common event"));
	}

	/*post a sticky event by rxbus*/
	public void stickyPost() {
		RxBus.getDefault().post(new StickyEvent("sticky event"));

	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void rcvCommonEvent(CommonEvent event) {
		if (event != null) {
			tvShow.setText("一般事件结果：" + event.msg);
		}
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
			case R.id.btn_msg_code:
				getMsgCode();
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
			case R.id.btn_db_delete_table:
				deleteTable();
				break;
			case R.id.btn_go:
				startActivity(new Intent(MainActivity.this, RcvActivity.class));
				break;
			case R.id.btn_rx_post:
				post();
				break;
			case R.id.btn_rx_sticky_post:
				stickyPost();
				break;
		}
	}

}
