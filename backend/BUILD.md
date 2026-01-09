# Build Instructions

## Quick Build

```bash
cd backend
./gradlew build
```

On Windows:
```bash
cd backend
gradlew.bat build
```

## Build Options

### Standard Build
```bash
./gradlew build
```
- Compiles code
- Runs tests
- Runs code quality checks
- Creates JAR file in `build/libs/`

### Build Without Tests (Faster)
```bash
./gradlew build -x test
```
Useful for quick builds when you know tests pass.

### Clean Build
```bash
./gradlew clean build
```
Removes previous build artifacts first, then builds fresh.

### Build Without Quality Checks (Faster)
```bash
./gradlew build -x checkstyleMain -x pmdMain -x spotbugsMain
```
Skips code quality checks for faster builds.

### Build Only (No Tests, No Checks)
```bash
./gradlew assemble
```
Just compiles and packages, no tests or checks.

## Build Output

After building, find the JAR file:
```
build/libs/backend-0.0.1-SNAPSHOT.jar
```

## Run the Application

### Option 1: Using Gradle
```bash
./gradlew bootRun
```

### Option 2: Using JAR
```bash
# Build first
./gradlew build

# Run
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

### Option 3: With Environment Variables
```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/academy
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

## Docker Build

### Build Docker Image
```bash
docker build -t academy-backend:latest .
```

### Run Docker Container
```bash
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host:port/db \
  -e DATABASE_USERNAME=user \
  -e DATABASE_PASSWORD=pass \
  academy-backend:latest
```

## Troubleshooting

### Build Fails with "Gradle not found"
Make sure you're in the `backend` directory and the `gradlew` file exists:
```bash
ls -la gradlew
chmod +x gradlew  # Make executable if needed
```

### Build Fails with "Java version error"
Check Java version:
```bash
java -version  # Should be 17 or higher
```

### Build Fails with "Database connection error"
Tests require a database. Either:
1. Skip tests: `./gradlew build -x test`
2. Set up PostgreSQL (see LOCAL_SETUP.md)
3. Tests use Testcontainers, so Docker must be running

### Build is Slow
- Use `-x test` to skip tests
- Use `-x checkstyleMain -x pmdMain -x spotbugsMain` to skip quality checks
- Or use `assemble` for fastest build

## Common Build Commands

```bash
# Full build with everything
./gradlew build

# Quick build (no tests)
./gradlew build -x test

# Clean and rebuild
./gradlew clean build

# Just compile and package
./gradlew assemble

# Run application
./gradlew bootRun

# Format code
./gradlew spotlessApply

# Run tests only
./gradlew test

# Run specific test
./gradlew test --tests "AcademyServiceTest"
```

## Build for Production

### Create Production JAR
```bash
./gradlew clean build -x test
```

### Verify JAR
```bash
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar --version
```

### Test Production Build
```bash
# Set environment variables
export DATABASE_URL=jdbc:postgresql://localhost:5432/academy
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

# Run
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

## Next Steps

After building:
1. ✅ JAR file created in `build/libs/`
2. ✅ Run with `java -jar build/libs/backend-0.0.1-SNAPSHOT.jar`
3. ✅ Or use `./gradlew bootRun` for development
4. ✅ Check health: http://localhost:8080/actuator/health

For more details, see:
- [LOCAL_SETUP.md](./LOCAL_SETUP.md) - Complete setup guide
- [TESTING.md](./TESTING.md) - Testing guide

