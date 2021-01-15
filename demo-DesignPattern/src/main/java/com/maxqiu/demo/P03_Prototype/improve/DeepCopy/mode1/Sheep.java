package com.maxqiu.demo.P03_Prototype.improve.DeepCopy.mode1;

import lombok.*;

/**
 * 羊 实体
 * 
 * @author Max_Qiu
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Cloneable {

    private String name;
    private Integer age;
    private String color;
    private Sheep friend;

    /**
     * 额外写一个没朋友的羊的构造方法
     */
    public Sheep(String name, Integer age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    // @Override
    // public Object clone() throws CloneNotSupportedException {
    // // 浅拷贝
    // return super.clone();
    // }

    /**
     * 深拷贝方式1：使用 clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 完成对基本数据类型(属性)、包装类、String的克隆
        Sheep sheep = (Sheep)super.clone();
        // 判断引用类型是否为空
        if (sheep.friend != null) {
            // 对引用类型的属性，进行单独处理
            sheep.friend = (Sheep)friend.clone();
        }
        return sheep;
    }

}
