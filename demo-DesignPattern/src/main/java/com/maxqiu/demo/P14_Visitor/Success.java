package com.maxqiu.demo.P14_Visitor;

/**
 * 评价成功
 * 
 * @author Max_Qiu
 */
public class Success extends Action {

    @Override
    public void getResult(Man man) {
        System.out.println("男人给的评价该歌手很成功！");
    }

    @Override
    public void getResult(Woman woman) {
        System.out.println("女人给的评价该歌手很成功！");
    }

}
