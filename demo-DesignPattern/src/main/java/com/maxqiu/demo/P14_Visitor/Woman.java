package com.maxqiu.demo.P14_Visitor;

/**
 * 女人
 * 
 * 说明：
 * 
 * 1. 这里我们使用到了双分派, 即首先在客户端程序中，将具体状态作为参数传递Woman中(第一次分派)
 * 
 * 2. 然后Woman 类调用作为参数的 "具体方法" 中方法getWomanResult, 同时将自己(this)作为参数传入，完成第二次的分派
 *
 * @author Max_Qiu
 */
public class Woman extends Person {

    @Override
    public void accept(Action action) {
        action.getResult(this);
    }

}
