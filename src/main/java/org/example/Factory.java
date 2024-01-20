package org.example;

import org.example.dao.*;
import org.example.entity.*;
import org.example.enums.Rating;
import org.example.enums.SpecialFeature;
import org.example.session.SessionManager;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.Set;

public class Factory {

    private final CustomerManager customerManager = new CustomerManager();
    private final StoreManager storeManager = new StoreManager();
    private final CityManager cityManager = new CityManager();
    private final FilmManager filmManager = new FilmManager();
    private final FilmTextManager filmTextManager = new FilmTextManager();
    private final LanguageManager languageManager = new LanguageManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private final ActorManager actorManager = new ActorManager();
    public Customer createNewCustomer() {
        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
            customerManager.setSession(session);
            storeManager.setSession(session);
            cityManager.setSession(session);
            Address address = Address.builder()
                    .address("JavaRush 1")
                    .address2("JavaRush 2")
                    .district("JavaRush district")
                    .city(cityManager.getById(135))
                    .postalCode("1337")
                    .phone("+18934040324")
                    .build();
            Customer customer = Customer.builder()
                    .store(storeManager.getById(1))
                    .firstName("Customer first name")
                    .lastName("Customer last name")
                    .email("email")
                    .address(address)
                    .active(true)
                    .build();
            customerManager.save(customer);
            return customer;
        }
    }

//    public void returnFilm() {
//        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
//
//        }
//    }

    public Film createNewFilm() {
        try(Session session = SessionManager.SESSION_FACTORY.getSession()) {
            filmManager.setSession(session);
            filmTextManager.setSession(session);
            languageManager.setSession(session);
            categoryManager.setSession(session);
            actorManager.setSession(session);
            storeManager.setSession(session);


            Film film = Film.builder()
                    .title("JavaRush Film")
                    .description("JavaRush Description")
                    .releaseYear(2024)
                    .language(languageManager.getById(1L))
                    .originalLanguage(languageManager.getById(1L))
                    .rentalDuration(2)
                    .rentalRate(10.0)
                    .length(15)
                    .replacementCost(20.99)
                    .rating(Rating.NC_17)
                    .features(Set.of(SpecialFeature.COMMENTARIES, SpecialFeature.TRAILERS, SpecialFeature.BEHIND_THE_SCENES))
                    .actors(Set.of(actorManager.getById(1L), actorManager.getById(2L), actorManager.getById(3L)))
                    .categories(Set.of(categoryManager.getById(1L), categoryManager.getById(2L), categoryManager.getById(11L)))
                    .build();

            FilmText filmText = FilmText.builder().title("JavaRush Film").description("JavaRush Description").film(film).build();
            filmTextManager.save(filmText);
            Set<Inventory> inventories = Set.of(
                    Inventory.builder().store(storeManager.getById(1L)).film(film).build(),
                    Inventory.builder().store(storeManager.getById(1L)).film(film).build(),
                    Inventory.builder().store(storeManager.getById(1L)).film(film).build());
            film.setInventories(inventories);

            return filmManager.save(film);
        }
    }
}