# Order Management System — DDD + TDD

Simple Spring Boot REST application demonstrating Domain-Driven Design and Test-Driven Development.

## Requirements
- Java 21
- Maven (or use IntelliJ's bundled Maven)
- Docker Desktop

## Start PostgreSQL
From the project folder:
```powershell
docker compose up -d
```

## Run tests
```powershell
mvn test
```

## Run application
```powershell
mvn spring-boot:run
```
Or run `OrderApplication` in IntelliJ.

## API examples (PowerShell)

Create order:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/orders" -Method POST -ContentType "application/json" -Body '{"productName":"Keyboard","quantity":2,"unitPrice":25.50}'
```

List orders:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/orders" -Method GET
```

Get order by ID (replace UUID):
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/orders/UUID" -Method GET
```

Pay order:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/orders/UUID/pay" -Method POST
```

Cancel order:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/orders/UUID/cancel" -Method POST
```

## Package structure
- `domain`: business objects, rules, repository abstraction
- `application`: use-case orchestration
- `infrastructure`: PostgreSQL/JPA implementation
- `presentation`: REST API and DTOs/exception handling

## TDD examples
- `OrderTest`: tests the business rules of the domain without Spring or database.
- `OrderServiceTest`: tests application logic using Mockito.

This is a small learning sample, not a production-ready application. In production, consider validation, DTO validation, database migrations, security, concurrency handling, and integration tests.
