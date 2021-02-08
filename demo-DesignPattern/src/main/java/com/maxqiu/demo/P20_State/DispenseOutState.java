package com.maxqiu.demo.P20_State;

/**
 * 奖品发放完毕状态（活动结束）
 * 
 * 当 activity 改变成 DispenseOutState，抽奖活动结束
 *
 * @author Max_Qiu
 */
public class DispenseOutState extends State {

    RaffleActivity activity;

    public DispenseOutState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("奖品发送完了，请下次再参加");
    }

    @Override
    public boolean raffle() {
        System.out.println("奖品发送完了，请下次再参加");
        return false;
    }

    @Override
    public void dispensePrize() {
        System.out.println("奖品发送完了，请下次再参加");
    }

}
