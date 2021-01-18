package com.maxqiu.demo.P04_Builder.demo;

/**
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        Villa villa = new Villa();
        villa.build();
        System.out.println("--------------------------");
        HighBuilding highBuilding = new HighBuilding();
        highBuilding.build();
    }

}
