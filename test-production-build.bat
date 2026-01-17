@echo off
echo ========================================
echo Testing Production Build for Railway
echo ========================================
echo.

echo Step 1: Cleaning previous builds...
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Clean failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Building application (skipping tests)...
call mvn package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Step 3: Checking if JAR was created...
if exist "target\crypto-portfolio-tracker-0.0.1-SNAPSHOT.jar" (
    echo SUCCESS: JAR file created!
    echo Location: target\crypto-portfolio-tracker-0.0.1-SNAPSHOT.jar
    
    echo.
    echo Checking JAR size...
    dir "target\crypto-portfolio-tracker-0.0.1-SNAPSHOT.jar" | findstr "crypto-portfolio-tracker"
) else (
    echo ERROR: JAR file not found!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Build Test Complete!
echo ========================================
echo.
echo Your application is ready for Railway deployment!
echo.
echo Next steps:
echo 1. Generate JWT secret: run generate-jwt-secret.bat
echo 2. Push to GitHub: git push
echo 3. Deploy on Railway: See QUICK_START.md
echo.
pause
