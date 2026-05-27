# Blood Bank Management System

A full-stack Java web application for managing blood donors, hospital blood requests, and real-time blood inventory. This project is designed around the Blood Bank Management System listed in my resume and demonstrates Spring Boot REST APIs, MySQL database design, validation, and role-focused dashboards.

## Built By

**Balla Sowmya Sushma**  
Computer Science Engineering student  
GitHub: [sowmya2619](https://github.com/sowmya2619)

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- REST APIs
- MySQL
- HTML, CSS, JavaScript
- H2 database for quick local demo

## Key Features

- Donor registration with blood group, contact, city, and eligibility details
- Hospital registration and blood request management
- Blood inventory tracking by blood group and unit availability
- RESTful CRUD APIs for donors, hospitals, requests, and inventory
- Dashboard summaries for total donors, hospitals, requests, and available units
- Request approval workflow that automatically reduces inventory
- Validation for clean and reliable input data
- MySQL-ready configuration with sample schema/data

## Project Structure

```text
blood-bank-management-system/
  src/main/java/com/sowmya/bloodbank/
    controller/       REST controllers
    dto/              API request/response objects
    entity/           JPA database entities
    exception/        API error handling
    repository/       Spring Data repositories
    service/          Business logic
  src/main/resources/
    static/           Frontend dashboard
    application.properties
    data.sql          Demo data
```

## Run Locally

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## MySQL Setup

Create a database:

```sql
CREATE DATABASE blood_bank_db;
```

Then update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blood_bank_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## API Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/dashboard` | Dashboard summary |
| GET | `/api/donors` | List donors |
| POST | `/api/donors` | Register donor |
| GET | `/api/hospitals` | List hospitals |
| POST | `/api/hospitals` | Register hospital |
| GET | `/api/inventory` | List blood inventory |
| PATCH | `/api/inventory` | Add inventory units |
| GET | `/api/requests` | List blood requests |
| POST | `/api/requests` | Create blood request |
| PATCH | `/api/requests/{id}/approve` | Approve request and update inventory |

## Resume Alignment

This project demonstrates:

- Full-stack web development using Spring Boot, HTML, CSS, and JavaScript
- REST API design and CRUD operations
- MySQL schema design with entity relationships
- Real-world workflow replacement for manual blood bank operations
- Validation, exception handling, and inventory consistency
"# bloodbank_management-" 
