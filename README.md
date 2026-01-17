A full-stack Java Spring Boot 3.2 application for tracking cryptocurrency portfolios with real-time price updates, user authentication, and profit/loss calculations.

✨ Features

🔐 JWT Authentication with BCrypt encryption
👤 User Management — Register & Login
📊 Portfolio Management (CRUD)
💰 Holdings Management (CRUD)
⏱ Real-time Price Updates every 5 minutes
📈 Automatic Profit/Loss Calculation
🌐 REST API + Swagger UI Documentation
💻 Responsive Frontend (HTML + CSS + JavaScript)

🛠 Tech Stack

Layer | Technology
Backend | Java 21, Spring Boot 3.2, Spring Security, Spring Data JPA
Database | MySQL 8.0
Auth | JWT + BCrypt
API Docs | Swagger / OpenAPI 3
Frontend | HTML5, CSS3, JavaScript (ES6+)
Build Tool | Maven

📍 Prerequisites

Java 21+
Maven 3.6+
MySQL 8.0
Git

🗄 Database Setup

CREATE USER 'crypto_user'@'localhost' IDENTIFIED BY 'crypto_password';
GRANT ALL PRIVILEGES ON . TO 'crypto_user'@'localhost';
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

Frontend: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

API Docs (JSON): http://localhost:8080/api-docs

📡 API Endpoints

🔐 Authentication
POST /api/auth/signup — Register user
POST /api/auth/signin — Login user

📊 Portfolios
GET /api/portfolios — Get user portfolios
POST /api/portfolios — Create portfolio
GET /api/portfolios/{id} — Get portfolio by ID
PUT /api/portfolios/{id} — Update portfolio
DELETE /api/portfolios/{id} — Delete portfolio

💰 Holdings
GET /api/holdings/portfolio/{portfolioId}
POST /api/holdings
PUT /api/holdings/{id}
DELETE /api/holdings/{id}

💱 Cryptocurrencies
GET /api/cryptocurrencies — Get all cryptocurrencies
GET /api/cryptocurrencies/{id} — Get cryptocurrency by ID
POST /api/cryptocurrencies — Create cryptocurrency

📌 Default Seeded Crypto Data

Bitcoin (BTC) — $45,000.00
Ethereum (ETH) — $3,000.00
Cardano (ADA) — $0.50
Polkadot (DOT) — $25.00
Chainlink (LINK) — $15.00

⚙ Feature Details

⏱ Real-time Price Updates
Prices update every 5 minutes
Random price variation: -10% to +10%
Portfolio values recalculated automatically

🔒 Security
JWT authentication
BCrypt password hashing
CORS enabled
Role-based API access

💻 Frontend
Responsive UI
Auto-refresh every 30 seconds
Modal dialogs for CRUD operations
Profit/Loss color indicators

🛠 Configuration

Located in application.yml, including:
Database connection
JWT secret & expiration
Swagger paths
Server port

🔧 Development Tips

Adding a new cryptocurrency
INSERT INTO cryptocurrencies (symbol, name, current_price, last_updated)
VALUES ('NEW', 'New Crypto', 100.00, NOW());

Changing price update frequency
Modify @Scheduled in CryptocurrencyService.java

🐞 Troubleshooting

Database Issues
Ensure MySQL is running
Check application.yml credentials
Verify user privileges

JWT Issues
Secret must be ≥ 32 characters
Verify expiration settings

CORS Issues
Update allowed origins in SecurityConfig.java

📄 License

This project is licensed under the MIT License.