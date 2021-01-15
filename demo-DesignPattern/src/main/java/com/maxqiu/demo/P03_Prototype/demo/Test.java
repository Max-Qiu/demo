package com.maxqiu.demo.P03_Prototype.demo;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        // 普通模式克隆
        Sheep s = new Sheep(1, "白色");
        Sheep c1 = new Sheep(s.getAge(), s.getColor());
        Sheep c2 = new Sheep(s.getAge(), s.getColor());
        System.out.println(s + " " + s.hashCode());
        System.out.println(c1 + " " + c1.hashCode());
        System.out.println(c2 + " " + c2.hashCode());
    }

}
