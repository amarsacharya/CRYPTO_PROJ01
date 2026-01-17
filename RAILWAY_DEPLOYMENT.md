# Railway Deployment Guide

## Prerequisites
- Railway account (sign up at https://railway.app)
- GitHub account (to connect your repository)

## Step 1: Push Your Code to GitHub
```bash
git init
git add .
git commit -m "Initial commit for Railway deployment"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
```

## Step 2: Create a New Project on Railway

1. Go to https://railway.app
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository
5. Railway will automatically detect it's a Java application

## Step 3: Add MySQL Database

1. In your Railway project, click "New"
2. Select "Database" → "Add MySQL"
3. Railway will automatically create a MySQL database and set the `DATABASE_URL` environment variable

## Step 4: Set Environment Variables

In your Railway project settings, add these environment variables:

### Required Variables:
- `SPRING_PROFILES_ACTIVE` = `prod`
- `JWT_SECRET` = `<generate-a-secure-random-string-at-least-32-characters>`
- `PORT` = `8080` (Railway usually sets this automatically)

### Database Variables (automatically set by Railway MySQL):
- `DATABASE_URL` - Automatically set when you add MySQL database

**Important:** Generate a secure JWT secret. You can use this command:
```bash
openssl rand -base64 32
```

## Step 5: Deploy

Railway will automatically deploy your application when you push to GitHub.

### Build Command (automatic):
```bash
mvn clean package -DskipTests
```

### Start Command (automatic):
```bash
java -Dserver.port=$PORT -jar target/crypto-portfolio-tracker-0.0.1-SNAPSHOT.jar
```

## Step 6: Access Your Application

Once deployed, Railway will provide you with a public URL like:
- `https://your-app-name.up.railway.app`

### Available Endpoints:
- Main App: `https://your-app-name.up.railway.app`
- Swagger UI: `https://your-app-name.up.railway.app/swagger-ui/index.html`
- API Docs: `https://your-app-name.up.railway.app/api-docs`

## Troubleshooting

### Check Logs
- Go to your Railway project
- Click on your service
- View the "Deployments" tab to see build and runtime logs

### Common Issues

1. **Build fails**: Check that Java 21 is specified in nixpacks.toml
2. **Database connection fails**: Verify DATABASE_URL is set correctly
3. **Port binding issues**: Ensure PORT environment variable is being used

### Database Connection Format
Railway's MySQL DATABASE_URL format:
```
mysql://user:password@host:port/database
```

Spring Boot expects:
```
jdbc:mysql://host:port/database?createDatabaseIfNotExist=true&useSSL=true&allowPublicKeyRetrieval=true
```

If Railway doesn't automatically convert it, you may need to manually set:
```
DATABASE_URL=jdbc:mysql://<host>:<port>/<database>?createDatabaseIfNotExist=true&useSSL=true&allowPublicKeyRetrieval=true
```

## Monitoring

Railway provides:
- Real-time logs
- Metrics (CPU, Memory, Network)
- Automatic restarts on failure
- Zero-downtime deployments

## Cost

Railway offers:
- $5 free credit per month
- Pay-as-you-go pricing after free tier
- Estimated cost: ~$5-10/month for small apps

## Updating Your Application

Simply push changes to your GitHub repository:
```bash
git add .
git commit -m "Update application"
git push
```

Railway will automatically rebuild and redeploy.
