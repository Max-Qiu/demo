package com.maxqiu.demo.P20_State;

/**
 * 状态抽象类
 * 
 * @author Max_Qiu
 */
public abstract class State {

    /**
     * 扣除积分
     */
    public abstract void deductMoney();

    /**
     * 是否抽中奖品
     * 
     * @return
     */
    public abstract boolean raffle();

    /**
     * 发放奖品
     */
    public abstract void dispensePrize();

}
