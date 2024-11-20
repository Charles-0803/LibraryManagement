package com.project.librarymanagement;

public class Book {
    private int id; // Book ID
    private String title; // Book title
    private boolean isAvailable; // Availability status of the book

    // Constructor to initialize the Book object with its id, title, and availability status
    public Book(int id, String title, boolean isAvailable) {
        this.id = id; // Set the book ID
        this.title = title; // Set the book title
        this.isAvailable = isAvailable; // Set the book availability status
    }

    // Getter method to retrieve the book's ID
    public int getId() {
        return id;
    }

    // Setter method to update the book's ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter method to retrieve the book's title
    public String getTitle() {
        return title;
    }

    // Setter method to update the book's title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method to check if the book is available
    public boolean isAvailable() {
        return isAvailable;
    }

    // Setter method to update the book's availability status
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
