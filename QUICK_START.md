# Quick Start Guide - Railway Deployment

## 🚀 Deploy to Railway in 5 Minutes

### 1. Generate JWT Secret
Run this command to generate a secure JWT secret:
```bash
# Windows PowerShell
[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))

# Or use any random 32+ character string
```

### 2. Push to GitHub
```bash
git init
git add .
git commit -m "Ready for Railway deployment"
git remote add origin <your-github-repo-url>
git push -u origin main
```

### 3. Deploy on Railway

1. Go to https://railway.app and sign in
2. Click **"New Project"**
3. Select **"Deploy from GitHub repo"**
4. Choose your repository
5. Click **"Add variables"** and set:
   - `SPRING_PROFILES_ACTIVE` = `prod`
   - `JWT_SECRET` = `<your-generated-secret>`
6. Click **"New"** → **"Database"** → **"Add MySQL"**
7. Wait for deployment (3-5 minutes)

### 4. Access Your App

Railway will give you a URL like: `https://your-app.up.railway.app`

**Endpoints:**
- Home: `https://your-app.up.railway.app`
- Swagger: `https://your-app.up.railway.app/swagger-ui/index.html`
- API: `https://your-app.up.railway.app/api-docs`

## 📝 Environment Variables Needed

| Variable | Value | Notes |
|----------|-------|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Required |
| `JWT_SECRET` | `<your-secret>` | Min 32 characters |
| `DATABASE_URL` | Auto-set by Railway | When you add MySQL |
| `PORT` | Auto-set by Railway | Usually 8080 |

## 🔄 Update Your App

Just push to GitHub:
```bash
git add .
git commit -m "Update"
git push
```

Railway auto-deploys on every push!

## 💰 Cost

- **Free tier**: $5 credit/month
- **Typical usage**: $5-10/month
- **Includes**: App hosting + MySQL database

## 🐛 Troubleshooting

**Build fails?**
- Check Railway logs in the Deployments tab
- Verify Java 21 is available

**Can't connect to database?**
- Ensure MySQL service is running
- Check DATABASE_URL is set

**App crashes?**
- View logs in Railway dashboard
- Check JWT_SECRET is set correctly

For detailed instructions, see `RAILWAY_DEPLOYMENT.md`
