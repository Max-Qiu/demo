package com.maxqiu.demo.TemporalAmount;

import java.time.LocalDate;
import java.time.Period;

import org.junit.Test;

/**
 * Period : 用于计算两个“日期”间隔
 * 
 * @author Max_Qiu
 */
public class TestPeriod {

    /**
     * 使用
     */
    @Test
    public void useAndGetDetail() {
        // 获取两个日期
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = LocalDate.of(2019, 1, 1);

        // 执行比较
        Period period = Period.between(localDate2, localDate1);
        System.out.println("比较结果：" + period);

        System.out.println("String 转 Duration ：" + Period.parse("P1Y11M21D"));
        System.out.println("------------------------------");

        System.out.println(">>> ofXxx 手动创建一个间隔");
        System.out.println(Period.ofYears(1));
        System.out.println(Period.ofMonths(1));
        System.out.println(Period.ofWeeks(1));
        System.out.println(Period.ofDays(1));
        System.out.println(Period.of(1, 1, 1));

        System.out.println("------------------------------");
        System.out.println("原始值：" + period);
        System.out.println("0值：" + Period.ZERO);
        System.out.println("是否为零：" + period.isZero());
        System.out.println("是否为负：" + period.isNegative());
        System.out.println("获取年 ：" + period.getYears());
        System.out.println("获取月 ：" + period.getMonths());
        System.out.println("获取日 ：" + period.getDays());
    }

    @Test
    public void operation() {
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = LocalDate.of(2019, 1, 1);

        Period period = Period.between(localDate2, localDate1);

        System.out.println(">>> withXxx 修改");
        System.out.println(period.withYears(10));
        System.out.println(period.withMonths(10));
        System.out.println(period.withDays(10));

        System.out.println(">>> plusXxx 增加");
        System.out.println(period.plus(period));
        System.out.println(period.plusYears(1));
        System.out.println(period.plusMonths(1));
        System.out.println(period.plusDays(1));

        System.out.println(">>> minusXxx 减少");
        System.out.println(period.minus(period));
        System.out.println(period.minusYears(1));
        System.out.println(period.minusMonths(1));
        System.out.println(period.minusDays(1));

        System.out.println(">>> toXxx 转换");
        System.out.println(period.toTotalMonths());

        System.out.println(">>> 数学计算");
        System.out.println("乘" + period.multipliedBy(5));
        System.out.println("取反" + period.negated());
    }
}
