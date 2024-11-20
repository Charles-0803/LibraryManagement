package com.project.librarymanagement;

public class Patron {
    private int id; // Patron ID
    private String name; // Patron's name

    // Constructor to initialize the Patron object with its id and name
    public Patron(int id, String name) {
        this.id = id; // Set the patron ID
        this.name = name; // Set the patron's name
    }

    // Getter method to retrieve the patron's ID
    public int getId() {
        return id;
    }

    // Setter method to update the patron's ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter method to retrieve the patron's name
    public String getName() {
        return name;
    }

    // Setter method to update the patron's name
    public void setName(String name) {
        this.name = name;
    }
}
