package com.maxqiu.demo.P13_Command;

/**
 * 灯光关闭命令
 * 
 * @author Max_Qiu
 */
public class LightOffCommand implements Command {

    /**
     * 聚合LightReceiver
     */
    LightReceiver light;

    /**
     * 构造器
     * 
     * @param light
     */
    public LightOffCommand(LightReceiver light) {
        this.light = light;
    }

    @Override
    public void execute() {
        // 调用接收者的方法
        light.off();
    }

    @Override
    public void undo() {
        // 调用接收者的方法
        light.on();
    }

}
