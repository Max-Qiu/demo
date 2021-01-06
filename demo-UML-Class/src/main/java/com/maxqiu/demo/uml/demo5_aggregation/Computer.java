package com.maxqiu.demo.uml.demo5_aggregation;

/**
 * @author Max_Qiu
 */
public class Computer {
    private Monitor monitor;
    private Mouse mouse;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}