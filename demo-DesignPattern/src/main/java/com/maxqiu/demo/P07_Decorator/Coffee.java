package com.maxqiu.demo.P07_Decorator;

/**
 * 咖啡
 * 
 * @author Max_Qiu
 */
public class Coffee extends Goods {

    protected Goods goods;

    /**
     * 传入调料或者咖啡豆
     */
    public Coffee(Goods goods) {
        des = "打包";
        price = 0.5;
        this.goods = goods;
    }

    @Override
    public Double getPrice() {
        // 咖啡的价格 = 咖啡豆 + 打包
        return goods.getPrice() + super.getPrice();
    }

    @Override
    public String getDes() {
        // 咖啡的描述 = 咖啡豆的描述 + 打包
        return goods.getDes() + " " + super.getDes();
    }

}
