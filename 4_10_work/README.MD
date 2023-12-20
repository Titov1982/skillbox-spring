# Приложение - Новости

Приложение предоставляет возможность ведения и просмотра новостей сторонним REST-клиентам с помощью
подготовленного и документированного Rest-api интерфейса.
Новости могут быть распределены по категориям и иметь комментарии.

Для запуска приложения необходимо подключить базу данных PostreSQL через Docker.
Для этого требуется перейти в папку __./docker__ и выпллнить в терминале команду __docker compose up__ - скачивание
и запуск образа базы данных.

Настройки запуска базы данных расположены в файле __docker-compose.yml__:

* __ports:__
    - __"5432:5432"__ - устанавливает соответствие портов базы данных host:container
* __environment:__
    - __POSTGRES_USER=postgres__ - установка переменной окружения user
    - __POSTGRES_PASSWORD=postgres__ - установка переменной окружения password
    - __POSTGRES_DB=news_service_db__ - установка переменной окружения с названием базы данных
* __volumes:__
    - __./init.sql:/docker-entrypoint-initdb.d/init.sql__ - запуск инициализационного скрипта для создания базы данных

### Rest-api интерфейса по адресу http://localhost:8080

Для просмотра подробной документации необходимо запустить приложение и перйти по адресу:
__http://localhost:8080/swagger-ui/index.htm__

Настройку приложения можно выполнить через файл __application.yml__:

* spring:
   - jpa:
     - hibernate:
       - ddl-auto: create - задаем поведение БД при старте приложения (none, create, create-drop, update)
     - generate-ddl: true - разрешаем использование DDL (true/false)
     - show-sql: true - разрешаем показывать SQL-скрипт в консоли
     - properties:
       - hibernate:
         - format_sql: true - разрешаем показывать отформатированный скрипт (true/false)
   - datasource:
     - url: jdbc:postgresql://localhost:5432/news_service_db  - настройка базы данных
     - username: postgres - настройка пользователя
     - password: postgres - настройка пароля
     - hikari:
       - schema: app_schema - настройка схемы

* app:
  - entity-generator:
    - enabled: false - включить/выключить генератор тестовой базы (true/false)

* logging:
  - level:
    - ru.tai: DEBUG - настройка логирования (DEBUG, WARN, и т.д.)