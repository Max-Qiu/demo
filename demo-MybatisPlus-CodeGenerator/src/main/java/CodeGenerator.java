import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * 代码生成器
 *
 * 以 MySQL 为例
 *
 * 1. 修改最重要的设置
 *
 * 2. 确认是否需要启用乐观锁、逻辑删除，本配置默认启用（详见实体策略配置）
 *
 * 3. 运行 generator
 *
 * 4. 拷贝代码至自己的项目
 *
 * PS 代码生成后推荐格式化一下，毕竟模板中可能有多余的空行或者空格或者import顺序不一样等等
 *
 * @author Max_Qiu
 */
public class CodeGenerator {
    /**
     * 最重要的配置
     */
    // 数据库地址
    private static final String URL = "jdbc:mysql://localhost:3306/mpg?useSSL=false&serverTimezone=GMT%2B8";
    // 用户名
    private static final String USERNAME = "root";
    // 密码
    private static final String PASSWORD = "123";

    // 作者（@author Max_Qiu）
    private static final String AUTHOR = "Max_Qiu";

    // 父包名
    private static final String PARENT = "com.maxqiu.demo";

    // 需要排除的表
    private static final String[] EXCLUDE = new String[] {"table_a", "table_b"};
    // 去除表前缀（smp_user -> User）（若不需要去除，则设置为 "" ）
    private static final String TABLE_PREFIX = "mpg_";

    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig
            // URL、用户名、密码
            .Builder(URL, USERNAME, PASSWORD)
                // 数据库查询实现
                .dbQuery(new MySqlQuery())
                // 类型转换器
                .typeConvert(new MySqlTypeConvert())
                // 数据库关键字处理器
                .keyWordsHandler(new MySqlKeyWordsHandler())
                // 构建数据库配置
                .build();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
            // 关闭生成后自动打开文件夹（默认true）
            .disableOpenDir()
            // 开启 kotlin 模式
            // .enableKotlin()
            // 开启 swagger 模式
            // .enableSwagger()
            // 文件输出目录（默认D:\\tmp）
            .outputDir("demo-MybatisPlus-CodeGenerator\\src\\main\\java")
            // 作者
            .author(AUTHOR)
            // 实体时间类型（使用Java8时间类，其他类型进入DateType枚举查看详细）
            .dateType(DateType.TIME_PACK)
            // 生成文件中的注释的日期（指定格式）
            .commentDate("yyyy-MM-dd")
            // 生成文件中的注释的日期（指定日期）
            // .commentDate(() -> "2021-06-25")
            // 构建全局配置
            .build();

        // 包相关的配置项
        PackageConfig packageConfig = new PackageConfig.Builder()
            // 包名
            .parent(PARENT)
            // 模块名（默认空）
            .moduleName("")
            // 实体包名（默认entity）
            .entity("entity")
            // service接口包名（默认service）
            .service("service")
            // service实现类包名（默认service.impl）
            .serviceImpl("service.impl")
            // mapper接口包名（默认mapper）
            .mapper("mapper")
            // xml包名（默认mapper.xml）
            .xml("mapper.xml")
            // 控制器包名（默认controller）
            .controller("controller")
            // 构建包配置对象
            .build();

        // 模板路径配置
        TemplateConfig templateConfig = new TemplateConfig.Builder()
            // 禁用所有模板
            // .disable()
            // 禁用指定模板
            // .disable(TemplateType.CONTROLLER,TemplateType.SERVICE)
            // 实体模板路径(JAVA)
            .entity("mybatis/entity.java")
            // service模板路径
            .service("mybatis/service.java").serviceImpl("mybatis/serviceImpl.java")
            // mapper模板路径
            .mapper("mybatis/mapper.java")
            // mapperXml模板路径
            .xml("mybatis/mapper.xml")
            // 控制器模板路径
            .controller("mybatis/controller.java")
            // 构建模板配置对象
            .build();

