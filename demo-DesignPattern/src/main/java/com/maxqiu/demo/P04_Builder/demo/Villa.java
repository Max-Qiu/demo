package com.maxqiu.demo.P04_Builder.demo;

/**
 * 别墅
 * 
 * @author Max_Qiu
 */
public class Villa extends AbstractHouse {

    @Override
    public void buildBasic() {
        System.out.println(" 别墅打地基 ");
    }

    @Override
    public void buildWalls() {
        System.out.println(" 别墅砌墙 ");
    }

    @Override
    public void roofed() {
        System.out.println(" 别墅封顶 ");
    }

}
