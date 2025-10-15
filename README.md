[readme.txt](https://github.com/user-attachments/files/22937282/readme.txt)
## Overview
This project demonstrates a **microservices-based system** built with **Spring Boot** that manages orders and inventory levels.  
It consists of two services:

1. **Order Service** – Handles order creation and retrieval. Validates item availability in the Inventory Service before placing an order.  
2. **Inventory Service** – Manages inventory stock levels and provides availability data to the Order Service.

---

## Objective
The goal is to build two decoupled microservices that communicate via REST APIs, following clean architecture, SOLID principles, and proper documentation.

## Technology Stack

| Component | Technology |
|------------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.3.4 |
| Database | H2 (in-memory, via Spring Data JPA) |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |
| Containerization | Docker & Docker Compose |

---

## Architecture
┌────────────────────┐       ┌────────────────────────┐
│   Order Service    │◄─────►│   Inventory Service    │
│  - Place Order     │       │  - Manage Stock        │
│  - Get Orders      │       │  - Check Availability  │
└────────────────────┘       └────────────────────────┘

## How to Run

cd inventory-service
mvn spring-boot:run

cd ../order-service
mvn spring-boot:run

or with Docker -> 
docker-compose up --build
