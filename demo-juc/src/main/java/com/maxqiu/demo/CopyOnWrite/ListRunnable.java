package com.maxqiu.demo.CopyOnWrite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Max_Qiu
 */
public class ListRunnable implements Runnable {

    // private static List<String> list = Collections.synchronizedList(new ArrayList<>());
    // CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
    // 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        list.forEach(s -> {
            System.out.println(s);
            list.add("AA");
        });
        System.out.println(list);
    }
}