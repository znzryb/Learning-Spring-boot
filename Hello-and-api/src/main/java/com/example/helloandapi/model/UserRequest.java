package com.example.helloandapi.model;

/**
 * UserRequest = 「客户端发过来的用户数据」的 Java 模型，传统 POJO 写法。
 *
 * POJO = Plain Old Java Object，就是「一个普普通通的 Java 类」：
 *   - 私有字段（private）
 *   - 公开的 getter / setter
 *   - （通常）一个无参构造方法（不写也行，编译器会自动给你一个默认的）
 *
 * Jackson 反序列化 POJO 的工作流程：
 *   1. 调用无参构造，先 new 一个空的 UserRequest 出来
 *   2. 看到 JSON 里的 "name" → 调用 setName(...) 把值塞进去
 *   3. 看到 JSON 里的 "email" → 调用 setEmail(...) 把值塞进去
 * 所以 setter 必须是 public，方法名要严格按 set + 字段名（首字母大写）。
 *
 * 取值时调 getName() / getEmail()，跟课程例子一致。
 */
public class UserRequest {

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
