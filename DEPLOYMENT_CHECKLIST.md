# ✅ Railway Deployment Checklist

## Files Created for Railway Deployment

- ✅ `railway.toml` - Railway configuration
- ✅ `nixpacks.toml` - Build configuration
- ✅ `Procfile` - Process definition
- ✅ `system.properties` - Java version specification
- ✅ `src/main/resources/application-prod.yml` - Production config
- ✅ `.gitignore` - Git ignore rules
- ✅ `.env.example` - Environment variables template
- ✅ `RAILWAY_DEPLOYMENT.md` - Detailed deployment guide
- ✅ `QUICK_START.md` - Quick start guide

## Pre-Deployment Checklist

### 1. Code Preparation
- [x] Production configuration created
- [x] Environment variables configured
- [x] Security config updated (removed deprecations)
- [x] Build tested successfully
- [x] Transactional annotation added to scheduled tasks

### 2. Before Pushing to GitHub
```bash
# Test build locally
mvn clean package -DskipTests

# Initialize git (if not already done)
git init
git add .
git commit -m "Ready for Railway deployment"
```

### 3. GitHub Setup
```bash
# Create a new repository on GitHub, then:
git remote add origin https://github.com/yourusername/your-repo.git
git branch -M main
git push -u origin main
```

### 4. Railway Setup Steps

1. **Sign up/Login**: https://railway.app
2. **New Project**: Click "New Project"
3. **Deploy from GitHub**: Select your repository
4. **Add MySQL Database**:
   - Click "New" → "Database" → "Add MySQL"
   - Railway auto-sets `DATABASE_URL`
5. **Set Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   JWT_SECRET=<generate-secure-32-char-string>
   ```
6. **Deploy**: Railway auto-deploys on push

### 5. Generate JWT Secret

**Windows PowerShell:**
```powershell
[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))
```

**Linux/Mac:**
```bash
openssl rand -base64 32
```

**Or use any random 32+ character string**

### 6. Post-Deployment Verification

After deployment, test these endpoints:

- [ ] Home page: `https://your-app.up.railway.app/`
- [ ] Swagger UI: `https://your-app.up.railway.app/swagger-ui/index.html`
- [ ] API Docs: `https://your-app.up.railway.app/api-docs`
- [ ] Health check: Try registering a user via Swagger

### 7. Monitor Deployment

In Railway dashboard:
- [ ] Check build logs (should complete in 2-3 minutes)
- [ ] Check runtime logs (should show Spring Boot startup)
- [ ] Verify database connection successful
- [ ] Check for any errors

## Common Issues & Solutions

### Build Fails
- **Check**: Java version in logs (should be 21)
- **Fix**: Verify `system.properties` and `nixpacks.toml`

### Database Connection Error
- **Check**: DATABASE_URL is set
- **Fix**: Ensure MySQL service is running in Railway

### Port Binding Error
- **Check**: PORT environment variable
- **Fix**: Railway usually sets this automatically

### JWT Secret Error
- **Check**: JWT_SECRET is set and at least 32 characters
- **Fix**: Generate new secret and update in Railway

### Application Crashes on Startup
- **Check**: Runtime logs in Railway
- **Common causes**:
  - Missing environment variables
  - Database connection issues
  - Invalid JWT secret

## Estimated Deployment Time

- **Build time**: 2-3 minutes
- **Startup time**: 30-60 seconds
- **Total**: ~5 minutes from push to live

## Cost Estimate

- **Free tier**: $5/month credit
- **App + MySQL**: ~$5-10/month
- **Bandwidth**: Usually included

## Next Steps After Deployment

1. **Custom Domain** (Optional):
   - Go to Railway project settings
   - Add your custom domain
   - Update DNS records

2. **Environment Variables**:
   - Add any additional API keys
   - Configure email settings (if needed)

3. **Monitoring**:
   - Set up Railway notifications
   - Monitor logs regularly

4. **Continuous Deployment**:
   - Every push to `main` auto-deploys
   - Use branches for testing

## Support

- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- GitHub Issues: Create issues in your repo

---

**Ready to deploy?** Follow `QUICK_START.md` for the fastest path to production!
