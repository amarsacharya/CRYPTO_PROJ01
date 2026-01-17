@echo off
echo Generating JWT Secret for Railway...
echo.
powershell -Command "[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))"
echo.
echo Copy the above string and use it as your JWT_SECRET in Railway
echo Minimum 32 characters required
pause
