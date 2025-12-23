# VoyageConnect

A comprehensive full-stack web application for travel booking, built with Java Spring Boot, JSP, and PostgreSQL.

## Features

- **User Authentication**: Secure registration and login with Spring Security
- **Flight Booking**: Search and book flights with real-time availability
- **Hotel Reservations**: Browse and reserve accommodations
- **Tour Packages**: Discover and book curated travel experiences
- **Payment Processing**: Integrated Stripe payment system
- **Admin Dashboard**: Manage offers, bookings, and users
- **Responsive Design**: Mobile-friendly interface with Bootstrap 5
- **Email Notifications**: Automated booking confirmations
- **Review System**: Rate and review travel experiences

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: JSP, Bootstrap 5, jQuery, AJAX
- **Database**: PostgreSQL 15 with Flyway migrations
- **Payment**: Stripe API integration
- **Email**: SMTP/SendGrid integration
- **Containerization**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Testing**: JUnit 5, Mockito, Testcontainers

## Project Structure

```
voyageconnect/
├── src/
│   ├── main/
│   │   ├── java/com/voyageconnect/
│   │   │   ├── controller/     # REST controllers and MVC controllers
│   │   │   ├── service/        # Business logic layer
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── model/          # JPA entities
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── security/       # Security configuration
│   │   │   └── config/         # Application configuration
│   │   └── webapp/WEB-INF/views/ # JSP views
│   └── test/                   # Unit and integration tests
├── sql/
│   └── flyway/                 # Database migrations
├── docker/                     # Docker configuration
├── docs/                       # Documentation
├── .github/workflows/          # CI/CD pipelines
├── Dockerfile                  # Application container
├── docker-compose.yml          # Development environment
└── pom.xml                     # Maven configuration
```

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- PostgreSQL 15 or higher
- Docker and Docker Compose (optional)

## Local Development Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/voyageconnect.git
cd voyageconnect
```

### 2. Environment Configuration

Copy the environment template and configure your settings:

```bash
cp .env.example .env
```

Edit `.env` with your configuration:
- Database credentials
- Stripe API keys (test mode)
- JWT secret
- SMTP settings

### 3. Database Setup

Start PostgreSQL using Docker Compose:

```bash
docker-compose up -d db
```

Or install PostgreSQL locally and create the database:

```sql
CREATE DATABASE voyageconnect;
CREATE USER voyageconnect WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE voyageconnect TO voyageconnect;
```

### 4. Run Database Migrations

```bash
mvn flyway:migrate
```

### 5. Start the Application

Using Maven:

```bash
mvn spring-boot:run
```

Using Docker Compose (includes all services):

```bash
docker-compose up
```

The application will be available at `http://localhost:8080`

### 6. Access MailHog (Email Testing)

Web interface: http://localhost:8025

## Default Accounts

- **Admin**: admin@voyageconnect.com / admin123
- **Test User**: user@voyageconnect.com / user123

## API Documentation

Swagger UI is available at: http://localhost:8080/swagger-ui.html

### Authentication Endpoints

- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User authentication

### Search Endpoints

- `GET /api/v1/destinations` - List destinations
- `GET /api/v1/flights` - Search flights
- `GET /api/v1/hotels` - Search hotels

### Booking Endpoints

- `POST /api/v1/bookings` - Create booking
- `GET /api/v1/bookings/{id}` - Get booking details
- `PUT /api/v1/bookings/{id}/cancel` - Cancel booking

### Payment Endpoints

- `POST /api/v1/payments/stripe` - Create payment session
- `POST /api/v1/payments/stripe/webhook` - Stripe webhook

## Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Code Coverage

```bash
mvn jacoco:report
```

View coverage report at: `target/site/jacoco/index.html`

### Code Quality

```bash
# Checkstyle
mvn checkstyle:check

# SpotBugs
mvn spotbugs:check
```

## Docker Build

Build the Docker image:

```bash
mvn clean package -DskipTests
docker build -t voyageconnect:latest .
```

Run with Docker:

```bash
docker run -p 8080:8080 --env-file .env voyageconnect:latest
```

## Deployment

### Production Environment

1. Set production environment variables
2. Build for production:
   ```bash
   mvn clean package -Pprod
   ```
3. Deploy using Docker or cloud provider

### CI/CD Pipeline

The GitHub Actions workflow automatically:
- Runs tests on push/PR
- Performs code quality checks
- Builds and pushes Docker images
- Deploys to staging/production environments

## Security Features

- BCrypt password hashing (12 rounds)
- CSRF protection
- XSS prevention
- SQL injection prevention
- Rate limiting
- CORS configuration
- Content Security Policy (CSP)
- JWT token authentication

## Monitoring

- Spring Boot Actuator endpoints
- Health checks
- Application metrics
- Log aggregation support

## Database Backups

Create a backup:

```bash
pg_dump -h localhost -U voyageconnect voyageconnect > backup.sql
```

Restore from backup:

```bash
psql -h localhost -U voyageconnect voyageconnect < backup.sql
```

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/your-feature`
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please contact: support@voyageconnect.com

## Roadmap

- [ ] Mobile application
- [ ] Multi-language support
- [ ] Advanced search filters
- [ ] Loyalty program
- [ ] Travel insurance integration
- [ ] Real-time notifications
- [ ] Social features

---

Built with ❤️ by the VoyageConnect Team
