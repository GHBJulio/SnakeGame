package SnakeGame.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerModelTest {

    @Test
    void testGetPlayerName() {
        String playerName = "John";
        int score = 100;
        PlayerModel player = new PlayerModel(playerName, score);

        assertEquals(playerName, player.getPlayerName());
    }

    @Test
    void testGetScore() {
        String playerName = "Alice";
        int score = 150;
        PlayerModel player = new PlayerModel(playerName, score);

        assertEquals(score, player.getScore());
    }

    @Test
    void testConstructor() {
        String playerName = "Bob";
        int score = 200;
        PlayerModel player = new PlayerModel(playerName, score);

        assertEquals(playerName, player.getPlayerName());
        assertEquals(score, player.getScore());
    }

    // Add more tests as needed

}
