package com.example.helloandapi.controller;

import com.example.helloandapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class PostUserController {
    private List<User> users=new ArrayList<>();

    @GetMapping
    public List<User> getUsers(){
        return users;
    }

    @PostMapping
    // curl -X POST 'http://localhost:8080/users?name=Alice&email=a@b.com'
    // public String createUsers(String name,String email){
    public String createUsers(@RequestBody User user){
        // curl -X POST http://localhost:8080/users -H 'Content-Type: application/json' -d '{"name":"Alice","email":"a@b.com"}'
        users.add(user);
        return "User added successfully!";
    }
}
