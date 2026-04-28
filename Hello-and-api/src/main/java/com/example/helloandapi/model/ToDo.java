package com.example.helloandapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDo {
    private String title;
    private boolean completed;

    @JsonCreator
    public ToDo(@JsonProperty(value = "title", required = true) String title,
                @JsonProperty(value = "completed", required = true) boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    /**
     * ⚠️ 问题：title 漏传时静默 200，title 字段塞 null 进列表 —— 脏数据无声入库。
     * 复现 POST： curl -i -X POST http://localhost:8080/todos -H 'Content-Type: application/json' -d '{"completed":false}'
     * 查询 GET：  curl -i http://localhost:8080/todos
     * [{"title":null,"completed":false}]
     * 修法：@JsonCreator + 参数上 @JsonProperty(value="X", required=true)（实测：字段上单标 @JsonProperty 无效；改 POJO 也无效；二者必须配合）。
     */
//    public ToDo(String title, boolean completed) {
//        this.title = title;
//        this.completed = completed;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
