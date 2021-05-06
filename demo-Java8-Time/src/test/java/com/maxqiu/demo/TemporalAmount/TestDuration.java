package com.maxqiu.demo.TemporalAmount;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Duration : 用于计算两个“时间”间隔
 * 
 * @author Max_Qiu
 */
public class TestDuration {
    /**
     * 使用
     */
    @Test
    public void useAndGetDetail() {
        // 获取两个时间
        Instant ins1 = Instant.now();
        try {
            Thread.sleep(3251);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ins2 = Instant.now();

        // 执行比较
        Duration duration = Duration.between(ins1, ins2);
        System.out.println("比较结果：" + duration);

        System.out.println("String 转 Duration ：" + Duration.parse("PT1S"));
        System.out.println("------------------------------");

        System.out.println(">>> ofXxx 手动创建一个间隔");
        System.out.println(Duration.ofDays(1));
        System.out.println(Duration.ofHours(1));
        System.out.println(Duration.ofMinutes(1));
        System.out.println(Duration.ofSeconds(1));
        // 秒 + 纳秒
        System.out.println(Duration.ofSeconds(1, 10));
        System.out.println(Duration.ofMinutes(1));
        System.out.println(Duration.ofNanos(1));
        // TODO Duration.of(3, TemporalUnit.)
        // TODO System.out.println(Duration.from(TemporalAmount));

        System.out.println("------------------------------");
        System.out.println("原始值：" + duration);
        System.out.println("0值：" + Duration.ZERO);
        System.out.println("是否为零：" + duration.isZero());
        System.out.println("是否为负：" + duration.isNegative());
        System.out.println("获取秒 ：" + duration.getSeconds());
        System.out.println("获取纳秒 ：" + duration.getNano());
    }

    /**
     * 操作
     *
     * 1. 修改 秒、纳秒
     *
     * 2. 增加 指定时长
     *
     * 3. 减少 指定时长
     * 
     * 4. 数学计算
     */
    @Test
    public void operation() {

        Instant ins1 = Instant.now();
        try {
            Thread.sleep(3251);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ins2 = Instant.now();
        Duration duration = Duration.between(ins1, ins2);

        System.out.println(">>> withXxx 修改");
        System.out.println(duration.withSeconds(10));
        System.out.println(duration.withNanos(235));

        System.out.println(">>> plusXxx 增加");
        // 增加一个时间量
        System.out.println(duration.plus(duration));
        // 增加指定时间单位的时间量
        System.out.println(duration.plus(3, ChronoUnit.DAYS));
        System.out.println(duration.plusDays(1));
        System.out.println(duration.plusHours(1));
        System.out.println(duration.plusMinutes(1));
        System.out.println(duration.plusSeconds(1));
        System.out.println(duration.plusMillis(1));
        System.out.println(duration.plusNanos(1));

        System.out.println(">>> minusXxx 减少");
        System.out.println(duration.minus(duration));
        System.out.println(duration.minus(3, ChronoUnit.DAYS));
        System.out.println(duration.minusDays(1));
        System.out.println(duration.minusHours(1));
        System.out.println(duration.minusMinutes(1));
        System.out.println(duration.minusSeconds(1));
        System.out.println(duration.minusMillis(1));
        System.out.println(duration.minusNanos(1));

        System.out.println(">>> toXxx 转换");
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
        System.out.println(duration.toMillis());
        System.out.println(duration.toNanos());

        System.out.println(">>> 数学计算");
        System.out.println("乘" + duration.multipliedBy(5));
        System.out.println("除" + duration.dividedBy(5));
        System.out.println("取反" + duration.negated());
        // duration.negated() 是个负数，取反后看到变成正数
        System.out.println("绝对值" + duration.negated().abs());
    }

    /**
     * 其他
     */
    @Test
    public void other() {
        Instant ins1 = Instant.now();
        try {
            Thread.sleep(3251);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ins2 = Instant.now();
        Duration duration = Duration.between(ins1, ins2);

        // duration.get(TemporalUnit);
        List<TemporalUnit> units = duration.getUnits();
        units.forEach(System.out::println);
        // duration.addTo()
        // duration.subtractFrom()
        // duration.compareTo()
    }
}
