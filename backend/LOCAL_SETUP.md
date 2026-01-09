# Local Development Setup Guide

## Prerequisites

### Required
- **Java 17 or higher** - [Download](https://adoptium.net/) or use SDKMAN
- **PostgreSQL 12+** - [Download](https://www.postgresql.org/download/)
- **Gradle 7.6+** (or use Gradle Wrapper included in project)

### Optional
- **Redis** - Only needed if you plan to use caching/OTP features
- **Docker** - For running PostgreSQL/Redis in containers (alternative to local install)

## Step 1: Install Prerequisites

### Java 17
```bash
# Check Java version
java -version

# Should show version 17 or higher
# If not installed, download from https://adoptium.net/
```

### PostgreSQL
```bash
# macOS (using Homebrew)
brew install postgresql@14
brew services start postgresql@14

# Linux (Ubuntu/Debian)
sudo apt-get install postgresql postgresql-contrib

# Windows
# Download and install from https://www.postgresql.org/download/windows/
```

### Redis (Optional)
```bash
# macOS (using Homebrew)
brew install redis
brew services start redis

# Linux (Ubuntu/Debian)
sudo apt-get install redis-server

# Windows
# Download from https://redis.io/download or use WSL
```

## Step 2: Set Up Database

### Create Database
```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE academy;

# Create user (optional, or use default postgres user)
CREATE USER academy_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE academy TO academy_user;

# Exit psql
\q
```

### Using Docker (Alternative)
```bash
# Run PostgreSQL in Docker
docker run --name academy-postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=academy \
  -p 5432:5432 \
  -d postgres:14

# Run Redis in Docker (optional)
docker run --name academy-redis \
  -p 6379:6379 \
  -d redis:7-alpine
```

### Using Docker Desktop with Docker Compose (Recommended)
**Note: Gradle build is NOT required manually - Docker handles it automatically during image build.**

#### Prerequisites
- **Docker Desktop** installed and running
  - Download from: https://www.docker.com/products/docker-desktop/
  - Ensure Docker Desktop is running (check system tray/status bar)

#### Steps to Run

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Start all services with Docker Compose**
   ```bash
   docker-compose up --build
   ```
   
   This command will:
   - Build the backend Docker image (Gradle build happens automatically inside Docker)
   - Start PostgreSQL database
   - Start Redis (optional)
   - Start the backend application
   - Wait for services to be healthy before starting dependent services

3. **Run in detached mode (background)**
   ```bash
   docker-compose up -d --build
   ```

4. **View logs**
   ```bash
   # All services
   docker-compose logs -f
   
   # Specific service
   docker-compose logs -f backend
   docker-compose logs -f postgres
   ```

5. **Stop services**
   ```bash
   docker-compose down
   ```

6. **Stop and remove volumes (clean slate)**
   ```bash
   docker-compose down -v
   ```

#### Verify Installation
Once services are running:
- Health check: `curl http://localhost:8080/actuator/health`
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/v3/api-docs

#### Troubleshooting Docker Desktop

**Docker Desktop not running**
- Ensure Docker Desktop is started (check system tray)
- Wait for Docker to fully initialize

**Port conflicts**
- If port 5432, 6379, or 8080 are already in use, stop conflicting services or modify ports in `docker-compose.yml`

**Build failures**
- Ensure Docker Desktop has enough resources allocated (Settings → Resources)
- Try: `docker-compose build --no-cache` to rebuild from scratch

**View container status**
```bash
docker-compose ps
```

**Restart a specific service**
```bash
docker-compose restart backend
```

## Step 3: Configure Application

### Option A: Use Default Configuration (Easiest)
The application uses sensible defaults for local development:
- Database: `localhost:5432/academy`
- Username: `postgres`
- Password: `postgres`
- Port: `8080`
- Redis: `localhost:6379` (optional)

### Option B: Use Environment Variables
Create a `.env` file in the `backend` directory (or set in your shell):

```bash
# Database
export DATABASE_URL=jdbc:postgresql://localhost:5432/academy
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

# Server
export PORT=8080

# Redis (optional)
export REDIS_HOST=localhost
export REDIS_PORT=6379

# CORS (for frontend development)
export CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:3001

# Logging (optional)
export LOG_LEVEL=info
export HIBERNATE_FORMAT_SQL=false
```

### Option C: Create application-local.yml
Create `src/main/resources/application-local.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/academy
    username: postgres
    password: postgres
  redis:
    host: localhost
    port: 6379

server:
  port: 8080

logging:
  level:
    root: info
    org.hibernate.SQL: debug
    org.hibernate.type.descriptor.sql.BasicBinder: trace
```

Then run with: `./gradlew bootRun --args='--spring.profiles.active=local'`

## Step 4: Build the Project

### Using Gradle Wrapper (Recommended)
```bash
# Navigate to backend directory
cd backend

# Build the project
./gradlew build

# On Windows
gradlew.bat build
```

### Build Options
```bash
# Build without tests (faster)
./gradlew build -x test

# Clean and build
./gradlew clean build

# Build and skip quality checks (faster)
./gradlew build -x checkstyleMain -x pmdMain -x spotbugsMain
```

## Step 5: Run the Application

### Option A: Using Gradle (Recommended for Development)
```bash
# Run the application
./gradlew bootRun

# On Windows
gradlew.bat bootRun

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=local'
```

### Option B: Using Java JAR
```bash
# Build first
./gradlew build

# Run the JAR
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

### Option C: Using IDE
1. Open the project in IntelliJ IDEA or Eclipse
2. Import as Gradle project
3. Run `BackendApplication` main class
4. Or use Spring Boot run configuration

## Step 6: Verify Installation

### Check Health Endpoint
```bash
# Health check
curl http://localhost:8080/actuator/health

# Should return:
# {"status":"UP"}
```

### Check API Documentation
Open in browser:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/v3/api-docs

### Test an Endpoint
```bash
# Get all academies (will be empty initially)
curl http://localhost:8080/api/v1/academies

# Create an academy
curl -X POST http://localhost:8080/api/v1/academies \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Academy",
    "address": "123 Test St",
    "phoneNumber": "1234567890",
    "email": "test@academy.com"
  }'
```

## Database Migrations

Flyway will automatically run migrations on startup. Check migration status:

```bash
# Connect to database
psql -U postgres -d academy

# Check Flyway schema history
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

# Exit
\q
```

## Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
# macOS/Linux
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or change port in application.yml or via environment variable
export PORT=8081
```

### Database Connection Failed
```bash
# Check PostgreSQL is running
# macOS/Linux
brew services list
# or
pg_isready

# Windows
# Check Services panel for PostgreSQL

# Test connection
psql -U postgres -h localhost -d academy
```

### Migration Errors
```bash
# Check Flyway logs in application output
# Common issues:
# 1. Database doesn't exist - create it first
# 2. User doesn't have permissions - grant privileges
# 3. Migration already applied - check flyway_schema_history table
```

### Build Failures
```bash
# Clean and rebuild
./gradlew clean build

# Check Java version
java -version  # Should be 17+

# Check Gradle version
./gradlew --version

# Clear Gradle cache (if needed)
./gradlew clean --refresh-dependencies
```

### Redis Connection Issues
Redis is optional. If you don't have Redis:
- The application will fail to start if Redis is required
- Either install Redis or remove Redis dependency from build.gradle
- Currently Redis is included but not actively used

## Development Tips

### Enable SQL Logging
Add to `application.yml` or set environment variable:
```yaml
logging:
  level:
    org.hibernate.SQL: debug
    org.hibernate.type.descriptor.sql.BasicBinder: trace
```

Or via environment:
```bash
export HIBERNATE_FORMAT_SQL=true
```

### Hot Reload (Spring DevTools)
Add to `build.gradle` dependencies:
```gradle
developmentOnly 'org.springframework.boot:spring-boot-devtools'
```

Then restart is automatic on code changes.

### Run Tests
```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "AcademyServiceTest"

# Run with coverage
./gradlew test jacocoTestReport
```

### Code Quality Checks
```bash
# Format code
./gradlew spotlessApply

# Run all checks
./gradlew check

# Individual checks
./gradlew checkstyleMain
./gradlew pmdMain
./gradlew spotbugsMain
```

## Quick Start Script

Create a `start.sh` (or `start.bat` for Windows):

```bash
#!/bin/bash
# start.sh

# Check if PostgreSQL is running
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo "PostgreSQL is not running. Please start it first."
    exit 1
fi

# Set environment variables
export DATABASE_URL=jdbc:postgresql://localhost:5432/academy
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

# Build and run
./gradlew clean build -x test && ./gradlew bootRun
```

Make it executable:
```bash
chmod +x start.sh
./start.sh
```

## Next Steps

1. ✅ Application is running
2. 📝 Review API documentation at http://localhost:8080/swagger-ui.html
3. 🧪 Test endpoints using Swagger UI or Postman
4. 🔗 Connect your frontend applications
5. 📚 Read `PRODUCTION_READINESS.md` for production deployment

## Useful Commands Summary

```bash
# Build
./gradlew build

# Run
./gradlew bootRun

# Test
./gradlew test

# Clean
./gradlew clean

# Format code
./gradlew spotlessApply

# Check health
curl http://localhost:8080/actuator/health

# View logs
tail -f logs/application.log  # if logging to file
```

---

**Need Help?** Check the troubleshooting section or review the application logs for specific error messages.

