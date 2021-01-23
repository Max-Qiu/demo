package com.maxqiu.demo.P07_Decorator;

/**
 * 商店
 * 
 * @author Max_Qiu
 */
public class Shop {

    public static void main(String[] args) {
        // 只要一份咖啡豆
        Goods espresso = new Espresso();
        System.out.println(espresso.getDes());
        System.out.println(espresso.getPrice());
        // 加工，制作成咖啡
        Coffee coffee1 = new Coffee(espresso);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        // 再加一份牛奶
        coffee1 = new Milk(coffee1);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        // 再加一份焦糖
        coffee1 = new Caramel(coffee1);
        System.out.println(coffee1.getDes());
        System.out.println(coffee1.getPrice());
        System.out.println("------------------------");
        // 要一份脱因咖啡打包并加两份牛奶
        Coffee coffee2 = new Milk(new Milk(new Coffee(new Decaf())));
        System.out.println(coffee2.getDes());
        System.out.println(coffee2.getPrice());
    }

}
