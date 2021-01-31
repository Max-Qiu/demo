package com.maxqiu.demo.P13_Command;

/**
 * 创建命令接口
 * 
 * @author Max_Qiu
 */
public interface Command {

    /**
     * 执行操作
     */
    void execute();

    /**
     * 撤销操作
     */
    void undo();

}
