package com.maxqiu.demo.P09_Facade;

/**
 * 播放器
 * 
 * @author Max_Qiu
 */
public class Player {

    public void on() {
        System.out.println("播放器\t打开");
    }

    public void off() {
        System.out.println("播放器\t关闭");
    }

    public void play() {
        System.out.println("播放器\t播放");
    }

    public void pause() {
        System.out.println("播放器\t暂停");
    }

}
