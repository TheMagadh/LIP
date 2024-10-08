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
spring.datasource.username=Youruser
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA & Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


Database Setup
Make sure PostgreSQL is installed and running.


**Create a new database in PostgreSQL by running the following command:**

sql
CREATE DATABASE LPI;

**Ensure that your PostgreSQL credentials match the values in the application.properties file.

The application will automatically create and update the necessary tables upon start, thanks to the spring.jpa.hibernate.ddl-auto=update setting.**

**Build and Run the Application
Clone the repository:**

git clone https://github.com/TheMagadh/LIP.git
cd LPI

**Build the project using Maven:**
mvn clean install

**Run the application:**
mvn spring-boot:run
The application will be accessible at http://localhost:8080.

**Swagger API Documentation**
Swagger is integrated into the application to provide API documentation and testing capabilities. The Swagger UI will be available once the application starts.

Accessing Swagger
After the application is running, open the following URL to access the Swagger UI:

http://localhost:8080/swagger-ui/index.html#/

Swagger allows you to explore and interact with the APIs directly from the web interface.

Example API Endpoints
Here are some of the key API endpoints available in the Learning Performance Indicator system:

Retrieve Random Quiz Questions
GET /api/quiz
Returns a list of 10 random quiz questions, without revealing the correct answers.

Retrieve Random Quiz Questions with Answers
GET /api/quiz/All
Returns 10 random quiz questions with correct answers included.

Get Answer for a Specific Quiz Question
GET /api/quiz/answer/{id}
Returns the correct answer for the given quiz question ID.

Create Questions in Bulk
POST /api/questions/bulk
Accepts a list of questions in the request body and saves them in the system.

Delete All Questions
DELETE /api/questions/all
Deletes all the questions from the database.

Contributing
We welcome contributions from the community. Here are the steps to contribute:

Fork the repository
Create a new branch: git checkout -b feature/new-feature
Commit your changes: git commit -m 'Add a new feature'
Push to the branch: git push origin feature/new-feature
Open a pull request
License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
If you have any questions or suggestions, feel free to reach out to us .

Thank you for using the Learning Performance Indicator! We hope it enhances your learning experience.

markdown
Copy code

### Explanation:
- **Configuration**: Explains how to configure the database and set properties.
- **Swagger API Documentation**: Directs users to the Swagger UI for testing APIs.
- **Example API Endpoints**: Provides key API endpoints for interacting with the system.
- **Contributing**: Guides developers on how to contribute to the project.
