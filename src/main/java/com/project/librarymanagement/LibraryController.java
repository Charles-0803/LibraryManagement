package com.project.librarymanagement;

import java.text.SimpleDateFormat;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.LinkedList;

public class LibraryController {
    private LinkedList<Book> books = new LinkedList<>(); // List to store books
    private LinkedList<Patron> patrons = new LinkedList<>(); // List to store patrons

    @FXML private TextField bookIdInput; // TextField for entering book ID
    @FXML private TextField bookTitleInput; // TextField for entering book title
    @FXML private TextField patronIdInput; // TextField for entering patron ID
    @FXML private TextField patronNameInput; // TextField for entering patron name
    @FXML private TextField lendBookIdInput; // TextField for lending book ID
    @FXML private TextField lendPatronIdInput; // TextField for lending patron ID
    @FXML private TextField returnBookIdInput; // TextField for returning book ID
    @FXML private TextField returnPatronIdInput; // TextField for returning patron ID

    @FXML
    private TextFlow displayArea; // TextFlow to display messages

    public LibraryController() {
        loadBooksFromDatabase(); // Load books from database
        loadPatronsFromDatabase(); // Load patrons from database
    }

    @FXML
    public void initialize() {
        displayArea.getChildren().clear(); // Ensure displayArea is cleared on initialization
    }

    @FXML
    private void displayAllBooks() {
        displayArea.getChildren().clear(); // Clear the existing content

        // Create bold header for book list
        Text header = new Text(String.format("%-10s %-40s %18s\n", "ID", "Title", "Status"));
        header.setStyle("-fx-font-weight: bold;"); // Apply bold style to header
        displayArea.getChildren().add(header);

        // Display each book's info (ID, Title, Availability)
        for (Book book : books) {
            Text bookInfo = new Text(String.format("%-10d %-40s %18s\n",
                    book.getId(),
                    book.getTitle(),
                    book.isAvailable() ? "Available" : "Not Available"));
            displayArea.getChildren().add(bookInfo);
        }
    }

    @FXML
    private void displayAllPatrons() {
        displayArea.getChildren().clear(); // Clear the existing content

        // Create bold header for patron list
        Text header = new Text(String.format("%-10s %-40s\n", "ID", "Name"));
        header.setStyle("-fx-font-weight: bold;"); // Apply bold style to header
        displayArea.getChildren().add(header);

        // Display each patron's info (ID, Name)
        for (Patron patron : patrons) {
            Text patronInfo = new Text(String.format("%-10d %-40s\n",
                    patron.getId(),
                    patron.getName()));
            displayArea.getChildren().add(patronInfo);
        }
    }

