# Task Management System

A task management backend system developed with Kotlin and Spring Boot. This project serves as a learning initiative to
transition from Java to Kotlin while implementing industry best practices.

## Technology Stack

- **Language:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.4.0
- **Build Tool:** Gradle 8.x
- **Database:** PostgreSQL
- **ORM:** JPA/Hibernate
- **Migration:** Flyway 10.4.1
- **Testing:** JUnit 5.10.2
- **Containerization:** Docker

## Quick Start with Docker

1. Clone the repository:

```bash
git clone https://github.com/kinyha/Task-Management-System-Azati.git
cd task-management-system
cd docker
```

2. Build and run with Docker Compose:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`

## API Endpoints

### Task Management

- `POST /api/v1/tasks` - Create a new task
- `PUT /api/v1/tasks/{taskId}/status` - Update task status
- `PUT /api/v1/tasks/{taskId}/assign/{userId}` - Assign task to user
- `GET /api/v1/tasks?status={status}` - Get tasks by status
- `GET /api/v1/tasks/{taskId}` - Get task by ID
- `GET /api/v1/tasks/overdueTasks` - Get overdue tasks

## Implementation Progress

### Core Language Features

- [x] Data classes for DTOs
- [x] Extension functions
- [x] Scope function
- [x] Inline functions
- [x] Destructuring declarations
- [x] Operator overloading

### Advanced Features

- [ ] Custom delegates
- [ ] Inline value classes
- [ ] JVM annotations (@JvmStatic, @JvmOverloads)
- [ ] Coroutines
- [ ] Kotlin/JS

### Development Practices

- [x] Test-Driven Development (TDD)
- [x] Database version control with migrations
- [x] Custom query optimization

### Infrastructure

- [x] Docker containerization
- [x] PostgreSQL configuration

## Next Steps
1. Implement custom delegates
2. Implement remaining Kotlin features (Coroutines, Kotlin/JS)
3. Add API documentation
4. Set up monitoring and logging
5. Implement security features

