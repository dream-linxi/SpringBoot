---

>**第 1 章 Spring Boot 入门**
>**第 2 章 Spring Boot 配置**
>**第 3 章 Spring Boot 与日志**
>**第 4 章 Spring Boot 与 Web 开发**
>**第 5 章 Spring Boot 与 Docker**
>**第 6 章 Spring Boot 与数据访问**
>**第 7 章 Spring Boot 启动配置原理**
>**第 8 章 Spring Boot 自定义 starters**
>**第 9 章 Spring Boot 与缓存**
>**第 10 章 Spring Boot 与消息**
>**第 11 章 Spring Boot 与检索**
>**第 12 章 Spring Boot 与任务**
>**第 13 章 Spring Boot 与安全**
>**第 14 章 Spring Boot 与分布式**
>**第 15 章 Spring Boot 与开发热部署**
>**第 16 章 Spring Boot 与监控管理**
>
>---



# 第 1 章  Spring Boot 入门

## 1.1 简介

​		Spring Boot来简化Spring应用开发，约定大于配置，去繁从简，just run就能创建一个独立的，产品级别的应用。

**背景：**

- J2EE笨重的开发、繁多的配置、低下的开发效率、复杂的部署流程、第三方技术集成难度大。

 **优点：**

- 快速创建独立运行的Spring项目以及与主流框架集成
- 使用嵌入式的Servlet容器，应用无需打成WAR包
- starters自动依赖与版本控制
- 大量的自动配置，简化开发，也可修改默认值
- 无需配置XML，无代码生成，开箱即用
- 准生产环境的运行时应用监控
- 与云计算的天然集成

**环境要求：**

- jdk1.8

- maven3.x

- IntelliJ IDEA 2017

- Spring Boot 1.5.9.RELEASE



## 1.2 HelloWorld 

- **maven 的 settings.xml 配置文件的 profiles 标签添加**

```xml
<profile>
  <id>jdk-1.8</id>
  <activation>
    <activeByDefault>true</activeByDefault>
    <jdk>1.8</jdk>
  </activation>
  <properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
  </properties>
</profile>
```

- **功能介绍**：浏览器发送hello请求，服务器接受请求并处理，响应Hello World字符串；



### 1.2.1 创建 maven 项目

![image-20200523133323896](\images\image-20200523133323896.png)



### 1.2.2 引入 starters

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
    </parent>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```



### 1.2.3 创建主程序

```java
package com.dreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication   // 该注解用户表示这是一个 SpringBoot 项目
public class HelloWorldMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(HelloWorldMain.class,args);
    }
}
```



### 1.2.4 编写业务类

```java
package com.dreams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController
{

    @ResponseBody
    @RequestMapping("/hello")
    public String hello()
    {
        return "Hello World!";
    }
}
```



### 1.2.5 运行主程序测试

![image-20200523134057734](\images\image-20200523134057734.png)



### 1.2.6 简化部署

```xml
 	<!-- 这个插件，可以将应用打包成一个可执行的jar包；-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```



## 1.3 HelloWorld 探究

### 1.3.1 POM 文件

1、父项目

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>

他的父项目是
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-dependencies</artifactId>
  <version>1.5.9.RELEASE</version>
  <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
他来真正管理Spring Boot应用里面的所有依赖版本；
```

​		Spring Boot的版本仲裁中心；以后我们导入依赖默认是不需要写版本；（没有在dependencies里面管理的依赖自然需要声明版本号）

2、启动器（starters）

​		Spring Boot 为我们提供了简化企业级开发绝大多数场景的 starter pom（启动器），只要引入了相应场景的starter pom，相关技术的绝大部分配置将会消除（自动配置），从而简化我们开发。业务中我们就会使用到Spring Boot 为我们自动配置的 bean。



###1.3.2 主程序入口

- @**SpringBootApplication 注解**

