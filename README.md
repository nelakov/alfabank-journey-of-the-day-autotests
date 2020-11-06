# _Дипломный проект профессии «Тестировщик»_

[План автоматизации](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Plan.md)

[Отчет по итогам тестирования](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Report.md)

[Отчет по итогам автоматизации](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Summary.md)

---------------------

## _Описание приложения_

Веб-сервис "Путешествие дня".

Приложение предлагает с помощью двух способов купить тур по фиксированной сумме:

1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (Payment Gate)
* кредитному сервису (Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

---------------------

## _Инструкция по запуску_

1. Склонировать репозиторий  
    <code>git clone https://github.com/elakovnick24/Elakov_Nick_Project.git </code>

2. Запустить контейнеры docker:  

Для того, чтобы запустить с MySql, PostgreSQL и Node.js нужно использовать команду
`docker-compose up -d --build`; 
_чтобы образ не пересобирался каждый раз необходимо убрать флаг --build_

3. Запустить приложение:  

* для запуска под MySQL использовать команду 
    ```
    java -jar artifacts/aqa-shop.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass
    ```
    * для запуска под PostgreSQL использовать команду 
    ```
    java -jar artifacts/aqa-shop.jar -P:jdbc.url=jdbc:postgresql://localhost:5432/app -P:jdbc.user=app -P:jdbc.password=pass

4. Запустить тесты:  

   * для запуска тестов под базой данных MySQL 
   ```
   gradlew -Ddb.url=jdbc:mysql://localhost:3306/app clean test
   ```
   * для запуска тестов под базой данных PostgreSQL 
   ```
   gradlew -Ddb.url=jdbc:postgresql://localhost:5432/app clean test
   ```

5. Сформировать отчеты командой:  
   <code>gradlew allureReport</code>  

6. Открыть отчеты в браузере командой:  
   <code>gradlew allureServe</code>
