> 本文档整理自视频教程：[尚硅谷_Java8新特性](http://www.atguigu.com/download_detail.shtml?v=6)

# 函数式接口

## 简介

- 只包含**一个**抽象方法的接口，称`为函数式接口`。
- 在任意函数式接口上使用`@FunctionalInterface`注解，这样做可以检查它是否是一个函数式接口，同时`javadoc`也会包含一条声明，说明这个接口是一个函数式接口。
- 可以通过`Lambda表达式`来创建该接口的对象。（若`Lambda表达式`抛出一个受检异常，那么该异常需要在目标接口的抽象方法上进行声明）。

## 示例

> 示例1：一般

```java
@FunctionalInterface
public interface MyNumber {

    Integer getValue(Integer num);

}
```

> 示例2：使用泛型

```java
@FunctionalInterface
public interface MyFun<T> {

    boolean test(T t);

}
```

## Java内置四大核心函数式接口

名称 | 类 | 参数类型 | 返回类型 | 用途
---|---|---|---|---
消费型接口 | Consumer<T> | T | void | 对类型为T的对象应用操作</br>void accept(T t)
供给型接口 | Supplier<T> | 无 | T | 返回类型为T的对象</br>T get();
函数型接口 | Function<T, R> | T | R | 对类型为T的对象应用操作，并返回结果。结果是R类型的对象</br>R apply(T t);
断定型接口 | Predicate<T> | T | boolean | 确定类型为T的对象是否满足某约束，并返回boolean值</br>boolean test(T t);

其他的还有`BiFunction<T, U, R>`、`UnaryOperator <T>`、`BinaryOperator<T>`、`BiConsumer<T, U>`等

> 具体使用示例见下文

# Lambda表达式

## 简介

`Lambda表达式`是一个**匿名函数**，可以把`Lambda表达式`理解为是一段可以传递的代码（将代码像数据一样进行传递）。

`Lambda表达式`需要`函数式接口`的支持。

## 语法

`Lambda表达式`在Java语言中引入了一个新的语法元素和操作符。这个操作符为`->`，该操作符被称为`Lambda操作符`或`剪头操作符`。

`() -> {}`

它将 Lambda 分为两个部分：

- 左侧：指定了`Lambda表达式`需要的所有参数
- 右侧：指定了方法体，即`Lambda表达式`要执行的功能。

### 无参无返回值

```java
// 传统方法
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("1");
    }
}).start();

// lambda语法
new Thread(() -> {
    System.out.println("2");
}).start();

// 单行方法时，不需要大括号
new Thread(() -> System.out.println("3")).start();

// 和匿名内部类一样，可以使用外部变量
int a = 1;
new Thread(() -> System.out.println(a)).start();
```

### 有参无返回值

#### 有一个参

```java
// 传统方法
Consumer<String> consumer1 = new Consumer<String>() {
    @Override
    public void accept(String s) {
        System.out.println(s);
    }
};
consumer1.accept("1");

// lambda语法
Consumer<String> consumer2 = (String x) -> System.out.println(x);
consumer2.accept("2");

// 参数的类型可以不写，因为JVM编译器通过上下文推断出数据类型，即“类型推断”
Consumer<String> consumer3 = (x) -> System.out.println(x);
consumer3.accept("2");

// 单个参数时，不需要小括号
Consumer<String> consumer4 = x -> System.out.println(x);
consumer4.accept("3");
```
#### 有多个参

```java
// 传统方法
BiConsumer<Integer, Integer> consumer1 = new BiConsumer<Integer, Integer>() {
    @Override
    public void accept(Integer a, Integer b) {
        System.out.println(a + b);
    }
};
consumer1.accept(1, 2);

// lambda语法
BiConsumer<Integer, Integer> consumer2 = (a, b) -> System.out.println(a + b);
consumer2.accept(1, 2);
```

### 无参有返回值、有参有返回值

```java
// 传统方法
Supplier<Integer> supplier1 = new Supplier<Integer>() {
    @Override
    public Integer get() {
        System.out.println("哈哈");
        return 1;
    }
};
System.out.println(supplier1.get());

// lambda语法
Supplier<Integer> supplier2 = () -> {
    System.out.println("哈哈");
    return 1;
};
System.out.println(supplier2.get());

// 单行方法时，不需要return和大括号
Supplier<Integer> supplier3 = () -> 1;
System.out.println(supplier3.get());
```

> PS：**有参有返回值** 同 **有参无返回值** 示例

## 四大函数式接口使用示例

```java
@Test
public void test1() {
    happy(10000, (m) -> System.out.println("传入的参数是：" + m));
}

/**
 * Consumer<T> 消费型接口
 */
public void happy(int v, Consumer<Integer> con) {
    con.accept(v);
}
```

```java
/**
 * 需求：产生指定个数的整数，并放入集合中
 */
@Test
public void test2() {
    // 100随机
    List<Integer> numList = getNumList(5, () -> (int)(Math.random() * 100));
    for (Integer num : numList) {
        System.out.println(num);
    }
    System.out.println("==============");
    // 1000随机
    List<Integer> numList2 = getNumList(5, () -> (int)(Math.random() * 1000));
    for (Integer num : numList2) {
        System.out.println(num);
    }
}

/**
 * Supplier<T> 供给型接口
 */
public List<Integer> getNumList(int num, Supplier<Integer> sup) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
        Integer n = sup.get();
        list.add(n);
    }
    return list;
}
```

```java
/**
 * 使用不同的方法处理字符串
 */
@Test
public void test3() {
    String newStr = strHandler("\t\t\t aaa   ", (str) -> str.trim());
    System.out.println(newStr);
    String subStr = strHandler("1234567890", (str) -> str.substring(2, 5));
    System.out.println(subStr);
}

/**
 * Function<T, R> 函数型接口
 */
public String strHandler(String str, Function<String, String> fun) {
    return fun.apply(str);
}
```

```java
/**
 * 需求：使用不同方式将满足条件的字符串放入集合中
 */
@Test
public void test4() {
    List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "w", "ok");
    List<String> strList = filterStr(list, (s) -> s.length() > 5);
    for (String str : strList) {
        System.out.println(str);
    }
    System.out.println("==============");
    List<String> strList2 = filterStr(list, (s) -> s.length() < 2);
    for (String str : strList2) {
        System.out.println(str);
    }
}

/**
 * Predicate<T> 断言型接口
 */
public List<String> filterStr(List<String> list, Predicate<String> pre) {
    List<String> strList = new ArrayList<>();
    for (String str : list) {
        if (pre.test(str)) {
            strList.add(str);
        }
    }
    return strList;
}
```

# 方法引用与构造器引用

## 方法引用

当要传递给`Lambda体`的操作，已经有实现的方法了，可以使用方法引用！（实现抽象方法的参数列表，必须与方法引用方法的参数列表保持一致！）

方法引用使用操作符`::`将方法名和对象或类的名字分隔开来（可以将方法引用理解为 `Lambda表达式`的另外一种表现形式）。三种主要使用情况如下：

- 对象 :: 实例方法
- 类 :: 静态方法
- 类 :: 实例方法

> 示例：

实体：

```
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Double salary;
    public Employee(String name) {
        this.name = name;
    }
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String show() {
        return "测试方法引用！";
    }
}
```

使用：

```java
/**
 * 对象 :: 实例方法
 */
@Test
public void test1() {
    Employee emp = new Employee(1, "张三", 18, 9000.00);

    Supplier<String> sup = () -> emp.getName();
    System.out.println(sup.get());

    System.out.println("-------------------------------------");

    Supplier<String> sup2 = emp::getName;
    System.out.println(sup2.get());

    System.out.println("=====================================");

    PrintStream ps = System.out;
    Consumer<String> con = (str) -> ps.println(str);
    con.accept("Hello World！");

    System.out.println("-------------------------------------");

    Consumer<String> con2 = ps::println;
    con2.accept("Hello Java8！");

    System.out.println("-------------------------------------");

    Consumer<String> con3 = System.out::println;
    con3.accept("Hello Lambda");
}
```

```java
/**
 * 类 :: 静态方法
 */
@Test
public void test2() {
    Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
    System.out.println(com1.compare(1, 2));

    System.out.println("-------------------------------------");

    Comparator<Integer> com2 = Integer::compare;
    System.out.println(com2.compare(1, 2));

    System.out.println("=====================================");

    BiFunction<Double, Double, Double> fun1 = (x, y) -> Math.max(x, y);
    System.out.println(fun1.apply(1.2, 1.5));

    System.out.println("-------------------------------------");

    BiFunction<Double, Double, Double> fun2 = Math::max;
    System.out.println(fun2.apply(1.2, 1.5));
}
```

```java
/**
 * 类 :: 实例方法
 */
@Test
public void test3() {
    BiPredicate<String, String> bp = (x, y) -> x.equals(y);
    System.out.println(bp.test("abcde", "abcde"));

    System.out.println("-------------------------------------");

    BiPredicate<String, String> bp2 = String::equals;
    System.out.println(bp2.test("abc", "abc"));

    System.out.println("=====================================");

    Function<Employee, String> fun = (e) -> e.show();
    System.out.println(fun.apply(new Employee()));

    System.out.println("-------------------------------------");

    Function<Employee, String> fun2 = Employee::show;
    System.out.println(fun2.apply(new Employee()));
}
```

> 注意

- 方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
- 若Lambda的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式：`ClassName::methodName`

## 构造器引用

格式：`ClassName :: new`

与函数式接口相结合，自动与函数式接口中方法兼容。可以把构造器引用赋值给定义的方法。构造器的参数列表要与函数式接口中抽象方法的参数列表一致！

> 示例

实体同上

```java
/**
 * 无参构造
 */
@Test
public void test1() {
    Supplier<Employee> sup1 = () -> new Employee();
    System.out.println(sup1.get());

    System.out.println("------------------------------------");

    Supplier<Employee> sup2 = Employee::new;
    System.out.println(sup2.get());
}
```

```java
/**
 * 有参构造
 */
@Test
public void test2() {
    Function<String, Employee> fun1 = s -> new Employee(s);
    System.out.println(fun1.apply("aaa"));

    System.out.println("------------------------------------");

    Function<String, Employee> fun = Employee::new;
    System.out.println(fun.apply("aaa"));

    System.out.println("=====================================");

    BiFunction<String, Integer, Employee> biFun1 = (name, age) -> new Employee(name, age);
    System.out.println(biFun1.apply("bbb", 18));

    System.out.println("------------------------------------");

    BiFunction<String, Integer, Employee> biFun2 = Employee::new;
    System.out.println(biFun2.apply("bbb", 18));
}
```
