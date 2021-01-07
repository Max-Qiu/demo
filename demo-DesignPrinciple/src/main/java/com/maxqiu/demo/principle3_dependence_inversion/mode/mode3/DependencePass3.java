package com.maxqiu.demo.principle3_dependence_inversion.mode.mode3;

import com.maxqiu.demo.principle3_dependence_inversion.mode.ChangHongTv;
import com.maxqiu.demo.principle3_dependence_inversion.mode.ITv;

/**
 * 方式3 , 通过setter方法传递
 * 
 * @author Max_Qiu
 */
public class DependencePass3 {

    public static void main(String[] args) {
        ChangHongTv changHong = new ChangHongTv();
        IOpenAndClose openAndClose = new OpenAndClose();
        openAndClose.setTv(changHong);
        openAndClose.open();
    }

}

interface IOpenAndClose {
    // 抽象方法
    void open();

    void setTv(ITv tv);
}

class OpenAndClose implements IOpenAndClose {
    private ITv tv;

    @Override
    public void setTv(ITv tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}
