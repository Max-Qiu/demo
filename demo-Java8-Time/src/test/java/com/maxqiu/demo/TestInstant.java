package com.maxqiu.demo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

/**
 * Instant : 时间戳。 （使用 Unix 元年 1970年1月1日 00:00:00 至某个时间所经历的毫秒值）
 * 
 * @author Max_Qiu
 */
public class TestInstant {

    @Test
    public void testInstantAndOffsetDateTime() {
        // 默认使用 UTC 时区
        Instant now = Instant.now();
        System.out.println(now);

        System.out.println(">>> 获取当前时间的年、月、日、时、分、秒");
        System.out.println(now.getEpochSecond());
        System.out.println(now.getNano());

        System.out.println(">>> withXXX 修改");
        // LocalDateTime withYear = now.withYear(8);
        // System.out.println(withYear);
        // System.out.println(now.withMonth(8));
        // System.out.println(now.withDayOfMonth(8));
        // System.out.println(now.withDayOfYear(8));
        // System.out.println(now.withHour(8));
        // System.out.println(now.withMinute(8));
        // System.out.println(now.withSecond(8));
        // System.out.println(now.withNano(8));

        System.out.println(">>> plusXxx 添加");
        System.out.println(now.plusSeconds(8));
        System.out.println(now.plusMillis(8));
        System.out.println(now.plusNanos(8));

        System.out.println(">>> minusXxx 减少");
        System.out.println(now.minusSeconds(2));
        System.out.println(now.minusMillis(2));
        System.out.println(now.minusNanos(2));

        System.out.println(">>> 最大最小值");
        System.out.println(LocalDateTime.MIN);
        System.out.println(LocalDateTime.MAX);

        OffsetDateTime odt = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(now.getNano());

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }
}
