package org.example.manager;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

public enum SessionManager {

    SESSION_FACTORY;

    private final SessionFactory sessionFactory;

    SessionManager() {
        Configuration configuration = new Configuration();
        Reflections reflections = new Reflections("org.example");
        reflections.getTypesAnnotatedWith(Entity.class).forEach(configuration::addAnnotatedClass);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @PreDestroy
    private void close() {
        sessionFactory.close();
    }
}