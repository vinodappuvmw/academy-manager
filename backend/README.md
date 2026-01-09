## Backend (Spring Boot 3, Gradle)

Purpose: Core API for academy management including coaches, students, training sessions, attendance, ratings, payments, and more.

Tech: Java 17+, Spring Boot 3, Gradle, PostgreSQL, Redis (optional), Flyway, Springdoc OpenAPI.

## Quick Start

### Prerequisites
- Java 17+
- PostgreSQL 12+
- Gradle 7.6+ (or use included wrapper)

### Setup
1. **Create Database**
   ```bash
   psql -U postgres
   CREATE DATABASE academy;
   \q
   ```

2. **Build & Run**
   ```bash
   ./gradlew build
   ./gradlew bootRun
   ```

3. **Verify**
   - Health: http://localhost:8080/actuator/health
   - API Docs: http://localhost:8080/swagger-ui.html

### Detailed Setup
See [LOCAL_SETUP.md](./LOCAL_SETUP.md) for complete local development guide.

## Documentation

- [LOCAL_SETUP.md](./LOCAL_SETUP.md) - Local development setup
- [RAILWAY_DEPLOYMENT.md](./RAILWAY_DEPLOYMENT.md) - Production deployment
- [PRODUCTION_READINESS.md](./PRODUCTION_READINESS.md) - Production checklist
- [CODE_REVIEW_SUMMARY.md](./CODE_REVIEW_SUMMARY.md) - Code review summary

## API Endpoints

All endpoints follow the pattern: `/api/v1/academies/{academyId}/...`

- **Academies**: `/api/v1/academies`
- **Coaches**: `/api/v1/academies/{academyId}/coaches`
- **Students**: `/api/v1/academies/{academyId}/students`
- **Sports**: `/api/v1/academies/{academyId}/sports`
- **Training Sessions**: `/api/v1/academies/{academyId}/training-sessions`
- **And more...**

Full API documentation available at `/swagger-ui.html` when running.

## Project Structure

```
backend/
├── src/main/java/com/academy/
│   ├── domain/          # JPA entities
│   ├── repository/      # Spring Data repositories
│   ├── service/         # Business logic
│   ├── controller/      # REST controllers
│   ├── dto/            # Data transfer objects
│   ├── mapper/         # MapStruct mappers
│   ├── exception/      # Custom exceptions
│   └── config/         # Configuration classes
├── src/main/resources/
│   ├── db/migration/   # Flyway migrations
│   └── application.yml # Application configuration
└── build.gradle        # Build configuration
```

## Features

- ✅ Full CRUD APIs for all entities
- ✅ Multi-tenancy (academy-scoped)
- ✅ Multi-sport support
- ✅ Student ratings with history
- ✅ Attendance tracking
- ✅ Fee and salary management
- ✅ Home workouts
- ✅ Periodisation plans
- ✅ OpenAPI/Swagger documentation

## Environment Variables

See `application.yml` for all configurable options. Key variables:

- `DATABASE_URL` - PostgreSQL connection string
- `DATABASE_USERNAME` - Database username
- `DATABASE_PASSWORD` - Database password
- `PORT` - Server port (default: 8080)
- `CORS_ALLOWED_ORIGINS` - Allowed CORS origins

## Building

```bash
# Build project
./gradlew build

# Build without tests
./gradlew build -x test

# Run application
./gradlew bootRun
```

## Testing

```bash
# Run all tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport
```

## Code Quality

```bash
# Format code
./gradlew spotlessApply

# Run all checks
./gradlew check
```

---

For detailed setup instructions, see [LOCAL_SETUP.md](./LOCAL_SETUP.md).

