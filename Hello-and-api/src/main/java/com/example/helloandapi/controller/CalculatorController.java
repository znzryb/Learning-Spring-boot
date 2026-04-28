package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.*;

// Representational State Transfer
@RestController
@RequestMapping("/api/calc")
public class CalculatorController {
    @GetMapping("/add")
    public int add(@RequestParam int a,@RequestParam int b){
        return a+b;
    }
    @GetMapping("/subtract")
    public int subtract(@RequestParam int a,@RequestParam int b){
        return a-b;
    }
    @GetMapping("/multiply")
    public int multiply(@RequestParam int a,@RequestParam int b){
        return a*b;
    }
    @GetMapping("/divide")
    public String divide(@RequestParam int a,@RequestParam int b){
        if(b==0) return "Cannot divide by zero";
        return Integer.toString(a/b);
    }
}
