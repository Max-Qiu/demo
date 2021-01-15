package com.maxqiu.demo.P03_Prototype.improve.Copy;

import lombok.*;

/**
 * 羊 实体
 * 
 * 实现 Cloneable 接口，并重写 clone() 方法
 * 
 * @author Max_Qiu
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sheep implements Cloneable {

    private Integer age;
    private String color;

    @Override
    public Sheep clone() throws CloneNotSupportedException {
        return (Sheep)super.clone();
    }

}
