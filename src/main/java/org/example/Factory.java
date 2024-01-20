package org.example;

import org.example.dao.CityManager;
import org.example.dao.CustomerManager;
import org.example.dao.StoreManager;
import org.example.entity.Address;
import org.example.entity.Customer;
import org.example.session.SessionManager;
import org.hibernate.Session;

public class Factory {
    public Customer createNewCustomer() {
        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
            CustomerManager customerManager = new CustomerManager(session);
            StoreManager storeManager = new StoreManager(session);
            CityManager cityManager = new CityManager(session);
            Address address = Address.builder()
                    .address("JavaRush 1")
                    .address2("JavaRush 2")
                    .district("JavaRush district")
                    .city(cityManager.getById(135))
                    .postalCode("1337")
                    .phone("+18934040324")
                    .build();
            Customer customer = Customer.builder()
                    .store(storeManager.getById(1))
                    .firstName("Customer first name")
                    .lastName("Customer last name")
                    .email("email")
                    .address(address)
                    .active(true)
                    .build();
            customerManager.save(customer);
            return customer;
        }
    }
}