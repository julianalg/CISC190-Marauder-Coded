package edu.sdccd.cisc190.services;

import edu.sdccd.cisc190.players.HumanPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

@SuppressWarnings("LoggingSimilarMessage")
public class PlayerSavesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerSavesService.class);

    /*
     * TODO: This method is responsible for saving the player's name and money to a file when they quit the game.
     * The file is called player_data.txt.
     */
    public static void saveState() {
        HumanPlayer player = HumanPlayer.getInstance();
        String data = "Username: " + player.getName() + ", Money: $" + player.getMoney();

        try {
            // TODO: Check if the file already exists. If it does, delete it before writing new data.
            File file = new File("player_data.txt");
            if (file.exists()) {
                if (!file.delete()) {
                    LOGGER.error("Failed to delete existing player_data.txt file.");
                    return;
                }
            }

            // TODO: Write the new data to the file (username and money) so it is saved.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(data);
                writer.newLine();
            }

        } catch (IOException e) {
            LOGGER.error("Error saving player data.", e);
        }
    }

    /*
     * TODO: This method loads the player's saved data from the file when the game starts. 
     * If the file exists, it sets the username and money for the player.
     */
    public static boolean loadState() {
        File file = new File("player_data.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine(); // TODO: Read the first line of the file
                if (line != null) {
                    // TODO: Split the line to get the username and money and save them for the player
                    String[] data = line.split(", ");
                    String username = data[0].split(": ")[1];
                    int money = Integer.parseInt(data[1].split(": ")[1].replace("$", ""));

                    HumanPlayer player = HumanPlayer.getInstance();
                    player.setUsername(username);
                    player.setMoney(money);

                    return true; // TODO: Return true if the data was successfully loaded.
                }
            } catch (IOException | NumberFormatException e) {
                LOGGER.error("Error reading player data", e); // TODO: Handle the error if the file can't be read.
            }
        }
        return false; // TODO: Return false if the file doesn't exist or if loading fails.
    }

    /*
     * TODO: This method deletes the player's saved data if they want to start fresh. 
     * It deletes the player_data.txt file.
     */
    public static void deleteState() {
        File file = new File("player_data.txt");
        if (file.exists()) {
            // TODO: Try to delete the file and handle if it fails.
            if (!file.delete()) {
                LOGGER.error("Failed to delete existing player_data.txt file.");
            }
        }
    }
}
