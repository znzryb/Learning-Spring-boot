package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程「Getting Started with Controllers」示例。
 *
 * 关键新概念：@RequestMapping 放在「类」上 = 路径前缀。
 *
 *   @RequestMapping("/api/greet")     ← 类上：所有方法的 URL 前缀
 *      └─ @GetMapping("/hello")        ← 方法上：拼到前缀后面
 *      最终路径 = /api/greet/hello
 *
 * 这是组织多个相关接口的标准套路：把一组「问候相关」的接口
 * 都放在 /api/greet/* 下面，比如以后再加 @GetMapping("/bye")
 * 自动就是 /api/greet/bye，不用每个方法都写完整路径。
 *
 * ── 关于包路径（注意我跟课程不一样的地方）─────────────────
 * 课程包是 com.example.demo.controller，因为它项目根包是 com.example.demo。
 * 我们这个项目的根包是 com.example.helloandapi，所以放到
 * com.example.helloandapi.controller 下。
 *
 * 为什么放在 controller 子包？纯组织习惯，不影响功能：
 *   com.example.helloandapi
 *     ├─ HelloAndApiApplication.java   ← @SpringBootApplication 在这里
 *     ├─ controller/                    ← 所有 Controller 类
 *     ├─ service/                       ← 业务逻辑（以后会学）
 *     └─ model/  或  dto/               ← 数据模型（UserRequest 这种）
 * @SpringBootApplication 默认从「它所在的包」往下递归扫描，
 * 所以放到子包里照样能被发现并注册。
 *
 * ── 测试 ────────────────────────────────────────────
 * 浏览器访问：http://localhost:8080/api/greet/hello
 * 预期响应： Hello, World!
 *
 * 注意 @GetMapping 没指定路径就是匹配类前缀本身。比如再加：
 *   @GetMapping
 *   public String root() { return "greeting root"; }
 * 这条匹配 GET /api/greet（不带子路径）。
 */
@RestController
@RequestMapping("/api/greet")
public class GreetingController {

    // GET /api/greet/hello
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
