package org.example.dao;

import org.example.entity.FilmCategory;
import org.hibernate.Session;

public class FilmCategoryManager extends EntityManager<FilmCategory>{
    public FilmCategoryManager(Session session) {
        super(FilmCategory.class, session);
    }

    public FilmCategoryManager() {
        super(FilmCategory.class);
    }
}