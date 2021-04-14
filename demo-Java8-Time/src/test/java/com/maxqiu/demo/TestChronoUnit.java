package com.maxqiu.demo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

/**
 * ChronoUnit：标准的日期周期单位集 枚举
 * 
 * 时间的单位 TemporalUnit 的实现类
 * 
 * @author Max_Qiu
 */
public class TestChronoUnit {

    /**
     * 常量
     */
    @Test
    public void constant() {
        // 纳秒 0.000000001S
        System.out.println(ChronoUnit.NANOS.getDuration());
        // 微妙 000001S
        System.out.println(ChronoUnit.MICROS.getDuration());
        // 毫秒 001S
        System.out.println(ChronoUnit.MILLIS.getDuration());
        // 秒 1S
        System.out.println(ChronoUnit.SECONDS.getDuration());
        // 分钟 1M
        System.out.println(ChronoUnit.MINUTES.getDuration());
        // 小时 1H
        System.out.println(ChronoUnit.HOURS.getDuration());
        // 半天 12H
        System.out.println(ChronoUnit.HALF_DAYS.getDuration());
        // 一天 24H
        System.out.println(ChronoUnit.DAYS.getDuration());
        // 一周 168H
        System.out.println(ChronoUnit.WEEKS.getDuration());
        // 一个月 730H29M6S
        System.out.println(ChronoUnit.MONTHS.getDuration());
        // 一年 8765H49M12S
        System.out.println(ChronoUnit.YEARS.getDuration());
        // 十年 87658H12M
        System.out.println(ChronoUnit.DECADES.getDuration());
        // 一个世纪（一百年） 876582H
        System.out.println(ChronoUnit.CENTURIES.getDuration());
        // 一千年 8765820H
        System.out.println(ChronoUnit.MILLENNIA.getDuration());
        // 一个时代（十万个一千年） 8765820000000H
        System.out.println(ChronoUnit.ERAS.getDuration());
        // 永远 2562047788015215H30M7.999999999S
        System.out.println(ChronoUnit.FOREVER.getDuration());

    }

    /**
     * 其他
     */
    @Test
    public void other() {
        // 是否估算
        System.out.println(ChronoUnit.NANOS.isDurationEstimated());
        // 单位是否为日期单位
        System.out.println(ChronoUnit.DAYS.isDateBased());
        // 单位是否为时间单位
        System.out.println(ChronoUnit.MINUTES.isTimeBased());
        // 修改时间（+3天）
        System.out.println(ChronoUnit.DAYS.addTo(LocalDateTime.now(), 3));
        // 以指定单位 比较时间
        System.out.println(ChronoUnit.DAYS.between(LocalDateTime.of(2020, 1, 1, 1, 1, 1), LocalDateTime.now()));
        // 检查指定的时间对象是否支持此单元
        System.out.println(ChronoUnit.DAYS.isSupportedBy(LocalDateTime.now()));
    }
}
