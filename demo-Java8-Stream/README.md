> 本文档整理自视频教程：[尚硅谷_Java8新特性](http://www.atguigu.com/download_detail.shtml?v=6)

# 基本介绍

`Stream`是Java8中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。使用`Stream API`对集合数据进行操作，就类似于使用`SQL`执行的数据库查询。也可以使用`Stream API`来并行执行操作。简而言之，`Stream API`提供了一种高效且易于使用的处理数据的方式。

> 注意事项：

- Stream 自己不会存储元素。
- Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
- Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

# 使用步骤

1. 创建Stream：一个数据源（如：集合、数组），获取一个流
2. 中间操作：一个中间操作链，对数据源的数据进行处理
3. 终止操作（终端操作）：一个终止操作，执行中间操作链，并产生结果

# 示例

## 创建Stream

> `stream.forEach()`、`stream.limit()`为终止操作和中间操作

### Collection 接口

```java
/**
 * Collection 提供了两个方法：stream() 与 parallelStream()
 */
@Test
public void test1() {
    // 创建一个list集合
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");
    list.add("c");
    list.add("d");
    list.add("e");

    // 获取一个顺序流（操作时，顺序执行）
    Stream<String> stream = list.stream();
    stream.forEach(System.out::println);
    System.out.println("==========================");
    // 获取一个并行流（操作时，并发执行）
    Stream<String> parallelStream = list.parallelStream();
    parallelStream.forEach(System.out::println);
}
```

    a
    b
    c
    d
    e
    ==========================
    c
    d
    e
    b
    a

### Arrays.stream()

```java
/**
 * 通过 Arrays 中的 stream() 获取一个数组流
 */
@Test
public void test2() {
    Integer[] nums = new Integer[] {1, 2, 3};
    Stream<Integer> stream = Arrays.stream(nums);
    stream.forEach(System.out::println);
}
```

### Stream.of()

```java
/**
 * 通过 Stream 类中静态方法 of()
 */
@Test
public void test3() {
    Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
    stream.forEach(System.out::println);
}
```

### Stream.iterate() / Stream.generate()

```java
/**
 * 创建无限流
 */
@Test
public void test4() {
    // 迭代
    Stream<Integer> stream1 = Stream.iterate(0, x -> x + 2).limit(5);
    stream1.forEach(System.out::println);
    System.out.println("===============================");
    // 生成
    Stream<Double> stream2 = Stream.generate(Math::random).limit(5);
    stream2.forEach(System.out::println);
}
```

    0
    2
    4
    6
    8
    ===============================
    0.9579180226604598
    0.9468086357038668
    0.3845215232302417
    0.4925416789453949
    0.6427715547510369

## 中间操作

多个**中间操作**可以连接起来形成一个流水线，除非流水线上触发终止操作，否则 中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。


```java
// 以下示例中，使用该list
List<Employee> employeeList;
{
    employeeList = new ArrayList<>();
    employeeList.add(new Employee(102, "李四", 59, 6666.66));
    employeeList.add(new Employee(101, "张三", 18, 9999.99));
    employeeList.add(new Employee(103, "王五", 18, 3333.33));
    employeeList.add(new Employee(104, "赵六", 8, 7777.77));
    employeeList.add(new Employee(105, "田七", 38, 5555.55));
    employeeList.add(new Employee(105, "田七", 38, 5555.55));
}
```

### 筛选与切片

```
/**
 * filter：接收Lambda，从流中排除某些元素。
 */
@Test
public void test1() {
    // 所有的中间操作不会做任何的处理
    Stream<Employee> stream = employeeList.stream().filter((e) -> {
        System.out.println("中间操作");
        return e.getAge() <= 35;
    });
    // 只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
    stream.forEach(System.out::println);
}
```

    中间操作
    中间操作
    Employee(id=101, name=张三, age=18, salary=9999.99, status=null)
    中间操作
    Employee(id=103, name=王五, age=18, salary=3333.33, status=null)
    中间操作
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=null)
    中间操作
    中间操作

---

