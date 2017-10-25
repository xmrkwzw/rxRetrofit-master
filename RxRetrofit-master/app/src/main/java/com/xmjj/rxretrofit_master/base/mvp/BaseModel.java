package com.xmjj.rxretrofit_master.base.mvp;

import java.util.Map;

/**
 * Created by wzw on 2017/3/13.
 * Description Model：用于数据的增删改查等，也包括一些数据对象
 */

public interface BaseModel {
    //将请求参数通过map集合上传
    void getData(Map<String, Object> params);
}
