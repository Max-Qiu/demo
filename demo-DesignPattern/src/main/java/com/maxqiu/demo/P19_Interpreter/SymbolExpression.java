package com.maxqiu.demo.P19_Interpreter;

/**
 * 抽象运算符号解析器
 * 
 * 这里，每个运算符号，都只和自己左右两个数字有关系，但左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression类的实现类
 * 
 * @author Max_Qiu
 */
public abstract class SymbolExpression extends Expression {

    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

}
