package org.example;

import jakarta.persistence.Entity;
import org.example.dao.*;
import org.example.entity.*;
import org.example.enums.SpecialFeature;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MainLogicTest {

    private static SessionFactory sessionFactory;
    private static final ActorManager actorManager = new ActorManager();
    private static final AddressManager addressManager = new AddressManager();
    private static final CategoryManager categoryManager = new CategoryManager();
    private static final CityManager cityManager = new CityManager();
    private static final CountryManager countryManager = new CountryManager();
    private static final CustomerManager customerManager = new CustomerManager();
    private static final FilmManager filmManager = new FilmManager();
    private static final FilmTextManager filmTextManager = new FilmTextManager();
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final LanguageManager languageManager = new LanguageManager();
    private static final PaymentManager paymentManager = new PaymentManager();
    private static final RentalManager rentalManager = new RentalManager();
    private static final StaffManager staffManager = new StaffManager();
    private static final StoreManager storeManager = new StoreManager();
    private Session session;
    private MainLogic mainLogic;

    @BeforeAll
    public static void setUp() {
        Configuration configuration = new Configuration();
        configuration.configure("META-INF/hibernate-test.cfg.xml");
        Reflections reflections = new Reflections("org.example");
        reflections.getTypesAnnotatedWith(Entity.class).forEach(configuration::addAnnotatedClass);
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();

        actorManager.setSession(session);
        addressManager.setSession(session);
        categoryManager.setSession(session);
        cityManager.setSession(session);
        countryManager.setSession(session);
        customerManager.setSession(session);
        filmManager.setSession(session);
        filmTextManager.setSession(session);
        inventoryManager.setSession(session);
        languageManager.setSession(session);
        paymentManager.setSession(session);
        rentalManager.setSession(session);
        staffManager.setSession(session);
        storeManager.setSession(session);
        mainLogic = new MainLogic(session);
    }

    @AfterEach
    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    @Test
    void create_new_customer_should_succeed() {

        long storeId = 1;
        long customerId = 2;
        long addressId = 3;
        Store store = storeManager.getById(storeId);
        Address address = addressManager.getById(addressId);
        Customer customer = Customer.builder()
                .store(store)
                .firstName("Customer first name")
                .lastName("Customer last name")
                .email("email")
                .address(address)
                .active(true)
                .build();

        customerManager.save(customer);

        assertEquals(customerId, customer.getId());
        assertEquals(customer, customerManager.getById(customerId));
        assertEquals(address, customer.getAddress());
        assertEquals(store, customer.getStore());
    }

    @Test
    void verify_customer_fields() {

        long storeId = 1;
        long customerId = 1;
        long addressId = 3;

        Store store = storeManager.getById(storeId);
        Address address = addressManager.getById(addressId);
        Customer customer = customerManager.getById(customerId);

        assertNotNull(customer.getId());
        assertEquals(customerId, customer.getId());
        assertEquals(address, customer.getAddress());
        assertEquals(store, customer.getStore());
    }

    @Test
    void retrieve_null_customer_should_return_null() {

        long id = 10L;

        Customer customer = customerManager.getById(id);

        assertNull(customer);
    }

    @Test
    void update_customer_should_change_first_name() {

        String name = "set first name";
        long id = 1;

        Customer customer = customerManager.getById(id);
        customer.setFirstName(name);
        customerManager.update(customer);
        session.detach(customer);
        session.clear();
        customer = customerManager.getById(id);

        assertEquals(name, customer.getFirstName());
    }

    @Test
    void create_film_with_valid_data() {

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

        Film film = mainLogic.createNewFilm();
        session.detach(film);
        session.clear();
        film = filmManager.getById(film.getId());

        assertIterableEquals(categories, film.getCategories());
        assertIterableEquals(actors, film.getActors());
        assertIterableEquals(features, film.getFeatures());
    }

    @Test
    void associate_inventory_with_film() {
        Film film = mainLogic.createNewFilm();
        Set<Inventory> inventories = film.getInventories();

        session.detach(film);
        session.clear();
        film = filmManager.getById(film.getId());

        assertIterableEquals(inventories, film.getInventories());
    }

    @Test
    void associate_film_text_with_film() {
        Film film = mainLogic.createNewFilm();

        session.detach(film);
        session.clear();
        film = filmManager.getById(film.getId());
        FilmText filmText = filmTextManager.getById(film.getId());

        assertEquals(filmText.getFilm(), film);
        assertEquals(filmText.getTitle(), film.getTitle());
        assertEquals(filmText.getDescription(), film.getDescription());
    }

    @Test
    void rentalInventory() {
    }

    @Test
    void returnRentedInventory() {
    }
}