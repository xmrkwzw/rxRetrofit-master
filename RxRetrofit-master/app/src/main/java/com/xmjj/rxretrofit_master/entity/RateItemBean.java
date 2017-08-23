package com.xmjj.rxretrofit_master.entity;

/**
 * @author wangzhiwei.
 * @create on 2017/4/10.
 * @description
 */

public class RateItemBean {
    /**
     * itemName : 健体星
     * status : true
     * isDefault : false
     * schoolId : 356212
     * itemId : 4
     */

    private String itemName;

    private int itemFlag;

    public int getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(int itemFlag) {
        this.itemFlag = itemFlag;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
