package com.maxqiu.demo.P12_TemplateMethod.Mode2;

/**
 * 豆浆
 * 
 * @author Max_Qiu
 */
public abstract class SoybeanMilk {

    /**
     * 模板方法（模板方法添加final，不让子类去覆盖）
     */
    final void make() {
        select();
        if (customerWantCondiments()) {
            addCondiments();
        }
        soak();
        beat();
    }

    /**
     * 选材料
     */
    void select() {
        System.out.println("选择新鲜黄豆");
    }

    /**
     * 添加不同的配料（抽象方法，子类具体实现）
     */
    abstract void addCondiments();

    /**
     * 浸泡
     */
    void soak() {
        System.out.println("浸泡，需要3小时");
    }

    /**
     * 榨汁
     */
    void beat() {
        System.out.println("榨汁");
    }

    /**
     * 决定是否需要添加配料（钩子方法）
     */
    boolean customerWantCondiments() {
        return true;
    }

}