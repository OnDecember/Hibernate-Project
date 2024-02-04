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

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void createNewCustomer_ShouldPersistCustomerDataCorrectly() {

        long storeId = 1;
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

        long customerId = customerManager.save(customer).getId();
        session.detach(customer);
        session.clear();

        assertEquals(customerId, customer.getId());
        assertEquals(customer, customerManager.getById(customerId));
        assertEquals(address, customer.getAddress());
        assertEquals(store, customer.getStore());
    }

    @Test
    void verifyCustomerFields_AreRetrievedCorrectly() {

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
    void retrieveCustomer_WithInvalidId_ShouldReturnNull() {

        long id = 1000000L;

        Customer customer = customerManager.getById(id);

        assertNull(customer);
    }

    @Test
    void updateCustomerFirstName_ShouldReflectChange() {

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
    void createFilm_WithValidData_ShouldSucceed() {

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
    void associateInventoryWithFilm_ShouldLinkCorrectly() {

        Film film = mainLogic.createNewFilm();
        Set<Inventory> inventories = film.getInventories();

        session.detach(film);
        session.clear();
        film = filmManager.getById(film.getId());

        assertIterableEquals(inventories, film.getInventories());
    }

    @Test
    void associateFilmTextWithFilm_ShouldMatchFilmDetails() {

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
    void rentalInventory_WithInvalidStore_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Inventory inventory = inventoryManager.getById(1);

        assertNotNull(inventory);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.rentalInventory(customer, customer.getStore(), inventory));
        assertEquals("This store does not have such inventory", exception.getMessage());
    }

    @Test
    void rentalInventory_WithNullInventory_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.rentalInventory(customer, customer.getStore(), null));
        assertEquals("Inventory can`t be null", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void rentalInventory_WithAlreadyRentedInventory_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Inventory inventory = mock(Inventory.class);
        Set<Rental> mockRentals = mock(HashSet.class);
        Rental rentalWithoutReturnDate = mock(Rental.class);
        Store store = spy(customer.getStore());
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        when(inventory.getRentals()).thenReturn(mockRentals);
        when(mockRentals.isEmpty()).thenReturn(Boolean.FALSE);
        when(mockRentals.stream()).thenReturn(Stream.of(rentalWithoutReturnDate));
        when(rentalWithoutReturnDate.getReturnDate()).thenReturn(null);

        assertFalse(inventory.getRentals().isEmpty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.rentalInventory(customer, store, inventory));
        assertEquals("This inventory is already rented", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void rentalProcess_ShouldCreateValidRentalAndPayment() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = mainLogic.rentalInventory(customer, store, inventory);

        long id = ((BigInteger) session.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();
        Payment payment = paymentManager.getById(id);

        assertEquals(payment, rental.getPayment());
        assertEquals(inventory, rental.getInventory());
        assertEquals(customer, rental.getCustomer());
        assertEquals(rental, payment.getRental());
    }

    @Test
    @SuppressWarnings("all")
    void returnRentedInventory_WithMismatchedInventory_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = mainLogic.rentalInventory(customer, store, inventory);
        Inventory newInventory = new Inventory();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.returnRentedInventory(customer, rental, newInventory));
        assertEquals("This rental does not apply to this inventory", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void returnRentedInventory_WithMismatchedCustomer_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = mainLogic.rentalInventory(customer, store, inventory);
        Customer newCustomer = new Customer();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.returnRentedInventory(newCustomer, rental, inventory));
        assertEquals("This rental does not apply to this user", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void returnRentedInventory_WithUnpaidRental_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = spy(mainLogic.rentalInventory(customer, store, inventory));
        when(rental.getPayment()).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.returnRentedInventory(customer, rental, inventory));
        assertEquals("This rent has not been paid", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void returnRentedInventory_WithAlreadyClosedLease_ShouldThrowException() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = spy(mainLogic.rentalInventory(customer, store, inventory));
        when(rental.getReturnDate()).thenReturn(ZonedDateTime.now());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> mainLogic.returnRentedInventory(customer, rental, inventory));
        assertEquals("This lease has already been closed", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    void returnRentedInventory_ShouldUpdateRentalStatusCorrectly() {

        Customer customer = mainLogic.createNewCustomer();
        Store store = spy(customer.getStore());
        Film film = mainLogic.createNewFilm();
        Inventory inventory = film.getInventories().stream().findFirst().get();
        Set<Inventory> inventories = new HashSet<>() {{
            add(inventory);
        }};

        when(store.getInventories()).thenReturn(inventories);
        Rental rental = mainLogic.rentalInventory(customer, store, inventory);
        rental = mainLogic.returnRentedInventory(customer, rental, inventory);
        session.refresh(customer);
        session.refresh(inventory);

        assertTrue(inventory.getRentals().contains(rental));
        assertTrue(rental.getCustomer().equals(customer));
        assertTrue(customer.getRentals().contains(rental));
        assertNotNull(rental.getReturnDate());
    }
}