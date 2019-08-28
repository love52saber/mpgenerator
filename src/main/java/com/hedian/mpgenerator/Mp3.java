package com.hedian.mpgenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mp3 {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 1数据源配置
        //1.1mysql
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("hedian12#$");
//        dsc.setUrl("jdbc:mysql://106.14.224.216:3306/suzhou_risk_test?serverTimezone=GMT%2B8&useUnicode=true" +
//                "&characterEncoding=utf-8");
//        mpg.setDataSource(dsc);

        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useUnicode=true" +
                "&characterEncoding=utf-8");
        mpg.setDataSource(dsc);

        //1.2oracle
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:oracle:thin:@47.96.150.182:1521:akydb");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
//        dsc.setUsername("jsasst");
//        dsc.setPassword("jsasst2018");
//        mpg.setDataSource(dsc);

        // 2数据库表设置 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setLogicDeleteFieldName("use_flag");

        ArrayList<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("del_flag", FieldFill.INSERT));
        tableFillList.add(new TableFill("use_flag", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_id", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("modified_id", FieldFill.UPDATE));
        tableFillList.add(new TableFill("modified_time", FieldFill.UPDATE));
        strategy.setTableFillList(tableFillList);

//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        strategy.setInclude(new String[]{"FW_AUTH_WS_INFO","FW_AUTH_WS_DD","FW_AUTH_DD_DC_DETAIL","FW_AUTH_DD",
//                "FW_AUTH_WS_DC_DD_OPT", "FW_AUTH_DC_DD_INVOLVE_OPT"});
        strategy.setInclude(new String[]{"tbl_teacher"});
//        strategy.setSuperEntityColumns("id");   //此行放开不生成id字段
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("tbl_");//即使没有前缀也不能注释，否则生成的实体缺少注解
        strategy.setEntityTableFieldAnnotationEnable(true);//false下划线字段不生成注解，可能导致错误
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 3全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("D://");
        gc.setAuthor("gjyang");
        gc.setOpen(true);        //完成后打开文件夹
        gc.setFileOverride(true);
        gc.setDateType(DateType.ONLY_DATE);//数据库日期转换成什么格式Date或localDate
        gc.setIdType(IdType.UUID);
        gc.setSwagger2(true);//开启swagger2注解
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");

        gc.setBaseResultMap(true);//xml文件生成baseresult
        gc.setBaseColumnList(true);//xml文件生成baseresult

        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();

        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.hedain.transactiondemo");
        pc.setMapper("persistence.mapper");
        pc.setXml("persistence.mapper.xml");
        pc.setEntity("persistence.pojo");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义xml输出位置配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });

//        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // templateConfig.setEntity();
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        mpg.execute();
    }
}
