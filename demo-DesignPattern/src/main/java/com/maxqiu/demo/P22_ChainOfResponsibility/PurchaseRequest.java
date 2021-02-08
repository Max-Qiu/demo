package com.maxqiu.demo.P22_ChainOfResponsibility;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求
 * 
 * @author Max_Qiu
 */
@Getter
@AllArgsConstructor
public class PurchaseRequest {

    /**
     * 请求ID
     */
    private int id;

    /**
     * 请求金额
     */
    private float price;

}
