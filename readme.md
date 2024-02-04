# Video Store Management System

## Overview
This Java-based system is designed to manage a video store's inventory, customer rentals, and payments. It incorporates a robust database with 16 tables representing films, actors, categories, languages, stores, staff, customers, and related transactions. The project utilizes Hibernate and JPA for entity mapping and transactional operations, ensuring data consistency and integrity.

## Features
- Create new customers with all dependent fields in a transactional manner.
- Handle film returns by customers.
- Process new rentals and payments in-store by customers.
- Add new films to the inventory and make them available for rental.

## Technologies
- **Java**: Primary programming language used for the application logic.
- **Hibernate**: ORM tool used for database operations and entity management, configured for both MySQL in production and H2 in-memory database for testing.
- **Java Persistence API (JPA)**: Specification for accessing, persisting, and managing data between Java objects and relational databases.
- **H2 Database**: In-memory database used for unit testing to simulate the application's data store.
- **MySQL**: Production database used to store the application's persistent data.
- **p6spy**: A library that enables database data to be seamlessly intercepted and logged for debugging purposes.
- **Maven**: Dependency management and build automation tool.
- **Lombok**: A Java library that automatically plugs into your editor and build tools, spicing up your Java.
- **Jakarta Persistence (formerly Java Persistence)**: Provides a POJO persistence model for object-relational mapping.
- **Reflections**: A library used to scan and analyze the metadata of the classpath for JPA entities.
- **JUnit**: A simple framework to write repeatable tests, used for implementing unit tests to ensure the correctness of the application logic.
- **Mockito**: A mocking framework used in conjunction with JUnit to create and configure mock objects for use in unit tests.
- **Hibernate Dialects**: Configuration to specify the type of database used so that the correct SQL syntax can be generated.
- **Hibernate HBM2DDL Configuration**: Automatically validates or exports schema DDL to the database when the SessionFactory is created.

## Setup and Installation
1. Ensure Java and Maven are installed on your system.
2. Clone the repository to your local machine.
3. Navigate to the project directory and run `mvn clean install` to build the project.
4. For testing, ensure the H2 database is configured correctly in `hibernate-test.cfg.xml`.

## Usage
To use this system:
1. Run `MainLogic.java` to start the application.
2. Utilize the provided methods in `MainLogic` to perform operations such as creating a new customer or processing rentals and returns.

## Contributing
Contributions to the project are welcome. To contribute:
1. Fork the repository.
2. Create a new branch for your feature.
3. Commit your changes.
4. Push to your branch.
5. Submit a pull request.

## Testing
Unit tests are written using JUnit and Mockito. The project employs an H2 in-memory database for testing, configured via Hibernate to replicate the MySQL environment. The tests simulate the business logic, such as creating new customers and processing rentals and returns. Reflections library is used to automatically pick up entity classes, making the test setup more dynamic and less prone to errors when new entities are added.

## CI/CD
GitHub Actions is used for continuous integration and deployment. The configuration is located in the `.github/workflows` directory.

## Database Schema Recommendations
Several improvements to the database schema are suggested for enhanced performance and consistency:
- Modify the `film_id` column in the `film_text` table to be a foreign key rather than a primary key.
- Change the `name` field in the `language` table to `VARCHAR`.
- Ensure the `title` field type is consistent in both the `film` and `film_text` tables.
- Remove the `title` and `description` fields from the `film` table to avoid duplication.
- Use a composite key in the `film_category` table for `film_id` and `category_id`.
- Establish proper `ON DELETE` constraints across tables to maintain data integrity.
- Introduce a foreign key for the `language` in the `film` table.

## License
This project is open-source and available under the [MIT License](LICENSE).

## Acknowledgements
- Thank you to all the contributors who have spent time improving this system.
- Special thanks to the Hibernate community for their support and resources.
