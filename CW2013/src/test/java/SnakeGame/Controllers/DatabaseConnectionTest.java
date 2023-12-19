package SnakeGame.Controllers;

import SnakeGame.Models.PlayerModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private static final String TEST_FILE_PATH = "test_leaderboard.xlsx";

    @BeforeEach
    void setUp() {
        // Set the test file path before each test
        DatabaseConnection.filePath = TEST_FILE_PATH;
    }

    @AfterEach
    void tearDown() {
        // Delete the test file after each test
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateLeaderboard() {
        try {
            boolean fileExists = DatabaseConnection.createLeaderboard();
            assertFalse(fileExists, "Leaderboard file should not exist before creation");

            assertTrue(Files.exists(Paths.get(TEST_FILE_PATH)), "Leaderboard file should be created");
        } catch (IOException e) {
            fail("Exception thrown while creating leaderboard: " + e.getMessage());
        }
    }

    @Test
    void testUpdateLeaderboard() {
        try {
            // Create leaderboard file
            DatabaseConnection.createLeaderboard();

            // Update leaderboard
            DatabaseConnection.updateLeaderboard("Player1", 100);

            // Read players from leaderboard
            List<PlayerModel> players = DatabaseConnection.readPlayersFromLeaderboard();

            assertNotNull(players, "Player list should not be null");
            assertEquals(1, players.size(), "There should be one player in the leaderboard");

            PlayerModel player = players.get(0);
            assertEquals("Player1", player.getPlayerName(), "Player name should match");
            assertEquals(100, player.getScore(), "Player score should match");

        } catch (IOException e) {
            fail("Exception thrown while updating leaderboard: " + e.getMessage());
        }
    }

    @Test
    void testReadPlayersFromLeaderboard() {
        try {
            // Create leaderboard file
            DatabaseConnection.createLeaderboard();

            // Update leaderboard
            DatabaseConnection.updateLeaderboard("Player1", 100);
            DatabaseConnection.updateLeaderboard("Player2", 150);

            // Read players from leaderboard
            List<PlayerModel> players = DatabaseConnection.readPlayersFromLeaderboard();

            assertNotNull(players, "Player list should not be null");
            assertEquals(2, players.size(), "There should be two players in the leaderboard");

            PlayerModel player1 = players.get(0);
            assertEquals("Player1", player1.getPlayerName(), "Player1 name should match");
            assertEquals(100, player1.getScore(), "Player1 score should match");

            PlayerModel player2 = players.get(1);
            assertEquals("Player2", player2.getPlayerName(), "Player2 name should match");
            assertEquals(150, player2.getScore(), "Player2 score should match");

        } catch (IOException e) {
            fail("Exception thrown while reading players from leaderboard: " + e.getMessage());
        }
    }
}
