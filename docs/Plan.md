# _Automation Plan_

## _The list of automated test cases_

Valid data when filling in the input fields should be considered:

1. Card number: 16 digits in the format **** **** **** ****
1. Month: 01-12, but not earlier than the current month if the current year is specified 
1. Year: the last two digits of the serial number of the year, not earlier than the current year, not more than 5 years from the current year
1. Owner: alphabetic characters of the Latin alphabet
1. CVC: Numbers - 3 pcs
. 1. Test data set - maps (data.json file):
* 4444 4444 4444 4441 , status APPROVED
* 4444 4444 4444 4442 , status DECLINED

Databases:
* MySql
* PostgreSQL 

### _test scenarios_

#### _Positive scenarios_
1. Payment by card with the APPROVED status  
The card number is 4444 4444 4444 4441, the other fields are filled with correct values  
Expected result: a pop-up window "Operation approved by the Bank" appeared, an entry with the APPROVED status appeared in the payment_entity database 

1. Credit according to the card data with the APPROVED status   
The card number is 4444 4444 4444 4441, the other fields are filled with correct values  
Expected result: a pop-up window "Operation approved by the Bank" appeared, an entry with the APPROVED status appeared in the credit_request_entity database 

1. Payment by card with DECLINED status    
The card number is 4444 4444 4444 4442, the other fields are filled with correct values   
Expected result: a pop-up window "Error! The bank refused to perform the operation", an entry with the DECLINED status appeared in the payment_entity database 

1. Credit according to the card data with the DECLINED status  
The card number is 4444 4444 4444 4442, the other fields are filled with correct values  
Expected result: a pop-up window "Error! The bank refused to carry out the operation", an entry with the DECLINED status appeared in the credit_request_entity database

#### _negative scenarios_
1. Payment using a non-existent card   
The card number is 4444 4444 4444 4443, the other fields are filled with correct values  
Expected result: a pop-up window "Error! The bank refused to carry out the operation", no new entry appeared in the DB in payment_entity 

1. Credit according to non-existent card data   
The card number is 4444 4444 4444 4443, the other fields are filled with correct values  
Expected result: a pop-up window "Error! The bank refused to carry out the operation", no new entry appeared in the credit_request_entity database

1. Payment by card, invalid number is specified   
The card number is 4444 4444 4444 444, the other fields are filled with correct values  
Expected result: an error message "Incorrect format" appeared under the Card Number field, no new entry appeared in the payment_entity database 

1. Credit according to the card data, invalid number is indicated    
The card number is 4444 4444 4444 444, the other fields are filled with correct values  
Expected result: an error message "Incorrect format" appeared under the Card Number field, no new entry appeared in the credit_request_entity database

1. Payment by expired card (month)  
Card number 4444 4444 4444 4441, specify the previous month, the current year, the remaining fields are filled with correct values  
Expected result: an error message appeared under the Month field "The card expiration date is incorrect", a new entry did not appear in the payment_entity database 

1. Credit according to expired card data (month)  
Card number 4444 4444 4444 4441, specify the previous month, the current year, the remaining fields are filled with correct values  
Expected result: an error message appeared under the Month field "The card expiration date is incorrect", a new entry did not appear in the credit_request_entity database

1. Payment according to the card data, the invalid month is specified   
The card number is 4444 4444 4444 4441, enter 00 in the Month field, the other fields are filled with correct values  
Expected result: an error message appeared under the Month field "The card expiration date is incorrect", a new entry did not appear in the payment_entity database 

1. Credit according to the card data, invalid month is specified   
The card number is 4444 4444 4444 4441, enter 00 in the Month field, the other fields are filled with correct values  
Expected result: an error message appeared under the Month field "The card expiration date is incorrect", a new entry did not appear in the credit_request_entity database

1. Payment by expired card (year)  
Card number 4444 4444 4444 4441, specify the previous year, the remaining fields are filled with correct values  
Expected result: an error message "Card expired" appeared under the Year field, no new entry appeared in the payment_entity database 

1. Credit according to expired card data (year)  
Card number 4444 4444 4444 4441, specify the previous year, the remaining fields are filled with correct values  
Expected result: an error message "Card expired" appeared under the Year field, no new entry appeared in the credit_request_entity database

1. Payment according to the card data, the invalid year is specified   
The card number is 4444 4444 4444 4441, in the Year field specify "the last two digits of the current year + 6", the remaining fields are filled with correct values  
Expected result: an error message appeared under the Year field "The card expiration date is incorrect", a new entry did not appear in the payment_entity database 

1. Credit according to the card data, the invalid year is indicated   
The card number is 4444 4444 4444 4441, in the Year field specify "the last two digits of the current year + 6", the remaining fields are filled with correct values  
Expected result: an error message appeared under the Year field "The card expiration date is incorrect", a new entry did not appear in the credit_request_entity database

1. Payment according to the card data, an incorrect value is specified in the Owner field  
The card number is 4444 4444 4444 4441, incorrect data should be specified in the Owner field, the remaining fields are filled with correct values  
Expected result: an error message appeared under the Owner field, no new entry appeared in the DB in payment_entity 

1. Credit according to the card data, an incorrect value is specified in the Owner field   
The card number is 4444 4444 4444 4441, incorrect data should be specified in the Owner field, the remaining fields are filled with correct values  
Expected result: an error message appeared under the Owner field, no new entry appeared in the credit_request_entity database

1. Payment according to the card data, an incorrect value is specified in the CVC/CVV field   
The card number is 4444 4444 4444 4441, specify 0 in the CVC/CVV field, the other fields are filled with correct values  
Expected result: an error message "Invalid format" appeared under the CVC/CVV field, no new entry appeared in the payment_entity database 

1. Credit according to the card data, an incorrect value is specified in the CVC/CVV field   
The card number is 4444 4444 4444 4441, specify 0 in the CVC/CVV field, the other fields are filled with correct values  
Expected result: an error message "Invalid format" appeared under the CVC/CVV field, no new entry appeared in the credit_request_entity database

## _The list of tools used with the justification of the choice_

* Java 8 - the language for writing auto-tests, the most reliable version
* Gradle - build automation and dependency management system
* JUnit is a platform for writing auto—tests and running them 
* Selenide is a framework for automated testing of web applications based on Selenium WebDriver, more convenient and easy to use than Selenium
* Docker Compose is a tool that allows you to run multi-container applications, so as not to install Node applications necessary for operation on your computer.js and DBMS
* Allure is a framework designed to create reports that are more visual than Gradle

## _The list and description of possible risks in automation_
* Due to the lack of technical specifications and any documentation, it is difficult to understand how the application should work and what system behavior should be considered an error
* The real system and real data are likely to be different from the SUT and test data, and test automation may be useless
* Using third-party libraries and frameworks may increase the risk of technical problems
* Possible problems due to the need to support two DBMS (MySQL, Postgres)
* The absence of test_id complicates the writing and support of tests

## _interval assessment taking into account risks (in hours)_
Setting up the test environment - 8  
Writing and debugging autotests - 30  
Test run and issue - 8 institution  
Elimination of comments - 6  
Preparation of a report on the results of automated testing - 8  
Preparation of a report on the results of automation - 8  
**Total, taking into account risks: ~70 hours**

## _Plan of delivery of works_
Automation - 28.10.2020  
Preparation of accounting documents - 31.10.2020
