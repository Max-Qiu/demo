package com.maxqiu.demo.P19_Interpreter;

import java.util.HashMap;
import java.util.Stack;

/**
 * 计算器类
 * 
 * @author Max_Qiu
 */
public class Calculator {

    /**
     * 定义表达式
     */
    private Expression expression;

    /**
     * 构造函数传参，并解析
     * 
     * @param expStr
     *            a+b
     */
    public Calculator(String expStr) {
        // 按照运算先后顺序
        Stack<Expression> stack = new Stack<>();
        // 表达式拆分成字符数组，即 [a , + , b]
        char[] charArray = expStr.toCharArray();
        Expression left;
        Expression right;
        // 遍历字符数组，即遍历[a , + , b]
        for (int i = 0; i < charArray.length; i++) {
            // 针对不同的情况，做处理
            switch (charArray[i]) {
                case '+':
                    // 从stack取出left => "a"
                    left = stack.pop();
                    // 取出右表达式 "b"，同时索引向右移动一格，即跳过右侧表达式
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    // 然后根据得到left 和 right 构建 AddExpression加入stack
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    // 同理
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    // 如果是一个 Var 就创建要给 VarExpression 对象，并push到stack
                    stack.push(new VarExpression(String.valueOf(charArray[i])));
                    break;
            }
        }
        // 当遍历完整个 charArray 数组后，stack就得到最后Expression
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        // 最后将表达式 a+b 和 var = {a=10,b=20} 传递给expression的interpreter进行解释执行
        return this.expression.interpreter(var);
    }

}