package com.maxqiu.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

/**
 * 时区id
 * 
 * @author Max_Qiu
 */
public class TestZoneId {
    /**
     * 可以使用的 ZoneId
     *
     * 即可用的时区字符串
     */
    @Test
    public void availableZoneIds() {
        System.out.println("可使用的时区如下：");
        // 获取可用的时区Set集合
        ZoneId.getAvailableZoneIds().forEach(System.out::println);
        // 短时区id
        System.out.println(ZoneId.SHORT_IDS);
    }

    @Test
    public void test() {
        // 新建一个时区
        System.out.println(ZoneId.of("Asia/Shanghai"));
        // 使用别名映射来补充标准时区ID
        System.out.println(LocalDateTime.now(ZoneId.of("CTT", ZoneId.SHORT_IDS)));

        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
        // 获取唯一的时区ID
        System.out.println(zoneId.getId());
        // 获取此ID的时区规则
        System.out.println(zoneId.getRules());
        // 标准化时区ID，并在可能的情况下返回ZoneOffset
        System.out.println(zoneId.normalized());
        // 从一个 TemporalAccessor 子类获取时区。注：此时间必须设置了时区
        System.out.println(ZoneId.from(LocalDateTime.now().atZone(ZoneId.systemDefault())));
        // 中国时区
        System.out.println(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        System.out.println(LocalDateTime.now(ZoneId.of("CTT", ZoneId.SHORT_IDS)));
        // 标准时区
        System.out.println(LocalDateTime.now(ZoneId.of("UTC")));
        // 美国太平洋标准时区
        System.out.println(LocalDateTime.now(ZoneId.of("America/Los_Angeles")));

        // TODO ofOffset getDisplayName
        // ZoneId.ofOffset()
        // zoneId.getDisplayName()
    }
}
