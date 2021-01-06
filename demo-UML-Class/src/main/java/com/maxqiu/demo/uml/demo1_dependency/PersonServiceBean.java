package com.maxqiu.demo.uml.demo1_dependency;

/**
 * @author Max_Qiu
 */
public class PersonServiceBean {
    /**
     * 类中使用到了对方
     */
    public void modify() {
        Department department = new Department();
    }

    /**
     * 类的成员属性
     */
    private PersonDao personDao;

    /**
     * 方法的返回类型
     */
    public IdCard getIdCard(Integer personId) {
        return null;
    }

    /**
     * 方法接收的参数类型
     */
    public void save(Person person) {}
}
