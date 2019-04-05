## 背景说明

此次的代号为Gaby-Master，为什么有这个项目产生呢？是因为鄙人在开发过程中，发现其实有部分代码是可以抽离出来，然后横向切入到各个项目里，耦合性较低，而且如果提取出来整理的话，那么开发接口的速度会非常之快。所以我在此期间，整理了一套快速开发的框架，注意，这个是后端的代码框架，还未涉及到前端。

## 介绍

此框架分为parent,pom,web-common,web-plugin,mybatis-support几个模块。

### parent

此工程就是一个pom工程，用来做顶级父级工程，作用是设置从哪里下载依赖等等信息。

### pom

此工程是一个pom工程，用来做父级工程，作用是定义各种依赖的版本。

### web-common

此工程是一个jar工程，作用是一些公共的模型提取，还有常用的工具类可以定义在这里面，基本可以理解为每个项目都能用到的东西可以放在这里。

**父类工程**：pom工程

### web-plugin

此工程是一个jar工程，作用是为控制层也就是controller层提供帮助，比如拦截器，统一异常处理，请求和返回的格式统一等。

**父类工程**:pom工程

### mybatis-support

mybatis-support工程是一个jar工程，作用是为了生成mapper层的代码，也就是数据访问层，类似传统的mybatis生成器。

**父类工程**:pom工程

### code-generator(了解)

code-generator是一个业务代码生成器，相对于mybatis-support中的生成器，这是为了节省写后端接口速度的。

启动类:Generator.java

配置文件:generator.properties

```properties
#加载模版的位置
current.load.template.path=D:\\environment\\idea_workspace\\121tb\\demo-generator\\src\\main\\resources\\ftl\\


#项目工程路径
project.real.path=D:/maven_project
#项目的名称
project.name=demo-ssm
#项目工程的子工程名称
module.name.api=demo-ssm-api
module.name.facade=demo-ssm-service
module.name.ctr=demo-ssm-controller

#包前缀--一般是com+公司名
package.prefix=com.gaby
#项目名称
module.parent=ssm
#模块名称，多路径用.
module.name=teacher
#动作名称
action.name=query

#接口定义
refs=teacher

```



## 实操

**代码地址**:

**注意**:以下讲解的配置文件不懂放在哪里的话，可以看项目中的demo工程。

maven的安装操作我在此就不多说，认为读者具备该能力，以下我讲的直接是怎么快速搭建后端工程。

> 规范:项目名-api ，项目名-common ,项目名-controller,项目名-service,项目名-web

### web层(项目名-web)

该层就放相关的配置文件，其余不放。

#### servlet-web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <aop:aspectj-autoproxy proxy-target-class="true"/>
    <!-- 自动扫描该包，使SpringMVC认为包下用了@controller注解的类是控制器 -->
    <context:component-scan base-package="com.gaby.**" use-default-filters="false">
        <!-- 扫描@Controller -->
        <context:include-filter type="annotation"
                                expression="org.springframework.stereotype.Controller"/>

    </context:component-scan>

    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.ByteArrayHttpMessageConverter"/>
            <bean class="org.springframework.http.converter.FormHttpMessageConverter"/>
            <ref bean="stringHttpMessageConverter"/>
            <ref bean="fastJsonHttpMessageConverter"></ref>
        </mvc:message-converters>
    </mvc:annotation-driven>
    <!--stringHttpMessageConverter转换器-->
    <bean id="stringHttpMessageConverter"
          class="org.springframework.http.converter.StringHttpMessageConverter">
        <constructor-arg value="UTF-8" index="0"></constructor-arg><!--
            避免出现乱码 -->
        <property name="supportedMediaTypes">
            <list>
                <value>text/html;charset=UTF-8</value>
                <value>application/json;charset=UTF-8</value>
                <value>application/x-www-form-urlencoded;charset=UTF-8</value>
            </list>
        </property>
    </bean>
    <!--fastJsonHttpMessageConverter转换器-->
    <bean id="fastJsonHttpMessageConverter" class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4">
        <property name="supportedMediaTypes">
            <list>
                <value>text/html;charset=UTF-8</value>
                <value>application/json;charset=UTF-8</value>
                <value>application/x-www-form-urlencoded;charset=UTF-8</value>
            </list>
        </property>
        <property name="fastJsonConfig">
            <bean class="com.alibaba.fastjson.support.config.FastJsonConfig">
                <property name="features">
                    <list>
                        <value>AllowArbitraryCommas</value>
                        <value>AllowUnQuotedFieldNames</value>
                        <value>DisableCircularReferenceDetect</value>
                    </list>
                </property>
                <!--<property name="dateFormat" value="yyyy-MM-dd HH:mm:ss"></property>-->
            </bean>
        </property>
    </bean>
    <mvc:resources mapping="/img/**" location="/img/" />
    <mvc:resources mapping="/css/**" location="/css/" />
    <mvc:resources mapping="/js/**" location="/js/" />
    <mvc:resources mapping="/plugins/**" location="/plugins/" />
    <mvc:resources mapping="/*/**.html" location="/" />
    <mvc:resources mapping="/*.html" location="/" />
    <!-- 定义跳转的文件的前后缀 ，视图模式配置-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 这里的配置我的理解是自动给后面action的方法return的字符串加上前缀和后缀，变成一个 可用的url地址 -->
        <property name="prefix" value="/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

#### spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
	http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!-- 数据库连接池 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close">
        <property name="url" value="${jdbc.url}" />
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />
        <property name="driverClassName" value="${jdbc.driver}" />
        <property name="maxActive" value="${jdbc.maxActive}" />
        <property name="minIdle" value="${jdbc.minIdle}" />
    </bean>
    <!-- 让spring管理sqlsessionfactory 使用mybatis和spring整合包中的 -->
    <!-- SqlSessionFactory -->
    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
        <property name="configLocation" value="classpath:xml/spring-mybatis.xml"></property>
        <!--<property name="plugins">-->
        <!--<array>-->
        <!--<bean id="paginationInterceptor" class="com.baomidou.mybatisplus.plugins.PaginationInterceptor">-->
        <!--<property name="dialectType" value="mysql"/>-->
        <!--</bean>-->
        <!--</array>-->
        <!--</property>-->
        <!-- 自动扫描 Xml 文件位置 -->
        <property name="mapperLocations">
            <list>
                <value>classpath*:com/gaby/**/mapper/xml/**/*.xml</value>
            </list>
        </property>
        <!--<property name="typeAliasesPackage" value="com.pinyougou.**.pojo"/>-->
        <!--<property name="typeAliasesSuperType" value="java.lang.Object"/>-->

        <property name="globalConfig" ref="globalConfig" />
    </bean>
    <bean id="globalConfig" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
        <!--
            AUTO->`0`("数据库ID自增")
             INPUT->`1`(用户输入ID")
            ID_WORKER->`2`("全局唯一ID")
            UUID->`3`("全局唯一ID")
        -->
        <!--<property name="idType" value="2" />-->
        <!--
            MYSQL->`mysql`
            ORACLE->`oracle`
            DB2->`db2`
            H2->`h2`
            HSQL->`hsql`
            SQLITE->`sqlite`
            POSTGRE->`postgresql`
            SQLSERVER2005->`sqlserver2005`
            SQLSERVER->`sqlserver`
        -->
        <!-- Oracle需要添加该项 -->
        <!-- <property name="dbType" value="oracle" /> -->
        <!-- 全局表为下划线命名设置 true -->
        <property name="dbColumnUnderline" value="true" />
    </bean>

    <!--事务-->
    <bean id="txTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--通知-->
    <tx:advice id="txAdvice" transaction-manager="txTransactionManager">
        <tx:attributes>
            <tx:method name="query*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="save*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="add*" propagation="REQUIRED" rollback-for="Exception" />
            <tx:method name="create*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="add**" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="insert*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="update*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="edit*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="modify*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="delete*" propagation="REQUIRED" rollback-for="Exception"/>
            <tx:method name="del*" propagation="REQUIRED" rollback-for="Exception"/>
        </tx:attributes>
    </tx:advice>
    <!--声明式事务-->
    <tx:annotation-driven transaction-manager="txTransactionManager"></tx:annotation-driven>
    <!--配置自定义切面类-->
    <!--<bean id="insertLastIdAsp" class="com.pinyougou.aspect.LastInsertIdAspectHelper"></bean>-->

    <!--配置切面-->
    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(* com.gaby..*.facade..*.*(..))"></aop:pointcut>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
    </aop:config>
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.gaby.**.mapper" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
</beans>
```

#### spring-main.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-3.1.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <aop:aspectj-autoproxy proxy-target-class="true"/>

    <!-- 启用注解扫描，并定义组件查找规则 ，除了@controller，扫描所有的Bean -->
    <context:component-scan base-package="com.gaby.**">
        <context:exclude-filter type="annotation"
                                expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <bean id="propertyConfigurer"
          class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations" value="classpath*:properties/*.properties" />
    </bean>
</beans>
```

#### spring-mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
		PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

	<!--驼峰式命名允许-->
	<settings>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>

	<plugins>
		<!-- com.github.pagehelper 为 PageHelper 类所在包名 -->
		<plugin interceptor="com.github.pagehelper.PageHelper">
			<!-- 设置数据库类型 Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL 六种数据库-->
			<property name="dialect" value="mysql"/>
		</plugin>
	</plugins>

</configuration>
```

#### logback.xml

```xml
<!-- 级别从高到低 OFF 、 FATAL 、 ERROR 、 WARN 、 INFO 、 DEBUG 、 TRACE 、 ALL -->
<!-- 日志输出规则 根据当前ROOT 级别，日志输出时，级别高于root默认的级别时 会输出 -->
<!-- 以下 每个配置的 filter 是过滤掉输出文件里面，会出现高级别文件，依然出现低级别的日志信息，通过filter 过滤只记录本级别的日志 -->
<!-- scan 当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。 -->
<!-- scanPeriod 设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug 当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!-- 动态日志级别 -->
    <jmxConfigurator />
    <!-- 定义日志文件 输出位置 -->
    <!-- <property name="log_dir" value="C:/test" />-->
    <property name="log_dir" value="/home/hadmin/data/logs/src" />
    <!-- 日志最大的历史 30天 -->
    <property name="maxHistory" value="30" />

    <!-- ConsoleAppender 控制台输出日志 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                <!-- 设置日志输出格式 -->
                %d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- ERROR级别日志 -->
    <!-- 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件 RollingFileAppender -->
    <appender name="ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 过滤器，只记录WARN级别的日志 -->
        <!-- 果日志级别等于配置级别，过滤器会根据onMath 和 onMismatch接收或拒绝日志。 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 设置过滤级别 -->
            <level>ERROR</level>
            <!-- 用于配置符合过滤条件的操作 -->
            <onMatch>ACCEPT</onMatch>
            <!-- 用于配置不符合过滤条件的操作 -->
            <onMismatch>DENY</onMismatch>
        </filter>
        <!-- 最常用的滚动策略，它根据时间来制定滚动策略.既负责滚动也负责出发滚动 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志输出位置 可相对、和绝对路径 -->
            <fileNamePattern>
                ${log_dir}/error/%d{yyyy-MM-dd}/error-log.log
            </fileNamePattern>
            <!-- 可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件假设设置每个月滚动，且<maxHistory>是6， 则只保存最近6个月的文件，删除之前的旧文件。注意，删除旧文件是，那些为了归档而创建的目录也会被删除 -->
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>
                <!-- 设置日志输出格式 -->
                %d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n
            </pattern>
        </encoder>
    </appender>

    <!-- WARN级别日志 appender -->
    <appender name="WARN" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 过滤器，只记录WARN级别的日志 -->
        <!-- 果日志级别等于配置级别，过滤器会根据onMath 和 onMismatch接收或拒绝日志。 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- 设置过滤级别 -->
            <level>WARN</level>
            <!-- 用于配置符合过滤条件的操作 -->
            <onMatch>ACCEPT</onMatch>
            <!-- 用于配置不符合过滤条件的操作 -->
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志输出位置 可相对、和绝对路径 -->
            <fileNamePattern>${log_dir}/warn/%d{yyyy-MM-dd}/warn-log.log</fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- INFO级别日志 appender -->
    <appender name="INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log_dir}/info/%d{yyyy-MM-dd}/info-log.log</fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- DEBUG级别日志 appender -->
    <appender name="DEBUG" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log_dir}/debug/%d{yyyy-MM-dd}/debug-log.log</fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- TRACE级别日志 appender -->
    <appender name="TRACE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>TRACE</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log_dir}/trace/%d{yyyy-MM-dd}/trace-log.log</fileNamePattern>
            <maxHistory>${maxHistory}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- root级别 DEBUG -->
    <root>
        <!-- 打印debug级别日志及以上级别日志 -->
        <level value="debug" />
        <!-- 控制台输出 -->
        <appender-ref ref="console" />
        <!-- 文件输出 -->
        <appender-ref ref="ERROR" />
        <appender-ref ref="INFO" />
        <appender-ref ref="WARN" />
        <appender-ref ref="DEBUG" />
        <appender-ref ref="TRACE" />
    </root>
