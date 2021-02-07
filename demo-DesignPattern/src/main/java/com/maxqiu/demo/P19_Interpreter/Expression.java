package com.maxqiu.demo.P19_Interpreter;

import java.util.HashMap;

/**
 * 抽象类表达式，通过 HashMap 键值对, 可以获取到变量的值
 * 
 * @author Max_Qiu
 */
public abstract class Expression {

    /**
     * a + b - c
     * 
     * 解释公式和数值，key就是公式（表达式）参数[a,b,c]
     * 
     * value就是就是具体值 HashMap{a=10, b=20}
     * 
     * @param var
     * @return
     */
    public abstract int interpreter(HashMap<String, Integer> var);

}
