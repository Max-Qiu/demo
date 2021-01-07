package com.maxqiu.demo.principle3_dependence_inversion.mode;

/**
 * 电视机实例
 * 
 * @author Max_Qiu
 */
public class ChangHongTv implements ITv {
    @Override
    public void play() {
        System.out.println("长虹电视机，打开");
    }
}
