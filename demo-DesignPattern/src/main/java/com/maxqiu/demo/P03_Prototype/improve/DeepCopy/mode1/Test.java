package com.maxqiu.demo.P03_Prototype.improve.DeepCopy.mode1;

/**
 * @author Max_Qiu
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 使用 clone() 方法完成深拷贝
        Sheep friendSheep = new Sheep("Jerry", 2, "黑色");
        Sheep s = new Sheep("Tom", 1, "白色", friendSheep);
        Sheep c1 = (Sheep)s.clone();
        Sheep c2 = (Sheep)s.clone();
        System.out.println(s + " " + s.hashCode() + " " + s.getFriend() + " " + s.getFriend().hashCode());
        System.out.println(c1 + " " + c1.hashCode() + " " + c1.getFriend() + " " + c1.getFriend().hashCode());
        System.out.println(c2 + " " + c2.hashCode() + " " + c2.getFriend() + " " + c2.getFriend().hashCode());
    }

}
