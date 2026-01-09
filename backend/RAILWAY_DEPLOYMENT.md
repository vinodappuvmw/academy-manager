# Railway Deployment Guide

## Prerequisites
- Railway account
- GitHub repository connected
- PostgreSQL database (Railway provides this)

## Step-by-Step Deployment

### 1. Create Railway Project
1. Go to [Railway](https://railway.app)
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository
5. Select the `backend` folder as the root directory

### 2. Add PostgreSQL Database
1. In your Railway project, click "New"
2. Select "Database" → "Add PostgreSQL"
3. Railway will create a PostgreSQL instance
4. Copy the connection details (you'll need these for environment variables)

### 3. Configure Environment Variables
In Railway project settings, add these environment variables:

#### Required Variables
```
DATABASE_URL=jdbc:postgresql://[host]:[port]/[database]
DATABASE_USERNAME=[username]
DATABASE_PASSWORD=[password]
PORT=8080
```

#### Optional but Recommended
```
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
LOG_LEVEL=info
REDIS_HOST=[redis-host-if-using]
REDIS_PORT=6379
S3_BUCKET=[your-s3-bucket]
S3_REGION=[your-s3-region]
```

### 4. Build Configuration
Railway will automatically detect Gradle and build the project. Ensure:
- Java 17 is available (Railway auto-detects)
- Build command: `./gradlew build`
- Start command: `java -jar build/libs/backend-0.0.1-SNAPSHOT.jar`

### 5. Deploy
1. Railway will automatically deploy on push to main branch
2. Monitor the deployment logs
3. Check health endpoint: `https://your-app.railway.app/actuator/health`

### 6. Verify Deployment
1. Check application logs in Railway dashboard
2. Test API endpoints:
   - `GET /actuator/health` - Should return 200
   - `GET /swagger-ui.html` - API documentation
   - `GET /api/v1/academies` - Test API endpoint

## Troubleshooting

### Database Connection Issues
- Verify `DATABASE_URL` format: `jdbc:postgresql://host:port/database`
- Check database credentials
- Ensure database is accessible from Railway

### Build Failures
- Check Java version (should be 17)
- Verify all dependencies are available
- Check build logs for specific errors

### Runtime Errors
- Check application logs in Railway dashboard
- Verify all environment variables are set
- Check database migrations completed successfully

### CORS Issues
- Update `CORS_ALLOWED_ORIGINS` with your frontend domain
- Ensure frontend is using correct API URL

## Post-Deployment Checklist
- [ ] Health endpoint responding
- [ ] Database migrations completed
- [ ] API endpoints accessible
- [ ] CORS configured correctly
- [ ] Logs are being captured
- [ ] Environment variables set correctly

## Monitoring
- Use Railway's built-in metrics
- Check `/actuator/metrics` for application metrics
- Monitor database connections and performance
- Set up alerts for errors

