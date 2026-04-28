package com.example.helloandapi.controller;


import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    // TODO 1: Create an endpoint to return Apple
    @GetMapping("/apple")
    public Fruit getApple() {
        // Return a Fruit object with name "Apple" and taste "Sweet"
        return new Fruit("Apple","Sweet");
    }

    // TODO 2: Create an endpoint to return Banana
    @GetMapping("/banana")
    public Fruit getBanana() {
        // Return a Fruit object with name "Banana" and taste "Sweet"
        return new Fruit("Banana","Sweet");
    }

    // TODO 3: Create an endpoint to return Lemon
    @GetMapping("/lemon")
    public Fruit getLemon() {
        // Return a Fruit object with name "Lemon" and taste "Sour"
        return new Fruit("Lemon","Sour");
    }

    // TODO 4: Create an endpoint to return a list of all fruits
    @GetMapping("/all")
    public List<Fruit> getAllFruits() {
        return List.of(getApple(),getBanana(),getLemon());
    }
}

// Fruit class (already completed)
class Fruit {
    private String name;
    private String taste;

    public Fruit(String name, String taste) {
        this.name = name;
        this.taste = taste;
    }

    public String getName() { return name; }
    public String getTaste() { return taste; }
}
