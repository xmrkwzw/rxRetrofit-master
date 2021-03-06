package com.xmjj.rxretrofit_master.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.Model;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.xmjj.rxretrofit_master.db.AppDataBase;

import java.util.Collection;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/24
 */

public class FlowManagerUtil {
	public static FlowManagerUtil instance;

	public static FlowManagerUtil getInstance() {
		if (instance == null) {
			instance = new FlowManagerUtil();
		}
		return instance;
	}


	public void saveAll(final Collection<? extends Model> models) {
		FlowManager.getDatabase(AppDataBase.class)
				.beginTransactionAsync(new ProcessModelTransaction.Builder<>(
						new ProcessModelTransaction.ProcessModel<Model>() {
							@Override
							public void processModel(Model model, DatabaseWrapper wrapper) {
								model.save();
							}
						}).addAll(models).build())
				.error(new Transaction.Error() {
					@Override
					public void onError(Transaction transaction, Throwable error) {

					}
				})
				.success(new Transaction.Success() {
					@Override
					public void onSuccess(Transaction transaction) {

					}
				}).build().execute();
	}


	public void deleteTable(Class table) {
		Delete.table(table);
	}

}
