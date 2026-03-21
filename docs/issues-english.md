# GitHub Issues — English Translations

Copy each issue below into the corresponding GitHub issue.
Format: Title + Body (markdown).

---

## Issue #1

**Title:** Spelling error in tour destination name

**Body:**

## Description

The tour destination name on the landing page contains a spelling error.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Observe the tour card heading

## Actual result

Tour destination displayed as **"Марракэш"** (with "э")

## Expected result

Tour destination should be **"Марракеш"** (with "е") — the correct Russian spelling of Marrakesh.

## Screenshot

![Landing page with spelling error](docs/ui-landing-page.png)

---

## Issue #2

**Title:** Error and success notifications appear simultaneously when paying with a DECLINED card

**Body:**

## Description

When submitting a payment with a DECLINED card (`4444 4444 4444 4442`), both the success notification ("Операция одобрена Банком") and the error notification ("Банк отказал в проведении операции") are displayed at the same time.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy)
3. Fill the form with DECLINED card number: `4444 4444 4444 4442`, valid month/year/holder/CVC
4. Click "Продолжить" (Continue)
5. Wait for the notification(s)

## Actual result

Both success and error notification pop-ups appear simultaneously.

## Expected result

Only the error notification should appear: "Ошибка! Банк отказал в проведении операции."

---

## Issue #3

**Title:** `credit_id` is not populated in `order_entity` table for credit transactions

**Body:**

## Description

When a credit transaction is completed successfully, the `credit_id` column in the `order_entity` table remains `NULL`. The `payment_id` is populated correctly for debit payments, but the equivalent field for credit flow is missing.

## Steps to reproduce

1. Start the SUT and infrastructure
2. Click "Купить в кредит" (Buy on credit)
3. Fill the form with APPROVED card: `4444 4444 4444 4441`, valid data
4. Click "Продолжить" (Continue)
5. Wait for the success notification
6. Query the database: `SELECT * FROM order_entity;`

## Actual result

`credit_id` is `NULL` in the `order_entity` record.

## Expected result

`credit_id` should contain the ID linking to the corresponding `credit_request_entity` record.

---

## Issue #4

**Title:** "Owner" field accepts invalid characters (special characters, digits)

**Body:**

## Description

The "Владелец" (Owner) field accepts input containing special characters (`!@#$%^&*()`), digits, and other non-alphabetic characters without showing a validation error.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy) or "Купить в кредит" (Buy on credit)
3. Fill the card number, month, year, and CVC with valid values
4. Enter `123!@#$%` in the "Владелец" (Owner) field
5. Click "Продолжить" (Continue)

## Actual result

The form is submitted without a validation error under the Owner field.

## Expected result

A validation error should appear under the Owner field indicating that only Latin alphabetic characters are allowed.

---

## Issue #5

**Title:** "Owner" field accepts Cyrillic characters

**Body:**

## Description

The "Владелец" (Owner) field accepts Cyrillic characters. Since card holder names are printed in Latin characters, Cyrillic input should be rejected.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy)
3. Fill all fields with valid data, but enter `Иванов Иван` in the Owner field
4. Click "Продолжить" (Continue)

## Actual result

The form is submitted without a validation error. The request is sent to the bank with Cyrillic characters in the holder name.

## Expected result

A validation error should appear under the Owner field indicating that only Latin characters are allowed.

---

## Issue #6

**Title:** Validation error for empty CVC/CVV field appears under the "Owner" field instead

**Body:**

## Description

When the CVC/CVV field is left empty and the form is submitted, the validation error message appears under the "Владелец" (Owner) field instead of under the CVC/CVV field.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy)
3. Fill all fields with valid data **except** CVC/CVV — leave it empty
4. Click "Продолжить" (Continue)

## Actual result

The validation error "Поле обязательно для заполнения" appears under the **Owner** field.

## Expected result

The validation error should appear under the **CVC/CVV** field.

---

## Issue #7

**Title:** Incorrect transaction status in database and wrong notification for DECLINED card payment

**Body:**

## Description

When paying with a DECLINED card (`4444 4444 4444 4442`), the database records the transaction with an APPROVED status, and/or the UI notification does not match the actual DB state.

## Steps to reproduce

1. Start the SUT with a clean database
2. Click "Купить" (Buy)
3. Fill the form with DECLINED card: `4444 4444 4444 4442`, valid data
4. Click "Продолжить" (Continue)
5. Observe the notification
6. Query: `SELECT status FROM payment_entity;`

## Actual result

The `payment_entity.status` shows `APPROVED` and/or the notification shows success instead of error. The UI state and DB state are inconsistent.

## Expected result

`payment_entity.status` should be `DECLINED`, and the UI should display the error notification.

---

## Issue #8

**Title:** Incorrect transaction status recorded in database for credit flow

**Body:**

## Description

When requesting credit with a DECLINED card (`4444 4444 4444 4442`), the `credit_request_entity` table records the status incorrectly (e.g., `APPROVED` instead of `DECLINED`).

## Steps to reproduce

1. Start the SUT with a clean database
2. Click "Купить в кредит" (Buy on credit)
3. Fill the form with DECLINED card: `4444 4444 4444 4442`, valid data
4. Click "Продолжить" (Continue)
5. Wait for the notification
6. Query: `SELECT status FROM credit_request_entity;`

## Actual result

`credit_request_entity.status` = `APPROVED`

## Expected result

`credit_request_entity.status` = `DECLINED`

---

## Issue #9

**Title:** Missing validation error for invalid month value (e.g., "00")

**Body:**

## Description

When entering `00` as the month value, no validation error is displayed. The form is submitted, and the request is sent to the bank with an invalid expiration date.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy) or "Купить в кредит" (Buy on credit)
3. Fill all fields with valid data, but enter `00` in the Month field
4. Click "Продолжить" (Continue)

## Actual result

No validation error. The form is submitted.

## Expected result

A validation error should appear under the Month field: "Неверно указан срок действия карты" (Invalid card expiration date).

---

## Issue #10

**Title:** Missing validation error for invalid data in input fields

**Body:**

## Description

When submitting the form with certain types of invalid data in input fields (e.g., single character in card number, single digit in CVC), no validation error is displayed, and the form either submits or behaves incorrectly.

## Steps to reproduce

1. Open `http://localhost:8080/`
2. Click "Купить" (Buy)
3. Enter incomplete/invalid data in one of the fields (e.g., `4` in card number, `0` in CVC)
4. Fill remaining fields with valid data
5. Click "Продолжить" (Continue)

## Actual result

No validation error message appears under the field with invalid data.

## Expected result

A validation error "Неверный формат" (Invalid format) should appear under the field containing invalid data.
