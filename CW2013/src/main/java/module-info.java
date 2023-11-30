module CW2013 {
    opens SnakeGame;
    opens SnakeGame.Models;
    opens SnakeGame.Controllers;
    requires java.desktop;
    //requires jlayer;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
}