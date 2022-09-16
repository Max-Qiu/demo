package com.maxqiu.demo.transaction;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.maxqiu.demo.util.JDBCUtils;

public class ConnectionTest {
    @Test
    public void testGetConnection() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }
}