</configuration>
```

#### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
<display-name>Archetype Created Web Application</display-name>
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:xml/spring-*.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- 解决post乱码 -->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 指定加载的配置文件 ，通过参数contextConfigLocation加载-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:xml/servlet-web.xml</param-value>
    </init-param>
  </servlet>

  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>

```

#### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>demo-ssm</artifactId>
        <groupId>com.gaby</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>demo-ssm-web</artifactId>
    <packaging>war</packaging>

    <name>demo-ssm-web Maven Webapp</name>

    <dependencies>

        <!--模块依赖-->
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>demo-ssm-controller</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
    </dependencies>
</project>

```

### controller层(项目名-controller)

#### 建包规范

> com.公司名.项目名.controller.模块名

**举例**

```java
package com.gaby.stu.controller.student;

import com.gaby.model.BaseResponse;
import com.gaby.web.plugin.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {
    @RequestMapping("hello")
    public BaseResponse hello() {
        return new BaseResponse();
    }
}
```

```
访问地址:http://localhost:8080/student/hello
```

```
结果:{"result":{"message":"请求成功","success":true}}
```

#### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>demo-ssm</artifactId>
        <groupId>com.gaby</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>demo-ssm-controller</artifactId>

    <name>demo-ssm-controller</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <!--模块依赖-->
    <dependencies>
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>demo-ssm-service</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--web-plugin引入-->
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>web-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

    </dependencies>

</project>

```

