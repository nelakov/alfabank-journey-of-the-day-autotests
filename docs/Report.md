# _The report on the results of testing_

### _Brief description_

The web service "Journey of the Day" was tested, which is a comprehensive service that interacts with the DBMS and the API of the Bank.

Testing was carried out for two databases - MySQL and PostgreSQL.

The service has been tested using the following tools:

* Docker Desktop
* Java 8
* junit-jupiter: 5.6.1
* selenide: 5.11.0
* allure 2.13.6
* Lombok 5.2.1
* MySQL and PostgreSQL

### _Number of test cases_

The total number of test cases is 26 (4 positive, 22 negative)

* successful - 16 (61.53%)
* unsuccessful - 10 (38.47%)

![proof](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/proof/allure.png)


### _The scenarios that are prescribed in the test plan have been tested:_
[Test Automation Plan](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Plan.md )

#### _Found bugs_
* [Spelling error in the name of the tour](https://github.com/elakovnick24/Elakov_Nick_Project/issues/1 )
* [When paying with an invalid card, error and success messages appear simultaneously](https://github.com/elakovnick24/Elakov_Nick_Project/issues/2 )
* [credit_id is not created in the DB in the order_entity table](https://github.com/elakovnick24/Elakov_Nick_Project/issues/3 )
* [Incorrect characters can be entered in the "Owner" field](https://github.com/elakovnick24/Elakov_Nick_Project/issues/4 )
* [You can enter Cyrillic characters in the "Owner" field](https://github.com/elakovnick24/Elakov_Nick_Project/issues/5 )
* [If the cvs/cvv field is not filled in, a warning appears under the Owner field](https://github.com/elakovnick24/Elakov_Nick_Project/issues/6 )
* [Incorrect status entry in the database and incorrect pop-up with transaction status](https://github.com/elakovnick24/Elakov_Nick_Project/issues/7 )
* [Invalid record of the transaction status in the database](https://github.com/elakovnick24/Elakov_Nick_Project/issues/8 )
* [Missing pop-up "Incorrect data entered"](https://github.com/elakovnick24/Elakov_Nick_Project/issues/9 )
* [There is no error message when entering incorrect data](https://github.com/elakovnick24/Elakov_Nick_Project/issues/10 )


### _General recommendations:_

* Create documentation for this application
* Add the functionality of blocking the "Continue" button until all fields are filled with the correct values
* Add a change in the color of the "Buy" and "Buy on Credit" buttons when switching between two tabs for user convenience 
* Replace the "Incorrect Format" warnings with more informative ones


