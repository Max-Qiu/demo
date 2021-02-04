package com.maxqiu.demo.P14_Visitor;

/**
 * 评价待定
 * 
 * @author Max_Qiu
 */
public class Wait extends Action {

    @Override
    public void getResult(Man man) {
        System.out.println("男人给的评价是该歌手待定。。。");
    }

    @Override
    public void getResult(Woman woman) {
        System.out.println("女人给的评价是该歌手待定。。。");
    }

}