    @FXML
    private void displayCurrentTransaction() {
        displayArea.getChildren().clear(); // Clear the existing content

        // Create bold header for current transactions
        Text header = new Text(String.format("%-10s %-20s %18s %25s %25s %20s\n", "ID", "Title", "Status", "Patron ID", "Borrowed By", "Transaction Date"));
        header.setStyle("-fx-font-weight: bold;"); // Apply bold style to header
        displayArea.getChildren().add(header);

        // SQL query to fetch currently borrowed books
        String sqlQuery = "SELECT b.id, b.title, p.id AS patron_id, p.name AS patron_name, t.transaction_date " +
                "FROM books b " +
                "JOIN transactions t ON b.id = t.book_id " +
                "JOIN patrons p ON t.patron_id = p.id " +
                "WHERE b.is_available = false " +
                "AND t.transaction_type = 'borrow' " +
                "AND t.transaction_date = (SELECT MAX(t2.transaction_date) " +
                "                            FROM transactions t2 " +
                "                            WHERE t2.book_id = b.id AND t2.transaction_type = 'borrow') " +
                "ORDER BY b.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format for the transaction date
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int patronId = rs.getInt("patron_id");
                String patronName = rs.getString("patron_name");
                java.sql.Timestamp transactionDate = rs.getTimestamp("transaction_date");

                // Format the transaction date if available
                String formattedDate = (transactionDate != null) ? dateFormat.format(transactionDate) : "N/A";

                // Displaying the borrowed books' information
                Text bookInfo = new Text(String.format("%-10d %-20s %18s %25d %25s %22s\n",
                        id, title, "Not Available", patronId, patronName, formattedDate));
                displayArea.getChildren().add(bookInfo);
            }

        } catch (SQLException e) {
            // Handle SQL exceptions by displaying an error message
            Text errorText = new Text("Error fetching books from database.\n");
            displayArea.getChildren().add(errorText);
            e.printStackTrace();
        }
    }

    // Load books data from the database
    private void loadBooksFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            books.clear(); // Clear existing books list
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions during book loading
        }
    }

    // Load patrons data from the database
    private void loadPatronsFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patrons")) {

            patrons.clear(); // Clear existing patrons list
            while (rs.next()) {
                patrons.add(new Patron(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions during patron loading
        }
    }

    // Add a new book to the database and display success message
    @FXML
    private void addBookHandler() {
        displayArea.getChildren().clear(); // Clear the existing content
        int id = Integer.parseInt(bookIdInput.getText()); // Get book ID
        String title = bookTitleInput.getText(); // Get book title
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books (id, title) VALUES (?, ?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.executeUpdate(); // Insert the book into the database
            books.add(new Book(id, title, true)); // Add the book to the list
            Text successText = new Text("Book added successfully!\n");
            displayArea.getChildren().add(successText);
        } catch (SQLException e) {
            Text errorText = new Text("Failed to add book.\n");
            displayArea.getChildren().add(errorText); // Display error message
            e.printStackTrace();
        }
    }

    // Add a new patron to the database and display success message
    @FXML
    private void addPatronHandler() {
        displayArea.getChildren().clear(); // Clear the existing content
        int id = Integer.parseInt(patronIdInput.getText()); // Get patron ID
        String name = patronNameInput.getText(); // Get patron name
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO patrons (id, name) VALUES (?, ?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate(); // Insert the patron into the database
            patrons.add(new Patron(id, name)); // Add the patron to the list
            Text successText = new Text("Patron added successfully!\n");
            displayArea.getChildren().add(successText);
        } catch (SQLException e) {
            Text errorText = new Text("Failed to add patron.\n");
            displayArea.getChildren().add(errorText); // Display error message
            e.printStackTrace();
        }
    }

    // Handle lending a book to a patron
    @FXML
    private void lendBookHandler() {
        displayArea.getChildren().clear(); // Clear the existing content
        int bookId = Integer.parseInt(lendBookIdInput.getText()); // Get book ID
        int patronId = Integer.parseInt(lendPatronIdInput.getText()); // Get patron ID

        Book book = findBookById(bookId); // Find book by ID
        if (book != null && book.isAvailable()) { // Check if the book is available
            Connection conn = null;
            try {
                conn = DatabaseConnection.getConnection();
                conn.setAutoCommit(false); // Start transaction

                // Insert transaction record for lending
                try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transactions (book_id, patron_id, transaction_type, transaction_date) VALUES (?, ?, ?, ?)")) {
                    pstmt.setInt(1, bookId);
                    pstmt.setInt(2, patronId);
                    pstmt.setString(3, "borrow");
                    pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                }

                // Update the book's availability in the database
                try (PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET is_available = false WHERE id = ?")) {
                    pstmt.setInt(1, bookId);
                    pstmt.executeUpdate();
                }

                conn.commit(); // Commit the transaction

                // Update the book's status in the list and display success message
                book.setAvailable(false);
                Text successText = new Text("Book lent successfully!\n");
                displayArea.getChildren().add(successText);
            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback(); // Rollback if there's an error
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                Text errorText = new Text("Failed to lend the book.\n");
                displayArea.getChildren().add(errorText);
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true); // Reset auto commit
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Text errorText = new Text("Book is not available for lending.\n");
            displayArea.getChildren().add(errorText);
        }
    }

    // Handle returning a book
    @FXML
    private void returnBookHandler() {
        displayArea.getChildren().clear(); // Clear the existing content
        int bookId = Integer.parseInt(returnBookIdInput.getText()); // Get book ID
        int patronId = Integer.parseInt(returnPatronIdInput.getText()); // Get patron ID

        Book book = findBookById(bookId); // Find book by ID
        if (book != null && !book.isAvailable()) { // Check if the book is already lent out
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO transactions (book_id, patron_id, transaction_type, transaction_date) VALUES (?, ?, ?, ?)");
                 PreparedStatement pstmt2 = conn.prepareStatement("UPDATE books SET is_available = true WHERE id = ?")) {

                // Insert transaction record for returning the book
                pstmt1.setInt(1, bookId);
                pstmt1.setInt(2, patronId);
                pstmt1.setString(3, "return");
                pstmt1.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                pstmt1.executeUpdate();

                // Update book availability
                pstmt2.setInt(1, bookId);
                pstmt2.executeUpdate();

                // Update the book's status and display success message
                book.setAvailable(true);
                Text successText = new Text("Book returned successfully!\n");
                displayArea.getChildren().add(successText);
            } catch (SQLException e) {
                Text errorText = new Text("Failed to return the book.\n");
                displayArea.getChildren().add(errorText);
                e.printStackTrace();
            }
        } else {
            Text errorText = new Text("Book was not borrowed or does not exist.\n");
            displayArea.getChildren().add(errorText);
        }
    }

    // Helper method to find a book by its ID
    private Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book; // Return book if found
            }
        }
        return null; // Return null if book is not found
    }
}
