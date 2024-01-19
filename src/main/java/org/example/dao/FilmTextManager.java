package org.example.dao;

import org.example.entity.FilmText;
import org.hibernate.Session;

public class FilmTextManager extends EntityManager<FilmText>{
    public FilmTextManager(Session session) {
        super(FilmText.class, session);
    }
}