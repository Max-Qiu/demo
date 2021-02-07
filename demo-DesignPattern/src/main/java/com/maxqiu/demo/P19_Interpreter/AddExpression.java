package com.maxqiu.demo.P19_Interpreter;

import java.util.HashMap;

/**
 * 加法解释器
 *
 * @author Max_Qiu
 */
public class AddExpression extends SymbolExpression {

    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * 处理相加
     * 
     * @param var
     *            {a=10,b=20}
     * @return
     */
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        // super.left.interpreter(var):返回 left 表达式对应的值 a = 10
        // super.right.interpreter(var):返回 right 表达式对应值 b = 20
        return super.left.interpreter(var) + super.right.interpreter(var);
    }

}
