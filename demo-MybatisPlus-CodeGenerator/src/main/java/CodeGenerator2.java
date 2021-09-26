import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * 代码生成器简化版：删除模板中的日期、删除service接口、关闭乐观锁和逻辑删除
 *
 * 以 MySQL 为例
 *
 * 1. 修改最重要的设置
 *
 * 2. 运行 generator
 *
 * 3. 删除 iservice 文件夹
 *
 * 4. 拷贝代码至自己的项目
 *
 * PS 代码生成后推荐格式化一下，毕竟模板中可能有多余的空行或者空格或者import顺序不一样等等
 *
 * @author Max_Qiu
 */
public class CodeGenerator2 {
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
            // 覆盖已有文件（默认false）
            .fileOverride()
            // 关闭生成后自动打开文件夹（默认true）
            .disableOpenDir()
            // 文件输出目录（默认D:\\tmp）
            .outputDir("demo-MybatisPlus-CodeGenerator\\src\\main\\java")
            // 作者
            .author(AUTHOR)
            // 实体时间类型（使用Java8时间类，其他类型进入DateType枚举查看详细）
            .dateType(DateType.TIME_PACK)
            // 生成文件中的注释的日期（指定格式）
            .commentDate("yyyy-MM-dd")
            // 构建全局配置
            .build();

        // 包相关的配置项
        PackageConfig packageConfig = new PackageConfig.Builder()
            // 包名
            .parent(PARENT)
            // service接口包名（默认service）
            .service("iservice")
            // service实现类包名（默认service.impl）
            .serviceImpl("service")
            // 构建包配置对象
            .build();

        // 模板路径配置
        TemplateConfig templateConfig = new TemplateConfig.Builder()
            // 禁用所有模板
            // .disable()
            // 禁用指定模板
            .disable(TemplateType.SERVICE)
            // 实体模板路径(JAVA)
            .entity("mybatis2/entity.java")
            // service模板路径
            .service("mybatis2/service.java").serviceImpl("mybatis2/serviceImpl.java")
            // mapper模板路径
            .mapper("mybatis2/mapper.java")
            // mapperXml模板路径
            .mapperXml("mybatis2/mapper.xml")
            // 控制器模板路径
            .controller("mybatis2/controller.java")
            // 构建模板配置对象
            .build();

        // 策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder()
            // 开启跳过视图
            .enableSkipView()
            // 精确匹配排除表，生成表，模糊匹配排查表，生成表
            .addExclude(EXCLUDE)
            // 设置需要移除的表前缀
            .addTablePrefix(TABLE_PREFIX);

        // 实体策略配置
        strategyConfigBuilder.entityBuilder()
            // 开启生成serialVersionUID
            // .disableSerialVersionUID()
            // 开启链式模型，即实体可以连续set，例：.setXxx().setXxx();
            .enableChainModel()
            // 开启lombok模型
            .enableLombok()
            // 开启Boolean类型字段移除is前缀，即 is_deleted -> deleted
            .enableRemoveIsPrefix()
            // 开启生成实体时生成字段注解，即每个字段都设置 @TableId/@TableField
            .enableTableFieldAnnotation()
            // 数据库表映射到实体的命名策略（不做任何改变，原样输出）（设置为：下划线转驼峰）
            .naming(NamingStrategy.underline_to_camel)
            // 数据库表字段映射到实体的命名策略（未指定按照 naming 执行）
            .columnNaming(NamingStrategy.underline_to_camel)
            // 添加表字段填充（基于数据库字段）
            .addTableFills(new Column("create_time", FieldFill.INSERT))
            // 添加表字段填充（基于实体属性）
            .addTableFills(new Property("updateTime", FieldFill.UPDATE))
            // 开启 ActiveRecord 模式
            .enableActiveRecord();

        strategyConfigBuilder.serviceBuilder()
            // 格式化文件名
            .convertServiceImplFileName((entityName -> entityName + ConstVal.SERVICE));

        // 控制器属性配置构建
        strategyConfigBuilder.controllerBuilder()
            // 开启驼峰转连字符 autoFill -> auto-fill
            .enableHyphenStyle()
            // 开启生成@RestController控制器
            .enableRestStyle();

        StrategyConfig strategyConfig = strategyConfigBuilder.build();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.global(globalConfig);
        autoGenerator.packageInfo(packageConfig);
        autoGenerator.strategy(strategyConfig);
        autoGenerator.template(templateConfig);

        autoGenerator.execute();

    }
}
