package com.maxqiu.demo.P03_Prototype.improve.Copy;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 浅拷贝
        Sheep s = new Sheep(1, "白色");
        Sheep c1 = s.clone();
        Sheep c2 = c1.clone();
        System.out.println(s + " " + s.hashCode());
        System.out.println(c1 + " " + c1.hashCode());
        System.out.println(c2 + " " + c2.hashCode());
    }

}
