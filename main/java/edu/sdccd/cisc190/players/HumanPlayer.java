package edu.sdccd.cisc190.players;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Defines the human player's attributes, such as username and money.
 * Implements the Singleton pattern to ensure only one instance of the human player is used.
 * Allows for easy management of the human player's money and username.
 */
public class HumanPlayer {
    // Constant for the initial money value
    private static final int INITIAL_MONEY = 1000;

    // Singleton instance
    private static final HumanPlayer instance = new HumanPlayer();

    // Instance-specific attributes
    private String username;
    private final IntegerProperty money = new SimpleIntegerProperty(this, "money", INITIAL_MONEY);

    /**
     * Private constructor to enforce Singleton pattern.
     * This is used to initialize the human player's state.
     */
    private HumanPlayer() {}

    /**
     * Provides access to the singleton instance of HumanPlayer.
     *
     * @return The single instance of HumanPlayer.
     */
    public static HumanPlayer getInstance() {
        return instance;
    }

    /**
     * Sets the username for the human player.
     *
     * @param username The name to set for the player.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the amount of money the human player has.
     *
     * @param value The amount of money to set for the player.
     */
    public final void setMoney(int value) {
        money.set(value);
    }

    /**
     * Gets the amount of money the human player currently has.
     *
     * @return The amount of money the player has.
     */
    public final int getMoney() {
        return money.get();
    }

    /**
     * Provides the money property for binding with JavaFX UI elements.
     *
     * @return The IntegerProperty representing the player's money.
     */
    public IntegerProperty moneyProperty() {
        return money;
    }

    /**
     * Gets the name of the human player.
     *
     * @return The username of the player.
     */
    public String getName() {
        return username;
    }

    /**
     * Returns a string representation of the HumanPlayer object.
     * Useful for debugging and logging.
     *
     * @return A string representing the player's name and money.
     */
    @Override
    public String toString() {
        return String.format("HumanPlayer{name='%s', money=%d}", getName(), getMoney());
    }
}