​         Spring Boot 应用标注在某个类上说明这个类是 SpringBoot 的主配置类，SpringBoot 就应该运行这个类的 main 方法来启动 SpringBoot 应用。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
      @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
      @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```



- @**SpringBootConfiguration**：Spring Boot 的配置类

  标注在某个类上，表示这是一个Spring Boot的配置类；

  - @**Configuration**:配置类上来标注这个注解

    配置类 -----  配置文件；配置类也是容器中的一个组件；@Component



- @**EnableAutoConfiguration**：开启自动配置功能

​        以前我们需要配置的东西，Spring Boot 帮我们自动配置；@**EnableAutoConfiguration **告诉SpringBoot 开启自动配置功能；这样自动配置才能生效；

```java
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

​		@**AutoConfigurationPackage**：自动配置包

​		@**Import**(AutoConfigurationPackages.Registrar.class)：

​		Spring的底层注解@Import，给容器中导入一个组件；导入的组件由AutoConfigurationPackages.Registrar.class；

​		 将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器；

​		@**Import**(EnableAutoConfigurationImportSelector.class)；

​		给容器中导入组件？

​		@**EnableAutoConfigurationImportSelector**：导入哪些组件的选择器；

​		将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；

​		会给容器中导入非常多的自动配置类（xxxAutoConfiguration）；就是给容器中导入这个场景需要的所有组件，并配置好这些组件；

![image-20200523141723033](\images\image-20200523141723033.png)

有了自动配置类，免去了我们手动编写配置注入功能组件等的工作；

​		SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class,classLoader)；



​		Spring Boot 在启动的时候从类路径下的 META-INF/spring.factorie s中获取 EnableAutoConfiguration 指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作；以前我们需要自己配置的东西，自动配置类都帮我们；

J2EE的整体整合解决方案和自动配置都在 spring-boot-autoconfigure-1.5.9.RELEASE.jar；



##1.4 使用Spring Initializer快速创建Spring Boot项目

IDE都支持使用Spring的项目创建向导快速创建一个Spring Boot项目；

选择我们需要的模块；向导会联网创建Spring Boot项目；

默认生成的Spring Boot项目；

- 主程序已经生成好了，我们只需要我们自己的逻辑
- resources文件夹中目录结构
  - static：保存所有的静态资源； js css  images；
  - templates：保存所有的模板页面；（Spring Boot默认 jar 包使用嵌入式的 Tomcat，默认不支持 JSP 页面）；可以使用模板引擎（freemarker、thymeleaf）；
  - application.properties：Spring Boot 应用的配置文件；可以修改一些默认设置；



#第 2 章 Spring Boot 配置

##2.1 配置文件

SpringBoot 使用一个全局的配置文件，配置文件名是固定的；

- application.properties

- application.yml

**配置文件的作用：**修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好；

**YAML（YAML Ain't Markup Language）**

​	YAML  A Markup Language：是一个标记语言

​	YAML   isn't Markup Language：不是一个标记语言；

**标记语言：**

​	以前的配置文件；大多都使用的是  **xxxx.xml**文件；

​	YAML：**以数据为中心**，比json、xml等更适合做配置文件；

​	**YAML：**配置例子

```yaml
server:
  port: 8081
```

​	**XML：**

```xml
<server>
	<port>8081</port>
</server>
```

###2.1.1 YAML 语法

1、基本语法

k:(空格)v：表示一对键值对（空格必须有）；

以**空格**的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的

```
server:
    port: 8081
    path: /hello
```

**注意：**属性和值也是大小写敏感；

###2.1.2 值的写法

#### 字面量：普通的值（数字，字符串，布尔）

​	k: v：字面直接来写；

​		字符串默认不用加上单引号或者双引号；

​		""：双引号；不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思

​				name:   "zhangsan \n lisi"：输出；zhangsan 换行  lisi

​		''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据

​				name:   ‘zhangsan \n lisi’：输出；zhangsan \n  lisi

#### 对象、Map（属性和值）（键值对）：

​	k: v：在下一行来写对象的属性和值的关系；注意缩进

​		对象还是k: v的方式

```yml
friends:
		lastName: zhangsan
		age: 20
```



##2.2 加载顺序



##2.3 配置原理
