## Backend (Spring Boot 3, Gradle)

Purpose: Core API for auth/OTP/JWT, organizations/owners, people, locations, batches, sessions, attendance, payments, attributes/ratings (JSONB), matches, workouts, media presign, analytics.

Tech: Java 17+, Spring Boot 3, Gradle (Groovy), PostgreSQL, Redis, S3-compatible storage, Flyway, Springdoc OpenAPI.

Quality (to add next): Spotless, Checkstyle/PMD, SpotBugs, ArchUnit; Testcontainers for Postgres/Redis.

Next steps:
1) Add quality plugins/configs to `build.gradle`.
2) Scaffold packages per plan (`auth`, `organization`, `owner`, `people`, `location`, `batch`, `session`, `attendance`, `payment`, `attributes`, `rating`, `match`, `workout`, `media`, `analytics`, `common`).
3) Wire Flyway, application profiles, and OpenAPI.


