package com.maxqiu.demo;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * LocalDateTime 本地时间和日期
 * 
 * @author Max_Qiu
 */
public class TestLocalDateTime {
    /**
     * 创建时间以及获取详细时间
     */
    @Test
    public void createAndGetDetail() {
        // 获取当前时间，默认无时区
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        // 获取指定时间
        LocalDateTime myTime1 = LocalDateTime.of(2016, 11, 21, 10, 10);
        System.out.println(myTime1);
        // 未设置则为 0
        System.out.println(myTime1.getSecond());

        // 月份也可以使用枚举类型
        System.out.println(LocalDateTime.of(2020, Month.DECEMBER, 30, 10, 11, 12, 13));
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        System.out.println(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        System.out.println(LocalDateTime.from(LocalDateTime.now()));

        System.out.println(">>> 获取当前时间的年、月、日、时、分、秒");
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getMonth().getValue());
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getDayOfYear());
        System.out.println(now.getDayOfWeek().getValue());
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());
        System.out.println(now.getNano());

        System.out.println(">>> 最大最小值");
        System.out.println(LocalDateTime.MIN);
        System.out.println(LocalDateTime.MAX);
    }

    /**
     * 格式化
     */
    @Test
    public void format() {
        // 默认格式为 yyyy-MM-ddTHH:mm:ss
        LocalDateTime localDateTime = LocalDateTime.parse("2020-12-23T08:06:23");
        System.out.println(localDateTime);

        // 自定义格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        // 转换为 String
        String format = localDateTime.format(dateTimeFormatter);
        System.out.println(format);

        System.out.println(LocalDateTime.parse(format, dateTimeFormatter));
    }

    /**
     * 操作
     * 
     * 1. 修改 年月日 等
     * 
     * 2. 增加 指定时长
     * 
     * 3. 减少 指定时长
     */
    @Test
    public void operation() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        System.out.println(">>> withXXX 修改");
        System.out.println(now.withYear(8));
        System.out.println(now.withMonth(8));
        System.out.println(now.withDayOfMonth(8));
        System.out.println(now.withDayOfYear(8));
        System.out.println(now.withHour(8));
        System.out.println(now.withMinute(8));
        System.out.println(now.withSecond(8));
        System.out.println(now.withNano(8));
        System.out.println(now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));

        System.out.println(">>> plusXxx 增加");
        System.out.println(now.plusYears(8));
        System.out.println(now.plusMonths(8));
        System.out.println(now.plusWeeks(8));
        System.out.println(now.plusDays(8));
        System.out.println(now.plusHours(8));
        System.out.println(now.plusMinutes(8));
        System.out.println(now.plusSeconds(8));
        System.out.println(now.plusNanos(8));
        // 增加基于时间的时间量 Duration/Period
        System.out.println(now.plus(Duration.ofDays(1)));

        System.out.println(">>> minusXxx 减少");
        System.out.println(now.minusYears(8));
        System.out.println(now.minusYears(2));
        System.out.println(now.minusMonths(2));
        System.out.println(now.minusWeeks(2));
        System.out.println(now.minusDays(2));
        System.out.println(now.minusHours(2));
        System.out.println(now.minusMinutes(2));
        System.out.println(now.minusSeconds(2));
        System.out.println(now.minusNanos(2));
        // 减少基于时间的时间量 Duration/Period
        System.out.println(now.minus(Duration.ofDays(1)));

        System.out.println(">>> isXxx 判断");
        System.out.println(now.isAfter(LocalDateTime.now()));
        System.out.println(now.isBefore(LocalDateTime.now()));
        System.out.println(now.isEqual(LocalDateTime.now()));
    }

    /**
     * 其他常用
     */
    @Test
    public void other() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        // 中国时区
        System.out.println(now);

        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        ZoneId from = ZoneId.from(zonedDateTime);
        System.out.println(from);
    }
}
