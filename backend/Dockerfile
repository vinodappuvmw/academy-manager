# Build stage
FROM gradle:8.9-jdk17-alpine AS build

WORKDIR /app

# Copy Gradle files first (for better caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build application (use wrapper to ensure correct Gradle version)  
RUN chmod +x ./gradlew && ./gradlew build -x test -x checkstyleMain -x pmdMain -x spotbugsMain -x spotlessJavaCheck -x spotlessMiscCheck --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

