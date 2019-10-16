package com.kgc.kgc_provider.model;



import java.io.Serializable;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/15 13:53
 * @since
 */

public class Goods implements Serializable {
    private String goodID;
    private String goodName;
    private String price;

    public String getGoodID() {
        return goodID;
    }

    public void setGoodID(String goodID) {
        this.goodID = goodID;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
