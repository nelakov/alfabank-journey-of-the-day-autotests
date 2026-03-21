# Test Automation Framework — "Journey of the Day"

[Automation Plan](docs/Plan.md) | [Test Report](docs/Report.md) | [Automation Summary](docs/Summary.md)

---------------------

## Application Description

Web service "Journey of the Day" — a tour-booking application.

The application offers two ways to buy a tour for a fixed amount:

1. Regular debit card payment
2. Credit based on bank card data

The application does not process card data directly, but forwards them to banking services:
* Payment Gate (port 9999)
* Credit Gate (port 9999)

Transaction results are stored in the database (card data is not persisted).

---------------------

## Tech Stack

* Java 25, Gradle 9.4.1
* JUnit 5.14.3, Selenide 7.15.0, REST-Assured 6.0.0
* Allure 2.33.0
* MySQL 8.4 / PostgreSQL 17.9
* Docker Compose (gate simulator + databases)

---------------------

## Launch Instructions

1. Clone the repository
   ```bash
   git clone https://github.com/nelakov/demo-alfabank-test-framework.git
   ```

2. Start infrastructure (gate simulator + both databases)
   ```bash
   docker-compose up -d --build
   ```

3. Start the application

   With MySQL (default):
   ```bash
   java -jar artifacts/aqa-shop.jar &
   ```

   With PostgreSQL:
   ```bash
   java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app &
   ```

4. Run tests

   Against MySQL:
   ```bash
   ./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app
   ```

   Against PostgreSQL:
   ```bash
   ./gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app
   ```

   Headless (CI):
   ```bash
   ./gradlew test -Dselenide.headless=true -Ddb.url=jdbc:mysql://localhost:3306/app
   ```

5. Generate and open Allure report
   ```bash
   ./gradlew allureServe
   ```
