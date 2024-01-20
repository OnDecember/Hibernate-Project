package org.example.dao;

import org.example.entity.Customer;
import org.hibernate.Session;

public class CustomerManager extends EntityManager<Customer> {
    public CustomerManager(Session session) {
        super(Customer.class, session);
    }

    public CustomerManager() {
        super(Customer.class);
    }
}