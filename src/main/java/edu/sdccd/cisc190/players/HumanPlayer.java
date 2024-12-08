package edu.sdccd.cisc190.players;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Initializes and defines the attributes of a human player, like username and money.
 * Create an instance of a human player for easier implementation to functionality of the application.
 * Use getters and setters to obtain and update the value of the human player's money, both on the backend and in JavaFX.
 */
public class HumanPlayer {
    // TODO: Keep track of one instance of HumanPlayer (Singleton pattern)
    private static HumanPlayer instance;
    
    // TODO: Declare a variable for the player's username.
    private String username;
    
    // TODO: Declare an IntegerProperty for the player's money to support JavaFX binding.
    private final IntegerProperty money = new SimpleIntegerProperty(this, "money", 1000);

    // TODO: Constructor is private so no one can directly create a new HumanPlayer object. This is part of the Singleton pattern.
    private HumanPlayer() {}

    // TODO: This method returns the single instance of HumanPlayer.
    public static HumanPlayer getInstance() {
        // TODO: If instance is not created yet, create it.
        if (instance == null) {
            instance = new HumanPlayer();
        }
        // TODO: Return the created instance.
        return instance;
    }

    // TODO: Set the username of the player.
    public void setUsername(String username) {
        this.username = username;
    }

    // TODO: Set the player's money to the value passed in. This is an update to the money property.
    public final void setMoney(int value) {
        money.set(value);
    }

    // TODO: Get the current amount of money the player has.
    public final int getMoney() {
        return money.get();
    }

    // TODO: Return the IntegerProperty of money to allow JavaFX to bind it to UI elements.
    public IntegerProperty moneyProperty() {
        return money;
    }

    // TODO: Get the player's username.
    public String getName() {
        return username;
    }
}
