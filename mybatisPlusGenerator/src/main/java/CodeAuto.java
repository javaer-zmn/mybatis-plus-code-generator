import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @description:
 * @author: ZMN
 * @create: 2023-04-12 09:10
 **/

@Slf4j
public class CodeAuto {

    private static final String DATABASE_NAME = "efm_db";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String PACKAGE_PARENT = "org.rxy";

    // 每次都生成该列表中的数据
    private static final String[] TABLE_LISTS = new String[]{"efm_distributed_lock"};

    public static void main(String[] args) {
        // 构建一个代码生成器对象
        AutoGenerator mpg = new AutoGenerator();

        // 配置策略
        // 1. 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor("ZMN");
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 2. 设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/" + DATABASE_NAME + "?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DATABASE_USERNAME);
        dsc.setPassword(DATABASE_PASSWORD);
        mpg.setDataSource(dsc);

        // 3. 包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT);
//        pc.setModuleName("dbInfo");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude(scanner("表名(与数据库一致，小写，多个表名用英文逗号隔开)").split(",")); // 设置要映射的表名
        strategy.setInclude(TABLE_LISTS); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setTablePrefix("t_402");
        strategy.setEntityLombokModel(true); // 自动lombok
        strategy.setLogicDeleteFieldName("IsDel"); // 逻辑删除
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);

        // 自动填充配置
//        TableFill addTime = new TableFill("AddDate", FieldFill.INSERT);
        // TableFill editTime = new TableFill("edit_time", FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(addTime);
        // tableFills.add(editTime);
//        strategy.setTableFillList(tableFills);
        // 乐观锁
        // strategy.setVersionFieldName("version");
        // strategy.setRestControllerStyle(true);
          // localhost:8080/hello_id_2
        mpg.setStrategy(strategy);

        // 配置自定义模板
        TemplateConfig templateConfig = new TemplateConfig()
                // 关闭默认 xml 生成，调整生成 至 根目录
                .setXml(null);
        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        templateConfig.setEntity("templates/entity.java.vm");
        templateConfig.setMapper("templates/mapper.java.vm");
        templateConfig.setXml("templates/mapper.xml.vm");
        templateConfig.setService("templates/service.java.vm");
        templateConfig.setServiceImpl("templates/serviceImpl.java.vm");
        templateConfig.setController("templates/controller.java.vm");
        mpg.setTemplate(templateConfig);

        System.err.println("开始执行!");
        mpg.execute(); // 执行
        System.err.println("执行结束!");

    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String help = "请输入" + tip +": ";
        log.info(help);
        if (scanner.hasNext()){
            String next = scanner.next();
            if (StringUtils.isNotBlank(next)){
                return next;
            }
        }
        throw new RuntimeException("请输入正确的" + tip + "!");

    }

}
