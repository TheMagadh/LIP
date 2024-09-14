# Learning Performance Indicator (LPI)

The **Learning Performance Indicator (LPI)** is a Spring Boot application designed to manage and evaluate learning outcomes, quiz handling, and goal performance. It provides APIs for quiz management, goal tracking, and performance evaluation.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [Build and Run the Application](#build-and-run-the-application)
- [Swagger API Documentation](#swagger-api-documentation)
- [Example API Endpoints](#example-api-endpoints)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- Goal and Milestone Management
- Quiz Management (Questions, Answers, and Matching)
- Goal and Milestone Performance Scoring
- Quiz Evaluation with Random Question Selection
- Swagger API documentation for easy API exploration

## Prerequisites

Make sure you have the following installed on your system:
- **Java 11** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git**

## Configuration

The configuration for the application is defined in `src/main/resources/application.properties`. Below are the default properties that connect the application to a PostgreSQL database:

```properties
# Application Name
spring.application.name=Learning Performance Indicator

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/LPI
spring.datasource.username=YourUsername
spring.datasource.password=Password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA & Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.datasource.url: Change the URL if your PostgreSQL instance is running on a different host or port.
spring.datasource.username: PostgreSQL username.
spring.datasource.password: PostgreSQL password.
spring.jpa.hibernate.ddl-auto: Manage how Hibernate handles schema updates.
Database Setup
SQL
CREATE DATABASE LPI;

**Build and Run the Application
Clone the repository:**

git clone https://github.com/your-repo/learning-performance-indicator.git
cd learning-performance-indicator

**Build the project using Maven**:
mvn clean install

**Run the application**:
mvn spring-boot:run
The application will be accessible at http://localhost:8080.

**Swagger API Documentation**

http://localhost:8080/swagger-ui/index.html#/
