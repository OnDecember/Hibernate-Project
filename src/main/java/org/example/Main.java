package org.example;

import org.example.entity.*;
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

//            Film film = session.get(Film.class, 100L);

//            Actor actor = session.get(Actor.class, 200L);
//
//            film.getActors().add(actor);

//            Country country = session.get(Country.class, 103L);
//            System.out.println(country);
//            country.getCities().forEach(System.out::println);

//            City city = session.get(City.class, 135L);
//            city.getAddresses().forEach(System.out::println);
//
//            Address address = session.get(Address.class, 405L);
//            System.out.println(address.getCity());

            Store store = session.get(Store.class, 1L);
            System.out.println(store.getManager());
            System.out.println(store.getEmployees());
            transaction.commit();

//            film.getCategories().forEach(c -> c.getFilms().forEach(System.out::println));

//            System.out.println(actor.getFilms());


//            String hql = "FROM Film";
//            Query<Film> query = session.createQuery(hql, Film.class);
//            query.setFirstResult(1000);
//            query.list().forEach(System.out::println);
        }
    }
}