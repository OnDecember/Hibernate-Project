package org.example.dao;

import org.example.entity.Country;
import org.hibernate.Session;

public class CountryManager extends EntityManager<Country>{
    public CountryManager(Session session) {
        super(Country.class, session);
    }
}