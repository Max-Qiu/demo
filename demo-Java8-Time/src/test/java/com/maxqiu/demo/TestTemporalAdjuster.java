package com.maxqiu.demo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * 时间调节器（修改时间）
 *
 * @author Max_Qiu
 */
public class TestTemporalAdjuster {
    /**
     * TemporalAdjusters : 官方实现的常用调节器
     */
    @Test
    public void testTemporalAdjusters() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间：" + now);
        System.out.println("当时时间基础上：");
        // System.out.println(now.with(TemporalAdjusters.ofDateAdjuster()));
        System.out.println("月初" + now.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("月初" + now.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("下月初" + now.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println("年初" + now.with(TemporalAdjusters.firstDayOfYear()));
        System.out.println("年末" + now.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println("下年初" + now.with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println("本月第一个周日" + now.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY)));
        System.out.println("本月最后一个周日" + now.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)));
        System.out.println("本月第二个周日" + now.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY)));
        System.out.println("本月倒数第二个周日" + now.with(TemporalAdjusters.dayOfWeekInMonth(-2, DayOfWeek.SUNDAY)));
        System.out.println("上个月最后一个周日" + now.with(TemporalAdjusters.dayOfWeekInMonth(0, DayOfWeek.SUNDAY)));
        System.out.println("下一个周日" + now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
        System.out.println("上一个周日" + now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));
        // OrSame ：包含当天，若今天是周日，则计算下个周日时，当天也算下一个周日，即得到当天时间
        System.out.println("下一个周日（包含当天）" + now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
        System.out.println("上一个周日（包含当天）" + now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));

        // 自定义：下一个工作日
        LocalDateTime custom = now.with(temporal -> {
            LocalDateTime localDateTime = (LocalDateTime)temporal;
            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDateTime.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDateTime.plusDays(2);
            } else {
                return localDateTime.plusDays(1);
            }
        });
        System.out.println(custom);
    }
}
