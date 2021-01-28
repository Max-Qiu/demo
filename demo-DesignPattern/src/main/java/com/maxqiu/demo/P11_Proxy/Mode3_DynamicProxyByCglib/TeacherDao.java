package com.maxqiu.demo.P11_Proxy.Mode3_DynamicProxyByCglib;

/**
 * 被代理对象
 * 
 * @author Max_Qiu
 */
public class TeacherDao {

    public void teach() {
        System.out.println("老师授课中。。。。。我是cglib代理，不需要实现接口");
    }

}
