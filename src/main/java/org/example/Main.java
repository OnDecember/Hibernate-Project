package org.example;

import org.example.dao.CustomerManager;
import org.example.dao.FilmManager;
import org.example.entity.Customer;
import org.example.entity.Film;
import org.example.session.SessionManager;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Factory factory = new Factory();
        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
            FilmManager filmManager = new FilmManager(session);
            CustomerManager customerManager = new CustomerManager(session);

        }
    }
}