package com.maxqiu.demo.P13_Command;

/**
 * 没有任何命令，即空执行: 用于初始化每个按钮, 当调用空命令时，对象什么都不做
 *
 * @author Max_Qiu
 */
public class NoCommand implements Command {

    @Override
    public void execute() {}

    @Override
    public void undo() {}

}
