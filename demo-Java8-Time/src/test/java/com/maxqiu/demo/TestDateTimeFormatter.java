package com.maxqiu.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

/**
 * DateTimeFormatter : 解析和格式化日期或时间
 * 
 * @author Max_Qiu
 */
public class TestDateTimeFormatter {

    /**
     * 
     */
    @Test
    public void testDateTimeFormatter() {
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_TIME));

        System.out.println(now.format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println(now.format(DateTimeFormatter.ISO_DATE));
        System.out.println(now.format(DateTimeFormatter.ISO_TIME));
        System.out.println(now.format(DateTimeFormatter.ISO_ORDINAL_DATE));
        System.out.println(now.format(DateTimeFormatter.ISO_WEEK_DATE));

        System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));

        // System.out.println(now.format(DateTimeFormatter.ISO_OFFSET_DATE));
        // System.out.println(now.format(DateTimeFormatter.ISO_OFFSET_TIME));
        // System.out.println(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        // System.out.println(now.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        // System.out.println(now.format(DateTimeFormatter.ISO_INSTANT));
        // System.out.println(now.format(DateTimeFormatter.RFC_1123_DATE_TIME));

        System.out.println("--- 自定义事件转换 ---");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

        // 格式化 转 String
        String strDate = now.format(dateTimeFormatter);
        System.out.println(strDate);

        // String 转 LocalDateTime
        // 默认转换格式：2007-12-03T10:15:30，即ISO_LOCAL_DATE_TIME
        LocalDateTime localDateTime = LocalDateTime.parse(strDate, dateTimeFormatter);
        System.out.println(localDateTime);
    }
}
