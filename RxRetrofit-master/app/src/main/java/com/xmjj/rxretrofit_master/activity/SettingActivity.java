package com.xmjj.rxretrofit_master.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.View;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.xmjj.rxretrofit_master.util.DialogUtils;
import com.xmjj.rxretrofit_master.util.ToastUtils;
import com.xmjj.rxretrofit_master.R;
import com.xmjj.rxretrofit_master.base.BaseActivity;
import com.xmjj.rxretrofit_master.db.DBFlowModel;
import com.xmjj.rxretrofit_master.entity.event.SkinEvent;
import com.xmjj.rxretrofit_master.util.CleanCacheUtils;

/**
 * 功能描述：
 * Created by wzw
 * 2017/9/21
 */

public class SettingActivity extends BaseActivity {

	//内部类，继承自PreferenceFragment的子类
	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//加载创建的preference.xml 文件，这个文件的位置在res/xml文件夹下
			addPreferencesFromResource(R.xml.preference_setting);
			final SwitchPreference switchPreference = (SwitchPreference) findPreference("night");
			switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					if (switchPreference.isChecked() != (Boolean) newValue) {
						boolean value = (Boolean) (newValue);
						switchPreference.setChecked(value);
						if (switchPreference.isChecked()) {

							RxBus.getDefault().post(new SkinEvent(MdActivity.BLACK));
						} else {
							RxBus.getDefault().post(new SkinEvent(MdActivity.BLUE));
						}
					}

					return true;
				}
			});

		}


		@Override
		public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
			String key = preference.getKey();
			if (key.equals("night")) {
				SwitchPreference switchPreference = (SwitchPreference) preference;
				if (switchPreference.isChecked()) {

					RxBus.getDefault().post(new SkinEvent(MdActivity.BLACK));
				} else {
					RxBus.getDefault().post(new SkinEvent(MdActivity.BLUE));
				}
			} else if (key.equals("clean")) {
				showCleanDialog();
			} else {
				ToastUtils.showShortMes(getActivity(), "暂无功能");
			}
			return super.onPreferenceTreeClick(preferenceScreen, preference);
		}

		/*显示清除对话框*/
		private void showCleanDialog() {
			DialogUtils.getInstance()
					.showDialog(getActivity(), new Listener())
					.setMessage("确认要清除所有缓存数据吗?");

		}

		/*清除缓存*/
		public void cleanMemory() {
			final ProgressDialog dialog = new ProgressDialog(getActivity());
			dialog.setMessage("正在清除缓存");
			dialog.show();
			new Thread() {
				@Override
				public void run() {
					super.run();
					CleanCacheUtils.cleanApplicationData(getActivity(), DBFlowModel.class);
					SystemClock.sleep(1500);
					dialog.dismiss();
				}
			}.start();
		}

		public class Listener extends DialogUtils.positiveListener {
			@Override
			public void onClick(View v) {
				super.onClick(v);
				cleanMemory();

			}
		}
	}


	@Override
	public int getLayoutResId() {
		return R.layout.activity_setting;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initData() {
		switchFragment(new SettingsFragment());
	}

}