```java
/**
 * limit：截断流，使其元素不超过给定数量。
 */
@Test
public void test2() {
    employeeList.stream().limit(3).forEach(System.out::println);
    System.out.println("====================================");
    // filter()和limit()是与的关系
    employeeList.stream().filter((e) -> {
        System.out.println("短路！");
        return e.getSalary() >= 5000;
    }).limit(3).forEach(System.out::println);
}
```

    Employee(id=102, name=李四, age=59, salary=6666.66, status=null)
    Employee(id=101, name=张三, age=18, salary=9999.99, status=null)
    Employee(id=103, name=王五, age=18, salary=3333.33, status=null)
    ====================================
    短路！
    Employee(id=102, name=李四, age=59, salary=6666.66, status=null)
    短路！
    Employee(id=101, name=张三, age=18, salary=9999.99, status=null)
    短路！
    短路！
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=null)

---

```java
/**
 * skip(n)：跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
 */
@Test
public void test3() {
    employeeList.stream().filter((e) -> e.getSalary() >= 5000).skip(2).forEach(System.out::println);
}
```

    Employee(id=104, name=赵六, age=8, salary=7777.77, status=null)
    Employee(id=105, name=田七, age=38, salary=5555.55, status=null)
    Employee(id=105, name=田七, age=38, salary=5555.55, status=null)

---

```java
/**
 * distinct：筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
 */
@Test
public void test4() {
    // 注意：备操作的对象要重写 hashCode() 和 equals() 方法
    employeeList.stream().distinct().forEach(System.out::println);
}
```

    Employee(id=102, name=李四, age=59, salary=6666.66, status=null)
    Employee(id=101, name=张三, age=18, salary=9999.99, status=null)
    Employee(id=103, name=王五, age=18, salary=3333.33, status=null)
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=null)
    Employee(id=105, name=田七, age=38, salary=5555.55, status=null)

### 映射

```java
/**
 * map：接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
 *
 * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
 */
@Test
public void test() {
    // 提取名称集合
    Stream<String> str = employeeList.stream().map(Employee::getName);
    str.forEach(System.out::println);

    System.out.println("=============================================");

    List<String> strList = Arrays.asList("aa", "bb", "cc", "dd", "ee");

    // 转换为大写
    Stream<String> stream = strList.stream().map(String::toUpperCase);
    stream.forEach(System.out::println);

    System.out.println("---------------------------------------------");

    Stream<Stream<Character>> stream2 = strList.stream().map(MiddleOperationOfMapTest::filterCharacter);
    stream2.forEach((sm) -> sm.forEach(System.out::println));

    System.out.println("---------------------------------------------");

    Stream<Character> stream3 = strList.stream().flatMap(MiddleOperationOfMapTest::filterCharacter);
    stream3.forEach(System.out::println);
}

public static Stream<Character> filterCharacter(String str) {
    List<Character> list = new ArrayList<>();
    for (Character ch : str.toCharArray()) {
        list.add(ch);
    }
    return list.stream();
}
```

    李四
    张三
    王五
    赵六
    田七
    田七
    =============================================
    AA
    BB
    CC
    DD
    EE
    ---------------------------------------------
    a
    a
    b
    b
    c
    c
    d
    d
    e
    e
    ---------------------------------------------
    a
    a
    b
    b
    c
    c
    d
    d
    e
    e

### 排序

```java
/**
 * sorted()：自然排序
 *
 * sorted(Comparator com)：定制排序
 */
@Test
public void test() {
    // 取出年龄，并自如排序，然后打印
    employeeList.stream().map(Employee::getAge).sorted().forEach(System.out::println);

    System.out.println("------------------------------------");

    // 按照年龄排序，如果年龄相同，则按工资排序
    employeeList.stream().sorted((x, y) -> {
        if (x.getAge().intValue() == y.getAge().intValue()) {
            return Double.compare(x.getSalary(), y.getSalary());
        } else {
            return Integer.compare(x.getAge(), y.getAge());
        }
    }).forEach(System.out::println);
}
```

    8
    18
    18
    38
    38
    59
    ------------------------------------
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=null)
    Employee(id=103, name=王五, age=18, salary=3333.33, status=null)
    Employee(id=101, name=张三, age=18, salary=9999.99, status=null)
    Employee(id=105, name=田七, age=38, salary=5555.55, status=null)
    Employee(id=105, name=田七, age=38, salary=5555.55, status=null)
    Employee(id=102, name=李四, age=59, salary=6666.66, status=null)

