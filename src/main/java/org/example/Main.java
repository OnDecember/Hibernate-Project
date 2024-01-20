package org.example;

import org.example.entity.*;
import org.example.session.SessionManager;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        try (Session session = SessionManager.SESSION_FACTORY.getSession()) {
            MainLogic mainLogic = new MainLogic(session);

            Film film = mainLogic.createNewFilm();

            Customer customer = mainLogic.createNewCustomer();

            Store store = customer.getStore();

            Inventory inventory = film.getInventories().stream()
                    .filter(r -> r.getStore().equals(store))
                    .findFirst()
                    .get();

            Rental rental = mainLogic.rentalInventory(customer, store, inventory);

            rental = mainLogic.returnRentedInventory(customer, rental, inventory);

            System.out.println(rental);
        }
    }
}