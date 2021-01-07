package com.maxqiu.demo.principle4_liskov_substitution.improve;

/**
 * @author Max_Qiu
 */
public class LiskovSubstitution {

    public static void main(String[] args) {
        ClassA classA = new ClassA();
        System.out.println("11-3=" + classA.func1(11, 3)); // 11-3=8
        System.out.println("-----------------------");
        ClassB classB = new ClassB();
        // 因为B类不再继承A类，所以调用者不会再认为func1是求减法，调用的功能就会很明确
        System.out.println("11+3=" + classB.func1(11, 3)); // 11+3=14
        System.out.println("11+3+9=" + classB.func2(11, 3)); // 11+3+9=23
        // 使用组合仍然可以使用到A类相关方法
        System.out.println("11-3=" + classB.func3(11, 3)); // 11-3=8
    }

}

// 创建一个更加基础的基类
class Base {
    // 把更加基础的方法和成员写到Base类
}

// A类
class ClassA extends Base {
    // 返回两个数的差
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

// B类继承了A
class ClassB extends Base {
    // 如果B需要使用A类的方法,使用组合关系
    private ClassA classA = new ClassA();

    public int func1(int a, int b) {
        return a + b;
    }

    // 增加了一个新功能：完成两个数相加,然后和9求和
    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }

    // 使用A的方法
    public int func3(int a, int b) {
        return this.classA.func1(a, b);
    }
}