### service层(项目名-service)

#### 建包规范

> com.公司名.项目名.service.模块名
>
> com.公司名.项目名.service.模块名.impl
>
> com.公司名.项目名.facade.模块名
>
> com.公司名.项目名.facade.模块名.impl
>
> com.公司名.项目名.mapper.dao.模块名
>
> com.公司名.项目名.mapper.xml.模块名

自定义的Service接口要继承BaseService接口，接口的实现类要继承BaseServiceImpl类。这样可以达到后期的可扩展。

facade类作用就是以往的比Service大一层的Service类，其实就是为了好控制事务，因为如果事务放在service类上，那么可能一个逻辑调用不同的service来完成，那么如果出现异常，不能回滚了，所以用facade层来写业务。

mapper包里的就是数据访问层的代码。

**举例**

```java

```

#### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>demo-ssm</artifactId>
        <groupId>com.gaby</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>demo-ssm-service</artifactId>

    <name>demo-ssm-service</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <!--模块依赖-->
    <dependencies>
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>demo-ssm-api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>mybatis-support</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--数据库连接-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
    </dependencies>

    <build>
        <!-- 不拦截xml -->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
    </build>
</project>

```

### common层(项目名-common)

#### 建包规范

> com.公司名.项目名.common.util

该项目主要写一些公共的东西，也比较少用到，因为最后有用的可以合并到web-common项目中。

### api层(项目名-api)

#### 建包规范

> com.公司名.项目名.api.model.模块.功能.实体类
>
> com.公司名.项目名.api.dubbo
>
> com.公司名.项目名.api.其他业务相关

这里面基本放的都是实体类。经常使用的是出入参可以放在这里。

#### pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>demo-ssm</artifactId>
        <groupId>com.gaby</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>demo-ssm-api</artifactId>

    <name>demo-ssm-api</name>

    <dependencies>
        <dependency>
            <groupId>com.gaby</groupId>
            <artifactId>web-common</artifactId>
        </dependency>
    </dependencies>
</project>

```

### recourse文件夹

#### application.properties

```properties

```



#### db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/stu_score?characterEncoding=utf-8
jdbc.username=root
jdbc.password=123456
jdbc.maxActive=10
jdbc.minIdle=5
```



#### log4j.properties

```properties
# priority  :debug<info<warn<error
#you cannot specify every priority with different file for log4j 
log4j.rootLogger=debug,stdout,info,debug,warn,error 
 
