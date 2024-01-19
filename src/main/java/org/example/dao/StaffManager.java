package org.example.dao;

import org.example.entity.Staff;
import org.hibernate.Session;

public class StaffManager extends EntityManager<Staff> {
    public StaffManager(Session session) {
        super(Staff.class, session);
    }
}