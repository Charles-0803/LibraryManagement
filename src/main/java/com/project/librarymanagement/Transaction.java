package com.project.librarymanagement;

import java.util.Date;

public class Transaction {
    private int id; // Transaction ID
    private int bookId; // ID of the book involved in the transaction
    private int patronId; // ID of the patron involved in the transaction
    private String transactionType; // Type of transaction, either "borrow" or "return"
    private Date date; // Date of the transaction

    // Constructor to initialize the Transaction object with its id, bookId, patronId, transactionType, and date
    public Transaction(int id, int bookId, int patronId, String transactionType, Date date) {
        this.id = id; // Set the transaction ID
        this.bookId = bookId; // Set the book ID involved in the transaction
        this.patronId = patronId; // Set the patron ID involved in the transaction
        this.transactionType = transactionType; // Set the transaction type (borrow/return)
        this.date = date; // Set the transaction date
    }

    // Getter method to retrieve the transaction ID
    public int getId() {
        return id;
    }

    // Setter method to update the transaction ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter method to retrieve the book ID
    public int getBookId() {
        return bookId;
    }

    // Setter method to update the book ID
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    // Getter method to retrieve the patron ID
    public int getPatronId() {
        return patronId;
    }

    // Setter method to update the patron ID
    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    // Getter method to retrieve the transaction type
    public String getTransactionType() {
        return transactionType;
    }

    // Setter method to update the transaction type
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Getter method to retrieve the transaction date
    public Date getDate() {
        return date;
    }

    // Setter method to update the transaction date
    public void setDate(Date date) {
        this.date = date;
    }
}
