# Project Overview

This project is built using **Hexagonal Architecture** to ensure maintainability, testability, and clear separation of concerns.

## Tech Stack
- **Java 21** as the programming language
- **H2 Database (file-based)** for persistence
- **MapStruct** for object mapping between different layers
- **JUnit 5** for unit testing
- **Liquibase** for database migration scripts

## Controllers
The application exposes two main controllers:

1. **ProductController**
    - CRUD operations for products

2. **PurchaseController**
    - CRUD operations for purchases
    - Endpoint to download purchase receipts

## Getting Started
1. Build and install the project:
   mvn clean install
# sales-taxes
