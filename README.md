# SimERP - Simple Enterprise Resource Planning System

SimERP is a modern, full-stack enterprise resource planning system built with Spring Boot and Vue.js. It provides essential business management features including inventory management, sales order processing, and customer management.

## Features

- **Inventory Management**
  - Product catalog with SKU tracking
  - Stock quantity monitoring
  - Price management
  - Product categorization

- **Sales Management**
  - Sales order creation and tracking
  - Multi-item order support
  - Order status management
  - Automatic total calculations
  - Order number generation

- **Customer Management**
  - Customer profiles
  - Contact information
  - Order history

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security with JWT
- Spring Data JPA
- PostgreSQL
- Maven

### Frontend
- Vue 3
- TypeScript
- Element Plus UI Framework
- Axios for HTTP requests
- Vite for build tooling

## Prerequisites

- Java 17 or later
- Node.js 16 or later
- PostgreSQL 12 or later
- Maven 3.6 or later

## Getting Started

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE simerp;
```

2. Update the database configuration in `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simerp
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build and run the application:
```bash
./mvnw clean spring-boot:run
```

The backend server will start on `http://localhost:8080`.

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend application will be available at `http://localhost:5173`.

## API Documentation

### Authentication
- POST `/api/auth/login` - User login
- POST `/api/auth/register` - User registration

### Products
- GET `/api/products` - List all products
- GET `/api/products/{id}` - Get product details
- POST `/api/products` - Create new product
- PUT `/api/products/{id}` - Update product
- DELETE `/api/products/{id}` - Delete product

### Sales Orders
- GET `/api/sales` - List all sales orders
- GET `/api/sales/{id}` - Get sales order details
- POST `/api/sales` - Create new sales order
- PUT `/api/sales/{id}` - Update sales order
- DELETE `/api/sales/{id}` - Delete sales order

### Customers
- GET `/api/customers` - List all customers
- GET `/api/customers/{id}` - Get customer details
- POST `/api/customers` - Create new customer
- PUT `/api/customers/{id}` - Update customer
- DELETE `/api/customers/{id}` - Delete customer

## Project Structure

```
simerp/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/simerp/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   └── SimErpApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── components/
    │   ├── views/
    │   ├── router/
    │   ├── store/
    │   └── App.vue
    ├── package.json
    └── vite.config.ts
```

## Security

The application implements JWT-based authentication with the following security features:
- Password encryption
- Token-based authentication
- Role-based access control
- CORS configuration
- Secure session management

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the development team. 