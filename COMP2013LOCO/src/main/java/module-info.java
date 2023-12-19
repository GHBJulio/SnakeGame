/**
 * The {@code CW2013} module is the main module for the SnakeGame application.
 * It specifies the packages that are opened to reflection and declares dependencies on various Java and third-party modules.
 */
module CW2013 {
    /**
     * Opens the package {@code SnakeGame} to allow reflective access.
     */
    opens SnakeGame;

    /**
     * Opens the package {@code SnakeGame.Models} to allow reflective access.
     */
    opens SnakeGame.Models;

    /**
     * Opens the package {@code SnakeGame.Controllers} to allow reflective access.
     */
    opens SnakeGame.Controllers;

    /**
     * Requires the Java Desktop module for AWT and Swing functionality.
     */
    requires java.desktop;

    /**
     * Requires the Java SQL module for database connectivity.
     */
    requires java.sql;

    /**
     * Requires the JavaFX Graphics module for graphical user interface.
     */
    requires javafx.graphics;

    /**
     * Requires the JavaFX Controls module for user interface controls.
     */
    requires javafx.controls;

    /**
     * Requires the JavaFX Media module for multimedia support.
     */
    requires javafx.media;

    /**
     * Requires the JavaFX FXML module for XML-based user interface design.
     */
    requires javafx.fxml;

    /**
     * Requires the Apache POI module for reading and writing Microsoft Office documents.
     */
    requires poi;

    /**
     * Requires the Apache POI OOXML module for reading and writing Office Open XML documents.
     */
    requires poi.ooxml;
}
