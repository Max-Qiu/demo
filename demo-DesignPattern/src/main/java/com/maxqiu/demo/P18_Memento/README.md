# 备忘录模式 Memento Pattern

> 简介

在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可将该对象恢复到原先保存的状态。可以这样理解备忘录模式：现实生活中的备忘录是用来记录某些要去做的事情，或者是记录已经达成的共同意见的事情，以防忘记了。而在软件层面，备忘录模式有着相同的含义，备忘录对象主要用来记录一个对象的某种状态，或者某些数据，当要做回退时，可以从备忘录对象里获取原来的数据进行恢复操作。

> UML类图与角色

![](https://cdn.maxqiu.com/upload/3c7137cab9f14ba58cb50044a60d9ce1.jpg)

- Originator：对象（需要保存状态的对象）
- Memento：备忘录对象，负责保存记录，即 Originator 内部状态
- Caretaker：守护者对象，负责保存多个备忘录对象，使用集合管理，提高效率
- 说明：如果希望保存多个 Originator 对象的不同时间的状态，只需要要 HashMap <String, 集合>

# 示例

## 典型示例

```java
/**
 * 要被记录的对象
 */
@Getter
@Setter
public class Originator {
    /**
     * 状态信息
     */
    private String state;
    /**
     * 编写一个方法，创建一个状态对象 Memento，并返回 Memento
     */
    public Memento saveStateMemento() {
        return new Memento(state);
    }
    /**
     * 通过备忘录对象，恢复状态
     */
    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
}
```

```java
/**
 * 备忘录
 */
public class Memento {
    /**
     * 存储的状态
     */
    private String state;
    /**
     * 构造器，存储状态
     */
    public Memento(String state) {
        this.state = state;
    }
    /**
     * 返回状态
     */
    public String getState() {
        return state;
    }
}
```

```java
/**
 * 备忘录管理者，管理多个备忘
 */
public class Caretaker {
    /**
     * 在 List 集合中会有很多的备忘录对象
     */
    private List<Memento> mementoList = new ArrayList<>();
    /**
     * 新增一个备忘录
     */
    public void add(Memento memento) {
        mementoList.add(memento);
    }
    /**
     * 获取到第index个Originator的备忘录对象（即保存状态）
     */
    public Memento get(int index) {
        return mementoList.get(index);
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建一个备忘录管理者
        Caretaker caretaker = new Caretaker();
        // 创建一个对象
        Originator originator = new Originator();
        originator.setState("状态#1 攻击力 100 ");
        // 保存了当前的状态
        caretaker.add(originator.saveStateMemento());
        // 修改并保存当前状态
        originator.setState("状态#2 攻击力 80 ");
        caretaker.add(originator.saveStateMemento());
        // 再次修改并保存当前状态
        originator.setState("状态#3 攻击力 50 ");
        caretaker.add(originator.saveStateMemento());

        System.out.println("当前的状态是：" + originator.getState());
        System.out.println("恢复到状态1");
        // 将 originator 恢复到状态1
        originator.getStateFromMemento(caretaker.get(0));
        System.out.println("当前的状态是：" + originator.getState());
    }
}
```

输出如下：

    当前的状态是：状态#3 攻击力 50
    恢复到状态1
    当前的状态是：状态#1 攻击力 100

## 游戏存档示例

> 情景模式

游戏角色有攻击力和防御力，在大战Boss前存档（保存攻击力和防御力状态），当没打过后攻击力和防御力为0（此处忽略过程），从存档读取状态

> UML类图

![](https://cdn.maxqiu.com/upload/521e369358f741489afa8b98336ed290.jpg)

> 代码实现

```java
/**
 * 游戏角色
 */
@Getter
@Setter
public class GameRole {
    private int vit;
    private int def;
    /**
     * 创建存档
     */
    public Archive createMemento() {
        return new Archive(vit, def);
    }
    /**
     * 从备忘录对象，恢复角色的状态
     */
    public void recoverGameRoleFromMemento(Archive archive) {
        this.vit = archive.getVit();
        this.def = archive.getDef();
    }
    /**
     * 显示当前游戏角色的状态
     */
    public void display() {
        System.out.println("角色当前的攻击力：" + this.vit + " 防御力: " + this.def);
    }
}
```

```java
/**
 * 单条存档（备忘录）
 */
public class Archive {
    // 攻击力
    private int vit;
    // 防御力
    private int def;
    public Archive(int vit, int def) {
        super();
        this.vit = vit;
        this.def = def;
    }
    public int getVit() {
        return vit;
    }
    public int getDef() {
        return def;
    }
}
```

```java
/**
 * 存档管理器（备忘录管理者，管理多个备忘）
 */
public class ArchiveManager {
    // 保存多个状态
    private HashMap<String, Archive> map = new HashMap<>();
    public Archive getMemento(String archiveId) {
        return map.get(archiveId);
    }
    public String setMemento(Archive archive) {
        String s = UUID.randomUUID().toString();
        this.map.put(s, archive);
        return s;
    }
}
```

```java
/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        // 创建游戏存储
        ArchiveManager archiveManager = new ArchiveManager();

        // 创建游戏角色
        GameRole gameRole = new GameRole();
        gameRole.setVit(100);
        gameRole.setDef(100);
        System.out.println("和boss大战前的状态");
        gameRole.display();

        // 把当前状态保存caretaker
        String archiveId = archiveManager.setMemento(gameRole.createMemento());
        System.out.println("存档完毕！存档ID：" + archiveId);

        System.out.println("和boss大战~~~");
        gameRole.setDef(0);
        gameRole.setVit(0);
        gameRole.display();

        System.out.println("没打过，读取存档！");
        gameRole.recoverGameRoleFromMemento(archiveManager.getMemento(archiveId));
        System.out.println("恢复后的状态");
        gameRole.display();
    }
}
```

# 注意事项

1. 给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便地回到某个历史的状态
2. 实现了信息的封装，使得用户不需要关心状态的保存细节
3. 如果类的成员变量过多，势必会占用比较大的资源，而且每一次保存都会消耗一定的内存
4. 适用的应用场景：
    - 后悔药
    - 打游戏时的存档
    - Windows里的 ctrl + z
    - IE 中的后退
    - 数据库的事务管理
5. 为了节约内存，备忘录模式可以和原型模式配合使用
