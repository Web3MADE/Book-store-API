# Book/ShoppingCart REST API

The Book/ShoppingCart REST API is a Spring Boot-based application that allows users to manage a collection of books and simulate a shopping cart experience, including adding books to the cart and calculating the total price.

## Features

- **Book Management**: Add, update, delete, and retrieve books.
- **Shopping Cart**: Add books to a shopping cart, update quantities, remove books, and view cart contents.
- **Checkout Process**: Calculate the total price of items in the shopping cart.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or newer
- Maven 3.6.3 or newer
- Spring Boot 2.3.0.RELEASE or newer

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

### Adding a Book to the Shopping Cart

POST `/api/cart/add`

Request body:
```json
{
  "bookId": 1,
  "quantity": 2
}
```

### Viewing Cart Contents
GET /api/cart

### Calculating Total Price
GET /api/cart/total
