# 情景介绍

学校OA系统的采购审批项目：采购员采购教学器材

1. 如果金额小于等于 5000，由教学主任审批（0 <= x <= 5000）
2. 如果金额小于等于 50000，由院长审批（5000 < x <= 50000）
4. 如果金额大于 50000，由校长审批（50000 < x ）

请设计程序完成采购审批项目

## 传统方案

PS：代码略

1. 传统方式是：接收到一个采购请求后，根据采购金额来调用对应的审批人完成审批。
2. 传统方式的问题分析：客户端这里会使用到分支判断（比如switch）来对不同的采购请求处理，这样就存在如下问题
   - 如果各个级别的人员审批金额发生变化，在客户端的也需要变化
   - 客户端必须明确的知道有多少个审批级别和访问
   - 这样对一个采购请求进行处理和审批人就存在强耦合关系，不利于代码的扩展和维护

解决方案 -> 职责链模式

# 职责链模式 Chain of Responsibility Pattern

> 基本介绍

职责链模式又叫责任链模式，为请求创建了一个接收者对象的链（简单示意图）。这种模式对请求的发送者和接收者进行解耦。职责链模式通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。

> UML类图与角色

![](https://cdn.maxqiu.com/upload/1a80b11ea82c4d408b48e70c05d956a8.jpg)

- Handler：抽象的处理者，定义了一个处理请求的接口，同时包含另外的 Handler
- ConcreteHandler：是具体的处理者，处理它自己负责的请求，可以访问它的后继者（即下一个处理者）。如果可以处理当前请求，则处理，否则就将该请求交给后继者去处理，从而形成一个职责链
- Request：包含很多属性，表示一个请求

## 改进方案

> UML类图

![](https://cdn.maxqiu.com/upload/534767f3f7bf497196a84c11807ee98a.jpg)

> 代码实现

```java
/**
 * 抽象处理者
 */
public abstract class Approver {
    /**
     * 下一个处理者
     */
    protected Approver approver;
    /**
     * 处理人名称
     */
    protected String name;
    public Approver(String name) {
        this.name = name;
    }
    /**
     * 设置下一个处理者
     */
    public void setApprover(Approver approver) {
        this.approver = approver;
    }
    /**
     * 处理审批请求的方法，得到一个请求, 处理是具体子类完成，因此该方法做成抽象
     *
     * @param purchaseRequest
     */
    public abstract void processRequest(PurchaseRequest purchaseRequest);
}
```

```java
/**
 * 具体的批准人（主任）
 */
public class DepartmentApprover extends Approver {
    public DepartmentApprover(String name) {
        super(name);
    }
    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() <= 5000) {
            System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
        } else {
            approver.processRequest(purchaseRequest);
        }
    }
}
```

```java
/**
 * 具体的批准人（院长）
 */
public class CollegeApprover extends Approver {
    public CollegeApprover(String name) {
        super(name);
    }
    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() > 5000 && purchaseRequest.getPrice() <= 50000) {
            System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
        } else {
            approver.processRequest(purchaseRequest);
        }
    }
}
```

```java
/**
 * 具体的批准人（校长）
 */
public class SchoolMasterApprover extends Approver {
    public SchoolMasterApprover(String name) {
        super(name);
    }
    @Override
    public void processRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPrice() > 50000) {
            System.out.println(" 请求编号 id= " + purchaseRequest.getId() + " 被 " + this.name + " 处理");
        } else {
            approver.processRequest(purchaseRequest);
        }
    }
}
```

```java
/**
 * 请求
 */
@Getter
@AllArgsConstructor
public class PurchaseRequest {
    /**
     * 请求ID
     */
    private int id;
    /**
     * 请求金额
     */
    private float price;
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建审批人
        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("佟校长");
        // 需要将各个审批级别的下一个设置好
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(schoolMasterApprover);
        // 处理人构成环形
        schoolMasterApprover.setApprover(departmentApprover);
        // 创建一个请求
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 1000);
        // 张主任去处理请求
        departmentApprover.processRequest(purchaseRequest);
        // 李院长去处理请求（最终依旧被张主任处理）
        collegeApprover.processRequest(purchaseRequest);
    }
}
```

输出如下：

    请求编号 id= 1 被 张主任 处理
    请求编号 id= 1 被 张主任 处理

# 注意事项

1. 将请求和处理分开，实现解耦，提高系统的灵活性
2. 简化了对象，使对象不需要知道链的结构
3. 性能会受到影响，特别是在链比较长的时候，因此需控制链中最大节点数量，一般通过在`Handler`中设置一个最大节点数量，在`setNext()`方法中判断是否已经超过阀值，超过则不允许该链建立，避免出现超长链无意识地破坏系统性能
4. 调试不方便。采用了类似递归的方式，调试时逻辑可能比较复杂
5. 最佳应用场景：有多个对象可以处理同一个请求时，比如：
    - 多级请求
    - 请假/加薪等审批流程
    - Java Web 中 Tomcat 对 Encoding 的处理
    - 拦截器
