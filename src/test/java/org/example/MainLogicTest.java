package org.example;

import jakarta.persistence.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.reflections.Reflections;

import static org.junit.jupiter.api.Assertions.*;

class MainLogicTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void setUp() {
        Configuration configuration = new Configuration();
        configuration.configure("META-INF/hibernate-test.cfg.xml");
        Reflections reflections = new Reflections("org.example");
        reflections.getTypesAnnotatedWith(Entity.class).forEach(configuration::addAnnotatedClass);
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void createNewCustomer() {
    }

    @Test
    void createNewFilm() {
    }

    @Test
    void rentalInventory() {
    }

    @Test
    void returnRentedInventory() {
    }
}