package com.maxqiu.demo.P09_Facade;

/**
 * 家庭影院（外观类）
 * 
 * @author Max_Qiu
 */
public class HomeTheatre {

    private Light light;
    private Player player;
    private Projector projector;

    public void ready() {
        projector.on();
        player.on();
        projector.focus();
    }

    public void play() {
        light.off();
        player.play();
    }

    public void pause() {
        light.faint();
        player.pause();
    }

    public void end() {
        light.on();
        player.off();
        projector.off();;
    }

    public HomeTheatre() {
        light = new Light();
        player = new Player();
        projector = new Projector();
    }

}
