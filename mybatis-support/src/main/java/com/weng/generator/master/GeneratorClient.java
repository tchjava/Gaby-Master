package com.weng.generator.master;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

public class GeneratorClient {
    public static void runMybatisPlusGenerator() {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig config = new GlobalConfig();
        //项目路径
        config.setOutputDir(PropertiesUtil.getValue("project.path") +"\\src\\main\\java");
        config.setFileOverride(true);
        config.setActiveRecord(false);
        config.setEnableCache(false);// XML 二级缓存
        config.setBaseResultMap(true);// XML ResultMap
        config.setBaseColumnList(true);// XML columList
        config.setAuthor("Gaby");
        config.setIdType(IdType.AUTO);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        config.setMapperName("%sMapper");
        config.setXmlName("%sMapper");
        config.setServiceName("%sService");
        config.setServiceImplName("%sServiceImpl");
        //config.setControllerName("%sController");
        autoGenerator.setGlobalConfig(config);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName(PropertiesUtil.getValue("jdbc.driverName"));
        dataSourceConfig.setUsername(PropertiesUtil.getValue("jdbc.username"));
        dataSourceConfig.setPassword(PropertiesUtil.getValue("jdbc.password"));
        dataSourceConfig.setUrl(PropertiesUtil.getValue("jdbc.url"));
        autoGenerator.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix("bmd_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(PropertiesUtil.getTables("tables")); // 需要生成的表

        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 字段名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 自定义实体父类
//        strategy.setSuperEntityClass("com.maxbill.base.SuperModel");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
//        strategy.setSuperMapperClass("com.maxbill.base.SuperMapper");
        // 自定义 service 父类
//        strategy.setSuperServiceClass("com.maxbill.base.SuperService");
        // 自定义 service 实现类父类
//        strategy.setSuperServiceImplClass("com.maxbill.base.SuperServiceImpl");
        // 自定义 controller 父类
//        strategy.setSuperControllerClass("com.maxbill.base.SuperController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategy.setEntityColumnConstant(PropertiesUtil.isEntityColumnConstant());
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        strategy.setEntityBuilderModel(true);
        autoGenerator.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PropertiesUtil.getValue("mp.parent"));
        //packageConfig.setController(PropertiesUtil.getValue("mp.controllerName"));
        packageConfig.setEntity(PropertiesUtil.getValue("mp.entityName"));
        packageConfig.setService(PropertiesUtil.getValue("mp.serviceName"));
        packageConfig.setServiceImpl(PropertiesUtil.getValue("mp.serviceImplName"));
        packageConfig.setMapper(PropertiesUtil.getValue("mp.mapperName"));
        //packageConfig.setXml(PropertiesUtil.getValue("mp.mapperXmlName"));
//        packageConfig.setModuleName("base");
        autoGenerator.setPackageInfo(packageConfig);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("maxbill", this.getConfig().getGlobalConfig().getAuthor() + "-MybatisPlus");
                map.put("author", this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        autoGenerator.setCfg(injectionConfig);
        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setEntity("./template/my.entity.java.vm");
        tc.setMapper("./template/mapper.java.vm");
        tc.setService("./template/service.java.vm");
        tc.setServiceImpl("./template/serviceImpl.java.vm");
        autoGenerator.setTemplate(tc);
        // 执行生成
        autoGenerator.execute();
        // 打印注入设置
        System.err.println(autoGenerator.getCfg().getMap().get("maxbill"));
    }

    public static void main(String[] args){
        runMybatisPlusGenerator();
    }
}
