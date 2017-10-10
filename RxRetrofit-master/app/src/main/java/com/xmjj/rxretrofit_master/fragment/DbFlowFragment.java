package com.xmjj.rxretrofit_master.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseFragment;
import com.xmjj.rxretrofit_master.db.DBFlowModel;
import com.xmjj.rxretrofit_master.db.DBFlowModel_Table;
import com.xmjj.rxretrofit_master.util.FlowManagerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/11
 */

public class DbFlowFragment extends BaseFragment  {
	@BindView(R.id.btn_db_add)
	Button btnDbAdd;
	@BindView(R.id.btn_db_delete)
	Button btnDbDelete;
	@BindView(R.id.btn_db_update)
	Button btnDbUpdate;
	@BindView(R.id.btn_db_query)
	Button btnDbQuery;
	@BindView(R.id.btn_db_delete_table)
	Button btnDeleteTable;
	@BindView(R.id.tv_show)
	TextView tvShow;


	@Override
	public int getLayoutResId() {
		return R.layout.fragment_db;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {

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
		query();
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


	@OnClick({R.id.btn_db_add, R.id.btn_db_delete, R.id.btn_db_update, R.id.btn_db_query, R.id.btn_db_delete_table})
	public void onClick(View view) {
		switch (view.getId()) {
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
		}
	}
}
