# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Test automation framework for a Spring Boot tour-booking application ("Путешествие дня"). Tests cover three layers: UI (Selenide), API (REST-Assured), and database verification (JDBC). The SUT jar is in `artifacts/aqa-shop.jar`; a Node.js gate simulator handles payment/credit responses on port 9999.

## Build & Test Commands

```bash
# Start infrastructure (gate simulator + both databases)
docker-compose up -d --build

# Start SUT with MySQL
java -jar artifacts/aqa-shop.jar &

# Run tests against MySQL (default)
./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app

# Run tests against PostgreSQL
java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app &
./gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app

# Run headless (CI)
./gradlew test -Dselenide.headless=true -Ddb.url=jdbc:mysql://localhost:3306/app

# Run a single test class
./gradlew test --tests "test.BuyingTripDbTest"

# Run a single test method
./gradlew test --tests "test.BuyingTripDbTest.shouldConfirmPaymentWithValidCard"

# Allure report
./gradlew allureServe
```

Default system property values: `db.user=app`, `db.password=pass`, `sut.url=http://localhost:8080/`.

## Architecture

**All source lives under `src/test/java/`** — there is no main source; this is a pure test project.

- **`data/Card.java`** — Java record (number, month, year, holder, cvc). Immutable. All test data flows through this type.
- **`page/`** — Page Object Model. `FormPage` base class holds all form fields, `fillData()`, and notification assertions. `PaymentPage` and `CreditPage` extend it with heading text and timeout only. `StartPage` navigates to either.
- **`utils/DataGenerator`** — Factory methods for card fixtures using dynamic `YearMonth` dates. Card numbers ending `4441` = APPROVED, `4442` = DECLINED, `4449` = unknown to gate.
- **`utils/ApiUtils`** — REST-Assured wrapper; POSTs card JSON to `/api/v1/pay` or `/api/v1/credit`.
- **`utils/DbUtils`** — Direct JDBC queries via Commons-DbUtils. Reads `payment_entity.status`, `credit_request_entity.status`, `order_entity` count. `clearTables()` wipes all three tables.
- **`test/BuyingTripUiTest`** — Parametrized form-validation tests (CSV-driven from `incorrectValues.cvs`) + expired/future-date tests.
- **`test/BuyingTripApiTest`** — API-level tests posting invalid data, asserting non-200 status.
- **`test/BuyingTripDbTest`** — Full integration: UI interaction → DB state verification. Clears tables in `@BeforeEach`.

## Database Schema (3 tables)

- `order_entity` — created on every successful transaction
- `payment_entity` — status: APPROVED or DECLINED
- `credit_request_entity` — status: APPROVED or DECLINED

## Key Conventions

- Java 25, Gradle 9.4.1, Java records for data classes.
- Dual-database support (MySQL 8.3 / PostgreSQL 16.1) — switched via `db.url` system property and SUT `--spring.datasource.url` flag.
- Page objects return page instances for fluent navigation (`goToPaymentPage()` → `PaymentPage`).
- Notification assertions use explicit waits (12–15 seconds via `FormPage.notificationTimeout`) — do not reduce these, the SUT is slow to respond through the gate simulator.
- CSV test data file is `incorrectValues.cvs` (not `.csv`) — this is intentional, do not rename.
