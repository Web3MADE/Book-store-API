# Book/ShoppingCart REST API

The Book/ShoppingCart REST API is a Spring Boot-based application that allows users to manage a collection of books and simulate a shopping cart experience, including adding books to the cart and calculating the total price.

## Features

- **Book Management**: Add, update, delete, and retrieve books.
- **Shopping Cart**: Add books to a shopping cart, update quantities, remove books, and view cart contents.
- **Checkout Process**: Calculate the total price of items in the shopping cart.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 21
- Maven 3.6.3 or newer
- Spring Boot 3.2.0 or newer

### Installation

1. Clone the repository:
2. Navigate to the `book-store-API` directory
3. build & run the project with `./mvnw spring-boot:run`

The application should now be running on `http://localhost:9090`.

## Usage

### Adding a Book

POST `/api/books/`

Request body:
```json
{
"title": "The Great Gatsby",
"author": "F. Scott Fitzgerald",
"price": 12.99,
"category": "Classic Literature"
}
```

### Adding a Book to the Shopping Cart

POST `/api/cart/add`

Request body:
```json
{
  "bookId": 1,
  "quantity": 2
}
```

### Viewing Cart Contents
GET /api/cart

### Calculating Total Price
GET /api/cart/total

### Running tests
`./mvnw test`

## Justifications for Key Decisions

### Choosing Spring Boot over Quarkus

While both Spring Boot and Quarkus offer compelling features for building modern Java applications, several factors influenced my decision to use Spring Boot:

- **Maturity and Ecosystem**: Spring Boot has a more mature ecosystem and a larger community, offering extensive documentation, a wide array of third-party libraries, and support. This maturity significantly reduces the risk and time taken to develop the app.
- **Developer Familiarity**: Sprint Boot is the most familiar Java Framework that I have encountered, and felt natural to work with.
- **Operational Readiness**: In a real applcation, Spring Boot's actuator endpoints offer out-of-the-box support for monitoring and managing the application in a production environment, a crucial requirement for the operational needs.

### Overall Design Decisions

- **RESTful API Design**: I adopted REST principles to ensure the API is scalable, stateless, and can be easily consumed by various clients. This design facilitates clear communication between frontend and backend components and among microservices.
- **Entity-Relationship Model**: The application's core functionalities revolve around managing books and shopping carts. I designed the entity-relationship model to efficiently represent books, inventory, and cart items, ensuring data integrity and facilitating complex operations like calculating cart totals.
- **Repository Pattern with Spring Data JPA**: Leveraging Spring Data JPA allowed me to abstract and encapsulate the data access layer, simplifying CRUD operations and reducing boilerplate code. This pattern enhances maintainability and allowed me to focus on business logic.
- **Unit and Integration Testing**: By using JUnit and Mockito for unit testing and Spring Boot Test for integration testing, I have established a robust testing framework that would be applicable in a Continous Integration pipeline like GitHub Actions.

### Future Considerations

- **Spring Security Integration**: Spring Security to secure the REST API. Spring Security is a powerful framework that provides authentication and access-control features. It fits well within the Spring ecosystem and offers a comprehensive security solution for Java applications.

- **JWT for Stateless Authentication**: JSON Web Tokens (JWT) for stateless authentication. This approach will involve generating a token upon successful login and requiring the client to include this token in the HTTP header for subsequent requests. JWTs are appealing because they are self-contained and can carry the necessary user identification and permissions, reducing the need for constant database lookups.

