package org.example.dao;

import org.example.entity.Inventory;
import org.hibernate.Session;

public class InventoryManager extends EntityManager<Inventory> {
    public InventoryManager(Session session) {
        super(Inventory.class, session);
    }
}