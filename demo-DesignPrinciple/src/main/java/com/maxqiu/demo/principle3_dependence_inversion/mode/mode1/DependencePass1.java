package com.maxqiu.demo.principle3_dependence_inversion.mode.mode1;

import com.maxqiu.demo.principle3_dependence_inversion.mode.ChangHongTv;
import com.maxqiu.demo.principle3_dependence_inversion.mode.ITv;

/**
 * 方式1： 通过接口传递实现依赖
 * 
 * @author Max_Qiu
 */
public class DependencePass1 {

    public static void main(String[] args) {
        ChangHongTv changHong = new ChangHongTv();
        IOpenAndClose iOpenAndClose = new OpenAndClose();
        iOpenAndClose.open(changHong);
    }

}

// 开关的接口
interface IOpenAndClose {
    // 抽象方法,接收接口
    void open(ITv tv);
}

// 实现接口
class OpenAndClose implements IOpenAndClose {
    @Override
    public void open(ITv tv) {
        tv.play();
    }
}
