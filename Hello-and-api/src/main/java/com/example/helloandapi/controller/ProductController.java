package com.example.helloandapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    List<String> products = new ArrayList<>();

    public ProductController() {
        products.add("Laptop");      // 下标 0
        products.add("Mobile");      // 下标 1
        products.add("Headphones");  // 下标 2
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable int id) {
        if (id < 0 || id >= products.size()) {
            return "Product not found";
        }
        return products.get(id);
    }

    @GetMapping
    public List<String> getAllProducts() {
        return products;
    }
}

