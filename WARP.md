# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Development Commands

### Running the Application
```bash
# Run the application using Maven wrapper
./mvnw spring-boot:run

# Run with specific profile (if needed)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Run using Java directly (after building)
./mvnw clean package
java -jar target/test-0.0.1-SNAPSHOT.jar
```

### Building and Testing
```bash
# Clean and compile the project
./mvnw clean compile

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=TestApplicationTests

# Package the application
./mvnw clean package

# Skip tests during packaging
./mvnw clean package -DskipTests
```

### Database Setup
Before running the application, ensure PostgreSQL is running and configured:
```bash
# Default database connection (update application.properties if different):
# URL: jdbc:postgresql://localhost:5432/local
# Username: scenario
# Password: scenario
```

### API Testing
```bash
# Access Swagger UI for API testing
curl http://localhost:8080/swagger-ui/index.html
# Or open in browser: http://localhost:8080/swagger-ui/index.html

# Test basic endpoint
curl -X GET http://localhost:8080/projects
```

## Code Architecture

### Project Structure
This is a Spring Boot 3.2.5 application using Java 17 with the following key components:

#### Main Application Package
- **Package**: `com.scenario.team.cloud.test`
- **Main Class**: `TestApplication` - Contains Spring Boot entry point and CORS configuration

#### Domain Layer (`projects` package)
- **Entity**: `Project` - JPA entity representing automation projects with id and name fields
- **Repository**: `ProjectRepository` - JPA repository interface extending JpaRepository
- **Service**: `ProjectService` - Business logic layer handling CRUD operations
- **Controller**: `ProjectController` - REST API endpoints for project management
- **DTOs**: `InProjectDTO` and `OutProjectDTO` - Data transfer objects for API requests/responses
- **Exceptions**: `ProjectNotFoundException` - Custom exception for project not found scenarios

#### Key Dependencies
- **Spring Boot Starter Web**: REST API functionality
- **Spring Boot Starter Data JPA**: Database integration with Hibernate
- **PostgreSQL Driver**: Database connectivity
- **SpringDoc OpenAPI**: Swagger documentation (accessible at `/swagger-ui/index.html`)

#### Database Configuration
- Uses PostgreSQL with Hibernate ORM
- Database schema auto-updates enabled (`spring.jpa.hibernate.ddl-auto=update`)
- SQL queries logged for development (`spring.jpa.show-sql=true`)

#### REST API Structure
Base path: `/projects`
- `POST /projects` - Create new project
- `GET /projects` - List all projects (sorted by ID)
- `PUT /projects/{id}` - Update existing project
- `DELETE /projects/{id}` - Delete project

### Development Patterns
- Uses constructor injection for dependencies
- Nullable annotations throughout for better null safety
- Record classes for DTOs (Java 14+ feature)
- Service layer pattern separating business logic from controllers
- Repository pattern using Spring Data JPA
