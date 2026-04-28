package com.example.helloandapi.controller;

import com.example.helloandapi.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class GetUserController {
    // http://localhost:8080/api/user/Alice
    @GetMapping("/Alice")
    public User getAlice(){
        return new User("Alice","Alice@email.com");
    }
}
