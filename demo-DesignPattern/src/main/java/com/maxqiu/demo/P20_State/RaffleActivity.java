package com.maxqiu.demo.P20_State;

/**
 * 抽奖活动
 *
 * @author Max_Qiu
 */
public class RaffleActivity {

    /**
     * 表示活动当前的状态，是变化
     */
    private State state;

    /**
     * 奖品数量
     */
    private int count;

    /**
     * 四个属性，表示四种状态
     */
    State normalRaffleState = new NormalRaffleState(this);
    State canRaffleState = new CanRaffleState(this);
    State dispenseState = new DispenseState(this);
    State dispenseOutState = new DispenseOutState(this);

    /**
     * 构造器
     * 
     * @param count
     */
    public RaffleActivity(int count) {
        // 1. 初始化当前的状态为 normalRaffleState（即不能普通的状态）
        this.state = getNormalRaffleState();
        // 2. 初始化奖品的数量
        this.count = count;
    }

    /**
     * 参与活动
     */
    public void participateInActivities() {
        // 扣分, 调用当前状态的 deductMoney
        state.deductMoney();
    }

    /**
     * 抽奖
     */
    public void raffle() {
        // 如果当前的状态是抽奖成功
        if (state.raffle()) {
            // 领取奖品
            state.dispensePrize();
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getNormalRaffleState() {
        return normalRaffleState;
    }

    public State getCanRaffleState() {
        return canRaffleState;
    }

    public State getDispenseState() {
        return dispenseState;
    }

    public State getDispenseOutState() {
        return dispenseOutState;
    }

}
