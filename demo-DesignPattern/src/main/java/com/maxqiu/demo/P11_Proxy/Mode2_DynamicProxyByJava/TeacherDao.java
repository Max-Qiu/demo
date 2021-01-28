package com.maxqiu.demo.P11_Proxy.Mode2_DynamicProxyByJava;

/**
 * 被代理对象
 * 
 * @author Max_Qiu
 */
public class TeacherDao implements ITeacherDao {

    @Override
    public void teach() {
        System.out.println("老师授课中。。。。。");
    }

}
