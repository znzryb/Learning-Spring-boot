package com.example.dependencyinject;

import com.example.dependencyinject.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DependencyInjectApplication implements CommandLineRunner {

    private final OrderService orderService;

    public DependencyInjectApplication(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DependencyInjectApplication.class, args);
    }

    @Override
    public void run(String... args) {
        orderService.placeOrder();
    }
}