## 终止操作

终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是void。

```java
// 以下示例中，使用该list
List<Employee> employeeList;
{
    employeeList = new ArrayList<>();
    employeeList.add(new Employee(102, "李四", 59, 6666.66, Status.BUSY));
    employeeList.add(new Employee(101, "张三", 18, 9999.99, Status.FREE));
    employeeList.add(new Employee(103, "王五", 28, 3333.33, Status.VOCATION));
    employeeList.add(new Employee(104, "赵六", 8, 7777.77, Status.BUSY));
    employeeList.add(new Employee(105, "田七", 38, 5555.55, Status.BUSY));
}
```

### 查找与匹配

```java
/**
 * allMatch：检查是否匹配所有元素
 *
 * anyMatch：检查是否至少匹配一个元素
 *
 * noneMatch：检查是否没有匹配的元素
 */
@Test
public void test1() {
    boolean allMatch = employeeList.stream().allMatch((e) -> e.getStatus().equals(Status.BUSY));
    System.out.println(allMatch);

    boolean anyMatch = employeeList.stream().anyMatch((e) -> e.getStatus().equals(Status.BUSY));
    System.out.println(anyMatch);

    boolean noneMatch = employeeList.stream().noneMatch((e) -> e.getStatus().equals(Status.BUSY));
    System.out.println(noneMatch);
}
```

    false
    true
    false

---

```java
/**
 * findFirst：返回第一个元素
 *
 * findAny：返回当前流中的任意元素
 */
@Test
public void test2() {
    Optional<Employee> op1 = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY)).findFirst();
    System.out.println(op1.get());

    System.out.println("--------------------------------");

    Optional<Employee> op2 =
        employeeList.parallelStream().filter((e) -> e.getStatus().equals(Status.BUSY)).findAny();
    System.out.println(op2.get());
}
```

    Employee(id=102, name=李四, age=59, salary=6666.66, status=BUSY)
    --------------------------------
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=BUSY)

---

```java
/**
 * count：返回流中元素的总个数
 *
 * max：返回流中最大值
 *
 * min：返回流中最小值
 */
@Test
public void test3() {
    long count = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY)).count();
    System.out.println(count);

    Optional<Double> op = employeeList.stream().map(Employee::getSalary).max(Double::compare);
    System.out.println(op.get());

    Optional<Employee> op2 = employeeList.stream().min(Comparator.comparingDouble(Employee::getSalary));
    System.out.println(op2.get());
}
```

    3
    9999.99
    Employee(id=103, name=王五, age=28, salary=3333.33, status=VOCATION)

---

```java
/**
 * 注意：流进行了终止操作后，不能再次使用
 */
@Test
public void test4() {
    Stream<Employee> stream = employeeList.stream().filter((e) -> e.getStatus().equals(Status.BUSY));
    System.out.println(stream.count());
    // 此处抛出异常
    Optional<Double> max = stream.map(Employee::getSalary).max(Double::compare);
    System.out.println(max);
}
```

    3

    java.lang.IllegalStateException: stream has already been operated upon or closed

    	at java.util.stream.AbstractPipeline.<init>(AbstractPipeline.java:203)

### 规约

```java
/**
 * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)：可以将流中元素反复结合起来，得到一个值。
 */
@Test
public void test1() {
    // 求和
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Integer sum = list.stream().reduce(0, Integer::sum);
    System.out.println(sum);

    System.out.println("----------------------------------------");

    // 求总工资
    Optional<Double> op = employeeList.stream().map(Employee::getSalary).reduce(Double::sum);
    System.out.println(op.get());
}
```

    55
    ----------------------------------------
    33333.3

---

