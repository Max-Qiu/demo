package com.maxqiu.demo.CopyOnWrite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Max_Qiu
 */
public class ListRunnable implements Runnable {

    // private static List<String> list = Collections.synchronizedList(new ArrayList<>());
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