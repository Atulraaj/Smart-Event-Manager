# Smart Event Management Portal

A full-stack web application built using **Java 21, Spring Boot 3, Thymeleaf, PostgreSQL, Spring Security, Bootstrap 5, and Maven**.

This project helps colleges or organizations manage events, user registrations, attendance, QR-based attendance, feedback, and reports from a single portal.

## Features

### Authentication

* User Registration
* Login
* BCrypt Password Encryption
* Role-Based Access Control
* Admin and User Dashboard Redirect

### Admin Features

* Manage Events
* View Event Registrations
* Mark Attendance
* QR Code Attendance
* View Feedback
* View Reports
* Dashboard Summary

### User Features

* View Events
* Register for Events
* View My Registrations
* Submit Feedback
* Duplicate Registration Prevention
* Duplicate Feedback Prevention

### Reports Module

* Total Users
* Total Events
* Total Registrations
* Total Attendance
* Total Feedback
* Event-wise Registration Report

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Thymeleaf
* Bootstrap 5
* PostgreSQL
* H2 Database Demo Profile
* Maven
* Docker
* Docker Compose

## Demo Admin Login

These credentials are only for the local demo profile.

Email: admin@event.com  
Password: Admin@123

## How to Run the Project Locally

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/smart-event-management-portal.git
cd smart-event-management-portal
```

### 2. Run Using H2 Demo Profile

This is the easiest way to run the project for demo or presentation.

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=demo"
```

### 3. Open in Browser

```text
http://localhost:8080
```

## Demo Profile Details

The demo profile uses an in-memory H2 database, so no PostgreSQL setup is required.

The project automatically creates:

* Default Admin User
* Sample Event Data

## PostgreSQL Setup

For normal development, configure your database details in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eventdb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## Docker Support

Dockerfile and Docker Compose files are included.

To run using Docker Compose:

```bash
docker compose up --build
```

Note: Docker Desktop must be installed and running before using this command.

## Project Structure

```text
src/main/java/com/atul/event/management
│
├── config
├── controller
├── entity
├── repository
├── service
└── SmartEventManagementApplication.java
```

## Screenshots

Screenshots will be added soon.



## Future Enhancements

* Email Notification System
* Payment Integration
* Event Certificate Generation
* Advanced Analytics Dashboard
* Export Reports as PDF or Excel
* Cloud Deployment


## License

This project is created for academic learning and demonstration purposes.
