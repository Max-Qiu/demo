package com.maxqiu.demo.P13_Command;

/**
 * 灯光开启命令
 *
 * @author Max_Qiu
 */
public class LightOnCommand implements Command {

    /**
     * 聚合LightReceiver
     */
    LightReceiver light;

    /**
     * 构造器
     * 
     * @param light
     */
    public LightOnCommand(LightReceiver light) {
        super();
        this.light = light;
    }

    @Override
    public void execute() {
        // 调用接收者的方法
        light.on();
    }

    @Override
    public void undo() {
        // 调用接收者的方法
        light.off();
    }

}
