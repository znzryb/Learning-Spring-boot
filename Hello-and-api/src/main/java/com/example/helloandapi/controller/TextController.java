package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/text")
public class TextController {
    @GetMapping("/uppercase")
    public String getUpperCase(@RequestParam String text){
        return text.toUpperCase();
    }
    @GetMapping("/lowercase")
    public String getLowerCase(@RequestParam String text){
        return text.toLowerCase();
    }
}

