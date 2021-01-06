package com.maxqiu.demo.uml.demo6_composition;

/**
 * @author Max_Qiu
 */
public class Computer {
    private Cpu cpu = new Cpu();
    private Monitor monitor;
    private Mouse mouse;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}
