package com.maxqiu.demo.P19_Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) throws IOException {
        // a+b
        String expStr = getExpStr();
        // var {a=10, b=20}
        HashMap<String, Integer> var = getValue(expStr);
        // 创建一个计算器，并传入表达式
        Calculator calculator = new Calculator(expStr);
        // 传入值并计算结果
        System.out.println("运算结果：" + expStr + "=" + calculator.run(var));
    }

    /**
     * 获得表达式
     * 
     * PS：该代码与解释器模式无关
     */
    public static String getExpStr() throws IOException {
        System.out.print("请输入表达式：");
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }

    /**
     * 获得值映射
     *
     * PS：该代码与解释器模式无关
     */
    public static HashMap<String, Integer> getValue(String expStr) throws IOException {
        HashMap<String, Integer> map = new HashMap<>(8);
        for (char ch : expStr.toCharArray()) {
            if (ch != '+' && ch != '-') {
                if (!map.containsKey(String.valueOf(ch))) {
                    System.out.print("请输入" + ch + "的值：");
                    String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(ch), Integer.valueOf(in));
                }
            }
        }
        return map;
    }

}
