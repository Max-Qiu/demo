package com.maxqiu.demo.principle3_dependence_inversion.mode.mode2;

import com.maxqiu.demo.principle3_dependence_inversion.mode.ChangHongTv;
import com.maxqiu.demo.principle3_dependence_inversion.mode.ITv;

/**
 * 方式2: 通过构造方法依赖传递
 * 
 * @author Max_Qiu
 */
public class DependencePass2 {

    public static void main(String[] args) {
        ChangHongTv changHong = new ChangHongTv();
        IOpenAndClose openAndClose = new OpenAndClose(changHong);
        openAndClose.open();
    }

}

interface IOpenAndClose {
    // 抽象方法
    void open();
}

class OpenAndClose implements IOpenAndClose {
    // 成员变量
    private ITv tv;

    // 构造器
    public OpenAndClose(ITv tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}
