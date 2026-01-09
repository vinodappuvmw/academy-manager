# CI/CD Setup Guide

## Overview

This project includes comprehensive CI/CD pipelines for:
- **GitHub Actions**: Continuous Integration and Deployment
- **Railway**: Automated deployment

## GitHub Actions CI/CD

### Setup

1. **Enable GitHub Actions**
   - GitHub Actions are automatically enabled when you push the workflow files
   - Workflows are in `.github/workflows/`

2. **Add Required Secrets**
   Go to: Repository → Settings → Secrets and variables → Actions
   
   Add these secrets:
   - `RAILWAY_TOKEN`: Railway API token (for CD workflow)
   - `DOCKER_USERNAME`: (Optional) Docker Hub username
   - `DOCKER_PASSWORD`: (Optional) Docker Hub password

### CI Workflow (`.github/workflows/ci.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop`

**What it does:**
1. Runs code quality checks (Spotless, Checkstyle, PMD, SpotBugs)
2. Runs all unit tests
3. Runs integration tests with Testcontainers
4. Builds the application
5. Builds Docker image (only on push to main)

**View Results:**
- Go to Actions tab in GitHub
- Click on the workflow run
- View logs for each job

### CD Workflow (`.github/workflows/cd-railway.yml`)

**Triggers:**
- Push to `main` branch
- Only when `backend/**` files change

**What it does:**
1. Builds the application
2. Deploys to Railway automatically

**Prerequisites:**
- Railway project must be connected to GitHub
- `RAILWAY_TOKEN` secret must be set

## Railway Deployment

### Automatic Deployment

Railway can automatically deploy when you push to GitHub:

1. **Connect Repository**
   - In Railway dashboard, create new project
   - Select "Deploy from GitHub repo"
   - Choose your repository
   - Select `backend` as root directory

2. **Configure Environment Variables**
   Set these in Railway:
   ```
   DATABASE_URL=jdbc:postgresql://...
   DATABASE_USERNAME=...
   DATABASE_PASSWORD=...
   PORT=8080
   CORS_ALLOWED_ORIGINS=https://your-frontend.com
   ```

3. **Deploy**
   - Railway will automatically detect the project
   - It will build using the `railway.json` configuration
   - Or use the Dockerfile if configured

### Manual Deployment

1. **Build Locally**
   ```bash
   cd backend
   ./gradlew build
   ```

2. **Deploy via Railway CLI**
   ```bash
   railway login
   railway link
   railway up
   ```

### Railway Configuration

The `railway.json` file configures:
- Build command: `./gradlew build -x test`
- Start command: `java -jar build/libs/backend-0.0.1-SNAPSHOT.jar`
- Restart policy: On failure, max 10 retries

## Docker Deployment

### Build Docker Image

```bash
cd backend
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

### Push to Registry

```bash
# Tag image
docker tag academy-backend:latest your-registry/academy-backend:latest

# Push
docker push your-registry/academy-backend:latest
```

## Testing CI/CD Locally

### Test GitHub Actions

Use [act](https://github.com/nektos/act):

```bash
# Install act
brew install act  # macOS

# Run CI workflow
act push

# Run specific job
act -j test
```

### Test Docker Build

```bash
# Build
docker build -t academy-backend:test .

# Run
docker run -p 8080:8080 academy-backend:test
```

## Troubleshooting

### CI Fails

1. **Check logs** in GitHub Actions tab
2. **Common issues:**
   - Tests failing: Check test output
   - Code quality: Run `./gradlew check` locally
   - Docker build: Test Dockerfile locally

### Railway Deployment Fails

1. **Check Railway logs** in dashboard
2. **Verify environment variables** are set
3. **Check build logs** for errors
4. **Ensure database is accessible**

### Docker Build Fails

1. **Check Dockerfile** syntax
2. **Verify all files** are included
3. **Test locally** before pushing
4. **Check .dockerignore** isn't excluding needed files

## Best Practices

1. **Test Locally First**: Always test changes locally before pushing
2. **Small Commits**: Make small, focused commits for easier debugging
3. **Monitor Deployments**: Watch deployment logs for issues
4. **Environment Variables**: Never commit secrets, use environment variables
5. **Rollback Plan**: Know how to rollback if deployment fails

## Workflow Status Badge

Add to your README:

```markdown
![CI](https://github.com/your-username/academy-manager/workflows/CI/badge.svg)
```

## Next Steps

1. ✅ Set up GitHub Actions secrets
2. ✅ Connect Railway to GitHub
3. ✅ Configure environment variables
4. ✅ Test deployment
5. ✅ Monitor first deployment

---

For more details, see:
- [TESTING.md](./TESTING.md) - Testing guide
- [RAILWAY_DEPLOYMENT.md](./RAILWAY_DEPLOYMENT.md) - Railway setup
- [LOCAL_SETUP.md](./LOCAL_SETUP.md) - Local development

