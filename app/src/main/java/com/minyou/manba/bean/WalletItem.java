package com.minyou.manba.bean;

/**
 * Created by Administrator on 2017/11/28.
 */
public class WalletItem {
    private String itemName;
    private int count;
    private int type;       // 0 灵气石  1 灵石

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


}
