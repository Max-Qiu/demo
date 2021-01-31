package com.maxqiu.demo.P13_Command;

/**
 * 电视关闭接口
 * 
 * @author Max_Qiu
 */
public class TvOffCommand implements Command {

    /**
     * 聚合TVReceiver
     */
    TvReceiver tv;

    /**
     * 构造器
     * 
     * @param tv
     */
    public TvOffCommand(TvReceiver tv) {
        super();
        this.tv = tv;
    }

    @Override
    public void execute() {
        // 调用接收者的方法
        tv.off();
    }

    @Override
    public void undo() {
        // 调用接收者的方法
        tv.on();
    }

}
