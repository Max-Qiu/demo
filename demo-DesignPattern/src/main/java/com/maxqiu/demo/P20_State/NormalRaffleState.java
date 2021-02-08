package com.maxqiu.demo.P20_State;

/**
 * 普通状态
 *
 * @author Max_Qiu
 */
public class NormalRaffleState extends State {

    /**
     * 初始化时传入活动引用，扣除积分后改变其状态
     */
    RaffleActivity activity;

    public NormalRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    /**
     * 当前状态可以扣积分，扣除后将状态设置成可以抽奖状态
     */
    @Override
    public void deductMoney() {
        // 如果有奖品，则转换为准备抽奖
        if (activity.getCount() > 0) {
            System.out.println("扣除50积分成功，您可以抽奖了");
            activity.setState(activity.getCanRaffleState());
        } else {
            System.out.println("没有奖品了，活动结束");
            activity.setState(activity.getDispenseOutState());
        }
    }

    /**
     * 当前状态不能抽奖
     * 
     * @return
     */
    @Override
    public boolean raffle() {
        System.out.println("扣了积分才能抽奖喔！");
        return false;
    }

    /**
     * 当前状态不能发奖品
     */
    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }

}
