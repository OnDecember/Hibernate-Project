package org.example.dao;

import org.example.entity.Store;
import org.hibernate.Session;

public class StoreManager extends EntityManager<Store> {
    public StoreManager(Session session) {
        super(Store.class, session);
    }

    public StoreManager() {
        super(Store.class);
    }
}