package org.example.dao;

import org.example.entity.Payment;
import org.hibernate.Session;

public class PaymentManager extends EntityManager<Payment> {
    public PaymentManager(Session session) {
        super(Payment.class, session);
    }
}