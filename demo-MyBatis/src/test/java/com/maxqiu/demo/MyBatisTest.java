package com.maxqiu.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.maxqiu.demo.bean.Department;
import com.maxqiu.demo.bean.Employee;
import com.maxqiu.demo.dao.DepartmentMapper;
import com.maxqiu.demo.dao.EmployeeMapper;
import com.maxqiu.demo.dao.EmployeeMapperAnnotation;
import com.maxqiu.demo.dao.EmployeeMapperDynamicSql;
import com.maxqiu.demo.dao.EmployeeMapperPlus;

// @formatter:off
// 1、接口式编程
// 	原生：		Dao		====>  DaoImpl
// 	mybatis：	Mapper	====>  xxMapper.xml
//
// 2、SqlSession代表和数据库的一次会话；用完必须关闭；
// 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
// 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
// 		（将接口和xml进行绑定）
// 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
// 5、两个重要的配置文件：
// 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
// 		sql映射文件：保存了每一个sql语句的映射信息：
// 					将sql抽取出来。
// @formatter:on

/**
 * @author Max_Qiu
 */
public class MyBatisTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

// @formatter:off
// 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
// 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
// 3、将sql映射文件注册在全局配置文件中
// 4、写代码：
// 		1）、根据全局配置文件得到SqlSessionFactory；
// 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
// 			一个sqlSession就是代表和数据库的一次会话，用完关闭
// 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
// @formatter:on

    /**
     *
     */
    @Test
    void test() throws IOException {

        // 2、获取sqlSession实例，能直接执行已经映射的sql语句
        // sql的唯一标识：statement Unique identifier matching the statement to use.
        // 执行sql要用的参数：parameter A parameter object to pass to the statement.
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("com.maxqiu.demo.dao.EmployeeMapper.getEmpById", 1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    @Test
    void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            // 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    @Test
    void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee empById = mapper.getEmpById(1);
            System.out.println(empById);
        } finally {
            openSession.close();
        }
    }

    /**
     * 测试增删改<br>
     * 1、mybatis允许增删改直接定义以下类型返回值 Integer、Long、Boolean、void<br>
     * 2、我们需要手动提交数据<br>
     * sqlSessionFactory.openSession();===》手动提交<br>
     * sqlSessionFactory.openSession(true);===》自动提交
     *
     */
    @Test
    void test03() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 一、获取到的SqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            // 1. 测试添加
            Employee employee = new Employee(null, "jerry4", null, 1);
            mapper.addEmp(employee);
            System.out.println(employee.getId());

            // 2. 测试修改
            // Employee employee = new Employee(1, "Tom", "jerry@atguigu.com", 0);
            // boolean updateEmp = mapper.updateEmp(employee);
            // System.out.println(updateEmp);

            // 3. 测试删除
            // Boolean delete = mapper.deleteEmpById(1);
            // System.out.println(delete);

            // 二、手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    void test04() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1、获取到的SqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            // 1. 多个参数传参
            // Employee employee = mapper.getEmpByIdAndLastName(1, "tom");
            // System.out.println(employee);

            // 2. Map传参
            // Map<String, Object> map = new HashMap<>();
            // map.put("id", 1);
            // map.put("lastName", "Tom");
            // Employee employee = mapper.getEmpByMap(map);
            // System.out.println(employee);

            // 3. 查询结果返回List
            // List<Employee> like = mapper.getEmpListByLastNameLike("%e%");
            // for (Employee employee : like) {
            // System.out.println(employee);
            // }

            // 4. 查询结果返回Map（单条记录）
            // Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
            // System.out.println(map);

            //
            Map<Integer, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
            System.out.println(map);

        } finally {
            openSession.close();
        }
    }

    @Test
    void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

            // 1. 根据ID查询
            Employee empById = mapper.getEmpById(2);
            System.out.println(empById);

            // 2. 连表查询
            // Employee empAndDept = mapper.getEmpAndDept(2);
            // System.out.println(empAndDept);

            // 3. 分步查询
            // Employee employee = mapper.getEmpByIdStep(3);
            // System.out.println(employee);

            // 4. 延迟加载（需要在config的setting中开启）
            // Employee employee = mapper.getEmpByIdStep(3);
            // System.out.println(employee.getLastName());
            // System.out.println(employee.getDept());
        } finally {
            openSession.close();
        }
    }

    @Test
    void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);

            // 1. 连表查询
            // Department department = mapper.getDeptByIdPlus(1);
            // System.out.println(department);

            // 2. 分步查询（也带延迟加载）
            Department deptByIdStep = mapper.getDeptByIdStep(1);
            System.out.println(deptByIdStep.getDepartmentName());
            System.out.println(deptByIdStep.getEmployeeList());
        } finally {
            openSession.close();
        }
    }

    @Test
    void testDynamicSql() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(1, "Admin", null, 1);

            // 一、测试if\where
            /*List<Employee> employeeList = mapper.getEmpListByConditionIf(employee);
            for (Employee emp : employeeList) {
                System.out.println(emp);
            }*/

            // 查询的时候如果某些条件没带可能sql拼装会有问题
            // 1、给where后面加上1=1，以后的条件都and xxx.
            // 2、mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉，where只会去掉第一个多出来的and或者or。

            // 二、测试trim
            /*List<Employee> employeeList = mapper.getEmpListByConditionTrim(employee);
            for (Employee emp : employeeList) {
                System.out.println(emp);
            }*/

            // 三、测试choose
            /*List<Employee> list = mapper.getEmpListByConditionChoose(employee);
            for (Employee emp : list) {
                System.out.println(emp);
            }*/

            // 四、测试set
            /*mapper.updateEmp(employee);
            openSession.commit();*/

            // 五、测试foreach
            List<Employee> list = mapper.getEmpListByConditionForeach(Arrays.asList(1, 2));
            for (Employee emp : list) {
                System.out.println(emp);
            }

        } finally {
            openSession.close();
        }
    }

    @Test
    void testBatchSave() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(new Employee(null, "smith0x1", "smith0x1@atguigu.com", 1, new Department(1)));
            employeeList.add(new Employee(null, "allen0x1", "allen0x1@atguigu.com", 0, new Department(1)));
            mapper.addEmpList(employeeList);
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    void testInnerParam() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSql mapper = openSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee2 = new Employee();
            employee2.setLastName("%e%");
            List<Employee> list = mapper.getEmpListTestInnerParameter(employee2);
            for (Employee employee : list) {
                System.out.println(employee);
            }
        } finally {
            openSession.close();
        }
    }
}
