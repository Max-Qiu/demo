package com.maxqiu.demo.P03_Prototype.improve.DeepCopy.mode2;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) {
        // 使用 Stream流 方法完成深拷贝
        Sheep friendSheep = new Sheep("jerry", 2, "黑色");
        Sheep s = new Sheep("tom", 1, "白色", friendSheep);
        Sheep c1 = s.clone();
        Sheep c2 = s.clone();
        System.out.println(s + " " + s.hashCode() + " " + s.getFriend() + " " + s.getFriend().hashCode());
        System.out.println(c1 + " " + c1.hashCode() + " " + c1.getFriend() + " " + c1.getFriend().hashCode());
        System.out.println(c2 + " " + c2.hashCode() + " " + c2.getFriend() + " " + c2.getFriend().hashCode());
    }

}
