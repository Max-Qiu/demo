package com.maxqiu.demo.P19_Interpreter;

import java.util.HashMap;

/**
 * 减法解释器
 * 
 * @author Max_Qiu
 */
public class SubExpression extends SymbolExpression {

    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * 处理相减
     * 
     * @param var
     * @return
     */
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }

}
