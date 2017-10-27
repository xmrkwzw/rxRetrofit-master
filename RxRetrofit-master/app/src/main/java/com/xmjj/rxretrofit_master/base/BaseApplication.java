package com.xmjj.rxretrofit_master.base;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xmjj.jujianglibrary.util.skinloader.load.SkinManager;


public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initSkinLoader();
		FlowManager.init(this);
		UMShareAPI.get(this);
		Config.DEBUG = true;


	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

	}
	/**
	 * Must call init first
	 */
	private void initSkinLoader() {
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();
	}
	//各个平台的配置，建议放在全局Application或者程序入口
	{
		PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
		//豆瓣RENREN平台目前只能在服务器端配置
		PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
		PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
		PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
		PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
		PlatformConfig.setAlipay("2015111700822536");
		PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
		PlatformConfig.setPinterest("1439206");
		PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
		PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
		PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
		PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
		PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
	}

}
