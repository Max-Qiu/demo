package com.maxqiu.demo.P10_Flyweight;

/**
 * 具体网站
 * 
 * @author Max_Qiu
 */
public class ConcreteWebSite extends WebSite {

    /**
     * 网站类型（共享的部分，内部状态）
     */
    private String type;

    @Override
    public void use(User user) {
        System.out.println("网站形式为：" + type + " 在使用中。。。使用者是" + user.getName());
    }

    public ConcreteWebSite(String type) {
        this.type = type;
    }

}
