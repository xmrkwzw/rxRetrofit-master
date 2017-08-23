package com.xmjj.rxretrofit_master.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Somewereb on 2017/8/18.
 */

public class BaseApplication extends Application {

    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        Log.i("11111", "2222222222");
        FlowManager.init(this);
       // FlowManager.init(new FlowConfig.Builder(this).build());//这句也可以初始化
//        FlowManager.init(new FlowConfig.Builder(this).build());
        Log.i("11111", "3333333333");
    }

    public static Context getContext() {
        return mAppContext;
    }
}
