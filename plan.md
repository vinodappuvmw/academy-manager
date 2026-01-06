## Plan: Java Backend + Web Admin + Two PWAs + Owner Portal (SOLID & Quality Focus)

### Scope
- Five separate projects/folders:
  - `backend` – Spring Boot 3 service
  - `admin-web` – Admin Web App (desktop-first, not a PWA)
  - `coach-pwa` – Coach PWA (installable, mobile-first)
  - `student-pwa` – Student/Player PWA (installable, mobile-first)
  - `owner-web` – Owner portal (web) for academy owner onboarding/login/config
- Tech: Spring Boot 3, PostgreSQL, Redis, S3 storage; React/Next.js + TypeScript for front-ends.

### Architecture & Design Principles
- Clean layering: API (controllers) → Application/Service → Domain → Infrastructure (adapters/gateways).
- SOLID:
  - Single Responsibility: thin controllers; services per domain; repositories per aggregate.
  - Open/Closed: strategies/interfaces for OTP, notifications, storage, payments.
  - Liskov: domain interfaces with contract tests.
  - Interface Segregation: fine-grained ports (OtpSender, MediaStorage, PaymentReminder).
  - Dependency Inversion: domain depends on ports; adapters implement ports.
- Patterns:
  - DTOs + mappers (e.g., MapStruct) to decouple transport from domain.
  - Value objects for IDs, money, phone/email; enums for status.
  - Validation via Bean Validation + domain invariants.
  - Idempotent handlers for key writes (attendance/ratings).
- Static analysis & quality gates:
  - Backend: Spotless (fmt), Checkstyle/PMD, SpotBugs, Error Prone (if enabled), ArchUnit (layering), Testcontainers for integrations.
  - Frontend: ESLint + TypeScript strict, Prettier, React Testing Library + Vitest/Jest, Lighthouse CI for PWA scoring (Coach/Player).
- Security:
  - Spring Security with JWT, method-level guards; OTP rate limiting via Redis; validation and output encoding; audit logging on auth and payment/attendance actions.

### Backend Plan (`backend`, Spring Boot)
- Deps: Web, Security, Data JPA, Validation, Actuator, Flyway, PostgreSQL driver, Redis, S3 SDK, springdoc-openapi, Testcontainers (pg/redis), optional MapStruct.
- Modules/packages: auth, organization, owner, people, location, batch, session, attendance, payment, attributes, rating, match, workout, media, analytics, common.
- REST endpoints (summary):
  - Auth/OTP/JWT: /auth/otp/request, /auth/otp/verify, /auth/token/refresh
  - Orgs/Roles/Owners: /orgs, /me/roles, /owners (create/manage owner accounts)
  - People: /coaches, /players, /admins
  - Locations: /locations
  - Batches: /batches + /batches/{id}/coaches|players|locations
  - Sessions: /sessions, /sessions/{id}
  - Attendance: /sessions/{id}/attendance
  - Media: /media/presign
  - Payments: /payments, /payments/{id}/remind
  - Attributes/Ratings: /attributes, /sessions/{id}/ratings/schema, /sessions/{id}/ratings, /players/{id}/ratings
  - Matches: /matches, /matches/{id}/participants, /matches/{id}/reports/player|team
  - Workouts: /workouts, /workouts/{id}/assign, /players/{id}/workouts
  - Analytics: /analytics/attendance, /analytics/payments
- Data model: orgs, users/roles, coaches/players/admins, locations, batches/sessions, attendances with photo ref, payments/reminders, attribute_definitions, ratings (JSONB), matches/participants/reports, player_positions, workouts, materialized views for attendance/payment summaries.
- Media: presigned S3 URLs; DB stores keys/URLs only.
- Scheduling: reminders, analytics view refresh.
- Tests: unit (services, validators), contract tests for adapters (OTP sender, storage), integration with Testcontainers.

### Frontend Plan

#### Admin Web (`admin-web`, Next.js + TS, desktop-first, not PWA)
- Features: CRUD students/coaches/locations/batches/sessions; payments & reminders; attendance/payment summaries; attribute config; matches/tournaments; dashboards.
- Tech: Next.js + TS, React Query, React Hook Form + Zod, UI library (MUI/Chakra/Tailwind), shared API client from OpenAPI.

#### Coach PWA (`coach-pwa`, Next.js + TS, installable PWA)
- Routes: login (OTP), home, sessions list/detail, attendance, plan upload, ratings, players list/detail, workouts, matches (1:1/team analysis), profile/settings.
- PWA/offline: manifest, service worker; cache shell + recent data; offline write queue for attendance/ratings.

#### Student PWA (`student-pwa`, Next.js + TS, installable PWA)
- Routes: login, home, profile, ratings (+history), workouts, payments, attendance, matches/reports.
- PWA/offline: same strategy; cache recent profile/ratings/workouts/payments/attendance.

#### Owner Web (`owner-web`, Next.js + TS, web, not PWA-required)
- Purpose: onboard academy owners, create/log in owners, configure their academy, invite first admin/coach.
- Features: owner signup/login (OTP), create organization, set branding details, add initial admins/coaches, view billing/plan info (optional future).
- Tech: Next.js + TS, React Query, RHF + Zod, same shared API client; simple desktop-first UI.

### Design System (from Figma)
- Extract tokens: colors, typography, radii, spacing, shadows.
- Base components: Button, Input, Select, Card, AppBar, BottomNav, TabBar, ListItem, Chip, Toast, ModalSheet.
- Domain components: SessionCard, PlayerListItem, AttendanceRow, RatingInputGroup, WorkoutCard, MatchCard, PaymentItem, StatPill, ReportSection.
- Shared across Coach/Player; Admin can reuse foundations.

### Quality & CI/CD
- Backend CI: Spotless, Checkstyle/PMD, SpotBugs, ArchUnit; unit + integration (Testcontainers); Docker image build.
- Frontend CI: ESLint, TS strict, Prettier, unit tests, Lighthouse CI (Coach/Student) enforcing PWA scores; bundle-size check.
- CD: deploy backend container + frontends (static/edge); managed Postgres/Redis/S3; monitoring (Actuator + APM/metrics).

### Delivery Steps
1) Backend scaffold with quality gates (Spotless/Checkstyle/PMD/SpotBugs/ArchUnit) and Flyway.
2) Auth/OTP/JWT + orgs/roles with Redis rate limits; publish OpenAPI spec.
3) Core domains: people, locations, batches, sessions.
4) Attendance + media (presigned S3) and tests.
5) Payments + reminders + analytics views; tests.
6) Attributes/ratings (JSONB), matches/reports, positions, workouts; tests.
7) Design system tokens/components from Figma; generate TS API client.
8) Admin Web screens (desktop CRUD, dashboards).
9) Coach PWA screens with offline queue/cache and PWA setup.
10) Student PWA screens with offline cache and PWA setup.
11) Owner Web screens for onboarding/configuring academies.
12) CI/CD pipelines with lint/tests/coverage + Lighthouse CI (Coach/Student) + Docker deploys; monitoring/logging.

