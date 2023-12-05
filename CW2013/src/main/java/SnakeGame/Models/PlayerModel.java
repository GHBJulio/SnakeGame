package SnakeGame.Models;

public class PlayerModel {
    private String playerName;
    private int score;

    public PlayerModel(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}