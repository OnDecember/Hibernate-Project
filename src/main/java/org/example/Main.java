package org.example;

import org.example.dao.AddressManager;
import org.example.dao.CityManager;
import org.example.dao.CustomerManager;
import org.example.entity.Address;
import org.example.entity.Customer;
import org.example.session.SessionManager;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
            CustomerManager customerManager = new CustomerManager(session);
            AddressManager addressManager = new AddressManager(session);
            CityManager cityManager = new CityManager(session);
            Address address = Address.builder()
                    .address("JavaRush 1 ")
                    .address2("JavaRush 1 ")
                    .district("JavaRush district")
                    .city(cityManager.getById(135))
                    .postalCode("1337")
                    .phone("+18934040324")
                    .build();
            Address save = addressManager.save(address);
            System.out.println(save.getId());
        }
    }
}