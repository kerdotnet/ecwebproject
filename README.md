#The Web project created by Ivanov Yevhen, 2018-05
#for Java External Courses 2018-03 - 2018-05
#The detailed task description will be provided later

Web application is designed for management of Libraries.
1. Description of the project
RUS:
Система Библиотека. Создайте Каталог, по которому можно искать по:
•	Автору (одному из группы).
•	Названию книги или её фрагменте.
•	Одному из ключевых слов книги (атрибут книги).
Каталог книг заполняет Администратор, добавляя и изменяя/удаляя их. Каждая книга должна иметь адрес (место на полке) или читателя. Читатель чтобы взять книгу регистрируется, оставляя э-мейл и номер телефона. Книга может быть взята у Администратора в библиотеке на время не более месяца,
только в случае если книга доступна в библиотеке. Администратор должен иметь страницу где отражаются взятые книги и читатели, которые пользуются книгой.
EN:
System Library. Create a Catalog where you can search by:
• The author (one of the group).
• The title of the book or its fragment.
• One of the book's keywords (book attribute).
The books catalog is filled in by the Administrator, adding and changing / deleting them. Each book must have an address (a place on the shelf) or a reader. The reader to take the book is registered, leaving an e-mail and phone number. The book can be borrowed from the Administrator in the library for a period of not more than a month,
Only if the book is available in the library. The administrator should have a page where the books and readers who are taking the book are reflected.
2. Requirements
The following infrastructure are required for the application installation:
- MySQL Community Server 5.7 or newer
- JVM 9.0.2
- Apache Tomcat 9.0.2
3. Installation guide
Database deployment. Please use script 02-web-library-db.sql in the folder "sql scripts" to deploy the database.
Default user for DataBase with login/password root/root is used. You also can change in the context.xml file which is in the delivery.
Also you can change settings of the default connection pool in context.xml.
The packaged WAR file has to be deployed on your Apache Tomcat server application.
Also logging settings could be changed in the "\resources\log4j.properties" file. By default two ways of logging are supported (console and file).
4. To start your application please access it to deployed URI. In the system default user with administrator rules is created (root/root).
