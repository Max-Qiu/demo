package com.maxqiu.demo.principle4_liskov_substitution.demo;

/**
 * @author Max_Qiu
 */
public class LiskovSubstitution {

    public static void main(String[] args) {
        ClassA classA = new ClassA();
        System.out.println("11-3=" + classA.func1(11, 3)); // 11-3=8
        System.out.println("-----------------------");
        ClassB classB = new ClassB();
        // 这里本意是求出11-3，但是结果却是14
        System.out.println("11-3=" + classB.func1(11, 3)); // 11-3=14
        System.out.println("11-3+9=" + classB.func2(11, 3)); // 11-3+9=23
    }

}

// A类
class ClassA {
    // 返回两个数的差
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

// B类继承了A
class ClassB extends ClassA {
    // 这里，重写了A类的方法, 可能是无意识
    @Override
    public int func1(int a, int b) {
        return a + b;
    }

    // 增加了一个新功能：完成两个数相减,然后和9求和
    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }
}
