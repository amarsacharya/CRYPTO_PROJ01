# Crypto Portfolio Tracker

A full-stack Java Spring Boot 3.2 application for tracking cryptocurrency portfolios with real-time price updates and profit/loss calculations.

## Features

- **JWT Authentication** with BCrypt password encryption
- **User Management** - Registration and login
- **Portfolio Management** - Create, view, update, and delete portfolios
- **Holdings Management** - Add, view, update, and delete cryptocurrency holdings
- **Real-time Price Updates** - Cryptocurrency prices update every 5 minutes
- **Profit/Loss Tracking** - Real-time calculation of gains and losses
- **REST API** with comprehensive endpoints
- **Swagger UI** for API documentation
- **Responsive Frontend** - HTML/CSS/JavaScript interface

## Technology Stack

- **Backend**: Java 21, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0
- **Authentication**: JWT with BCrypt
- **Documentation**: Swagger/OpenAPI 3
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Build Tool**: Maven

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0
- Git

## Database Setup

1. Install MySQL 8.0 and start the service
2. Create a database user:
```sql
CREATE USER 'crypto_user'@'localhost' IDENTIFIED BY 'crypto_password';
GRANT ALL PRIVILEGES ON *.* TO 'crypto_user'@'localhost';
FLUSH PRIVILEGES;
```

The application will automatically create the `crypto_portfolio` database on first run.

## Installation & Running

1. **Clone the repository**
```bash
git clone <repository-url>
cd crypto-portfolio-tracker
```

2. **Build the application**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Access Points

- **Frontend**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **API Documentation**: http://localhost:8080/api-docs

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/signin` - Login user

### Portfolios
- `GET /api/portfolios` - Get user portfolios
- `POST /api/portfolios` - Create portfolio
- `GET /api/portfolios/{id}` - Get portfolio by ID
- `PUT /api/portfolios/{id}` - Update portfolio
- `DELETE /api/portfolios/{id}` - Delete portfolio

### Holdings
- `GET /api/holdings/portfolio/{portfolioId}` - Get portfolio holdings
- `POST /api/holdings` - Create holding
- `PUT /api/holdings/{id}` - Update holding
- `DELETE /api/holdings/{id}` - Delete holding

### Cryptocurrencies
- `GET /api/cryptocurrencies` - Get all cryptocurrencies
- `GET /api/cryptocurrencies/{id}` - Get cryptocurrency by ID
- `POST /api/cryptocurrencies` - Create cryptocurrency

## Default Data

The application initializes with sample cryptocurrencies:
- Bitcoin (BTC) - $45,000.00
- Ethereum (ETH) - $3,000.00
- Cardano (ADA) - $0.50
- Polkadot (DOT) - $25.00
- Chainlink (LINK) - $15.00

## Features in Detail

### Real-time Price Updates
- Prices update automatically every 5 minutes
- Simulated price changes between -10% to +10%
- Holdings and portfolio values recalculated automatically

### Security
- JWT token-based authentication
- BCrypt password hashing
- CORS configuration for cross-origin requests
- Secure API endpoints with role-based access

### Frontend Features
- Responsive design for mobile and desktop
- Real-time data updates every 30 seconds
- Modal dialogs for creating portfolios and holdings
- Color-coded profit/loss indicators
- User-friendly error handling

## Configuration

Key configuration in `application.yml`:
- Database connection settings
- JWT secret and expiration
- Server port configuration
- Swagger UI path configuration

## Development

### Adding New Cryptocurrencies
Use the API endpoint or add them directly via the database:
```sql
INSERT INTO cryptocurrencies (symbol, name, current_price, last_updated) 
VALUES ('NEW', 'New Crypto', 100.00, NOW());
```

### Customizing Price Updates
Modify the `@Scheduled` annotation in `CryptocurrencyService.java` to change update frequency.

## Troubleshooting

1. **Database Connection Issues**
   - Verify MySQL is running
   - Check username/password in application.yml
   - Ensure database user has proper permissions

2. **JWT Token Issues**
   - Check JWT secret length (minimum 32 characters)
   - Verify token expiration settings

3. **CORS Issues**
   - Check CORS configuration in SecurityConfig.java
   - Verify allowed origins match your frontend URL

## License

This project is licensed under the MIT License.