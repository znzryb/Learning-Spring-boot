package com.example.helloandapi.controller;

import com.example.helloandapi.model.ToDo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {
    List<ToDo> toDoList= new ArrayList<>();
    @GetMapping
    public List<ToDo> getToDoList(){
        return toDoList;
    }
    @PostMapping
    public String addTodo(@RequestBody ToDo toDo){
        toDoList.add(toDo);
        return "Todo added successfully";
    }
}
