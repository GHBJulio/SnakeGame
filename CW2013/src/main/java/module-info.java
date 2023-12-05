module CW2013 {
    opens SnakeGame;
    opens SnakeGame.Models;
    opens SnakeGame.Controllers;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires poi;
    requires poi.ooxml;
}