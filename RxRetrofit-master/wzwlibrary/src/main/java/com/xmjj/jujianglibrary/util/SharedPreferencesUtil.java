package com.xmjj.jujianglibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wangZhiWei
 * @version V3.0.0 2016/9/18
 * @description 添加一个SharedPreferencesUtil的工具类
 */
public class SharedPreferencesUtil {

    public String spName = "preferences";

    public static SharedPreferences mSharedPreferences;

    private volatile static SharedPreferencesUtil mSharedPreferencesUtil=null;


    /**
     * 使用饿汉式，单例对象初始化非常快，而且占用内存非常小的时候这种方式是比较合适的，可以直接在应用启动时加载并初始化
     * @return
     */
    public static SharedPreferencesUtil getInstance() {

        if (mSharedPreferences == null) {
            throw new IllegalStateException("请在获取实例前先调用 init(Context) 方法");
        }
		/* 确保mSharedPreferences存在 */
        if (mSharedPreferences == null)
            mSharedPreferencesUtil = null;
        return mSharedPreferencesUtil;
    }

    public static void init(Context context) {
        mSharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    protected SharedPreferencesUtil(Context context) {

        if (context != null) {
            mSharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
    }

    public void putInt(String key, int value) {

        mSharedPreferences.edit().putInt(key, value).apply();
        ;
    }

    public void putBoolean(String key, boolean value) {

        mSharedPreferences.edit().putBoolean(key, value).apply();
        ;
    }

    public void putString(String key, String value) {

        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void putFloat(String key, float value) {

        mSharedPreferences.edit().putFloat(key, value).apply();
        ;
    }

    public void putLong(String key, Long value) {

        mSharedPreferences.edit().putLong(key, value).apply();
        ;
    }

	/*public void putDouble(String key, double value) {

		*//* 默认没有保存 double 的功能，将double转为字符串后保存 *//*
		mSharedPreferences.edit().putString(key, String.valueOf(value)).apply();;
	}

	public double getDouble(String key, double defaultValue) {

		*//* 默认不能取double，将字符串解析为doueble *//*
		String result = mSharedPreferences.getString(key, String.valueOf(defaultValue));
		return Double.valueOf(result);
	}*/

    public int getInt(String key) {

        return mSharedPreferences.getInt(key, -1);
    }

    public String getString(String key) {

        return mSharedPreferences.getString(key, "");
    }

    public float getFloat(String key) {

        return mSharedPreferences.getFloat(key, -1);
    }

    public boolean getBoolean(String key) {

        return mSharedPreferences.getBoolean(key, false);
    }

    public long getLong(String key) {

        return mSharedPreferences.getLong(key, -1);
    }

    /**
     * 移除数据
     *
     * @param key 键名称
     */
    public void removeKey(String key) {

        //SharedPreferences sp = content.getSharedPreferences(FILE_NAME, 0); //读取文件,如果没有则会创建
        if (mSharedPreferences.contains(key)) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.commit();
        }

    }

    /**
     * 返回是否包含数据
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {


        return mSharedPreferences.contains(key);
    }


    /**
     * 清除所有的数据
     */
    public void clearSharedPreferences() {


        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear().commit();
    }
}
