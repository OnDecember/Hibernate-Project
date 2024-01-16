package org.example;

import org.example.entity.Film;
import org.example.entity.Language;
import org.example.manager.SessionManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {
        try (Session session = SessionManager.SESSION_FACTORY.getSession()) {
//            String hql = "FROM Film";
//            Query<Film> query = session.createQuery(hql, Film.class);
//            query.setMaxResults(10);
//            query.list().forEach(System.out::println);
            Film film = Film.builder()
                    .title("title")
                    .language(session.get(Language.class, 1))
                    .rentalDuration(1)
                    .rentalRate(1.1)
                    .replacementCost(1.1)
                    .build();
            session.save(film);
        }
    }
}