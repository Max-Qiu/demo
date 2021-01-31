package com.maxqiu.demo.P13_Command;

/**
 * 电视打开命令
 * 
 * @author Max_Qiu
 */
public class TvOnCommand implements Command {

    /**
     * 聚合TVReceiver
     */
    TvReceiver tv;

    /**
     * 构造器
     * 
     * @param tv
     */
    public TvOnCommand(TvReceiver tv) {
        super();
        this.tv = tv;
    }

    @Override
    public void execute() {
        // 调用接收者的方法
        tv.on();
    }

    @Override
    public void undo() {
        // 调用接收者的方法
        tv.off();
    }

}
