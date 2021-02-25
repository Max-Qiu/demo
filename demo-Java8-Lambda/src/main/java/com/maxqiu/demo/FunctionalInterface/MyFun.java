package com.maxqiu.demo.FunctionalInterface;

/**
 * 带泛型的函数式接口
 * 
 * @param <T>
 * @author Max_Qiu
 */
@FunctionalInterface
public interface MyFun<T> {

    boolean test(T t);

}
