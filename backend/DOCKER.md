# Docker Setup Guide

## Overview

The Dockerfile builds and runs the application container. The database is a **separate service** (best practice). Use `docker-compose.yml` to run everything together.

## Why Separate Database?

✅ **Best Practice**: Database and application should be separate containers
- Easier to scale independently
- Better for production deployments
- Database can be managed separately (backups, updates)
- Application container is stateless

## Dockerfile (Application Only)

The `Dockerfile`:
- ✅ Builds the application
- ✅ Creates the JAR file
- ✅ Runs the application
- ❌ Does NOT include database (by design)

**Database connection** comes from **environment variables** at runtime.

## Running with Docker Compose (Recommended)

### Start Everything
```bash
docker-compose up -d
```

This starts:
- PostgreSQL database
- Redis (optional)
- Backend application

### Stop Everything
```bash
docker-compose down
```

### Stop and Remove Volumes
```bash
docker-compose down -v
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f postgres
```

### Rebuild After Code Changes
```bash
docker-compose up -d --build
```

## Running Dockerfile Only

If you only want to build/run the application container:

### Build
```bash
docker build -t academy-backend:latest .
```

### Run (with external database)
```bash
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/academy \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=postgres \
  academy-backend:latest
```

**Note**: You need a database running separately (local PostgreSQL or another container).

## Environment Variables

The application needs these environment variables:

### Required
- `DATABASE_URL` - PostgreSQL connection string
- `DATABASE_USERNAME` - Database username
- `DATABASE_PASSWORD` - Database password

### Optional
- `REDIS_HOST` - Redis host (default: localhost)
- `REDIS_PORT` - Redis port (default: 6379)
- `PORT` - Server port (default: 8080)
- `CORS_ALLOWED_ORIGINS` - CORS origins
- `LOG_LEVEL` - Logging level

## Database Migrations

Flyway automatically runs migrations when the application starts:
- Migrations are in `src/main/resources/db/migration/`
- Flyway runs them in order (V1, V2, V3, etc.)
- Database must exist before application starts

## Health Checks

### Application Health
```bash
curl http://localhost:8080/actuator/health
```

### Database Health (from host)
```bash
docker exec academy-postgres pg_isready -U postgres
```

## Production Deployment

For production:
1. **Use managed database** (Railway, AWS RDS, etc.)
2. **Set environment variables** in your deployment platform
3. **Build and push** Docker image
4. **Deploy** application container only

Example for Railway:
- Add PostgreSQL service in Railway
- Set `DATABASE_URL` environment variable
- Deploy backend container
- Database connection is automatic

## Troubleshooting

### Application can't connect to database
- Check database is running: `docker-compose ps`
- Verify connection string in environment variables
- Check network connectivity: `docker network inspect academy-network`

### Database migrations fail
- Ensure database exists
- Check database credentials
- Verify Flyway has permissions

### Port conflicts
- Change ports in `docker-compose.yml`
- Or stop conflicting services

## Summary

- ✅ **Dockerfile**: Application container only (no database)
- ✅ **docker-compose.yml**: Complete setup (database + app)
- ✅ **Environment variables**: Database connection details
- ✅ **Best practice**: Separate containers for database and app

