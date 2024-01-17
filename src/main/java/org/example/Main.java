package org.example;

import org.example.entity.Film;
import org.example.entity.FilmText;
import org.example.entity.Language;
import org.example.enums.Rating;
import org.example.enums.SpecialFeature;
import org.example.manager.SessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try (Session session = SessionManager.SESSION_FACTORY.getSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Film film = Film.builder()
                    .id(1432L)
                    .releaseYear(2024)
                    .title("title")
                    .description("desc")
                    .language(session.get(Language.class, 2))
                    .originalLanguage(session.get(Language.class, 6))
                    .rentalDuration(3)
                    .rentalRate(1.2)
                    .length(14543)
                    .replacementCost(1.1)
                    .rating(Rating.PG_13)
                    .features(Set.of(SpecialFeature.COMMENTARIES, SpecialFeature.TRAILERS, SpecialFeature.DELETED_SCENES))
                    .build();
            FilmText filmText = FilmText.builder().title("title").description("description").film(film).build();
            film.setFilmText(filmText);
            session.save(film);
            transaction.commit();


            String hql = "FROM Film";
            Query<Film> query = session.createQuery(hql, Film.class);
            query.setFirstResult(1000);
            query.list().forEach(System.out::println);
        }
    }
}