package com.maxqiu.demo.P20_State;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建活动对象，奖品有2个奖品
        RaffleActivity activity = new RaffleActivity(2);
        // 我们连续抽300次奖
        for (int i = 0; i < 10; i++) {
            System.out.println("--------第" + (i + 1) + "次抽奖----------");
            // 参加抽奖，第一步点击扣除积分
            activity.participateInActivities();
            // 第二步抽奖
            activity.raffle();
        }
    }

}
