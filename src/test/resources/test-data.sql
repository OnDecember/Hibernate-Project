INSERT INTO movie.country (country, last_update) VALUES ('Ukraine', CURRENT_TIMESTAMP), ('USA', CURRENT_TIMESTAMP);

INSERT INTO movie.city (city, country_id, last_update) VALUES ('Kyiv', 1, CURRENT_TIMESTAMP), ('Lviv', 1, CURRENT_TIMESTAMP), ('New York', 2, CURRENT_TIMESTAMP), ('Lawrence', 2, CURRENT_TIMESTAMP);

INSERT INTO movie.address (address, address2, district, city_id, postal_code, phone, last_update) VALUES ('123 Aslan Way', 'Suite A', 'Northern District', 1, '12345', '555-1234', CURRENT_TIMESTAMP),('456 Bag End', 'Suite B', 'Shire District', 2, '23456', '555-2345', CURRENT_TIMESTAMP),('789 Red Keep Avenue', 'Suite C', 'Landing District', 3, '34567', '555-3456', CURRENT_TIMESTAMP);

ALTER TABLE movie.store ALTER COLUMN manager_staff_id DROP NOT NULL;

INSERT INTO movie.store (address_id, manager_staff_id,  last_update) VALUES (1, null, CURRENT_TIMESTAMP), (2, null, CURRENT_TIMESTAMP);

INSERT INTO movie.staff (first_name, last_name, address_id, email, store_id, active, username, password, last_update) VALUES ('John', 'Doe', 1, 'johndoe@example.com', 1, 1, 'johndoe', 'password123', CURRENT_TIMESTAMP),('Jane', 'Smith', 2, 'janesmith@example.com', 2, 1, 'janesmith', 'password321', CURRENT_TIMESTAMP);

UPDATE movie.store SET manager_staff_id = 1 WHERE store_id = 1;
UPDATE movie.store SET manager_staff_id = 2 WHERE store_id = 2;

ALTER TABLE movie.store ALTER COLUMN manager_staff_id SET NOT NULL;

INSERT INTO movie.customer (store_id, first_name, last_name, email, address_id, active, create_date, last_update) VALUES (1, 'Customer first name', 'Customer last name', 'email', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movie.actor (first_name, last_name, last_update) VALUES ('Actor 1', 'Actor 1', CURRENT_TIMESTAMP),('Actor 2', 'Actor 2', CURRENT_TIMESTAMP),('Actor 3', 'Actor 3', CURRENT_TIMESTAMP);

INSERT INTO movie.category (category_id, name, last_update) VALUES (1, 'c1', CURRENT_TIMESTAMP), (2, 'c2', CURRENT_TIMESTAMP), (11, 'c3', CURRENT_TIMESTAMP);

INSERT INTO movie.language (name, last_update) VALUES ('l1', CURRENT_TIMESTAMP), ('l2', CURRENT_TIMESTAMP);

INSERT INTO movie.film (title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, last_update) VALUES ('title', 'desc', 2024, 1, 1, 2, 4.99, 100, 20.99, 'G', 'Commentaries, Trailers', CURRENT_TIMESTAMP);

INSERT INTO movie.film_text (film_id, title, description) VALUES (1, 'title', 'desc');