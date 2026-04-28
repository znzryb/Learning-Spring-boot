package com.example.helloandapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用的「入口类」。一个 Spring Boot 项目通常有且只有一个这种类。
 *
 * @SpringBootApplication 是个「三合一」组合注解，等价于同时写：
 * @SpringBootConfiguration 声明这是一个配置类（≈ @Configuration），里面可以用 @Bean 定义 bean。
 * @EnableAutoConfiguration 打开 Spring Boot 的「自动配置」魔法。它会扫描 classpath，看到
 * spring-boot-starter-webmvc 这个依赖，就自动给你装好：
 * - 内嵌 Tomcat
 * - DispatcherServlet（请求总分发器）
 * - Jackson（JSON 序列化）
 * - 默认错误页（那个 Whitelabel Error Page 就是它给的）
 * 这就是为什么你不写 web.xml、不配 Tomcat，也能跑起来。
 * @ComponentScan 从「当前类所在的包」开始，递归扫描所有子包，把带 @Component /
 * @Service / @Repository / @Controller / @RestController 等注解的类
 * 注册成 bean。
 * → 这就是 HelloController 不需要在哪儿手动登记，就能被发现并接管
 * /hello 路由的原因。
 * → ⚠️ 推论：你的 Controller 必须放在 com.example.helloandapi 或它
 * 的子包下；放在外面（比如 com.foo）会扫不到。
 */
@SpringBootApplication
public class HelloAndApiApplication {

    /**
     * 标准 Java main 方法。运行时 IntelliJ / java 命令从这里进入。
     * <p>
     * SpringApplication.run(...) 做的事：
     * 1. 创建 Spring 容器（ApplicationContext）
     * 2. 触发上面 @EnableAutoConfiguration 的自动装配
     * 3. 扫描并实例化所有 @Component / @RestController 类
     * 4. 启动内嵌 Tomcat 监听 8080 端口（可在 application.properties 里
     * 用 server.port=xxxx 改）
     * 5. 阻塞主线程，等请求进来
     * <p>
     * 第一个参数是「主配置类」，告诉 Spring 从哪里开始扫描；
     * 第二个参数 args 是命令行参数，可以用来覆盖配置（如 --server.port=9000）。
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloAndApiApplication.class, args);
    }

}
