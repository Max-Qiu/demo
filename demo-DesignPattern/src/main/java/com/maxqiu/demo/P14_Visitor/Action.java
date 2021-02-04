package com.maxqiu.demo.P14_Visitor;

/**
 * 行动（访问者）
 * 
 * @author Max_Qiu
 */
public abstract class Action {

    /**
     * 得到男性 的测评
     * 
     * @param man
     */
    public abstract void getResult(Man man);

    /**
     * 得到女性 测评
     * 
     * @param woman
     */
    public abstract void getResult(Woman woman);

}
