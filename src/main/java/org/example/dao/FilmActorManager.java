package org.example.dao;

import org.example.entity.FilmActor;
import org.hibernate.Session;

public class FilmActorManager extends EntityManager<FilmActor> {
    public FilmActorManager(Session session) {
        super(FilmActor.class, session);
    }
}