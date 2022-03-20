package com.maxqiu.demo;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 虚引用哈希表
 *
 * @author Max_Qiu
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        hashMap();
        System.out.println("==============");
        weakHashMap();
    }

    public static void hashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = new Integer("1");
        String value = "xxx";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }

    public static void weakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer("1");
        String value = "xxx";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
}
