package org.example.dao;

import org.example.entity.Address;
import org.hibernate.Session;

public class AddressManager extends EntityManager<Address>{
    public AddressManager(Session session) {
        super(Address.class, session);
    }

    public AddressManager() {
        super(Address.class);
    }
}