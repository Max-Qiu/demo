package com.maxqiu.demo.P07_Decorator;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品
 * 
 * @author Max_Qiu
 */
@Getter
@Setter
public abstract class Goods {

    /**
     * 描述
     */
    private String des;

    /**
     * 价格
     */
    private Double price;

}
