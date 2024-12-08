package edu.sdccd.cisc190.services;

import edu.sdccd.cisc190.players.HumanPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PlayerSavesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerSavesService.class);
    private static final String SAVE_FILE = "player_data.txt"; // Constant for the save file name

    /*
     * Saves the user's name and money into a player_data.txt file on quit to persist their progress
     */
    public static void saveState() {
        HumanPlayer player = HumanPlayer.getInstance();
        String data = String.format("Username: %s, Money: $%d", player.getName(), player.getMoney());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(data);
            writer.newLine();
            LOGGER.info("Player data saved: {}", data);
        } catch (IOException e) {
            LOGGER.error("Error saving player data.", e);
        }
    }

    /*
     * Loads user data from player_data.txt file if available on game open
     * Returns true if the data is successfully loaded, false otherwise
     */
    public static boolean loadState() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            LOGGER.warn("No previous player data found.");
            return false; // File does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = parsePlayerData(line);
                if (data != null) {
                    HumanPlayer player = HumanPlayer.getInstance();
                    player.setUsername(data[0]);
                    player.setMoney(Integer.parseInt(data[1]));
                    LOGGER.info("Player data loaded: Username = {}, Money = ${}", data[0], data[1]);
                    return true;
                }
            }
        } catch (IOException | NumberFormatException e) {
            LOGGER.error("Error reading player data", e);
        }
        return false; // Data could not be loaded due to format or reading error
    }

    /*
     * Deletes user's information in player_data.txt if available
     */
    public static void deleteState() {
        File file = new File(SAVE_FILE);
        if (file.exists() && file.delete()) {
            LOGGER.info("Player data file deleted.");
        } else {
            LOGGER.warn("Failed to delete player data file or file does not exist.");
        }
    }

    /*
     * Helper method to parse player data from the file
     */
    private static String[] parsePlayerData(String data) {
        try {
            String[] parts = data.split(", ");
            if (parts.length == 2) {
                String username = parts[0].split(": ")[1];
                String money = parts[1].split(": ")[1].replace("$", "");
                return new String[]{username, money};
            }
        } catch (Exception e) {
            LOGGER.error("Error parsing player data: {}", data, e);
        }
        return null; // Return null if parsing fails
    }
}
