package com.maxqiu.demo.P18_Memento.T2_Demo;

import java.util.HashMap;
import java.util.UUID;

/**
 * 存档管理器（备忘录管理者，管理多个备忘）
 * 
 * @author Max_Qiu
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