        // 策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder()
            // 开启跳过视图
            .enableSkipView()
            // 精确匹配排除表，生成表，模糊匹配排查表，生成表
            .addExclude(EXCLUDE)
            // .addInclude("")
            // .notLikeTable(new LikeTable("xxx"))
            // .likeTable(new LikeTable("xxx"))
            // 设置需要移除的字段前缀 abc_username -> username
            // .addFieldPrefix("abc")
            // 设置需要移除的表前缀
            .addTablePrefix(TABLE_PREFIX);

        // 实体策略配置
        strategyConfigBuilder.entityBuilder()
            // 关闭生成serialVersionUID（默认true）
            // .disableSerialVersionUID()
            // 开启链式模型，即实体可以连续set，例：.setXxx().setXxx();
            .enableChainModel()
            // 开启lombok模型
            .enableLombok()
            // 开启Boolean类型字段移除is前缀，即 is_deleted -> deleted
            .enableRemoveIsPrefix()
            // 开启生成实体时生成字段注解，即每个字段都设置 @TableId/@TableField
            .enableTableFieldAnnotation()
            // 开启 ActiveRecord 模式
            .enableActiveRecord()
            // 乐观锁数据库表字段名称（数据库）
            // .versionColumnName("version")
            // 乐观锁实体属性字段名称（实体）
            .versionPropertyName("version")
            // 逻辑删除数据库字段名称（数据库）
            // .logicDeleteColumnName("is_deleted")
            // 逻辑删除实体属性名称（实体）
            .logicDeletePropertyName("deleted")
            // 数据库表映射到实体的命名策略（不做任何改变，原样输出）（设置为：下划线转驼峰）
            .naming(NamingStrategy.underline_to_camel)
            // 数据库表字段映射到实体的命名策略（未指定按照 naming 执行）
            .columnNaming(NamingStrategy.underline_to_camel)
        // 添加表字段填充（基于数据库字段）
        // .addTableFills(new Column("create_time", FieldFill.INSERT))
        // 添加表字段填充（基于实体属性）
        // .addTableFills(new Property("updateTime", FieldFill.UPDATE))
        // 全局主键策略 自增、空、手动输入、雪花ID、UUID。。。等（不建议设置，如果是自增则会自动添加注解，其他情况手动指定）
        // .idType(IdType.AUTO)
        // 转换文件名称
        // .convertFileName(entityName -> entityName + "Entity")
        // 指定文件名格式化（会覆盖转换文件名称），即实体文件名后缀为Entity
        // .formatFileName("%sEntity")
        ;

        strategyConfigBuilder.mapperBuilder()
        // 开启 @Mapper 注解
        // .enableMapperAnnotation()
        // 开启baseResultMap
        // .enableBaseResultMap()
        // 开启baseColumnList
        // .enableBaseColumnList()
        // 设置缓存实现类
        // .cache(new EhcacheCache())
        // 文件名格式化，同上
        // .convertMapperFileName()
        // .convertXmlFileName()
        // .formatMapperFileName()
        // .formatXmlFileName()
        ;

        strategyConfigBuilder.serviceBuilder()
        // 格式化文件名
        // .convertServiceFileName()
        // .convertServiceImplFileName()
        // .formatServiceFileName()
        // .formatServiceImplFileName()
        ;

        // 控制器属性配置构建
        strategyConfigBuilder.controllerBuilder()
            // 开启驼峰转连字符 autoFill -> auto-fill
            .enableHyphenStyle()
            // 开启生成@RestController控制器
            .enableRestStyle()
        // 格式化文件名
        // .convertFileName()
        // .formatFileName()
        ;

        StrategyConfig strategyConfig = strategyConfigBuilder.build();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.global(globalConfig);
        autoGenerator.packageInfo(packageConfig);
        autoGenerator.strategy(strategyConfig);
        autoGenerator.template(templateConfig);

        autoGenerator.execute();
    }
}
