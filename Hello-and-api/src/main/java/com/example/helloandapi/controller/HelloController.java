package com.example.helloandapi.controller;

import com.example.helloandapi.model.UserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller = Spring MVC 里专门处理 HTTP 请求的类。
 *
 * @RestController = @Controller + @ResponseBody 的组合：
 *   - @Controller   告诉 Spring「这是一个处理 web 请求的类」，
 *                   启动时会被扫描并注册成 bean（容器里的对象）。
 *   - @ResponseBody 让方法的返回值「直接当作 HTTP 响应体」写回浏览器，
 *                   而不是被解析成视图名（JSP / Thymeleaf 模板）。
 *
 * 所以下面 home() 返回的字符串就是浏览器看到的纯文本。
 * 如果要返回对象（比如 User），Spring 会自动用 Jackson 序列化成 JSON——
 *   这就是「写 REST API」的标准姿势。
 */
@RestController
public class HelloController {

    /**
     * @GetMapping("/hello") = 「当浏览器发 GET 请求到 /hello 时，调用这个方法」。
     *
     * 完整 URL = http://<host>:<port><contextPath><mapping>
     *         = http://localhost:8080/hello
     *
     * 注意：路径**严格匹配**。
     *   - 当前只注册了 "/hello"，所以访问 "/" 会得到 404 (Whitelabel Error Page)。
     *   - 想让根路径也响应，要么把这里改回 "/"，要么再写一个方法 @GetMapping("/")。
     *
     * 同族注解还有：
     *   @PostMapping  处理 POST 请求（提交表单 / 新建资源）
     *   @PutMapping   处理 PUT  请求（整体更新）
     *   @DeleteMapping 处理 DELETE
     *   @RequestMapping 通用的，可指定 method = RequestMethod.GET
     */
//    @GetMapping("/hello")
//    public String home() {
//        // 因为类上有 @RestController，这个 String 不会被当成视图名查找模板，
//        // 而是直接作为响应体（Content-Type: text/plain）写给浏览器。
//        return "This is my first springboot application";
//    }
    /**
     * 进阶版：返回 JSON 而不是纯文本——这才是真正写 REST API 的姿势。
     *
     * 关键变化：返回类型从 String 变成 Map<String, String>。
     * 浏览器访问 http://localhost:8080/ 会看到：
     *   {
     *     "id": "1",
     *     "name": "Leanne Graham",
     *     "email": "leanne@example.com"
     *   }
     *
     * ── Java Map 是怎么变成 JSON 的？ ──────────────────────────
     * @RestController 类里的方法，返回值会被「消息转换器」处理：
     *   - 返回 String         → 当作纯文本写出（Content-Type: text/plain）
     *   - 返回 Map / List / 对象 → 由 Jackson 库自动序列化成 JSON
     *                              （Content-Type: application/json）
     * Spring Boot 在 starter-webmvc 里默认带了 Jackson，所以「Map → JSON」
     * 这一步是 0 配置自动完成的。
     *
     * ── Map.of(...) 是什么？ ─────────────────────────────────
     * Java 9+ 的工厂方法，一行创建一个**不可变** Map。
     * 参数按 (key1, value1, key2, value2, ...) 交替出现。
     * 比 new HashMap<>() + put() 简洁，适合写常量。
     *
     * ── JSON 和 Java 类型的对照 ──────────────────────────────
     *   JSON 对象 {}   ←→  Java Map / POJO
     *   JSON 数组 []   ←→  Java List / 数组
     *   "字符串"       ←→  String
     *   数字           ←→  int / double
     *   true / false   ←→  boolean
     *   null           ←→  null
     *
     * ── 真实项目里通常这么写 ─────────────────────────────────
     * 用一个自定义类（POJO / Record）替代 Map，类型更安全：
     *   public record User(String id, String name, String email) {}
     *
     *   @GetMapping("/")
     *   public User getUser() {
     *       return new User("1", "Leanne Graham", "leanne@example.com");
     *   }
     * Jackson 同样会把它序列化成上面那个 JSON。Map 适合「字段不固定」的场景。
     */
//    @GetMapping("/")
//    public Map<String, String> getUser() {
//        // Map.of 创建一个不可变 Map。Jackson 会把它转成 JSON 对象 {} 写回浏览器。
//        return Map.of(
//                "id", "1",
//                "name", "Leanne Graham",
//                "email", "leanne@example.com"
//        );
//    }

    /**
     * 接收 JSON ——「客户端 → Spring」方向的演示。
     *
     * ── 你刚才哪里写错了？ ────────────────────────────────────
     * 原写法： public <UserRequest> String addUser(@RequestBody UserRequest userRequest)
     *                ^^^^^^^^^^^^^
     *         尖括号里的内容是 Java「泛型方法的类型参数声明」，
     *         相当于在说「我定义一个叫 UserRequest 的占位类型」。
     *         结果：方法体里的 userRequest 类型是 Object（未知类型），
     *               .getName() / .getEmail() 编译就会报错。
     *
     * 正确写法：把尖括号删掉，让 UserRequest 指向真正的类
     *   （我已经新建了 UserRequest.java，是个 record）。
     *
     * ── 三个新注解 ──────────────────────────────────────────
     *   @PostMapping("/addUser")
     *       匹配 POST /addUser 请求。GET 不能带 body，要把 JSON 发上来必须 POST。
     *
     *   @RequestBody UserRequest userRequest
     *       告诉 Spring：把请求体里的 JSON「反序列化」成 UserRequest 对象再传给我。
     *       这是上节 Map → JSON 的反向操作，同样由 Jackson 自动完成。
     *
     * ── 完整数据流 ──────────────────────────────────────────
     *   [客户端]                    [Spring]                  [你的方法]
     *      │   POST /addUser          │                           │
     *      │   body: {                │                           │
     *      │     "name": "Jane",      │                           │
     *      │     "email": "..."       │                           │
     *      │   }                      │                           │
     *      ├─────────────────────────>│ Jackson 反序列化           │
     *      │                          │ JSON → UserRequest        │
     *      │                          ├──────────────────────────>│
     *      │                          │                           │ println(...)  → IDEA 控制台
     *      │                          │                           │ return "..."  → HTTP 响应体
     *      │   "User Jane added..."   │                           │
     *      │<─────────────────────────┤                           │
     *
     * ── 浏览器地址栏测不了，要用工具 ────────────────────────────
     * 浏览器只能发 GET。测 POST 用 curl / IDEA 的 .http 文件 / Postman：
     *
     *   curl -X POST http://localhost:8080/addUser -H "Content-Type: application/json" -d '{"name":"Jane Doe","email":"jane@example.com"}'
     */
    // Last login: Tue Apr 28 11:02:09 on ttys006
    //zzy@MacBook-Pro Hello-and-api %  curl -X POST http://localhost:8080/addUser -H "Content-Type: application/json" -d '{"name":"Jane Doe","email":"jane@example.com"}'
    //User Jane Doe added successfully!
    @PostMapping("/addUser")
    public String addUser(@RequestBody UserRequest userRequest) {
        // 打印到 IDEA 的 Run 控制台——给开发者调试看，客户端看不到
        System.out.println("Received user: " + userRequest.getName() + ", " + userRequest.getEmail());
        // return 的字符串会作为 HTTP 响应体返回给客户端
        return "User " + userRequest.getName() + " added successfully!";
    }
}
