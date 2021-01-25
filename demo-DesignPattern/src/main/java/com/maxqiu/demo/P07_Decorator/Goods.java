package com.maxqiu.demo.P07_Decorator;

/**
 * 商品
 * 
 * @author Max_Qiu
 */
public abstract class Goods {

    /**
     * 描述
     */
    protected String des;

    /**
     * 价格
     */
    protected Double price;

    public String getDes() {
        return des;
    }

    public Double getPrice() {
        return price;
    }

}
