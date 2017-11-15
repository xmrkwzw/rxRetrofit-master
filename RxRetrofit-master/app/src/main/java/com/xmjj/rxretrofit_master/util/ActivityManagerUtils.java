package com.xmjj.rxretrofit_master.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 功能描述:Activity统一管理工具类
 * Created by  on 2016/7/27.
 */

public class ActivityManagerUtils {
    public static Stack<Activity> activityStack;
    private static ActivityManagerUtils instance;
    public int count = 0;

    private ActivityManagerUtils() {
    }

    /**
     * 描述：获取单例
     */
    public static ActivityManagerUtils getInstance() {
        if (instance == null) {
            instance = new ActivityManagerUtils();
        }
        return instance;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static void setActivityStack(Stack<Activity> activityStack) {
        ActivityManagerUtils.activityStack = activityStack;
    }

    /**
     * 描述：添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        count = count + 1;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 描述：结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 描述：结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 是否已打开
     * @param cls
     * @return
     */
    public boolean containesActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 描述：结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 描述：结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 关闭其他activity 只剩下一个
     * @param only  不关闭的activity名称
     */
    public void finishOtherActivity(Class<?> only){
        try {
            List<Activity> activities = new ArrayList<Activity>();
            int size = activityStack.size();
            for (int i =0; i<size; i++) {
                Activity activity = activityStack.get(i);
                if (null != activityStack.get(i)) {
                    if (activity != null && activity.getClass().equals(only)) {
                        break;
                    } else {
                        activities.add(activity);
                        activity.finish();
                    }
                }
            }
            for (int i = 0; i < activities.size(); i++) {
                activityStack.remove(activities.get(i));
                activities.get(i).finish();
            }
            activities.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 描述：退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }
    /**
     * app是否运行在前台
     */
    public static boolean isAppForground(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }
        return true;
    }
}
