# 解释器模式 Interpreter Pattern

> 基本介绍

在编译原理中，一个算术表达式通过词法分析器形成词法单元，而后这些词法单元再通过语法分析器构建语法分析树，最终形成一颗抽象的语法分析树。这里的词法分析器和语法分析器都可以看做是解释器。解释器模式（Interpreter Pattern）：是指给定一个语言（表达式），定义它的文法的一种表示，并定义一个解释器，使用该解释器来解释语言中的句子（表达式）

> 应用场景

- 应用可以将一个需要解释执行的语言中的句子表示为一个抽象语法树
- 一些重复出现的问题可以用一种简单的语言来表达
- 一个简单语法需要解释的场景。这样的例子比如编译器、运算表达式计算、正则表达式、机器人等

> UML类图与角色

![](https://cdn.maxqiu.com/upload/8f0ab928d8784d44961443a4058e3857.jpg)

- Context：是环境角色，含有解释器之外的全局信息。
- AbstractExpression：抽象表达式，声明一个抽象的解释操作，这个方法为抽象语法树中所有的节点所共享
- TerminalExpression：为终结符表达式，实现与文法中的终结符相关的解释操作
- NonTerminalExpression：为非终结符表达式，为文法中的非终结符实现解释操作
- 说明：输入 Context 和 TerminalExpression 信息通过 Client 输入即可

# 示例

> 情景介绍

实现计算器的加减运算，如计算`a+b-c`的值

> UML类图

![](https://cdn.maxqiu.com/upload/d273c2c9a9bf44af99f714be97816508.jpg)

> 代码实现

PS：请忽略栈的使用，重点关注设计模式

```java
/**
 * 抽象类表达式，通过 HashMap 键值对, 可以获取到变量的值
 */
public abstract class Expression {
    /**
     * a + b - c
     * 解释公式和数值，key就是公式（表达式）参数[a,b,c]
     * value就是就是具体值 HashMap{a=10, b=20}
     * @param var
     * @return
     */
    public abstract int interpreter(HashMap<String, Integer> var);
}
```

```java
/**
 * 变量的解释器：即表示公式中的 a b
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
```

```java
/**
 * 抽象运算符号解析器
 * 这里，每个运算符号，都只和自己左右两个数字有关系，但左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression类的实现类
 */
public abstract class SymbolExpression extends Expression {
    protected Expression left;
    protected Expression right;
    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
```

```java
/**
 * 加法解释器
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
```

```java
/**
 * 减法解释器
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
```

```java
/**
 * 计算器类
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
```

```java
/**
 * 客户端
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
```

运行结果如下

    请输入表达式：a+a+b+c
    请输入a的值：10
    请输入b的值：10
    请输入c的值：20
    运算结果：a+a+b+c=50

# 总结

- 当有一个语言需要解释执行，可将该语言中的句子表示为一个抽象语法树，就可以考虑使用解释器模式，让程序具有良好的扩展性
- 应用场景：编译器、运算表达式计算、正则表达式、机器人等
- 使用解释器可能带来的问题：解释器模式会引起类膨胀、解释器模式采用递归调用方法，将会导致调试非常复杂、效率可能降低。
