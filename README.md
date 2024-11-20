# Library Management System

This is a simple Java-based Library Management System (LMS) application built using JavaFX for the front-end and PostgreSQL as the database. The system allows users to manage books, patrons, and book transactions (borrow and return). The application includes functionality for adding books and patrons, lending and returning books, and displaying the current status of books and patrons.

## Features

- **Manage Books**: Add books to the library collection, view book details, and check their availability status.
- **Manage Patrons**: Add patrons (users) to the library system and view their details.
- **Book Transactions**: Lend books to patrons and track the borrowing transactions. Also, support returning books.
- **Display Information**: View a list of all books, patrons, and current transactions.

## Technologies Used

- **Java**: Main programming language for backend logic.
- **JavaFX**: Frontend for the graphical user interface (GUI).
- **PostgreSQL**: Database to store books, patrons, and transactions data.
- **JDBC**: Java Database Connectivity to interact with PostgreSQL.
- **Dotenv**: Used for managing environment variables for database configuration.

## Requirements

- **Java 17+**
- **PostgreSQL Server** (configured and running)
- **JavaFX SDK**
- **Maven** (optional, if you use Maven for dependencies)

## Database Setup

Before running the application, ensure that the PostgreSQL database is set up correctly. The application connects to the database using environment variables for the database URL, user, and password.

### 1. Install PostgreSQL

Make sure that PostgreSQL is installed and running on your system. You can download it from the [official PostgreSQL website](https://www.postgresql.org/download/).

### 2. Create Database

You need to create the database and tables for books, patrons, and transactions. You can use the following SQL queries:

```sql
CREATE DATABASE library;

\c library;

CREATE TABLE books (
    id INT PRIMARY KEY,  -- Manually assigned ID
    title VARCHAR(255) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE patrons (
    id INT PRIMARY KEY,  -- Manually assigned ID
    name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    id INT PRIMARY KEY,  -- Manually assigned ID
    book_id INT NOT NULL,
    patron_id INT NOT NULL,
    transaction_type VARCHAR(10) CHECK (transaction_type IN ('borrow', 'return')),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (patron_id) REFERENCES patrons(id)
);
