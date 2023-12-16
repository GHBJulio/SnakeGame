package SnakeGame.Models;

/**
 * The PlayerModel class represents a player in the Snake Game, storing information
 * such as the player's name and score. It provides methods to retrieve the player's
 * name and score.
 *
 * @author Guilherme Julio
 */
public class PlayerModel {
    /** The name of the player. */
    private String playerName;

    /** The score achieved by the player. */
    private int score;

    /**
     * Constructs a PlayerModel with the specified player name and score.
     *
     * @param playerName The name of the player.
     * @param score      The score achieved by the player.
     */
    public PlayerModel(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the score achieved by the player.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
}
