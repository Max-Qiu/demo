package com.maxqiu.demo.P11_Proxy.Mode1_StaticProxy;

/**
 * 代理对象,静态代理
 * 
 * @author Max_Qiu
 */
public class TeacherDaoProxy implements ITeacherDao {

    /**
     * 目标对象，通过接口来聚合
     */
    private ITeacherDao target;

    /**
     * 构造器
     * 
     * @param target
     */
    public TeacherDaoProxy(ITeacherDao target) {
        this.target = target;
    }

    @Override
    public void teach() {
        System.out.println("静态代理开始");
        target.teach();
        System.out.println("静态代理结束");
    }

}
