# GitHub Actions CI/CD

## Workflows

### CI Workflow (`.github/workflows/ci.yml`)

Runs on every push and pull request to `main` or `develop` branches.

**Jobs:**
1. **Test**: Runs code quality checks and unit tests
2. **Integration Test**: Runs integration tests with Testcontainers
3. **Build Docker**: Builds Docker image (only on push to main)

**Steps:**
- Checkout code
- Set up JDK 17
- Cache Gradle dependencies
- Run code quality checks (Spotless, Checkstyle, PMD, SpotBugs)
- Run tests
- Build application
- Build and push Docker image (if on main branch)

### CD Workflow (`.github/workflows/cd-railway.yml`)

Runs on push to `main` branch when `backend/**` files change.

**Steps:**
- Checkout code
- Set up JDK 17
- Build application
- Deploy to Railway using Railway CLI

## Required Secrets

### For CI Workflow
- `DOCKER_USERNAME` (optional): Docker Hub username
- `DOCKER_PASSWORD` (optional): Docker Hub password

### For CD Workflow
- `RAILWAY_TOKEN`: Railway API token

## Setting Up Secrets

1. Go to your GitHub repository
2. Navigate to Settings → Secrets and variables → Actions
3. Add the required secrets

### Getting Railway Token

1. Go to Railway dashboard
2. Navigate to your project
3. Go to Settings → Tokens
4. Create a new token
5. Copy and add to GitHub secrets as `RAILWAY_TOKEN`

## Local Testing

Test workflows locally using [act](https://github.com/nektos/act):

```bash
# Install act
brew install act  # macOS
# or download from https://github.com/nektos/act/releases

# Run CI workflow
act push

# Run specific job
act -j test
```

## Workflow Status

Check workflow status:
- GitHub Actions tab in repository
- Green checkmark = passed
- Red X = failed
- Yellow circle = in progress

