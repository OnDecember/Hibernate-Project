package org.example;

import org.example.dao.*;
import org.example.entity.*;
import org.example.enums.Rating;
import org.example.enums.SpecialFeature;
import org.hibernate.Session;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainLogic {

    private final CustomerManager customerManager;
    private final StoreManager storeManager;
    private final CityManager cityManager;
    private final FilmManager filmManager;
    private final FilmTextManager filmTextManager;
    private final LanguageManager languageManager;
    private final CategoryManager categoryManager;
    private final ActorManager actorManager;
    private final PaymentManager paymentManager;
    private final RentalManager rentalManager;
    private final InventoryManager inventoryManager;

    public MainLogic(Session session) {
        customerManager = new CustomerManager(session);
        storeManager = new StoreManager(session);
        cityManager = new CityManager(session);
        filmManager = new FilmManager(session);
        filmTextManager = new FilmTextManager(session);
        languageManager = new LanguageManager(session);
        categoryManager = new CategoryManager(session);
        actorManager = new ActorManager(session);
        paymentManager = new PaymentManager(session);
        rentalManager = new RentalManager(session);
        inventoryManager = new InventoryManager(session);
    }

    public Customer createNewCustomer() {
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

    public Film createNewFilm() {

        Set<SpecialFeature> features = new HashSet<>() {{
            add(SpecialFeature.COMMENTARIES);
            add(SpecialFeature.TRAILERS);
            add(SpecialFeature.BEHIND_THE_SCENES);
        }};

        Set<Actor> actors = new HashSet<>() {{
            add(actorManager.getById(1L));
            add(actorManager.getById(2L));
            add(actorManager.getById(3L));
        }};

        Set<Category> categories = new HashSet<>() {{
            add(categoryManager.getById(1L));
            add(categoryManager.getById(2L));
            add(categoryManager.getById(11L));
        }};

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
                .features(features)
                .actors(actors)
                .categories(categories)
                .build();

        FilmText filmText = FilmText.builder().title("JavaRush Film").description("JavaRush Description").film(film).build();
        filmTextManager.save(filmText);
        Set<Inventory> inventories = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Inventory inventory = Inventory.builder().store(storeManager.getById(1L)).film(film).build();
            inventories.add(inventoryManager.save(inventory));
        }
        film.setInventories(inventories);

        return filmManager.save(film);
    }

    public Rental rentalInventory(Customer customer, Store store, Inventory inventory) {

        if (Objects.isNull(inventory)) {
            throw new RuntimeException("Inventory can`t be null");
        }

        if (!store.getInventories().contains(inventory))
            throw new RuntimeException("This store does not have such inventory");

        if (!inventory.getRentals().isEmpty()) {
            long count = inventory.getRentals()
                    .stream()
                    .filter(r -> Objects.isNull(r.getReturnDate()))
                    .count();
            if (count > 0) throw new RuntimeException("This inventory is already rented");
        }

        Rental rental = Rental.builder()
                .inventory(inventory)
                .customer(customer)
                .staff(store.getManager())
                .rentalDate(ZonedDateTime.now())
                .build();

        Payment payment = Payment.builder()
                .paymentDate(rental.getRentalDate())
                .amount(inventory.getFilm().getRentalRate())
                .customer(customer)
                .staff(store.getManager())
                .rental(rental)
                .build();
        rental.setPayment(payment);
        paymentManager.save(payment);
        return rental;
    }

    public Rental returnRentedInventory(Customer customer, Rental rental, Inventory inventory) {

        if (!rental.getInventory().equals(inventory)) {
            throw new RuntimeException("This rental does not apply to this inventory");
        }

        if (!rental.getCustomer().equals(customer)) {
            throw new RuntimeException("This rental does not apply to this user");
        }

        if (Objects.isNull(rental.getPayment())) {
            throw new RuntimeException("This rent has not been paid");
        }

        if (Objects.nonNull(rental.getReturnDate())) {
            throw new RuntimeException("This lease has already been closed");
        }

        rental.setReturnDate(ZonedDateTime.now());

        return rentalManager.update(rental);
    }
}