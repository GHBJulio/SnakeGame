package SnakeGame.Controllers;

import SnakeGame.Models.PlayerModel;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the connection to the local leaderboard.
 * It provides methods for creating, updating, and reading player data from the leaderboard.
 *
 * @author Guilherme Julio
 */
public class DatabaseConnection {

    /**
     * Default constructor for the DatabaseConnection class.
     */
    public DatabaseConnection() {
    }

    /** The path to the local leaderboard file. */
    public static String filePath = "local_leaderboard.xlsx";

    /** The name of the sheet in the leaderboard file. */
    public static String SHEET_NAME = "Leaderboard";

    /**
     * Creates the leaderboard file if it doesn't exist.
     *
     * @return True if the leaderboard file already exists, false if it was created.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean createLeaderboard() throws IOException {
        Path path = Paths.get(filePath);

        // Check if the file already exists
        if (Files.exists(path)) {
            System.out.println("File already exists: " + filePath);
            return true;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        // Create headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Player Name");
        headerRow.createCell(1).setCellValue("Score");

        // Save the workbook
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        workbook.close();

        System.out.println("File created successfully: " + filePath);
        return false;
    }

    /**
     * Updates the leaderboard with the provided player name and score.
     *
     * @param playerName The name of the player.
     * @param score The player's score.
     * @throws IOException If an I/O error occurs.
     */
    public static void updateLeaderboard(String playerName, int score) throws IOException {
        // Check if the file exists
        boolean fileExists = new File(filePath).exists();

        // Create new workbook if the file doesn't exist
        Workbook workbook = null;
        if (!fileExists) {
            createLeaderboard();
            System.out.println("ERROR WORKBOOK DOESN'T EXIST");
        } else {
            // If file exists, read data from the file
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        }
        Sheet sheet = workbook.getSheet(SHEET_NAME);

        // Find the last row number with data
        int lastRowNum = sheet.getPhysicalNumberOfRows();
        System.out.println(lastRowNum);

        // Create a new row right after the last row
        Row row = sheet.createRow(lastRowNum++);

        // Set player name and score
        row.createCell(0).setCellValue(playerName);
        row.createCell(1).setCellValue(score);

        // Save the workbook with append mode
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    /**
     * Reads the player data from the leaderboard.
     *
     * @return A list of PlayerModel objects representing the players on the leaderboard.
     * @throws IOException If an I/O error occurs.
     */
    public static List<PlayerModel> readPlayersFromLeaderboard() throws IOException {
        List<PlayerModel> players = new ArrayList<>();

        Path path = Paths.get(filePath);

        // Check if the file already exists
        if (!Files.exists(path)) {
            System.out.println("File Doesn't exist: " + filePath);
            return null;
        }

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);

            if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
                // No data or only header row found
                showAlert("Leaderboard not available yet.");
                throw new IOException("Leaderboard is empty or not available yet.");
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }

                String playerName = row.getCell(0).getStringCellValue();
                int score = (int) row.getCell(1).getNumericCellValue();

                players.add(new PlayerModel(playerName, score));
            }
        }

        return players;
    }
    /**
     * Shows an information alert with the specified message.
     *
     * @param message The message to display in the alert.
     */
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
