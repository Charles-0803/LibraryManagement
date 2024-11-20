package com.project.librarymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    // This is the entry point for the JavaFX application.
    // The 'start' method is overridden to set up the main stage (window) of the application.
    @Override
    public void start(Stage stage) throws IOException {
        // Create an FXMLLoader object to load the FXML file for the user interface.
        // This FXML file defines the layout of the scene and the associated controls.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Library.fxml"));

        // Load the FXML file and create the Scene object that will hold the graphical components.
        // The scene is responsible for displaying the layout to the user.
        Scene scene = new Scene(fxmlLoader.load());

        // Set the title of the window (Stage).
        stage.setTitle("Library Management");

        // Set the scene (which is the UI layout) to the stage.
        stage.setScene(scene);

        // Show the stage (window) to the user, making it visible on the screen.
        stage.show();
    }

    // The 'main' method is the entry point for the Java application.
    // It launches the JavaFX application, which internally calls the 'start' method.
    public static void main(String[] args) {
        launch(); // Launches the JavaFX application
    }
}
