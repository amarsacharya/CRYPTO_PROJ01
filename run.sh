#!/bin/bash

echo "Crypto Portfolio Tracker - Setup and Run Script"
echo ""

echo "Step 1: Ensure you have Java 21 and Maven installed"
echo "Step 2: Ensure MySQL 8.0 is running with the configured user"
echo ""

echo "Creating database user (run this in MySQL):"
echo "CREATE USER 'crypto_user'@'localhost' IDENTIFIED BY 'crypto_password';"
echo "GRANT ALL PRIVILEGES ON *.* TO 'crypto_user'@'localhost';"
echo "FLUSH PRIVILEGES;"
echo ""

echo "Building and running the application..."
echo ""

mvn clean install
if [ $? -ne 0 ]; then
    echo "Build failed! Please check your Maven installation and try again."
    exit 1
fi

echo ""
echo "Starting the application..."
echo "Access the application at: http://localhost:8080"
echo "Swagger UI at: http://localhost:8080/swagger-ui/index.html"
echo ""

mvn spring-boot:run