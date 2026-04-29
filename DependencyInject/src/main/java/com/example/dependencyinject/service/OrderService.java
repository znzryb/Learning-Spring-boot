package com.example.dependencyinject.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final PaymentServiceInterface paymentService;

    // Constructor Injection
    public OrderService(@Qualifier("netBankingPaymentService") PaymentServiceInterface paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.pay();
        System.out.println("Order placed");
    }
}
