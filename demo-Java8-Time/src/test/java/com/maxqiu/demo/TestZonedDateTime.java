package com.maxqiu.demo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.Test;

/**
 * ZonedDateTime ： 带时区的时间或日期
 * 
 * @author Max_Qiu
 */
public class TestZonedDateTime {

    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    @Test
    public void test7() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        System.out.println(now);

        // 中国时区
        System.out.println(now);

        ZonedDateTime zonedDateTime = now.withZoneSameLocal(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        ZoneId from = ZoneId.from(zonedDateTime);
        System.out.println(from);
    }
}