```java
/**
 * 需求：搜索名字中 “六” 出现的次数
 */
@Test
public void test2() {
    Optional<Integer> sum = employeeList.stream().map(Employee::getName)
        .flatMap(MiddleOperationOfMapTest::filterCharacter).map((ch) -> {
            if (ch.equals('六')) {
                return 1;
            } else {
                return 0;
            }
        }).reduce(Integer::sum);
    System.out.println(sum.get());
}
```

    1

### 收集

> collect：将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法

```java
/**
 * 转换
 */
@Test
public void test1() {
    // 收集到List
    List<String> list = employeeList.stream().map(Employee::getName).collect(Collectors.toList());
    list.forEach(System.out::println);

    System.out.println("----------------------------------");

    // 收集到Set
    Set<String> set = employeeList.stream().map(Employee::getName).collect(Collectors.toSet());
    set.forEach(System.out::println);

    System.out.println("----------------------------------");

    // 收集到自定义的Collection
    HashSet<String> hs =
        employeeList.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
    hs.forEach(System.out::println);
}
```

---

```java
/**
 * 统计
 */
@Test
public void test2() {
    // 求工资的平均值
    Double avg = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
    System.out.println(avg);

    System.out.println("--------------------------------------------");

    // 求工资的多种统计值
    DoubleSummaryStatistics dss = employeeList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
    System.out.println(dss.getMax());
    System.out.println(dss.getCount());
}
```

    6666.660000000001
    --------------------------------------------
    9999.99
    5

---

```java
/**
 * 分组
 */
@Test
public void test3() {
    Map<Status, List<Employee>> map = employeeList.stream().collect(Collectors.groupingBy(Employee::getStatus));
    System.out.println(map);
}
```

    {
        FREE=[
        Employee(id=101, name=张三, age=18, salary=9999.99, status=FREE)
        ],
        BUSY=[
        Employee(id=102, name=李四, age=59, salary=6666.66, status=BUSY),
        Employee(id=104, name=赵六, age=8, salary=7777.77, status=BUSY),
        Employee(id=105, name=田七, age=38, salary=5555.55, status=BUSY)
        ],
        VOCATION=[
        Employee(id=103, name=王五, age=28, salary=3333.33, status=VOCATION)
        ]
    }

---

```java
/**
 * 多级分组
 */
@Test
public void test4() {
    Map<Status, Map<String, List<Employee>>> map =
        employeeList.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
            if (e.getAge() >= 60) {
                return "老年";
            } else if (e.getAge() >= 35) {
                return "中年";
            } else {
                return "成年";
            }
        })));
    System.out.println(map);
}
```

    {
    FREE={
    成年=[Employee(id=101, name=张三, age=18, salary=9999.99, status=FREE)]
    },
    BUSY={
    成年=[Employee(id=104, name=赵六, age=8, salary=7777.77, status=BUSY)],
    中年=[Employee(id=102, name=李四, age=59, salary=6666.66, status=BUSY),
    Employee(id=105, name=田七, age=38, salary=5555.55, status=BUSY)
    ]
    },
    VOCATION={
    成年=[Employee(id=103, name=王五, age=28, salary=3333.33, status=VOCATION)]}
    }

---

```java
/**
 * 分区
 */
@Test
public void test5() {
    Map<Boolean, List<Employee>> map =
        employeeList.stream().collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
    System.out.println(map);
}
```

    {
    false=[
    Employee(id=103, name=王五, age=28, salary=3333.33, status=VOCATION)
    ],
    true=[
    Employee(id=102, name=李四, age=59, salary=6666.66, status=BUSY),
    Employee(id=101, name=张三, age=18, salary=9999.99, status=FREE),
    Employee(id=104, name=赵六, age=8, salary=7777.77, status=BUSY),
    Employee(id=105, name=田七, age=38, salary=5555.55, status=BUSY)
    ]
    }

---

```java
/**
 * 拼接
 */
@Test
public void test6() {
    String str = employeeList.stream().map(Employee::getName).collect(Collectors.joining(",", "----", "----"));
    System.out.println(str);
}
```

    ----李四,张三,王五,赵六,田七----
