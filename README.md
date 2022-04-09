# _Diploma project of the profession "Automation QA"_

[Automation plan](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Plan.md)

[Report on the results of testing](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Report.md)

[Report on the results of automation](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Summary.md)

---------------------

## _Application Description_

Web service "Journey of the day".

The application offers two ways to buy a tour for a fixed amount:

1. Regular debit card payment
1. Unique technology: issuing credit according to bank card data

The application itself does not process card data, but sends them to banking services:
* payment service (Payment Gate)
* credit service (Credit Gate)

The application must store information in its own DBMS about how the payment was made and whether it was made successfully (at the same time, it is not allowed to save card data).

---------------------

## _Launch Instructions_

1. Clone a repository  
    <code>git clone https://github.com/elakovnick24/Elakov_Nick_Project.git </code>

2. Launch docker containers: 

   In order to run with MySQL, PostgreSQL and Node.js need to use the command
   `docker-compose up -d --build`; 
  _in order for the image not to be rebuilt every time, you need to remove the --build flag_

3. Launch the application:  

   -  Before launching under MySQL, check the connection url in the **application.properties file**
    `
    spring.datasource.url=jdbc:mysql://localhost:3306/app
    `
    
   -  to run under PostgreSQL, check the connection url in the **application.properties file**
    `
    spring.datasource.url=jdbc:postgresql://localhost:5432/app
   `
   
   - Execute the command 
  
     `
     java -jar ./artifacts/aqa-shop.jar
     `

4. Run tests:  

   * для запуска тестов под базой данных MySQL 
    
     `
     gradlew -Ddb.url=jdbc:mysql://localhost:3306/app clean test
     `
   * to run tests under the PostgreSQL database 
    
     `
     gradlew -Ddb.url=jdbc:postgresql://localhost:5432/app clean test
     `

5. Generate reports with the command:  
   <code>gradlew allureReport</code>  

6. Open reports in the browser with the command: 
   <code>gradlew allureServe</code>
