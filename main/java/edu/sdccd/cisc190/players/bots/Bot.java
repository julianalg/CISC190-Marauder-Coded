package edu.sdccd.cisc190.players.bots;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class defining the behavior of bots playing in the background.
 * Bots have attributes such as name, money, luck, and aura, which determine their gameplay behavior.
 *
 * @version 1.0
 */
public abstract class Bot {

    // Bot attributes
    private final String name;  // Name of the bot
    private final IntegerProperty money = new SimpleIntegerProperty(); // Current money of the bot
    private final double luck;  // Probability of winning
    private final double aura;  // Influence on bet size or other gameplay aspects

    /**
     * Constructor for creating a Bot instance.
     *
     * @param name The name of the bot.
     * @param initialMoney The initial money of the bot (must be non-negative).
     * @param luck The luck of the bot, representing a probability [0.0, 1.0].
     * @param aura The aura of the bot, influencing behavior (range: [0.0, 1.0]).
     * @throws IllegalArgumentException if any parameter is out of range.
     */
    public Bot(String name, int initialMoney, double luck, double aura) {
        if (initialMoney < 0) {
            throw new IllegalArgumentException("Initial money cannot be negative.");
        }
        if (luck < 0.0 || luck > 1.0) {
            throw new IllegalArgumentException("Luck must be in the range [0.0, 1.0].");
        }
        if (aura < 0.0 || aura > 1.0) {
            throw new IllegalArgumentException("Aura must be in the range [0.0, 1.0].");
        }

        this.name = name;
        this.money.set(initialMoney);
        this.luck = luck;
        this.aura = aura;
    }

    /**
     * Gets the name of the bot.
     *
     * @return The bot's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current money of the bot.
     *
     * @return The bot's current money.
     */
    public int getMoney() {
        return money.get();
    }

    /**
     * Sets the current money of the bot.
     *
     * @param value The new money value (must be non-negative).
     * @throws IllegalArgumentException if the value is negative.
     */
    public void setMoney(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Money cannot be negative.");
        }
        money.set(value);
    }

    /**
     * Gets the money property for JavaFX bindings.
     *
     * @return The money property.
     */
    public IntegerProperty moneyProperty() {
        return money;
    }

    /**
     * Gets the bot's luck.
     *
     * @return The bot's luck value (range: [0.0, 1.0]).
     */
    public double getLuck() {
        return luck;
    }

    /**
     * Gets the bot's aura.
     *
     * @return The bot's aura value (range: [0.0, 1.0]).
     */
    public double getAura() {
        return aura;
    }

    /**
     * Provides a string representation of the bot.
     *
     * @return A string describing the bot's attributes.
     */
    @Override
    public String toString() {
        return String.format("Bot{name='%s', money=%d, luck=%.2f, aura=%.2f}", name, getMoney(), luck, aura);
    }
}
