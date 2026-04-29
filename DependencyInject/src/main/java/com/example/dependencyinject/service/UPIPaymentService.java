package com.example.dependencyinject.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("UPIPaymentService")
//@Primary
public class UPIPaymentService implements PaymentServiceInterface {

    @Override
    public void pay() {
        System.out.println("Payment done using UPI");
    }
}
