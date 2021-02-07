package com.maxqiu.demo.P18_Memento.T2_Demo;

/**
 * 单条存档（备忘录）
 * 
 * @author Max_Qiu
 */
public class Archive {

    // 攻击力
    private int vit;
    // 防御力
    private int def;

    public Archive(int vit, int def) {
        super();
        this.vit = vit;
        this.def = def;
    }

    public int getVit() {
        return vit;
    }

    public int getDef() {
        return def;
    }

}
