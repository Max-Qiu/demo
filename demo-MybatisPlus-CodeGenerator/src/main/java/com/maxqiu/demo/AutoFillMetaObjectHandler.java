package com.maxqiu.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * Mybatis Plus 自动填充
 *
 * @author Max_Qiu
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    /**
     * 根据 MySQL datetime 字段设置的长度值设置精度
     *
     * 解决 Java 和 MySQL 时间精度不一样的问题（即使用MySQL的时间精度格式化一下）
     *
     * 数据库中的时间字段长度设置为几时，下方最后就有几个S。例如：datetime(4) -> uuuu-MM-dd'T'HH:mm:ss.SSSS
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSS");

    /**
     * 创建时自动插入
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动插入创建时间
        this.strictInsertFill(metaObject, "createTime",
            () -> LocalDateTime.parse(LocalDateTime.now().format(FORMATTER), FORMATTER), LocalDateTime.class);
    }

    /**
     * 更新时自动插入
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动插入修改时间
        this.strictUpdateFill(metaObject, "updateTime",
            () -> LocalDateTime.parse(LocalDateTime.now().format(FORMATTER), FORMATTER), LocalDateTime.class);
    }
}
