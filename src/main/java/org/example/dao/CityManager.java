package org.example.dao;

import org.example.entity.City;
import org.hibernate.Session;

public class CityManager extends EntityManager<City> {
    public CityManager(Session session) {
        super(City.class, session);
    }

    public CityManager() {
        super(City.class);
    }
}