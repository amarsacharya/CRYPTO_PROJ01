📌 Crypto Portfolio Tracker

A full-stack Java Spring Boot 3.2 application for tracking cryptocurrency portfolios with real-time price updates, user authentication, and profit/loss calculations.

✨ Features

🔐 JWT Authentication with BCrypt encryption

👤 User Management — Register & Login

📊 Portfolio Management (CRUD)

💰 Holdings Management (CRUD)

⏱ Real-time Price Updates every 5 minutes

📈 Profit/Loss Calculation automatically

🌐 REST API + Swagger UI Documentation

💻 Responsive Frontend (HTML + CSS + JavaScript)

🛠 Tech Stack
Layer	Technology
Backend	Java 21, Spring Boot 3.2, Spring Security, Spring Data JPA
Database	MySQL 8.0
Auth	JWT + BCrypt
API Docs	Swagger / OpenAPI 3
Frontend	HTML5, CSS3, JavaScript (ES6+)
Build Tool	Maven
📍 Prerequisites

Java 21+

Maven 3.6+

MySQL 8.0

Git

🗄 Database Setup
CREATE USER 'crypto_user'@'localhost' IDENTIFIED BY 'crypto_password';
GRANT ALL PRIVILEGES ON *.* TO 'crypto_user'@'localhost';
FLUSH PRIVILEGES;


The application will automatically create the crypto_portfolio database on first run.

🚀 Installation & Running
1️⃣ Clone the repository
git clone https://github.com/amarsacharya/CRYPTO_PROJ01.git
cd CRYPTO_PROJ01

2️⃣ Build the application
mvn clean install

3️⃣ Run the application
mvn spring-boot:run

📍 Access URLs
Function	URL
Frontend	http://localhost:8080

Swagger UI	http://localhost:8080/swagger-ui/index.html

API Docs (JSON)	http://localhost:8080/api-docs
📡 API Endpoints
🔐 Authentication
Method	Endpoint	Description
POST	/api/auth/signup	Register user
POST	/api/auth/signin	Login user
📊 Portfolios
Method	Endpoint	Description
GET	/api/portfolios	Get user portfolios
POST	/api/portfolios	Create portfolio
GET	/api/portfolios/{id}	Get portfolio
PUT	/api/portfolios/{id}	Update
DELETE	/api/portfolios/{id}	Delete
💰 Holdings
Method	Endpoint
GET	/api/holdings/portfolio/{portfolioId}
POST	/api/holdings
PUT	/api/holdings/{id}
DELETE	/api/holdings/{id}
💱 Cryptocurrencies
Method	Endpoint	Description
GET	/api/cryptocurrencies	Get all
GET	/api/cryptocurrencies/{id}	Get by ID
POST	/api/cryptocurrencies	Create crypto
📌 Default Seeded Crypto Data
Name	Symbol	Price
Bitcoin	BTC	$45,000.00
Ethereum	ETH	$3,000.00
Cardano	ADA	$0.50
Polkadot	DOT	$25.00
Chainlink	LINK	$15.00
⚙ Feature Details
⏱ Real-time Price Updates

Updates every 5 minutes

Simulated range: -10% to +10%

Portfolio values recalculated automatically

🔒 Security

JWT authentication

BCrypt password hashing

CORS enabled

Secured endpoints

💻 Frontend

Responsive UI

Real-time updates every 30 seconds

Modal dialogs for CRUD actions

Color-coded profit/loss indicators

🛠 Configuration

All major configs reside in application.yml, including:

DB connection

JWT secret + expiration

Swagger paths

Server port

🔧 Development Tips
Adding new cryptocurrencies
INSERT INTO cryptocurrencies (symbol, name, current_price, last_updated) 
VALUES ('NEW', 'New Crypto', 100.00, NOW());

Changing price update frequency

Modify @Scheduled in CryptocurrencyService.java.

🐞 Troubleshooting
🔹 Database issues

Ensure MySQL is running

Verify credentials in application.yml

Check privileges

🔹 JWT issues

Secret must be >= 32 characters

Check expiration settings

🔹 CORS issues

Update allowed origins in SecurityConfig.java

📄 License

This project is licensed under the MIT License.
