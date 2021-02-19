# 状态模式 State Pattern

> 基本介绍

主要用来解决对象在多种状态转换时，需要对外输出不同的行为的问题。状态和行为是一一对应的，状态之间可以相互转换。当一个对象的内在状态改变时，允许改变其行为，这个对象看起来像是改变了其类

> UML类图与角色

![](https://cdn.maxqiu.com/upload/8dd4ce06ed3548c6b2e49e7847ac42f3.jpg)

- Context：环境角色，用于维护 State 实例，这个实例定义当前状态
- State：抽象状态角色，定义一个接口封装与 Context 的一个特点接口相关行为
- ConcreteState：具体的状态角色，每个子类实现一个与 Context 的一个状态相关行为

# 示例

> 情景介绍

请编写程序完成 APP 抽奖活动 具体要求如下

1. 假如每参加一次这个活动要扣除用户 50 积分（积分细节可以忽略，仅关注状态），中奖概率是 10%
2. 奖品数量固定，抽完就不能抽奖
3. 活动有四个状态: 普通状态、准备抽奖、领取奖品和活动结束
4. 活动的四个状态转换关系图如下：

![](https://cdn.maxqiu.com/upload/29f44a1878ca40e4912ba33fafd43244.jpg)

> UML类图

![](https://cdn.maxqiu.com/upload/014ff7007a404d1d80b00ca541b54161.jpg)

> 代码实现

```java
/**
 * 状态抽象类
 */
public abstract class State {
    /**
     * 扣除积分
     */
    public abstract void deductMoney();
    /**
     * 是否抽中奖品
     *
     * @return
     */
    public abstract boolean raffle();
    /**
     * 发放奖品
     */
    public abstract void dispensePrize();
}
```

```java
/**
 * 普通状态
 */
public class NormalRaffleState extends State {
    /**
     * 初始化时传入活动引用，扣除积分后改变其状态
     */
    RaffleActivity activity;
    public NormalRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }
    /**
     * 当前状态可以扣积分，扣除后将状态设置成可以抽奖状态
     */
    @Override
    public void deductMoney() {
        // 如果有奖品，则转换为准备抽奖
        if (activity.getCount() > 0) {
            System.out.println("扣除50积分成功，您可以抽奖了");
            activity.setState(activity.getCanRaffleState());
        } else {
            System.out.println("没有奖品了，活动结束");
            activity.setState(activity.getDispenseOutState());
        }
    }
    /**
     * 当前状态不能抽奖
     */
    @Override
    public boolean raffle() {
        System.out.println("扣了积分才能抽奖喔！");
        return false;
    }
    /**
     * 当前状态不能发奖品
     */
    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}
```

```java
/**
 * 可以抽奖的状态
 */
public class CanRaffleState extends State {
    RaffleActivity activity;
    public CanRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }
    /**
     * 已经扣除了积分，不能再扣
     */
    @Override
    public void deductMoney() {
        System.out.println("已经扣取过了积分");
    }
    /**
     * 可以抽奖, 抽完奖后，根据实际情况，改成新的状态
     *
     * @return
     */
    @Override
    public boolean raffle() {
        System.out.println("正在抽奖，请稍等！");
        Random r = new Random();
        int num = r.nextInt(10);
        // 10%中奖机会
        if (num == 0) {
            // 改变活动状态为领取奖品
            activity.setState(activity.getDispenseState());
            return true;
        } else {
            System.out.println("很遗憾没有抽中奖品！");
            // 改变状态为不能抽奖
            activity.setState(activity.getNormalRaffleState());
            return false;
        }
    }
    /**
     * 不能发放奖品
     */
    @Override
    public void dispensePrize() {
        System.out.println("没中奖，不能发放奖品");
    }
}
```

```java
/**
 * 发放奖品的状态
 */
public class DispenseState extends State {
    RaffleActivity activity;
    public DispenseState(RaffleActivity activity) {
        this.activity = activity;
    }
    @Override
    public void deductMoney() {
        System.out.println("不能扣除积分");
    }
    @Override
    public boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }
    /**
     * 发放奖品
     */
    @Override
    public void dispensePrize() {
        System.out.println("恭喜中奖了");
        activity.setCount(activity.getCount() - 1);
        if (activity.getCount() > 0) {
            activity.setState(activity.getNormalRaffleState());
        } else {
            System.out.println("很遗憾，奖品发送完了");
            // 改变状态为奖品发送完毕, 后面我们就不可以抽奖
            activity.setState(activity.getDispenseOutState());
        }
    }
}
```

```java
/**
 * 奖品发放完毕状态（活动结束）
 * 当 activity 改变成 DispenseOutState，抽奖活动结束
 */
public class DispenseOutState extends State {
    RaffleActivity activity;
    public DispenseOutState(RaffleActivity activity) {
        this.activity = activity;
    }
    @Override
    public void deductMoney() {
        System.out.println("奖品发送完了，请下次再参加");
    }
    @Override
    public boolean raffle() {
        System.out.println("奖品发送完了，请下次再参加");
        return false;
    }
    @Override
    public void dispensePrize() {
        System.out.println("奖品发送完了，请下次再参加");
    }
}
```

```java
/**
 * 抽奖活动
 */
public class RaffleActivity {
    /**
     * 表示活动当前的状态，是变化
     */
    private State state;
    /**
     * 奖品数量
     */
    private int count;
    /**
     * 四个属性，表示四种状态
     */
    State normalRaffleState = new NormalRaffleState(this);
    State canRaffleState = new CanRaffleState(this);
    State dispenseState = new DispenseState(this);
    State dispenseOutState = new DispenseOutState(this);
    /**
     * 构造器
     */
    public RaffleActivity(int count) {
        // 1. 初始化当前的状态为 normalRaffleState（即不能普通的状态）
        this.state = getNormalRaffleState();
        // 2. 初始化奖品的数量
        this.count = count;
    }
    /**
     * 参与活动
     */
    public void participateInActivities() {
        // 扣分, 调用当前状态的 deductMoney
        state.deductMoney();
    }
    /**
     * 抽奖
     */
    public void raffle() {
        // 如果当前的状态是抽奖成功
        if (state.raffle()) {
            // 领取奖品
            state.dispensePrize();
        }
    }
    public State getState() { return state; }
    public void setState(State state) { this.state = state; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public State getNormalRaffleState() { return normalRaffleState; }
    public State getCanRaffleState() { return canRaffleState; }
    public State getDispenseState() { return dispenseState; }
    public State getDispenseOutState() { return dispenseOutState; }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建活动对象，奖品有2个奖品
        RaffleActivity activity = new RaffleActivity(2);
        // 我们连续抽300次奖
        for (int i = 0; i < 10; i++) {
            System.out.println("--------第" + (i + 1) + "次抽奖----------");
            // 参加抽奖，第一步点击扣除积分
            activity.participateInActivities();
            // 第二步抽奖
            activity.raffle();
        }
    }
}
```

输出如下：

    --------第1次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    很遗憾没有抽中奖品！
    --------第2次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    恭喜中奖了
    --------第3次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    很遗憾没有抽中奖品！
    --------第4次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    很遗憾没有抽中奖品！
    --------第5次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    很遗憾没有抽中奖品！
    --------第6次抽奖----------
    扣除50积分成功，您可以抽奖了
    正在抽奖，请稍等！
    恭喜中奖了
    很遗憾，奖品发送完了
    --------第7次抽奖----------
    奖品发送完了，请下次再参加
    奖品发送完了，请下次再参加
    --------第8次抽奖----------
    奖品发送完了，请下次再参加
    奖品发送完了，请下次再参加
    --------第9次抽奖----------
    奖品发送完了，请下次再参加
    奖品发送完了，请下次再参加
    --------第10次抽奖----------
    奖品发送完了，请下次再参加
    奖品发送完了，请下次再参加

# 总结

1. 代码有很强的可读性。状态模式将每个状态的行为封装到对应的一个类中
2. 方便维护。将容易产生问题的`if-else`语句删除了，如果把每个状态的行为都放到一个类中，每次调用方法时都要判断当前是什么状态，不但会产出很多`if-else`语句，而且容易出错
3. 符合“开闭原则”。容易增删状态
4. 会产生很多类。每个状态都要一个对应的类，当状态过多时会产生很多类，加大维护难度
5. 应用场景：当一个事件或者对象有很多种状态，状态之间会相互转换，对不同的状态要求有不同的行为的时候，可以考虑使用状态模式
