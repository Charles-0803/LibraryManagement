module com.project.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.librarymanagement to javafx.fxml;
    exports com.project.librarymanagement;
}