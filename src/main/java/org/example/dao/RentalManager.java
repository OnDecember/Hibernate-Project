package org.example.dao;

import org.example.entity.Rental;
import org.hibernate.Session;

public class RentalManager extends EntityManager<Rental> {
    public RentalManager(Session session) {
        super(Rental.class, session);
    }
}