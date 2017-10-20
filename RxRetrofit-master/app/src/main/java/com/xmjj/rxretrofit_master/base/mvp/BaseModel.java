package com.xmjj.rxretrofit_master.base.mvp;

/**
 * Created by wzw on 2017/3/13.
 * Description Model：用于数据的增删改查等，也包括一些数据对象
 */

public interface BaseModel {
    // 并实现函数回调
    void getData(BaseCallBack callBack);
}
