# Spring Boot Api

### Version 1.0

---

This Spring Boot API provides a robust foundation for developing custom APIs with Spring Boot. It comes equipped with essential backend components, including containerization files, a PostgreSQL database, and a RESTful API.

The project originates from an ongoing collaboration within a two-person team. Notably, the Spring Boot configuration, database setup, and server-side rendering (SSR) have been fully implemented by me, showcasing complete ownership of these core backend functionalities.

This project is a user management system with authentication and SSR frontend.

## Runtime environment

### Requirements
- Docker + Docker Compose

## Launch instructions

1. Create and populate the `.env` and `secrets` files.
2. Build and run all containers: `docker-compose up`.

## Features

- **Authorization** – User authentication using JWT, supported by *reset* and *refresh tokens* for improved session management.
- **Flexible Login** – Google OAuth2 login integration for Android and iOS.
- **Password Change** – Sends an email with multi-language support, redirecting the user to SSR to set a new password. Passwords are changed using a reset token.
- **Spring Security** – Configured JWT and security settings, including a custom JWT authorization filter.
- **Custom Endpoint Errors** – Global exception handlers for consistent error responses.
- **Data Validation** – User registration with input validation (e.g., email format, password strength).
- **BCrypt** – Secure password hashing for user login, as well as hashed reset and refresh tokens.
- **Account Deletion** – Soft delete with timestamp for potential recovery.
- **Updateable User Details** – Ability to update username and email.
- **Automatic Cleanup** – Scheduled tasks for deleting expired tokens and soft-deleted users.
- **PostgreSQL** – Database for storing user information, reset tokens, and refresh tokens.
- **Secure Storage** – Sensitive data stored in `.env` files and secret configuration files.
- **Docker** – Dockerized application for easy deployment and scalability.
- **Docker Compose** – Setup for managing the application, database, and other services.
- **Spring Resources** – Configurable message sources for easy localization.
- **Tests** – Unit and integration tests with dependency mocking and JaCoCo coverage (total 30%, login via email 100% – work in progress).
- **NGINX** – For improved communication with the frontend.
- **SSR (Server-Side Rendering)** – Frontend using Express and EJS.
- **Health Check** – Logging and monitoring of application health using Spring Actuator.

---

- Modular project structure for easy maintenance and scalability.
- Use of modern technologies like Spring Boot, PostgreSQL, and Docker.
- Designed with best practices for security, performance, and maintainability.

## Project structure
- Frontend (SSR): Express, EJS, CSS
- API: Spring Boot (Java with Gradle)
- Database: PostgreSQL
- Infrastructure: Docker, NGINX, Bash (scripts)
