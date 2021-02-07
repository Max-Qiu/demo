package com.maxqiu.demo.P19_Interpreter;

import java.util.HashMap;

/**
 * 变量的解释器：即表示公式中的 a b
 * 
 * @author Max_Qiu
 */
public class VarExpression extends Expression {

    // key=a,key=b,key=c
    private String key;

    public VarExpression(String key) {
        this.key = key;
    }

    /**
     * var 就是{a=10, b=20}，interpreter 根据变量名称，返回对应值
     */
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(this.key);
    }

}
