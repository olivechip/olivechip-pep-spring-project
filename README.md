# Spring Social Media Blog API

This project implements a backend API for a hypothetical social media application using the Spring Boot framework. It manages user accounts and messages, providing endpoints for registration, login, message creation, retrieval, and deletion.

## Background

Full-stack applications typically consist of a front-end (user interface) and a back-end (data management). This project focuses on the back-end, handling data persistence and business logic for a micro-blogging or messaging app.  It leverages the Spring framework for dependency injection, data persistence, and API endpoint management.

## Database Tables

The following tables are initialized in the project's embedded database upon startup, using the configuration in `application.properties` and the provided SQL script:

### Account

```sql
CREATE TABLE Account (
    accountId INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE Message (
    messageId INTEGER PRIMARY KEY AUTO_INCREMENT,
    postedBy INTEGER,
    messageText VARCHAR(255),
    timePostedEpoch BIGINT,
    FOREIGN KEY (postedBy) REFERENCES Account(accountId)
);
```

Spring Technical Requirements
This project is built using the Spring Boot framework.  The provided Java classes are integrated with Spring to leverage its features.  Functional test cases, including SpringTest, verify the correct usage of Spring components:

Beans are defined for AccountService, MessageService, AccountRepository, MessageRepository, and SocialMediaController.
AccountRepository and MessageRepository are working JpaRepository instances based on the Account and Message entities.
The Spring Boot application utilizes Spring MVC, which is verified by checking for Spring's default error message structure.
The project starts as a Spring Boot application with a valid application.properties file and database entities.

User Stories (API Endpoints)
1. User Registration (POST /register)
Request Body: JSON representation of an Account (without accountId).
Success (200 OK): Username is unique, password is at least 4 characters long, and not blank. Response body contains the created Account including the generated accountId.
Conflict (409): Username already exists.
Bad Request (400): Other registration errors (e.g., missing username or short password).
2. User Login (POST /login)
Request Body: JSON representation of an Account (username and password).
Success (200 OK): Username and password match an existing account. Response body contains the Account including accountId.
Unauthorized (401): Invalid username or password.
3. Create Message (POST /messages)
Request Body: JSON representation of a Message (without messageId).
Success (200 OK): messageText is not blank and not over 255 characters, and postedBy refers to an existing user. Response body contains the created Message including the generated messageId.
Bad Request (400): Message creation errors (e.g., invalid messageText or postedBy).
4. Get All Messages (GET /messages)
Success (200 OK): Returns a JSON list of all messages. An empty list is returned if no messages exist.
5. Get Message by ID (GET /messages/{messageId})
Success (200 OK): Returns a JSON representation of the message with the given messageId. An empty response is returned if no such message exists.
6. Delete Message (DELETE /messages/{messageId})
Success (200 OK): Deletes the message with the given messageId. Returns the number of rows updated (1 if the message existed). If the message did not exist, the response body is empty. This ensures idempotency.
7. Get Messages by User (GET /accounts/{accountId}/messages)
Success (200 OK): Returns a JSON list of all messages posted by the user with the given accountId. An empty list is returned if no messages exist for that user.
Spring Framework Usage
The project utilizes the Spring framework, including dependency injection, autowiring, and Spring annotations.  This is verified by the included tests.

Running the Application

Bash
./mvnw spring-boot:run

Testing

Bash
./mvnw test
