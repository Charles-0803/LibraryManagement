module com.project.librarymanagement {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires dotenv.java;


    opens com.project.librarymanagement to javafx.fxml;
    exports com.project.librarymanagement;
}