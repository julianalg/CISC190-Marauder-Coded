package co.ppj2.controllers;

import co.ppg2.model.Player;
import java.io.*;
import java.util.ArrayList;

public class PlayerDataController {

    private static final String FILE_NAME = "players.dat";

    public static void savePlayers(ArrayList<Player> players) {
        // TODO: Check if players list is null or empty before attempting to save to file.
        if (players == null || players.isEmpty()) {
            System.out.println("No players to save."); // Add a message or log this scenario.
            return; // Exit the method if there's nothing to save.
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(players); // TODO: Add error handling for serialization issues (e.g., Player class not serializable).
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Implement better error handling, possibly logging the error to a file or displaying a user-friendly message.
        }
    }

    public static ArrayList<Player> loadPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        // TODO: Check if the file exists before attempting to load from it to avoid FileNotFoundException.
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No player data file found. Returning empty list.");
            return players; // Return empty list if the file doesn't exist.
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            players = (ArrayList<Player>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // TODO: Implement better error handling, such as notifying the user or retrying.
        }
        // TODO: Validate the loaded players list to ensure it contains valid Player objects.
        return players;
    }
}


