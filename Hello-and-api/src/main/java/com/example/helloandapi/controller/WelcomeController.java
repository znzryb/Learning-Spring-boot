package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    // GET /api/welcome/user?name=YourName
    @GetMapping("/user")
    public String welcomeUser(@RequestParam String name) {
        // "Welcome, YourName!"
        return "Welcome, "+name+"!";
    }

    // GET /api/welcome/admin?name=YourName
    @GetMapping("/admin")
    public String welcomeAdmin(@RequestParam String name) {
        // "Welcome Admin, YourName!"
        return "Welcome Admin, "+name+"!";
    }
}
