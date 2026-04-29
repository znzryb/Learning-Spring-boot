package com.example.dependencyinject.service;

import org.springframework.stereotype.Service;

@Service
public class CreditCardPaymentService implements PaymentServiceInterface {
    @Override
    public void pay() {
        System.out.println("Payment done using Credit Card");
    }
}
