# Test Report

### Brief description

The web service "Journey of the Day" was tested — a tour-booking application that integrates with a DBMS and the Bank's payment API.

Testing was carried out against two databases: MySQL and PostgreSQL.

### Tools used

| Tool | Version |
|:-----|:--------|
| Docker Compose | v2+ |
| Java | 25 |
| JUnit Jupiter | 5.14.3 |
| Selenide | 7.15.0 |
| REST-Assured | 6.0.0 |
| Allure | 2.33.0 |
| MySQL | 8.4 |
| PostgreSQL | 17.9 |

### Application under test

<p align="center">
  <img src="ui-landing-page.png" alt="Landing page" width="600"/>
</p>

Two purchase flows were tested — **debit card payment** and **credit by card data**:

<p align="center">
  <img src="ui-payment-form.png" alt="Payment form" width="400"/>
  <img src="ui-credit-form.png" alt="Credit form" width="400"/>
</p>

### Test results

The total number of test cases: **26** (4 positive, 22 negative)

| Result | Count | Percentage |
|:-------|------:|-----------:|
| Passed | 16 | 61.53% |
| Failed | 10 | 38.47% |

**Expected notifications:**

| Scenario | Notification | Screenshot |
|:---------|:-------------|:-----------|
| APPROVED card | "Успешно — Операция одобрена Банком" | ![approved](ui-credit-approved.png) |
| DECLINED card | "Ошибка — Банк отказал в проведении операции" | ![declined](ui-credit-declined.png) |

### Allure report

![Allure Report](proof/allure.png)

### Test scenarios reference

All scenarios from the [Test Automation Plan](Plan.md) have been executed.

### Found bugs

1. [Spelling error in the name of the tour](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/1)
2. [When paying with an invalid card, error and success messages appear simultaneously](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/2)
3. [credit_id is not created in the DB in the order_entity table](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/3)
4. [Incorrect characters can be entered in the "Owner" field](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/4)
5. [You can enter Cyrillic characters in the "Owner" field](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/5)
6. [If the CVC/CVV field is not filled in, a warning appears under the Owner field](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/6)
7. [Incorrect status entry in the database and incorrect pop-up with transaction status](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/7)
8. [Invalid record of the transaction status in the database](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/8)
9. [Missing pop-up "Incorrect data entered"](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/9)
10. [There is no error message when entering incorrect data](https://github.com/nelakov/alfabank-journey-of-the-day-autotests/issues/10)

### Recommendations

- Create documentation / technical specification for the application
- Block the "Продолжить" button until all fields contain valid data
- Add visual feedback when switching between "Купить" and "Купить в кредит" tabs
- Replace generic "Incorrect format" warnings with field-specific error messages
