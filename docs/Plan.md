# Automation Plan

## Application under test

The web service "Journey of the Day" — a tour-booking application with two purchase flows:

| Flow | Button | Form heading | Screenshot |
|:-----|:-------|:-------------|:-----------|
| Debit card payment | "Купить" | "Оплата по карте" | [Payment form](ui-payment-form.png) |
| Credit by card data | "Купить в кредит" | "Кредит по данным карты" | [Credit form](ui-credit-form.png) |

**Landing page:**

![Landing page](ui-landing-page.png)

## Valid data for input fields

1. Card number: 16 digits in the format `**** **** **** ****`
2. Month: 01–12, but not earlier than the current month if the current year is specified
3. Year: last two digits, not earlier than the current year, not more than 5 years ahead
4. Owner: Latin alphabetic characters
5. CVC: 3 digits

### Test data (from `data.json`):

| Card Number | Status |
|:------------|:-------|
| `4444 4444 4444 4441` | APPROVED |
| `4444 4444 4444 4442` | DECLINED |

### Databases: MySQL, PostgreSQL

---

## Test scenarios

### Positive scenarios

**1. Payment by card with APPROVED status**
Card: `4444 4444 4444 4441`, other fields — valid values.
Expected: notification "Операция одобрена Банком" ([screenshot](ui-credit-approved.png)), `payment_entity.status = APPROVED`.

**2. Credit by card data with APPROVED status**
Card: `4444 4444 4444 4441`, other fields — valid values.
Expected: notification "Операция одобрена Банком" ([screenshot](ui-credit-approved.png)), `credit_request_entity.status = APPROVED`.

**3. Payment by card with DECLINED status**
Card: `4444 4444 4444 4442`, other fields — valid values.
Expected: notification "Банк отказал в проведении операции" ([screenshot](ui-credit-declined.png)), `payment_entity.status = DECLINED`.

**4. Credit by card data with DECLINED status**
Card: `4444 4444 4444 4442`, other fields — valid values.
Expected: notification "Банк отказал в проведении операции" ([screenshot](ui-credit-declined.png)), `credit_request_entity.status = DECLINED`.

### Negative scenarios

**5. Payment using a non-existent card**
Card: `4444 4444 4444 4443`, other fields — valid values.
Expected: error notification, no new record in `payment_entity`.

**6. Credit by non-existent card data**
Card: `4444 4444 4444 4443`, other fields — valid values.
Expected: error notification, no new record in `credit_request_entity`.

**7. Payment — invalid card number**
Card: `4444 4444 4444 444` (15 digits), other fields — valid values.
Expected: validation error "Неверный формат" under Card Number field, no DB record.

**8. Credit — invalid card number**
Card: `4444 4444 4444 444` (15 digits), other fields — valid values.
Expected: validation error "Неверный формат" under Card Number field, no DB record.

**9. Payment — expired card (month)**
Card: `4444 4444 4444 4441`, month = previous month, year = current year.
Expected: validation error "Неверно указан срок действия карты" under Month field.

**10. Credit — expired card (month)**
Card: `4444 4444 4444 4441`, month = previous month, year = current year.
Expected: validation error "Неверно указан срок действия карты" under Month field.

**11. Payment — invalid month (00)**
Card: `4444 4444 4444 4441`, month = `00`.
Expected: validation error "Неверно указан срок действия карты" under Month field.

**12. Credit — invalid month (00)**
Card: `4444 4444 4444 4441`, month = `00`.
Expected: validation error "Неверно указан срок действия карты" under Month field.

**13. Payment — expired card (year)**
Card: `4444 4444 4444 4441`, year = previous year.
Expected: validation error "Истёк срок действия карты" under Year field.

**14. Credit — expired card (year)**
Card: `4444 4444 4444 4441`, year = previous year.
Expected: validation error "Истёк срок действия карты" under Year field.

**15. Payment — year exceeds 5-year limit**
Card: `4444 4444 4444 4441`, year = current + 6.
Expected: validation error "Неверно указан срок действия карты" under Year field.

**16. Credit — year exceeds 5-year limit**
Card: `4444 4444 4444 4441`, year = current + 6.
Expected: validation error "Неверно указан срок действия карты" under Year field.

**17. Payment — invalid Owner field**
Card: `4444 4444 4444 4441`, owner = invalid data (Cyrillic, special chars).
Expected: validation error under Owner field, no DB record.

**18. Credit — invalid Owner field**
Card: `4444 4444 4444 4441`, owner = invalid data (Cyrillic, special chars).
Expected: validation error under Owner field, no DB record.

**19. Payment — invalid CVC/CVV**
Card: `4444 4444 4444 4441`, CVC = `0`.
Expected: validation error "Неверный формат" under CVC/CVV field, no DB record.

**20. Credit — invalid CVC/CVV**
Card: `4444 4444 4444 4441`, CVC = `0`.
Expected: validation error "Неверный формат" under CVC/CVV field, no DB record.

---

## Tools used (with rationale)

| Tool | Purpose |
|:-----|:--------|
| Java 25 | Test language — modern features (records, var, pattern matching) |
| Gradle 9.4.1 | Build automation and dependency management |
| JUnit 5.14.3 | Test framework with parameterized tests and display names |
| Selenide 7.15.0 | Web UI testing — built on Selenium WebDriver |
| REST-Assured 6.0.0 | API testing library |
| Docker Compose | Multi-container orchestration for gate simulator and databases |
| Allure 2.33.0 | Test reporting framework |

---

## Risks

- No technical specification or documentation — hard to distinguish expected behavior from bugs
- Real production data likely differs from test fixtures (`data.json`), limiting test portability
- Third-party library compatibility issues across versions
- Dual-DBMS support (MySQL + PostgreSQL) increases maintenance overhead
- Absence of `test_id` attributes in the UI complicates selector stability

---

## Time estimate (including risks)

| Phase | Hours |
|:------|------:|
| Test environment setup | 8 |
| Writing and debugging autotests | 30 |
| Test execution and bug reporting | 8 |
| Bug fix verification | 6 |
| Test results report | 8 |
| Automation summary report | 8 |
| **Total** | **~70** |

---

## Delivery schedule

| Milestone | Date |
|:----------|:-----|
| Automation complete | 28.10.2020 |
| Documentation delivered | 31.10.2020 |
