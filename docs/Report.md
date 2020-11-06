# _Отчет по итогам тестирования_

### _Краткое описание_

Был протестирован веб-сервис "Путешествие дня", который представляет из себя комплексный сервис, взаимодействующий с СУБД и API Банка.

Тестирование проведено для двух БД - MySQL и PostgreSQL.

Cервис был протестирован с использованием следующих инструментов:

* Docker Desktop
* Java 8
* junit-jupiter: 5.6.1
* selenide: 5.11.0
* allure 2.13.6
* Lombok 5.2.1
* MySql и PostgreSQL

### _Количество тест-кейсов_

Общее количество тест-кейсов - 26 (4 позитивных, 22 негативных)

* успешных - 16 (61,53%)
* неуспешных - 10 (38,47%)

![proof](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/proof/allure.png)


### _Были протестированы сценарии, которые прописаны в плане тестирования:_
[План автоматизации тестирования](https://github.com/elakovnick24/Elakov_Nick_Project/blob/master/docs/Plan.md)

#### _Найденные баги_
* [Орфографическая ошибка в названии тура](https://github.com/elakovnick24/Elakov_Nick_Project/issues/1)
* [При оплате невалидной картой одновременно появляются сообщения об ошибке и успехе](https://github.com/elakovnick24/Elakov_Nick_Project/issues/2)
* [В БД не создаётся credit_id в таблице order_entity](https://github.com/elakovnick24/Elakov_Nick_Project/issues/3)
* [В поле "Владелец" можно ввести некорректные символы](https://github.com/elakovnick24/Elakov_Nick_Project/issues/4)
* [В поле "Владелец" можно ввести символы на кириллице](https://github.com/elakovnick24/Elakov_Nick_Project/issues/5)
* [Если поле cvs/cvv не заполнено предупреждение появляется под полем Владелец](https://github.com/elakovnick24/Elakov_Nick_Project/issues/6)
* [Неверная запись статуса в БД и неверный поп-ап со статусом транзакции](https://github.com/elakovnick24/Elakov_Nick_Project/issues/7)
* [Неверная запись статуса транзакции в БД](https://github.com/elakovnick24/Elakov_Nick_Project/issues/8)
* [Отсутствует поп-ап "Введены некорректные данные"](https://github.com/elakovnick24/Elakov_Nick_Project/issues/9)
* [Отсутствует сообщение об ошибке при вводе некорректных данных](https://github.com/elakovnick24/Elakov_Nick_Project/issues/10)


### _Общие рекомендации:_

* Создать документацию для данного приложения
* Добавить функциональность блокирования кнопки "Продолжить" до тех пор, пока все поля не будут заполнены корректными значениями
* Добавить изменение цвета кнопок "Купить" и "Купить в кредит" при переключении между двумя вкладками для удобства пользователя 
* Заменить предупреждения "Неверный формат" на более информативные


