package com.maxqiu.demo.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.Test;

/**
 * jdbc连接测试
 *
 * @author Max_Qiu
 */
public class ConnectionTest {
    /**
     * 方式一
     *
     * @throws SQLException
     */
    @Test
    public void testConnectionTest1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://127.0.0.1:3306/test";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123");

        Connection connection = driver.connect(url, info);

        System.out.println(connection);
    }

    /**
     * 方式二：对方式一的迭代，程序中不出现第三方的 api
     *
     * @throws Exception
     */
    @Test
    public void testConnectionTest2() throws Exception {
        // 1.获取Driver实现类对象:使用反射
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();

        // 2.提供要连接的数据库
        String url = "jdbc:mysql://127.0.0.1:3306/test";

        // 3.提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123");

        // 4. 获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    /**
     * 方式三：使用 DriverManager 替换 Driver
     *
     * @throws Exception
     */
    @Test
    public void testConnectionTest3() throws Exception {
        // 1.获取Driver实现类的对象
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)aClass.newInstance();

        // 2.提供另外三个连接的基本信息:
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String password = "123";

        // 3. 注册驱动
        DriverManager.registerDriver(driver);

        // 4. 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    /**
     * 方式四：使用 DriverManager 替换 Driver
     *
     * @throws Exception
     */
    @Test
    public void testConnectionTest4() throws Exception {
        // 1.提供三个连接的基本信息:
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String password = "123";

        // 2.加载Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 相较于方式三，可以省略如下的操作:
        // Driver driver = (Driver)aClass.newInstance();
        // 注册驱动
        // DriverManager.registerDriver(driver);

        // 3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    /**
     * 方式五（final版）：使用配置文件
     *
     * @throws Exception
     */
    @Test
    public void testConnectionTest5() throws Exception {
        InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(inputStream);
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        // 2.加载驱动
        Class.forName(driverClass);
        // 3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
