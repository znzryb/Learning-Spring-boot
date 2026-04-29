package com.example.dependencyinject.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("netBankingPaymentService")
public class NetBankingPaymentService implements PaymentServiceInterface {

    @Override
    public void pay() {
        System.out.println("Payment done using NetBanking");
    }
}