#console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender 
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout 
log4j.appender.stdout.layout.ConversionPattern= [%d{yyyy-MM-dd HH:mm:ss a}]:%p %l%m%n
#info log
log4j.logger.info=info
log4j.appender.info=org.apache.log4j.DailyRollingFileAppender 
log4j.appender.info.DatePattern='_'yyyy-MM-dd'.log'
log4j.appender.info.File=d\:/test/info/info.log
log4j.appender.info.Append=true
log4j.appender.info.Threshold=INFO
log4j.appender.info.layout=org.apache.log4j.PatternLayout 
log4j.appender.info.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss a} [Thread: %t][ Class:%c >> Method: %l ]%n%p:%m%n
#debug log
log4j.logger.debug=debug
log4j.appender.debug=org.apache.log4j.DailyRollingFileAppender 
log4j.appender.debug.DatePattern='_'yyyy-MM-dd'.log'
log4j.appender.debug.File=d\:/test/debug/debug.log
log4j.appender.debug.Append=true
log4j.appender.debug.Threshold=DEBUG
log4j.appender.debug.layout=org.apache.log4j.PatternLayout 
log4j.appender.debug.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss a} [Thread: %t][ Class:%c >> Method: %l ]%n%p:%m%n
#warn log
log4j.logger.warn=warn
log4j.appender.warn=org.apache.log4j.DailyRollingFileAppender 
log4j.appender.warn.DatePattern='_'yyyy-MM-dd'.log'
log4j.appender.warn.File=d\:/test/warn/warn.log
log4j.appender.warn.Append=true
log4j.appender.warn.Threshold=WARN
log4j.appender.warn.layout=org.apache.log4j.PatternLayout 
log4j.appender.warn.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss a} [Thread: %t][ Class:%c >> Method: %l ]%n%p:%m%n
#error
log4j.logger.error=error
log4j.appender.error = org.apache.log4j.DailyRollingFileAppender
log4j.appender.error.DatePattern='_'yyyy-MM-dd'.log'
log4j.appender.error.File = d\:/test/error/error.log 
log4j.appender.error.Append = true
log4j.appender.error.Threshold = ERROR 
log4j.appender.error.layout = org.apache.log4j.PatternLayout
log4j.appender.error.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss a} [Thread: %t][ Class:%c >> Method: %l ]%n%p:%m%n
```

## redis

### pom.xml

```xml
<!-- 缓存 -->
		<dependency> 
		  <groupId>redis.clients</groupId> 
		  <artifactId>jedis</artifactId> 
		  <version>2.8.1</version> 
		</dependency> 
		<dependency> 
		  <groupId>org.springframework.data</groupId> 
		  <artifactId>spring-data-redis</artifactId> 
		  <version>1.7.2.RELEASE</version> 
		</dependency>	
```

### 配置文件

spring-redis.xml

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans" 
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p" 
  xmlns:context="http://www.springframework.org/schema/context" 
  xmlns:mvc="http://www.springframework.org/schema/mvc" 
  xmlns:cache="http://www.springframework.org/schema/cache"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
            http://www.springframework.org/schema/beans/spring-beans.xsd   
            http://www.springframework.org/schema/context   
            http://www.springframework.org/schema/context/spring-context.xsd   
            http://www.springframework.org/schema/mvc   
            http://www.springframework.org/schema/mvc/spring-mvc.xsd 
            http://www.springframework.org/schema/cache  
            http://www.springframework.org/schema/cache/spring-cache.xsd">  
  
   <context:property-placeholder location="classpath*:properties/*.properties" />   
  
   <!-- redis 相关配置 --> 
   <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">  
     <property name="maxIdle" value="${redis.maxIdle}" />   
     <property name="maxWaitMillis" value="${redis.maxWait}" />  
     <property name="testOnBorrow" value="${redis.testOnBorrow}" />  
   </bean>  
  
   <bean id="JedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" 
       p:host-name="${redis.host}" p:port="${redis.port}" p:password="${redis.pass}" p:pool-config-ref="poolConfig"/>  
   
   <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">  
    	<property name="connectionFactory" ref="JedisConnectionFactory" />  
   </bean>  
 
</beans>  
```

redis.properties

```properties
# Redis settings 
# server IP 
redis.host=127.0.0.1
# server port 
redis.port=6379
# server pass 
redis.pass=
# use dbIndex 
redis.database=0
redis.maxIdle=300  
redis.maxWait=3000  
redis.testOnBorrow=true
```

