package com.maxqiu.demo.P03_Prototype.improve.DeepCopy.mode2;

import java.io.*;

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
public class Sheep implements Serializable {

    private static final long serialVersionUID = 1L;
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

    /**
     * 深拷贝方式2：通过对象的序列化实现 (推荐)
     */
    @Override
    public Sheep clone() {
        // 创建流对象
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            // 序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            // 反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            return (Sheep)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭流
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }

}
