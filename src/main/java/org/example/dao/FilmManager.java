package org.example.dao;

import org.example.entity.Film;
import org.hibernate.Session;

public class FilmManager extends EntityManager<Film> {
    public FilmManager(Session session) {
        super(Film.class, session);
    }
}