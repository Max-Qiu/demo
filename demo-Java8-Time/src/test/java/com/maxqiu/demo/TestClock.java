package com.maxqiu.demo;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

/**
 * 时钟
 * 
 * @author Max_Qiu
 */
public class TestClock {
    @Test
    public void test() {
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock);
        System.out.println(clock.getZone());
        System.out.println(clock.withZone(ZoneId.systemDefault()));
        System.out.println(clock.millis());
        System.out.println(clock.instant());
        System.out.println("--------------------------------------------");
        System.out.println(Clock.systemUTC());
        System.out.println(Clock.system(ZoneId.of("America/Los_Angeles")));
        System.out.println(Clock.tickSeconds(ZoneId.of("America/Los_Angeles")));
        System.out.println(Clock.tickMinutes(ZoneId.of("America/Los_Angeles")));
        System.out.println(Clock.tick(Clock.systemDefaultZone(), Duration.ZERO));
        System.out.println(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        System.out.println(Clock.offset(Clock.systemDefaultZone(), Duration.ZERO));
    }
}
