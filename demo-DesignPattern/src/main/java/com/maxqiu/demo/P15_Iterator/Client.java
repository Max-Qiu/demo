package com.maxqiu.demo.P15_Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 客户端
 * 
 * @author Max_Qiu
 */
public class Client {

    public static void main(String[] args) {
        // 创建学院
        List<College> collegeList = new ArrayList<>();

        ComputerCollege computerCollege = new ComputerCollege();
        InfoCollege infoCollege = new InfoCollege();

        collegeList.add(computerCollege);
        collegeList.add(infoCollege);

        // 从 collegeList 取出所有学院，Java 中的 List 已经实现Iterator
        Iterator<College> iterator = collegeList.iterator();
        while (iterator.hasNext()) {
            College college = iterator.next();
            // 取出一个学院
            System.out.println("=== " + college.getName() + " ===");
            // 得到对应迭代器
            Iterator<Department> iter = college.createIterator();
            while (iter.hasNext()) {
                Department d = iter.next();
                System.out.println(d.getName());
            }
        }
    }

}
