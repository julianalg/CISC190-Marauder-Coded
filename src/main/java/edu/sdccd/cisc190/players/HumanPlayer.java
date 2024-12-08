package edu.sdccd.cisc190.players;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * initializes and defines the attributes of a human player i.e. username and money
 * Create an instance of a human player for easier implementation to functionality of application
 * use getters and setters to obtain and update the value of the human player's money, both on the backend and in JavaFX
 */
public class HumanPlayer {
    private static HumanPlayer instance;
    private String username;
    private final IntegerProperty money = new SimpleIntegerProperty(this, "money", 1000);

    private HumanPlayer() {}

    public static HumanPlayer getInstance() {
        // TODO: Chek if using singleton pattern is necessary for this class

        if (instance == null) {
            instance = new HumanPlayer();
        }
        return instance;
    }

    public void setUsername(String username) {
        // TODO: Add validation to ensure username is not null or empty
        this.username = username;
    }

    public final void setMoney(int value) {
        // TODO: Ensure money value cannot be negative
        money.set(value);
    }

    public final int getMoney() {
        return money.get();
    }

    public IntegerProperty moneyProperty() {
        // TODO: Document why money is an IntegerProperty (used in JavaFX bindings)
        return money;
    }

    public String getName() {
        // TODO: Consider renaming this method to "getUsername" for consistency
        return username;
    }
}
